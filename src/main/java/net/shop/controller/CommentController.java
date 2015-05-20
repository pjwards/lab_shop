package net.shop.controller;

import net.shop.error.BoardNotFoundException;
import net.shop.error.CommentNotFoundException;
import net.shop.error.MemberIdNotFoundException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @RequestMapping(value = "/list.do")
    public ModelAndView commentList(@RequestParam(value = "cp", required = false) Integer requestPage,
                                    @RequestParam(value = "cs", required = false) String separator,
                                    @RequestParam(value = "boardNumber", required = true) Integer boardNumber,
                                    Authentication auth) throws Exception {

        ModelAndView modelAndView = new ModelAndView();

        if(requestPage == null) requestPage = 1;
        if(requestPage <= 0) throw new IllegalArgumentException("requestPage <= 0 : " + requestPage);

        if(boardNumber == null) throw new BoardNotFoundException("게시글이 존재하지 않음 : " + boardNumber);
        if(boardNumber < 0) throw new IllegalArgumentException("board number < 1 : " + boardNumber);

        modelAndView.addObject("cs", separator);

        String memberId;
        try {
            memberId = util.isMemberId(auth);
            modelAndView.addObject("isLogin", true);
            modelAndView.addObject("memberId", memberId);
        } catch (Exception e) {
            memberId = null;
            modelAndView.addObject("isLogin", false);
        }

        int totalCount;

        if(separator  == null || separator.equals(""))  {
            totalCount = commentService.selectCount(boardNumber);
        } else {
            totalCount = commentService.selectCount(boardNumber, separator);
        }

        /*Paging 메소드의 사용 */
        PagingVO pagingVO = util.paging(requestPage, 3, totalCount);
        modelAndView.addObject("pagingVO", pagingVO);
        modelAndView.setViewName("/comment/list");

        if(totalCount == 0){
            modelAndView.addObject("commentVOList", Collections.<CommentVO>emptyList());
            modelAndView.addObject("hasComment", false);
            return modelAndView;
        }

        List<CommentVO> commentVOList;

        if(separator  == null || separator.equals(""))  {
            commentVOList = commentService.selectList(boardNumber, pagingVO.getFirstRow(), pagingVO.getEndRow());
        } else {
            commentVOList = commentService.selectList(boardNumber, pagingVO.getFirstRow(), pagingVO.getEndRow(), separator);
        }

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
                               @RequestParam(value = "cs", required = true, defaultValue = "default") String separator,
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
        return "/common/success";
    }

    /*
    댓글 수정 폼
     */
    @RequestMapping(value = "/update.do")
    public ModelAndView commentUpdateForm(@RequestParam(value = "commentNumber", required = true) Integer commentNumber,
                                          @RequestParam(value = "cs", required = true) String separator,
                                          Authentication auth) throws Exception{

        ModelAndView modelAndView = new ModelAndView();

        String memberId = util.isMemberId(auth);

        CommentVO commentVO = commentService.selectOne(commentNumber);
        if(commentVO == null) throw new CommentNotFoundException("댓글이 존재하지 않음 : " + commentNumber);

        util.isEqualMemberId(commentVO.getUserEmail(), memberId);

        modelAndView.addObject("commentVO", commentVO);
        modelAndView.addObject("cs", separator);
        modelAndView.addObject("commentNumber", commentNumber);
        modelAndView.setViewName("/comment/updateForm");
        return modelAndView;
    }

    /*
    댓글 수정
     */
    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    public String commentUpdate(@RequestParam(value = "commentNumber", required = true) Integer commentNumber,
                              @RequestParam(value = "content", required = true) String content,
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

        return "/common/success";
    }

    /*
    댓글 답글 폼
     */
    @RequestMapping(value = "/reply.do")
    public ModelAndView commentReply(@RequestParam(value = "parentCommentNumber", required = true) Integer parentCommentNumber,
                               @RequestParam(value = "boardNumber", required = true) Integer boardNumber,
                               @RequestParam(value = "cs", required = true, defaultValue = "default") String separator)
            throws Exception{

        ModelAndView modelAndView = new ModelAndView();

        CommentVO parent = commentService.selectOne(parentCommentNumber);

        util.checkParent(parent, parentCommentNumber);

        String searchMaxSeqNum = parent.getSequenceNumber();
        String searchMinSeqNum = util.getSearchMinSeqNum(parent);

        String lastChildSeq = commentService.selectLastSequenceNumber(searchMaxSeqNum, searchMinSeqNum);
        String sequenceNumber = util.getSequenceNumber(parent, lastChildSeq);

        modelAndView.addObject("parentCommentNumber", parentCommentNumber);
        modelAndView.addObject("boardNumber", boardNumber);
        modelAndView.addObject("cs", separator);
        modelAndView.setViewName("/comment/replyForm");

        return modelAndView;
    }

    /*
    댓글 답글
     */
    @RequestMapping(value = "/reply.do", method = RequestMethod.POST)
    public String commentReply(@RequestParam(value = "content", required = true) String content,
                             @RequestParam(value = "boardNumber", required = true) Integer boardNumber,
                             @RequestParam(value = "parentCommentNumber", required = true) Integer parentCommentNumber,
                             @RequestParam(value = "cs", required = true, defaultValue = "default") String separator,
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

        return "/common/success";
    }

    /*
    댓글 삭제
     */
    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    public String commentDelete(@RequestParam(value = "boardNumber", required = true) Integer boardNumber,
                                @RequestParam(value = "commentNumber", required = true) Integer commentNumber,
                                Authentication auth) throws Exception {

        String memberId = util.isMemberId(auth);

        CommentVO commentVO = commentService.selectOne(commentNumber);
        util.isEqualMemberId(commentVO.getUserEmail(), memberId);

        commentService.delete(commentNumber);
        commentService.decreaseCommentCount(boardNumber);

        return "/common/success";
    }

    @RequestMapping(value = "/isEqualMember.do")
    public String isEqualMember(@RequestParam(value = "commentNumber", required = true) Integer commentNumber,
                                Authentication auth) throws Exception {

        String memberId = util.isMemberId(auth);

        CommentVO commentVO = commentService.selectOne(commentNumber);
        if (commentVO == null) throw new CommentNotFoundException("댓글이 존재하지 않음 : " + commentNumber);

        util.isEqualMemberId(commentVO.getUserEmail(), memberId);

        return "common/success";
    }
}
