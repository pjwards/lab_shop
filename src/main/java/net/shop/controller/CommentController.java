package net.shop.controller;

import net.shop.error.BoardNotFoundException;
import net.shop.error.CommentNotFoundException;
import net.shop.service.CommentService;
import net.shop.service.UserService;
import net.shop.util.Util;
import net.shop.vo.CommentVO;
import net.shop.vo.PagingVO;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

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
@RequestMapping(value = "/comment")
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
    @RequestMapping(value = "/listAll.do")
    public ModelAndView commentListAll(@RequestParam(value = "boardNumber", required = true) Integer boardNumber)
            throws Exception {

        ModelAndView modelAndView = new ModelAndView();

        if(boardNumber == null) throw new BoardNotFoundException("게시글이 존재하지 않음 : " + boardNumber);
        if(boardNumber < 0) throw new IllegalArgumentException("board number < 1 : " + boardNumber);

        int totalCount = commentService.selectCount(boardNumber);

        modelAndView.setViewName("/board/read");

        if(totalCount == 0){
            modelAndView.addObject("commentVOList", Collections.<CommentVO>emptyList());
            modelAndView.addObject("hasComment", false);
            return modelAndView;
        }

        List<CommentVO> commentVOList = commentService.selectList(boardNumber);
        modelAndView.addObject("commentVOList", commentVOList);
        modelAndView.addObject("hasComment", true);

        return modelAndView;
    }

    /*
    댓글 리스트
     */
    @RequestMapping(value = "/list.do")
    public ModelAndView commentList(@RequestParam(value = "cp", required = true) Integer requestPage,
                                    @RequestParam(value = "boardNumber", required = true) Integer boardNumber)
            throws Exception {

        ModelAndView modelAndView = new ModelAndView();

        if(requestPage == null) requestPage = 1;
        if(requestPage <= 0) throw new IllegalArgumentException("requestPage <= 0 : " + requestPage);


        if(boardNumber == null) throw new BoardNotFoundException("게시글이 존재하지 않음 : " + boardNumber);
        if(boardNumber < 0) throw new IllegalArgumentException("board number < 1 : " + boardNumber);

        int totalCount = commentService.selectCount(boardNumber);

        /*Paging 메소드의 사용 */
        PagingVO pagingVO = util.paging(requestPage, 10, totalCount);
        modelAndView.addObject("pagingVO", pagingVO);
        modelAndView.setViewName("/board/read");

        if(totalCount == 0){
            modelAndView.addObject("commentVOList", Collections.<CommentVO>emptyList());
            modelAndView.addObject("hasComment", false);
            return modelAndView;
        }

        List<CommentVO> commentVOList = commentService.selectList(boardNumber, pagingVO.getFirstRow(), pagingVO.getEndRow());
        modelAndView.addObject("commentVOList", commentVOList);
        modelAndView.addObject("hasComment", true);

        return modelAndView;
    }

    /*
    댓글 글쓰기
     */
    @RequestMapping(value = "/write.do", method = RequestMethod.POST)
    public String commentWrite(@RequestParam(value = "content", required = true) String content,
                               @RequestParam(value = "boardNumber", required = true ) Integer boardNumber,
                               @RequestParam(value = "s", required = true, defaultValue = "default") String separator,
                               @RequestParam(value = "p", required = false) String page,
                               Authentication auth) throws Exception{

        String memberId = util.isMemberId(auth);

        int groupId = commentService.generateNextGroupNumber("comment");
        int userNumber = userService.selectOneNo(memberId);

        CommentVO commentVO = new CommentVO();
        commentVO.setGroupNumber(groupId);
        DecimalFormat decimalFormat = new DecimalFormat("0000000000");
        commentVO.setSequenceNumber(decimalFormat.format(groupId) + "99");
        commentVO.setContent(content);
        commentVO.setUserNumber(userNumber);
        commentVO.setUserEmail(memberId);
        commentVO.setBoardNumber(boardNumber);
        commentVO.setSeparatorName(separator);

        commentService.insert(commentVO);
        commentService.increaseCommentCount(boardNumber);

        return "redirect:/board/read.do?s=" + separator + "&p=" + page +"&boardNumber=" + boardNumber;
    }

    /*
    댓글 읽기
     */
    public CommentVO commentRead(int commentNumber) throws Exception {

        CommentVO commentVO = commentService.selectOne(commentNumber);
        if(commentVO == null) throw new CommentNotFoundException("댓글이 존재하지 않음 : " + commentNumber);

        return commentVO;
    }

    /*
    댓글 수정 폼
     */
    @RequestMapping(value = "/update.do")
    public ModelAndView commentUpdate(@RequestParam(value = "commentNumber", required = true) Integer commentNumber,
                                      Authentication auth) throws Exception{

        ModelAndView modelAndView = new ModelAndView();

        String memberId = util.isMemberId(auth);

        CommentVO commentVO = commentService.selectOne(commentNumber);
        if(commentVO == null) throw new CommentNotFoundException("댓글이 존재하지 않음 : " + commentNumber);

        util.isEqualMemberId(commentVO.getUserEmail(), memberId);

        modelAndView.addObject("commentVO", commentVO);
        modelAndView.setViewName("/comment/updateForm");
        return modelAndView;
    }

    /*
    댓글 수정
     */
    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    public String commentUpdate(@RequestParam(value = "commentNumber", required = true) Integer commentNumber,
                                @RequestParam(value = "boardNumber", required = true) String boardNumber,
                                @RequestParam(value = "content", required = true) String content,
                                @RequestParam(value = "p", required = false) String page,
                                @RequestParam(value = "s", required = false) String separator,
                                Authentication auth) throws Exception {

        String memberId = util.isMemberId(auth);

        CommentVO commentVO = commentService.selectOne(commentNumber);
        if(commentVO == null) throw new CommentNotFoundException("댓글이 존재하지 않음 : " + commentNumber);

        util.isEqualMemberId(commentVO.getUserEmail(), memberId);

        commentVO = new CommentVO();
        commentVO.setNumber(commentNumber);
        commentVO.setContent(content);

        int updateCount = commentService.update(commentVO);
        if (updateCount == 0) throw new CommentNotFoundException("댓글이 존재하지 않음 : " + commentNumber);

        commentService.selectOne(commentNumber);

        return "redirect:/board/read.do?s=" + separator + "&p=" + page +"&boardNumber=" + boardNumber;
    }

    /*
    댓글 답글 폼
     */
    @RequestMapping(value = "/reply.do")
    public String commentReply(@RequestParam(value = "parentCommentNumber", required = true) Integer parentCommentNumber)
            throws Exception{

        CommentVO parent = commentService.selectOne(parentCommentNumber);

        util.checkParent(parent, parentCommentNumber);

        String searchMaxSeqNum = parent.getSequenceNumber();
        String searchMinSeqNum = util.getSearchMinSeqNum(parent);

        String lastChildSeq = commentService.selectLastSequenceNumber(searchMaxSeqNum, searchMinSeqNum);
        String sequenceNumber = util.getSequenceNumber(parent, lastChildSeq);

        return "/comment/replyForm";

    }

    /*
    댓글 답글
     */
    @RequestMapping(value = "/reply.do", method = RequestMethod.POST)
    public String commentReply(@RequestParam(value = "content", required = true) String content,
                               @RequestParam(value = "boardNumber", required = true) Integer boardNumber,
                               @RequestParam(value = "parentCommentNumber", required = true) Integer parentCommentNumber,
                               @RequestParam(value = "s", required = true, defaultValue = "default") String separator,
                               @RequestParam(value = "p", required = false) String page,
                               Authentication auth) throws Exception{

        String memberId = util.isMemberId(auth);

        CommentVO commentVO = new CommentVO();
        commentVO.setContent(content);
        commentVO.setBoardNumber(boardNumber);

        CommentVO parent = commentService.selectOne(parentCommentNumber);

        util.checkParent(parent, parentCommentNumber);

        String searchMaxSeqNum = parent.getSequenceNumber();
        String searchMinSeqNum = util.getSearchMinSeqNum(parent);

        String lastChildSeq = commentService.selectLastSequenceNumber(searchMaxSeqNum, searchMinSeqNum);
        String sequenceNumber = util.getSequenceNumber(parent, lastChildSeq);
        int userNumber = userService.selectOneNo(memberId);

        commentVO.setGroupNumber(parent.getGroupNumber());
        commentVO.setSequenceNumber(sequenceNumber);
        commentVO.setUserNumber(userNumber);
        commentVO.setUserEmail(memberId);
        commentVO.setSeparatorName(separator);

        int commentNumber = commentService.insert(commentVO);
        if(commentNumber == -1) throw new RuntimeException("DB 삽입 실패 : " + commentNumber);
        commentService.increaseCommentCount(boardNumber);

        return "redirect:/board/read.do?s=" + separator + "&p=" + page + "&boardNumber=" + boardNumber;
    }

    /*
    댓글 삭제
     */
    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    public String commentDelete(@RequestParam(value = "boardNumber", required = true) Integer boardNumber,
                                @RequestParam(value = "commentNumber", required = true) Integer commentNumber,
                                @RequestParam(value = "p", required = false) String page,
                                @RequestParam(value = "s", required = false) String separator,
                                Authentication auth) throws Exception {

        String memberId = util.isMemberId(auth);

        CommentVO commentVO = commentService.selectOne(commentNumber);
        util.isEqualMemberId(commentVO.getUserEmail(), memberId);

        commentService.delete(commentNumber);
        commentService.decreaseCommentCount(boardNumber);

        return "redirect:/board/read.do?s=" + separator + "&p=" + page +"&boardNumber=" + boardNumber;
    }

}
