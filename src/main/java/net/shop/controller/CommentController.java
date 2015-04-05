package net.shop.controller;

import net.shop.error.BoardNotFoundException;
import net.shop.error.CommentNotFoundException;
import net.shop.service.CommentService;
import net.shop.service.UserService;
import net.shop.util.Util;
import net.shop.vo.CommentVO;
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
import java.util.List;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-03-25
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */

@Controller
public class CommentController {

    @Resource(name = "commentService")
    private CommentService commentService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "util")
    private Util util;

    /*
    댓글 리스트
     */
    @RequestMapping(value = "/comment/listAll.do")
    public ModelAndView commentListAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        /*
        웹 브라우저가 게시글 목록을 캐싱하지 않도록 캐시 관련 헤더를 설정
         */
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.addHeader("Cache-Control", "no-store");
        response.setDateHeader("Expire", 1L);

        String boardNumberString = request.getParameter("boardNumber");

        if(boardNumberString == null || boardNumberString.equals("")) {
            throw new BoardNotFoundException("게시글이 존재하지 않음 : " + boardNumberString);
        }

        int boardNumber = Integer.parseInt(boardNumberString);

        if(boardNumber < 0){
            throw new IllegalArgumentException("board number < 1 : " + boardNumber);
        }

        int totalCount = commentService.selectCount(boardNumber);

        modelAndView.setViewName("/board/read");

        if(totalCount == 0){
            modelAndView.addObject("commentVOList", Collections.<CommentVO>emptyList());
            request.setAttribute("hasComment", new Boolean(false));
            return modelAndView;
        }

        List<CommentVO> commentVOList = commentService.selectList(boardNumber);
        request.setAttribute("commentVOList", commentVOList);
        request.setAttribute("hasComment", new Boolean(true));

        return modelAndView;
    }

    /*
    댓글 리스트
     */
    @RequestMapping(value = "/comment/list.do")
    public ModelAndView commentList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        /*
        웹 브라우저가 댓글 목록을 캐싱하지 않도록 캐시 관련 헤더를 설정
         */
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.addHeader("Cache-Control", "no-store");
        response.setDateHeader("Expire", 1L);

        String requestPageString = request.getParameter("cp");

        if(requestPageString == null || requestPageString.equals("")) {
            requestPageString = "1";
        }

        int requestPage = Integer.parseInt(requestPageString);

        if(requestPage <= 0){
            throw new IllegalArgumentException("requestPage <= 0 : " + requestPage);
        }

        String boardNumberString = request.getParameter("boardNumber");

        if(boardNumberString == null || boardNumberString.equals("")) {
            throw new BoardNotFoundException("게시글이 존재하지 않음 : " + boardNumberString);
        }

        int boardNumber = Integer.parseInt(boardNumberString);

        if(boardNumber < 0){
            throw new IllegalArgumentException("board number < 1 : " + boardNumber);
        }

        int totalCount = commentService.selectCount(boardNumber);

        /*Paging 메소드의 사용 */
        PagingVO pagingVO = util.paging(requestPage, 10, totalCount);
        modelAndView.addObject("pagingVO", pagingVO);
        modelAndView.setViewName("/board/read");

        if(totalCount == 0){
            modelAndView.addObject("commentVOList", Collections.<CommentVO>emptyList());
            request.setAttribute("hasComment", new Boolean(false));
            return modelAndView;
        }

        List<CommentVO> commentVOList = commentService.selectList(boardNumber, pagingVO.getFirstRow(), pagingVO.getEndRow());
        request.setAttribute("commentVOList", commentVOList);
        request.setAttribute("hasComment", new Boolean(true));

        return modelAndView;
    }

    /*
    댓글 글쓰기
     */
    @RequestMapping(value = "/comment/write.do", method = RequestMethod.POST)
    public ModelAndView commentWrite(HttpServletRequest request, HttpServletResponse response) throws Exception{
        /*
        수정 : Member Id 를 세션으로 넣을 경우 수정이 필요함
         */
        String memberId = request.getParameter("memberId");
        util.isMemberId(memberId);

        int groupId = commentService.generateNextGroupNumber("comment");
        int userNumber = userService.selectUserNumberByEmail(memberId);

        CommentVO commentVO = new CommentVO();
        commentVO.setGroupNumber(groupId);
        DecimalFormat decimalFormat = new DecimalFormat("0000000000");
        commentVO.setSequenceNumber(decimalFormat.format(groupId) + "999999");
        commentVO.setContent(request.getParameter("content"));
        commentVO.setUserNumber(userNumber);
        commentVO.setUserEmail(memberId);
        commentVO.setBoardNumber(Integer.parseInt(request.getParameter("boardNumber")));
        commentVO.setSeparatorName(request.getParameter("s"));

        commentService.insert(commentVO);
        commentService.increaseCommentCount(Integer.parseInt(request.getParameter("boardNumber")));

        return (ModelAndView)new ModelAndView("redirect:/board/read.do?s=" + request.getParameter("s")
                +"&p=" + request.getParameter("p") +"&boardNumber=" + request.getParameter("boardNumber"));
    }

    /*
    댓글 읽기
     */
    public CommentVO commentRead(int commentNumber) throws Exception {

        CommentVO commentVO = commentService.selectOne(commentNumber);

        if(commentVO == null){
            throw new CommentNotFoundException("댓글이 존재하지 않음 : " + commentNumber);
        }

        return commentVO;
    }

    /*
    댓글 수정 폼
     */
    @RequestMapping(value = "/comment/update.do")
    public ModelAndView commentUpdate(HttpServletRequest request) throws Exception{

        ModelAndView modelAndView = new ModelAndView();

        int commentNumber = Integer.parseInt(request.getParameter("commentNumber"));

        /*
        수정 : Member Id 를 세션으로 넣을 경우 수정이 필요함
         */
        String memberId = request.getParameter("memberId");
        util.isMemberId(memberId);


        CommentVO commentVO = commentService.selectOne(commentNumber);
        if(commentVO == null){
            throw new CommentNotFoundException("댓글이 존재하지 않음 : " + commentNumber);
        }

        util.isEqualMemberId(commentVO.getUserEmail(), memberId);

        modelAndView.addObject("commentVO", commentVO);
        modelAndView.setViewName("/comment/updateForm");
        return modelAndView;
    }

    /*
    댓글 수정
     */
    @RequestMapping(value = "/comment/update.do", method = RequestMethod.POST)
    public ModelAndView commentUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView modelAndView = new ModelAndView();

        int commentNumber = Integer.parseInt(request.getParameter("commentNumber"));

        /*
        수정 : Member Id 를 세션으로 넣을 경우 수정이 필요함
         */
        String memberId = request.getParameter("memberId");
        util.isMemberId(memberId);


        CommentVO commentVO = commentService.selectOne(commentNumber);
        if(commentVO == null){
            throw new CommentNotFoundException("댓글이 존재하지 않음 : " + commentNumber);
        }

        util.isEqualMemberId(commentVO.getUserEmail(), memberId);

        String content = request.getParameter("content");

        commentVO = new CommentVO();
        commentVO.setNumber(commentNumber);
        commentVO.setContent(content);

        int updateCount = commentService.update(commentVO);
        if (updateCount == 0) {
            throw new CommentNotFoundException("댓글이 존재하지 않음 : " + commentNumber);
        }

        commentVO = commentService.selectOne(commentNumber);

        //modelAndView.addObject("commentVO", commentVO);
        //modelAndView.setViewName("/comment/update");
        //return modelAndView;

        return (ModelAndView)new ModelAndView("redirect:/board/read.do?s=" + request.getParameter("s")
                +"&p=" + request.getParameter("p") +"&boardNumber=" + request.getParameter("boardNumber"));
    }

    /*
    댓글 답글 폼
     */
    @RequestMapping(value = "/comment/reply.do")
    public ModelAndView commentReply(HttpServletRequest request) throws Exception{

        ModelAndView modelAndView = new ModelAndView();

        int parentCommentNumber = Integer.parseInt(request.getParameter("parentCommentNumber"));

        CommentVO parent = commentService.selectOne(parentCommentNumber);

        util.checkParent(parent, parentCommentNumber);

        String searchMaxSeqNum = parent.getSequenceNumber();
        String searchMinSeqNum = util.getSearchMinSeqNum(parent);

        String lastChildSeq = commentService.selectLastSequenceNumber(searchMaxSeqNum, searchMinSeqNum);
        String sequenceNumber = util.getSequenceNumber(parent, lastChildSeq);

        modelAndView.setViewName("/comment/replyForm");

        return modelAndView;

    }

    /*
    댓글 답글
     */
    @RequestMapping(value = "/comment/reply.do", method = RequestMethod.POST)
    public ModelAndView commnetReply(HttpServletRequest request, HttpServletResponse response) throws Exception{

        ModelAndView modelAndView = new ModelAndView();

        /*
        수정 : Member Id 를 세션으로 넣을 경우 수정이 필요함
         */
        String memberId = request.getParameter("memberId");
        util.isMemberId(memberId);

        CommentVO commentVO = new CommentVO();
        commentVO.setContent(request.getParameter("content"));
        commentVO.setBoardNumber(Integer.parseInt(request.getParameter("boardNumber")));
        int parentCommentNumber = Integer.parseInt(request.getParameter("parentCommentNumber"));

        CommentVO parent = commentService.selectOne(parentCommentNumber);

        util.checkParent(parent, parentCommentNumber);

        String searchMaxSeqNum = parent.getSequenceNumber();
        String searchMinSeqNum = util.getSearchMinSeqNum(parent);

        String lastChildSeq = commentService.selectLastSequenceNumber(searchMaxSeqNum, searchMinSeqNum);
        String sequenceNumber = util.getSequenceNumber(parent, lastChildSeq);
        int userNumber = userService.selectUserNumberByEmail(memberId);

        commentVO.setGroupNumber(parent.getGroupNumber());
        commentVO.setSequenceNumber(sequenceNumber);
        commentVO.setUserNumber(userNumber);
        commentVO.setUserEmail(memberId);
        commentVO.setSeparatorName(request.getParameter("s"));

        int commentNumber = commentService.insert(commentVO);
        if(commentNumber == -1){
            throw new RuntimeException("DB 삽입 실패 : " + commentNumber);
        }
        commentService.increaseCommentCount(Integer.parseInt(request.getParameter("boardNumber")));

        //commentVO.setNumber(commentNumber);
        //modelAndView.addObject("commentVO", commentVO);

        return (ModelAndView)new ModelAndView("redirect:/board/read.do?s=" + request.getParameter("s")
                +"&p=" + request.getParameter("p") +"&boardNumber=" + request.getParameter("boardNumber"));
    }

    /*
    댓글 삭제
     */
    @RequestMapping(value = "/comment/delete.do", method = RequestMethod.POST)
    public ModelAndView commentDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {

        int boardNumber = Integer.parseInt(request.getParameter("boardNumber"));
        int commentNumber = Integer.parseInt(request.getParameter("commentNumber"));

        /*
        수정 : Member Id 를 세션으로 넣을 경우 수정이 필요함
         */
        String memberId = request.getParameter("memberId");
        util.isMemberId(memberId);

        CommentVO commentVO = commentService.selectOne(commentNumber);
        util.isEqualMemberId(commentVO.getUserEmail(), memberId);

        commentService.delete(commentNumber);
        commentService.decreaseCommentCount(boardNumber);

        return (ModelAndView)new ModelAndView("redirect:/board/read.do?s=" + request.getParameter("s")
                +"&p=" + request.getParameter("p") +"&boardNumber=" + request.getParameter("boardNumber"));
    }

}
