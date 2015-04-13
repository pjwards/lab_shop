package net.shop.controller;

import net.shop.error.BoardNotFoundException;
import net.shop.service.BoardService;
import net.shop.service.UserService;
import net.shop.util.Util;
import net.shop.vo.BoardVO;
import net.shop.vo.PagingVO;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.DecimalFormat;
import java.util.Collections;
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

    @Resource(name = "util")
    private Util util;

    /*
    게시판 리스트
     */
    @RequestMapping(value = "/list.do")
    public ModelAndView boardList(HttpServletRequest request, HttpServletResponse response) throws Exception{
        ModelAndView modelAndView = new ModelAndView();

        /*
        웹 브라우저가 게시글 목록을 캐싱하지 않도록 캐시 관련 헤더를 설정
         */
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.addHeader("Cache-Control", "no-store");
        response.setDateHeader("Expire", 1L);

        String requestPageString = request.getParameter("p");
        String separatorName = request.getParameter("s");

        if(requestPageString == null || requestPageString.equals("")) {
            requestPageString = "1";
        }

        int requestPage = Integer.parseInt(requestPageString);

        if(requestPage <= 0){
            return (ModelAndView)new ModelAndView("redirect:/board/list.do?s=" + request.getParameter("s"));
        }

        int totalCount;
        if(separatorName  == null || separatorName.equals(""))  {
            totalCount = boardService.selectCount();
        } else {
            totalCount = boardService.selectCount(separatorName);
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
            modelAndView.addObject("hasBoard", new Boolean(false));
            return modelAndView;
        }

        List<BoardVO> boardVOList;
        if(separatorName  == null || separatorName.equals(""))  {
            boardVOList = boardService.selectList(pagingVO.getFirstRow(), pagingVO.getEndRow());
        } else {
            boardVOList = boardService.selectList(pagingVO.getFirstRow(), pagingVO.getEndRow(), separatorName);
        }

        modelAndView.addObject("boardVOList", boardVOList);
        modelAndView.addObject("hasBoard", new Boolean(true));

        return modelAndView;
    }

    /*
    게시판 글쓰기 폼
     */
    @RequestMapping(value = "/write.do")
    public ModelAndView boardWrite(HttpServletRequest request, Authentication auth) throws Exception{

        UserDetails vo = (UserDetails) auth.getPrincipal();
        String memberId = vo.getUsername();
        util.isMemberId(memberId);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/board/writeForm");
        return modelAndView;
    }

    /*
    게시판 글쓰기
     */
    @RequestMapping(value = "/write.do", method = RequestMethod.POST)
    public ModelAndView boardWrite(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws Exception{

    	UserDetails vo = (UserDetails) auth.getPrincipal();
        String memberId = vo.getUsername();
        util.isMemberId(memberId);

        int groupId = boardService.generateNextGroupNumber("board");
        int userNumber = userService.selectOneNo(memberId);

        BoardVO boardVO = new BoardVO();
        boardVO.setGroupNumber(groupId);
        DecimalFormat decimalFormat = new DecimalFormat("0000000000");
        boardVO.setSequenceNumber(decimalFormat.format(groupId) + "99");
        boardVO.setTitle(request.getParameter("title"));
        boardVO.setContent(request.getParameter("content"));
        boardVO.setUserNumber(userNumber);
        boardVO.setUserEmail(memberId);
        boardVO.setSeparatorName(request.getParameter("s"));

        boardService.insert(boardVO);

        /*
        글쓰기 완료 페이지 구현을 위한 부분
         */
        //int boardNumber = boardService.selectLastBoardNumberByEmail(request.getParameter("memberId"));
        //boardVO.setNumber(boardNumber);
        //request.setAttribute("postedBoardVO", boardVO);
        //return "/board/write";

        return (ModelAndView)new ModelAndView("redirect:/board/list.do?s=" + request.getParameter("s"));
    }

    /*
    게시판 읽기
     */
    @RequestMapping(value = "/read.do")
    public ModelAndView boardRead(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        /*
        웹 브라우저가 게시글 목록을 캐싱하지 않도록 캐시 관련 헤더를 설정
         */
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.addHeader("Cache-Control", "no-store");
        response.setDateHeader("Expire", 1L);

        int boardNumber = Integer.parseInt(request.getParameter("boardNumber"));

        BoardVO boardVO = boardService.selectOne(boardNumber);

        if(boardVO == null){
            throw new BoardNotFoundException("게시글이 존재하지 않음 : " + boardNumber);
        }

        boardService.increaseReadCount(boardNumber);
        boardVO.setReadCount(boardVO.getReadCount() + 1);

        request.setAttribute("boardVO", boardVO);
        //modelAndView.setViewName("/board/read");

        return (ModelAndView)new ModelAndView("forward:/comment/listAll.do?s=" + request.getParameter("s")
                +"&p=" + request.getParameter("p") +"&boardNumber=" + request.getParameter("boardNumber"));
    }

    /*
    게시판 수정 폼
     */
    @RequestMapping(value = "/update.do")
    public ModelAndView boardUpdate(HttpServletRequest request, Authentication auth) throws Exception{

        ModelAndView modelAndView = new ModelAndView();

        int boardNumber = Integer.parseInt(request.getParameter("boardNumber"));

        UserDetails vo = (UserDetails) auth.getPrincipal();
        String memberId = vo.getUsername();
        util.isMemberId(memberId);

        BoardVO boardVO = boardService.selectOne(boardNumber);
        if(boardVO == null){
            throw new BoardNotFoundException("게시글이 존재하지 않음 : " + boardNumber);
        }

        util.isEqualMemberId(boardVO.getUserEmail(), memberId);

        modelAndView.addObject("boardVO", boardVO);
        modelAndView.setViewName("/board/updateForm");
        return modelAndView;
    }

    /*
    게시판 수정
     */
    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    public ModelAndView boardUpdate(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws Exception {

        ModelAndView modelAndView = new ModelAndView();

        int boardNumber = Integer.parseInt(request.getParameter("boardNumber"));

        UserDetails vo = (UserDetails) auth.getPrincipal();
        String memberId = vo.getUsername();
        util.isMemberId(memberId);

        BoardVO boardVO = boardService.selectOne(boardNumber);
        if(boardVO == null){
            throw new BoardNotFoundException("게시글이 존재하지 않음 : " + boardNumber);
        }

        util.isEqualMemberId(boardVO.getUserEmail(), memberId);

        String title = request.getParameter("title");
        String content = request.getParameter("content");

        boardVO = new BoardVO();
        boardVO.setNumber(boardNumber);
        boardVO.setTitle(title);
        boardVO.setContent(content);

        int updateCount = boardService.update(boardVO);
        if (updateCount == 0) {
            throw new BoardNotFoundException("게시글이 존재하지 않음 : "+ boardNumber);
        }

        boardVO = boardService.selectOne(boardNumber);

        modelAndView.addObject("boardVO", boardVO);
        //modelAndView.setViewName("/board/update");

        return (ModelAndView)new ModelAndView("redirect:/board/read.do?s=" + request.getParameter("s")
                +"&p=" + request.getParameter("p") +"&boardNumber=" + request.getParameter("boardNumber"));
    }

    /*
    게시판 답글 폼
     */
    @RequestMapping(value = "/reply.do")
    public ModelAndView boardReply(HttpServletRequest request) throws Exception{

        ModelAndView modelAndView = new ModelAndView();

        int parentBoardNumber = Integer.parseInt(request.getParameter("parentBoardNumber"));

        BoardVO parent = boardService.selectOne(parentBoardNumber);

        util.checkParent(parent, parentBoardNumber);

        String searchMaxSeqNum = parent.getSequenceNumber();
        String searchMinSeqNum = util.getSearchMinSeqNum(parent);

        String lastChildSeq = boardService.selectLastSequenceNumber(searchMaxSeqNum, searchMinSeqNum);
        String sequenceNumber = util.getSequenceNumber(parent, lastChildSeq);

        modelAndView.setViewName("/board/replyForm");

        return modelAndView;

    }

    /*
    게시판 답글
     */
    @RequestMapping(value = "/reply.do", method = RequestMethod.POST)
    public ModelAndView boardReply(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws Exception{

        ModelAndView modelAndView = new ModelAndView();

        UserDetails vo = (UserDetails) auth.getPrincipal();
        String memberId = vo.getUsername();
        util.isMemberId(memberId);

        BoardVO boardVO = new BoardVO();
        boardVO.setTitle(request.getParameter("title"));
        boardVO.setContent(request.getParameter("content"));
        int parentBoardNumber = Integer.parseInt(request.getParameter("parentBoardNumber"));

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
        boardVO.setSeparatorName(request.getParameter("s"));

        int boardNumber = boardService.insert(boardVO);
        if(boardNumber == -1){
            throw new RuntimeException("DB 삽입 실패 : " + boardNumber);
        }

        //boardVO.setNumber(boardNumber);
        //modelAndView.addObject("boardVO", boardVO);

        return (ModelAndView)new ModelAndView("redirect:/board/list.do?s=" + request.getParameter("s"));
    }

    /*
    게시판 삭제
     */
    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    public ModelAndView boardDelete(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws Exception {

        int boardNumber = Integer.parseInt(request.getParameter("boardNumber"));

        UserDetails vo = (UserDetails) auth.getPrincipal();
        String memberId = vo.getUsername();
        util.isMemberId(memberId);

        BoardVO boardVO = boardService.selectOne(boardNumber);
        util.isEqualMemberId(boardVO.getUserEmail(), memberId);

        boardService.delete(boardNumber);

        return (ModelAndView)new ModelAndView("redirect:/board/list.do?s=" + request.getParameter("s")
        +"&p=" + request.getParameter("p"));
    }
}
