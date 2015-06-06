package net.shop.controller;

import net.shop.error.BoardNotFoundException;
import net.shop.service.BoardService;
import net.shop.service.GoodsService;
import net.shop.service.UserService;
import net.shop.util.ImageUtil;
import net.shop.util.Util;
import net.shop.vo.BoardVO;
import net.shop.vo.GoodsVO;
import net.shop.vo.PagingVO;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  :
 * Date         : 2015-03-20
 * Description  :
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      :
 */

@Controller
@RequestMapping(value = "/board")
public class BoardController {
	public static final String loadPath = "/home/jisung/git/lab_shop/src/main/webapp/resource/upload/";				

    @Resource(name = "boardService")
    private BoardService boardService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "goodsService")
    private GoodsService goodsService;

    @Resource(name = "util")
    private Util util;

    @Resource(name = "imageUtil")
	private ImageUtil imageUtil;
    /*
    게시판 리스트
     */
    @RequestMapping(value = "/list.do")
    public ModelAndView boardList(@RequestParam(value = "p", required = false) Integer requestPage,
                                  @RequestParam(value = "s", required = false) String separator,
                                  @RequestParam(value="q",required=false) String keyword) throws Exception{

        ModelAndView modelAndView = new ModelAndView();

        if(requestPage == null) requestPage = 1;
        if(requestPage <= 0) return (ModelAndView)new ModelAndView("redirect:/board/list.do?s=" + separator);

        if(separator == null || separator.equals("")) {
            separator = null;
        }

        if(keyword == null || keyword.equals("")) {
            keyword = null;
        }

        int totalCount;

//        if(separator  == null || separator.equals(""))  {
//            totalCount = boardService.selectCount();
//        } else {
//            totalCount = boardService.selectCount(separator);
//        }

        totalCount = boardService.selectCount(separator, keyword);

        /*Paging 메소드의 사용 */
        PagingVO pagingVO = util.paging(requestPage, 10, totalCount);
        modelAndView.addObject("pagingVO", pagingVO);
        modelAndView.setViewName("/board/list");

        /*
        게시글이 없을 때 수행된다.
         */
        if(totalCount == 0){
            modelAndView.addObject("boardVOList", Collections.<BoardVO>emptyList());
            modelAndView.addObject("hasBoard", false);
            return modelAndView;
        }

        List<BoardVO> boardVOList;
//        if(separator  == null || separator.equals(""))  {
//            boardVOList = boardService.selectList(pagingVO.getFirstRow(), pagingVO.getEndRow());
//        } else {
//            boardVOList = boardService.selectList(pagingVO.getFirstRow(), pagingVO.getEndRow(), separator);
//        }

        boardVOList = boardService.selectList(pagingVO.getFirstRow(), pagingVO.getEndRow(), separator, keyword);


        modelAndView.addObject("boardVOList", boardVOList);
        modelAndView.addObject("hasBoard", true);

        return modelAndView;
    }

    /*
    게시판 글쓰기 폼
     */
    @RequestMapping(value = "/write.do")
    public ModelAndView boardWrite(Authentication auth) throws Exception{

        ModelAndView modelAndView = new ModelAndView();
        util.isMemberId(auth);

        modelAndView.addObject("act", "write");
        modelAndView.setViewName("/board/writeForm");

        return modelAndView;
    }

    /*
    게시판 글쓰기
     */
    @SuppressWarnings("static-access")
	@RequestMapping(value = "/write.do", method = RequestMethod.POST)
    public String boardWrite(@RequestParam(value = "title", required = true) String title,
                             @RequestParam(value = "daumeditor", required = true ) String daumeditor,
                             @RequestParam(value = "thumnail") MultipartFile multipartFile,
                             @RequestParam(value = "total_price", required = false ) Integer totalPrice,
                             @RequestParam(value = "s", required = true, defaultValue = "default") String separator,
                             Authentication auth, HttpServletRequest request, Model model) throws Exception{

    	
        String memberId = util.isMemberId(auth);
        
        int groupId = boardService.generateNextGroupNumber("board");
        int userNumber = userService.selectOneNo(memberId);
        String imagePath = null;
        
        //upload thumnail
  		if ( multipartFile.getSize() > 0 ) {
  			String fileName = multipartFile.getOriginalFilename();
  			if ( imageUtil.isImageFile ( fileName))
  			{
  				Calendar cal = Calendar.getInstance();
  				String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length()).toLowerCase();
  				File uploadFile =  File.createTempFile( "/home/jisung/", fileType);
  				multipartFile.transferTo( uploadFile);
  				String tempName =  cal.getTimeInMillis() + "";
  				String replaceName = tempName+"_thum"+ fileType;
  				File thumbnail =  new File (loadPath+replaceName);
  				imageUtil.uploadImage( uploadFile, thumbnail, 100, 100);
  				imagePath = replaceName;
  			}
  			else{
  				model.addAttribute("say", "Wrong Image");
  				model.addAttribute("url", request.getContextPath()+"/board/list.do?s=" + separator);
  				return "/error/alert";
  			}
  		}
      		
        BoardVO boardVO = new BoardVO();
        boardVO.setGroupNumber(groupId);
        DecimalFormat decimalFormat = new DecimalFormat("0000000000");
        boardVO.setSequenceNumber(decimalFormat.format(groupId) + "99");
        boardVO.setTitle(title);
        boardVO.setContent(daumeditor);
        boardVO.setUserNumber(userNumber);
        boardVO.setUserEmail(memberId);
        boardVO.setSeparatorName(separator);
        boardVO.setImagePath(imagePath);
        
        if(separator.equals("product")){
            boardVO.setTotalPrice(totalPrice);
        } else {
            boardVO.setTotalPrice(0);
        }

        boardService.insert(boardVO);
        int boardNumber = boardService.selectLastBoardNumberByEmail(memberId);

        if(separator.equals("product")){
            String products[] = request.getParameterValues("goods");

            if(products != null) {
                for (String product : products) {
                    goodsService.insert(boardNumber, Integer.parseInt(product));
                    goodsService.increaseGoodsCount(boardNumber);
                }
            }
        }

        return "redirect:/board/list.do?s=" + separator;
    }

    /*
    게시판 읽기
     */
    @RequestMapping(value = "/read.do")
    public ModelAndView boardRead(@RequestParam(value = "boardNumber", required = true) Integer boardNumber)
            throws Exception {

        ModelAndView modelAndView = new ModelAndView();

        BoardVO boardVO = boardService.selectOne(boardNumber);

        if(boardVO == null) throw new BoardNotFoundException("게시글이 존재하지 않음 : " + boardNumber);

        boardService.increaseReadCount(boardNumber);
        boardVO.setReadCount(boardVO.getReadCount() + 1);

        modelAndView.addObject("boardVO", boardVO);
        modelAndView.setViewName("/board/read");

        return modelAndView;
    }

    /*
    게시판 수정 폼
     */
    @RequestMapping(value = "/update.do")
    public ModelAndView boardUpdate(HttpServletRequest request, Authentication auth) throws Exception{

        ModelAndView modelAndView = new ModelAndView();

        int boardNumber = Integer.parseInt(request.getParameter("boardNumber"));

        String memberId = util.isMemberId(auth);

        BoardVO boardVO = boardService.selectOne(boardNumber);
        if(boardVO == null) throw new BoardNotFoundException("게시글이 존재하지 않음 : " + boardNumber);

        util.isEqualMemberId(boardVO.getUserEmail(), memberId);

        if(boardVO.getSeparatorName().equals("product")){

            List<Integer> boardGoodsList = goodsService.selectBoardGoodsByBoard(boardNumber);
            List<GoodsVO> goodsVOList = new ArrayList<GoodsVO>();

            if(!boardGoodsList.isEmpty()) {
                for(int goodsNumber: boardGoodsList) {
                    goodsVOList.add(goodsService.selectOne(goodsNumber));
                }

                modelAndView.addObject("goodsVOList", goodsVOList);
                modelAndView.addObject("hasGoods", true);
            } else {
                modelAndView.addObject("goodsVOList", Collections.<GoodsVO>emptyList());
                modelAndView.addObject("hasGoods", false);
            }
        }

        modelAndView.addObject("boardVO", boardVO);
        modelAndView.setViewName("/board/updateForm");
        return modelAndView;
    }

    /*
    게시판 수정
     */
    @SuppressWarnings("static-access")
	@RequestMapping(value = "/update.do", method = RequestMethod.POST)
    public String boardUpdate(@RequestParam(value = "boardNumber", required = true) Integer boardNumber,
                              @RequestParam(value = "title", required = true) String title,
                              @RequestParam(value = "daumeditor", required = true ) String daumeditor,
                              @RequestParam(value = "thumnail",required=false) MultipartFile multipartFile,
                              @RequestParam(value = "total_price", required = false ) Integer totalPrice,
                              @RequestParam(value = "s", required = false) String separator,
                              @RequestParam(value = "p", required = false) String page,
                              Authentication auth, HttpServletRequest request, Model model) throws Exception {

        String memberId = util.isMemberId(auth);
        
        BoardVO boardVO = boardService.selectOne(boardNumber);
        if(boardVO == null) throw new BoardNotFoundException("게시글이 존재하지 않음 : " + boardNumber);

        util.isEqualMemberId(boardVO.getUserEmail(), memberId);

        BoardVO updateBoardVO = new BoardVO();
        String imagePath = updateBoardVO.getImagePath();
        updateBoardVO.setNumber(boardNumber);
        
        //upload thumnail
  		if ( multipartFile.getSize() > 0 ) {
  			String fileName = multipartFile.getOriginalFilename();
  			if ( imageUtil.isImageFile (fileName))
  			{
  				Calendar cal = Calendar.getInstance();
  				String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length()).toLowerCase();
  				File uploadFile =  File.createTempFile( "/home/jisung/", fileType);
  				multipartFile.transferTo( uploadFile);
  				String tempName =  cal.getTimeInMillis() + "";
  				String replaceName = tempName+"_thum"+ fileType;
  				File thumbnail =  new File (loadPath+replaceName);
  				imageUtil.uploadImage( uploadFile, thumbnail, 100, 100);
  				imagePath = replaceName;
  			}
  			else{
  				model.addAttribute("say", "Wrong Image");
  				model.addAttribute("url", request.getContextPath()+"/board/read.do?s=" + separator + "&p=" + page +"&boardNumber=" + boardNumber);
  				return "/error/alert";
  			}
  		}
  		
        if(!boardVO.getTitle().equals(title)) updateBoardVO.setTitle(title);
        if(!boardVO.getContent().equals(daumeditor)) updateBoardVO.setContent(daumeditor);
        if(!boardVO.getContent().equals(imagePath)) updateBoardVO.setContent(imagePath);
        
        if(separator.equals("product")){
            if(updateBoardVO.getTotalPrice()!=totalPrice) updateBoardVO.setTotalPrice(totalPrice);
        }

        int updateCount = boardService.update(updateBoardVO);
        if (updateCount == 0) {
            throw new BoardNotFoundException("게시글이 존재하지 않음 : "+ boardNumber);
        }

        if(separator.equals("product")){
            String products[] = request.getParameterValues("goods");

            if(boardVO.getGoodsCount()>0){
                goodsService.deleteBoardGoodsByBoard(boardNumber);
                boardService.setGoodsCountZero(boardNumber);
            }

            if(products != null) {
                for (String product : products) {
                    goodsService.insert(boardNumber, Integer.parseInt(product));
                    goodsService.increaseGoodsCount(boardNumber);
                }
            }
        }

        return "redirect:/board/read.do?s=" + separator + "&p=" + page +"&boardNumber=" + boardNumber;
    }

    /*
    게시판 답글 폼
     */
    @RequestMapping(value = "/reply.do")
    public ModelAndView boardReply(@RequestParam(value = "parentBoardNumber", required = true) Integer parentBoardNumber)
            throws Exception{

        ModelAndView modelAndView = new ModelAndView();

        BoardVO parent = boardService.selectOne(parentBoardNumber);

        util.checkParent(parent, parentBoardNumber);

        String searchMaxSeqNum = parent.getSequenceNumber();
        String searchMinSeqNum = util.getSearchMinSeqNum(parent);

        String lastChildSeq = boardService.selectLastSequenceNumber(searchMaxSeqNum, searchMinSeqNum);
        String sequenceNumber = util.getSequenceNumber(parent, lastChildSeq);

        modelAndView.addObject("act", "reply");
        modelAndView.setViewName("/board/writeForm");

        return modelAndView;

    }

    /*
    게시판 답글
     */
    @RequestMapping(value = "/reply.do", method = RequestMethod.POST)
    public String boardReply(@RequestParam(value = "parentBoardNumber", required = true) Integer parentBoardNumber,
                             @RequestParam(value = "title", required = true) String title,
                             @RequestParam(value = "daumeditor", required = true ) String daumeditor,
                             @RequestParam(value = "s", required = true, defaultValue = "default") String separator,
                             Authentication auth) throws Exception{

        String memberId = util.isMemberId(auth);

        BoardVO boardVO = new BoardVO();
        boardVO.setTitle(title);
        boardVO.setContent(daumeditor);

        BoardVO parent = boardService.selectOne(parentBoardNumber);

        util.checkParent(parent, parentBoardNumber);

        String searchMaxSeqNum = parent.getSequenceNumber();
        String searchMinSeqNum = util.getSearchMinSeqNum(parent);

        String lastChildSeq = boardService.selectLastSequenceNumber(searchMaxSeqNum, searchMinSeqNum);
        String sequenceNumber = util.getSequenceNumber(parent, lastChildSeq);
        int userNumber = userService.selectOneNo(memberId);

        boardVO.setGroupNumber(parent.getGroupNumber());
        boardVO.setSequenceNumber(sequenceNumber);
        boardVO.setUserNumber(userNumber);
        boardVO.setUserEmail(memberId);
        boardVO.setSeparatorName(separator);

        int boardNumber = boardService.insert(boardVO);
        if(boardNumber == -1){
            throw new RuntimeException("DB 삽입 실패 : " + boardNumber);
        }

        return "redirect:/board/list.do?s=" + separator;
    }

    /*
    게시판 삭제
     */
    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    public String boardDelete(@RequestParam(value = "boardNumber", required = true) Integer boardNumber,
                              @RequestParam(value = "s", required = false) String separator,
                              @RequestParam(value = "p", required = false) String page,
                              Authentication auth) throws Exception {

        String memberId = util.isMemberId(auth);

        BoardVO boardVO = boardService.selectOne(boardNumber);
        util.isEqualMemberId(boardVO.getUserEmail(), memberId);

        if(separator.equals("product")&&boardVO.getGoodsCount()>0){
            goodsService.deleteBoardGoodsByBoard(boardNumber);
        }

        boardService.delete(boardNumber);

        return "redirect:/board/list.do?s=" + separator + "&p=" + page;
    }
}
