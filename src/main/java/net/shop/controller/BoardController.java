package net.shop.controller;

import net.shop.error.BoardNotFoundException;
import net.shop.service.BoardService;
import net.shop.service.GoodsService;
import net.shop.service.UserService;
import net.shop.util.Util;
import net.shop.vo.BoardVO;
import net.shop.vo.PagingVO;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.text.DecimalFormat;
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

    @Resource(name = "boardService")
    private BoardService boardService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "goodsService")
    private GoodsService goodsService;

    @Resource(name = "util")
    private Util util;

    /*
    게시판 리스트
     */
    @RequestMapping(value = "/list.do")
    public ModelAndView boardList(@RequestParam(value = "p", required = false) Integer requestPage,
                                  @RequestParam(value = "s", required = false) String separator) throws Exception{

        ModelAndView modelAndView = new ModelAndView();

        if(requestPage == null) requestPage = 1;
        if(requestPage <= 0) return (ModelAndView)new ModelAndView("redirect:/board/list.do?s=" + separator);

        int totalCount;
        if(separator  == null || separator.equals(""))  {
            totalCount = boardService.selectCount();
        } else {
            totalCount = boardService.selectCount(separator);
        }

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
        if(separator  == null || separator.equals(""))  {
            boardVOList = boardService.selectList(pagingVO.getFirstRow(), pagingVO.getEndRow());
        } else {
            boardVOList = boardService.selectList(pagingVO.getFirstRow(), pagingVO.getEndRow(), separator);
        }

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
    @RequestMapping(value = "/write.do", method = RequestMethod.POST)
    public String boardWrite(@RequestParam(value = "title", required = true) String title,
                             @RequestParam(value = "daumeditor", required = true ) String daumeditor,
                             @RequestParam(value = "s", required = true, defaultValue = "default") String separator,
                             Authentication auth, HttpServletRequest request, Model model) throws Exception{

    	
        String memberId = util.isMemberId(auth);

        int groupId = boardService.generateNextGroupNumber("board");
        int userNumber = userService.selectOneNo(memberId);

        BoardVO boardVO = new BoardVO();
        boardVO.setGroupNumber(groupId);
        DecimalFormat decimalFormat = new DecimalFormat("0000000000");
        boardVO.setSequenceNumber(decimalFormat.format(groupId) + "99");
        boardVO.setTitle(title);
        boardVO.setContent(daumeditor);
        boardVO.setUserNumber(userNumber);
        boardVO.setUserEmail(memberId);
        boardVO.setSeparatorName(separator);

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

        modelAndView.addObject("boardVO", boardVO);
        modelAndView.setViewName("/board/updateForm");
        return modelAndView;
    }

    /*
    게시판 수정
     */
    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    public String boardUpdate(@RequestParam(value = "boardNumber", required = true) Integer boardNumber,
                              @RequestParam(value = "title", required = true) String title,
                              @RequestParam(value = "daumeditor", required = true ) String daumeditor,
                              @RequestParam(value = "s", required = false) String separator,
                              @RequestParam(value = "p", required = false) String page,
                              Authentication auth) throws Exception {

        String memberId = util.isMemberId(auth);

        BoardVO boardVO = boardService.selectOne(boardNumber);
        if(boardVO == null) throw new BoardNotFoundException("게시글이 존재하지 않음 : " + boardNumber);

        util.isEqualMemberId(boardVO.getUserEmail(), memberId);

        boardVO = new BoardVO();
        boardVO.setNumber(boardNumber);
        boardVO.setTitle(title);
        boardVO.setContent(daumeditor);

        int updateCount = boardService.update(boardVO);
        if (updateCount == 0) {
            throw new BoardNotFoundException("게시글이 존재하지 않음 : "+ boardNumber);
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
