package net.shop.controller;

import net.shop.service.BoardService;
import net.shop.service.UserService;
import net.shop.util.Util;
import net.shop.vo.BoardVO;
import net.shop.vo.PagingVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
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
public class BoardController {

    @Resource(name = "boardService")
    private BoardService boardService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "util")
    private Util util;

    //private static final int COUNT_PER_PAGE = 10;

    @RequestMapping(value = "/board/list.do")
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
            modelAndView.setViewName("redirect:/board/list.do");
            return modelAndView;
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

    @RequestMapping(value = "/board/write.do")
    public String boardWrite() throws Exception{
        return "/board/writeForm";
    }

    @RequestMapping(value = "/board/write.do", method = RequestMethod.POST)
    public String boardWrite(HttpServletRequest request, HttpServletResponse response) throws Exception{

        String memberId = request.getParameter("memberId");

        if(memberId == null || memberId.equals("")){
            return "/board/error";
        }

        int groupId = boardService.generateNextGroupNumber("board");
        int userNumber = userService.selectUserNumberByEmail(request.getParameter("memberId"));

        BoardVO boardVO = new BoardVO();
        boardVO.setGroupNumber(groupId);
        DecimalFormat decimalFormat = new DecimalFormat("0000000000");
        boardVO.setSequenceNumber(decimalFormat.format(groupId) + "999999");
        boardVO.setTitle(request.getParameter("title"));
        boardVO.setContent(request.getParameter("content"));
        boardVO.setUserNumber(userNumber);
        boardVO.setUserEmail(request.getParameter("memberId"));
        boardVO.setSeparatorName(request.getParameter("separator"));

        boardService.insert(boardVO);

        /*
        글쓰기 완료 페이지 구현을 위한 부분
         */
        int boardNumber = boardService.selectLastBoardNumberByEmail(request.getParameter("memberId"));
        boardVO.setNumber(boardNumber);
        request.setAttribute("postedBoardVO", boardVO);
        //return "/board/write";

        return "redirect:/board/list.do";
    }

    @RequestMapping(value = "/board/read.do")
    public ModelAndView boardRead(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        /*
        웹 브라우저가 게시글 목록을 캐싱하지 않도록 캐시 관련 헤더를 설정
         */
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.addHeader("Cache-Control", "no-store");
        response.setDateHeader("Expire", 1L);

        String requestPageNumberString = request.getParameter("p");

        int boardNumber = Integer.parseInt(request.getParameter("boardNumber"));

        BoardVO boardVO = boardService.selectOne(boardNumber);

        if(boardVO == null){
            modelAndView.setViewName("/board/error");
            return modelAndView;
        }

        boardService.increaseReadCount(boardNumber);
        boardVO.setReadCount(boardVO.getReadCount() + 1);

        modelAndView.addObject("boardVO", boardVO);
        modelAndView.setViewName("/board/read");

        /*
        댓글 구현부분
         */
        /*
        ListHandler listHandler = new ListHandler();
        listHandler.process(request, response);
        */

        return modelAndView;
    }

}
