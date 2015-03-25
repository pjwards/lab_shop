package net.shop.controller;

import net.shop.error.BoardNotFoundException;
import net.shop.error.CannotReplyBoardException;
import net.shop.error.LastChildAleadyExistsException;
import net.shop.error.MemberIdNotFoundException;
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

    /*
    게시판 리스트
     */
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
    @RequestMapping(value = "/board/write.do")
    public ModelAndView boardWrite(HttpServletRequest request) throws Exception{
        /*
        수정 : Member Id 를 세션으로 넣을 경우 수정이 필요함

        String memberId = request.getParameter("memberId");
        util.isMemberId(memberId);
        */

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/board/writeForm");
        return modelAndView;
    }

    /*
    게시판 글쓰기
     */
    @RequestMapping(value = "/board/write.do", method = RequestMethod.POST)
    public ModelAndView boardWrite(HttpServletRequest request, HttpServletResponse response) throws Exception{
        /*
        수정 : Member Id 를 세션으로 넣을 경우 수정이 필요함
         */
        String memberId = request.getParameter("memberId");
        util.isMemberId(memberId);

        int groupId = boardService.generateNextGroupNumber("board");
        int userNumber = userService.selectUserNumberByEmail(memberId);

        BoardVO boardVO = new BoardVO();
        boardVO.setGroupNumber(groupId);
        DecimalFormat decimalFormat = new DecimalFormat("0000000000");
        boardVO.setSequenceNumber(decimalFormat.format(groupId) + "999999");
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

    /*
    게시판 수정 폼
     */
    @RequestMapping(value = "/board/update.do")
    public ModelAndView boardUpdate(HttpServletRequest request) throws Exception{

        ModelAndView modelAndView = new ModelAndView();

        int boardNumber = Integer.parseInt(request.getParameter("boardNumber"));

        /*
        수정 : Member Id 를 세션으로 넣을 경우 수정이 필요함
         */
        String memberId = request.getParameter("memberId");
        util.isMemberId(memberId);

        BoardVO boardVO = boardService.selectOne(boardNumber);
        util.isEqualMemberId(boardVO.getUserEmail(), memberId);

        modelAndView.addObject("boardVO", boardVO);
        modelAndView.setViewName("/board/updateForm");
        return modelAndView;
    }

    /*
    게시판 수정
     */
    @RequestMapping(value = "/board/update.do", method = RequestMethod.POST)
    public ModelAndView boardUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView modelAndView = new ModelAndView();

        int boardNumber = Integer.parseInt(request.getParameter("boardNumber"));

        /*
        수정 : Member Id 를 세션으로 넣을 경우 수정이 필요함
         */
        String memberId = request.getParameter("memberId");
        util.isMemberId(memberId);

        BoardVO boardVO = boardService.selectOne(boardNumber);
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
        modelAndView.setViewName("/board/update");

        return modelAndView;
    }

    /*
    게시판 답글 폼
     */
    @RequestMapping(value = "/board/reply.do")
    public ModelAndView boardReply(HttpServletRequest request) throws Exception{

        ModelAndView modelAndView = new ModelAndView();

        int parentBoardNumber = Integer.parseInt(request.getParameter("parentBoardNumber"));

        BoardVO parent = boardService.selectOne(parentBoardNumber);

        checkParent(parent, parentBoardNumber);

        String searchMaxSeqNum = parent.getSequenceNumber();
        String searchMinSeqNum = getSearchMinSeqNum(parent);

        String lastChildSeq = boardService.selectLastSequenceNumber(searchMaxSeqNum, searchMinSeqNum);
        String sequenceNumber = getSequenceNumber(parent, lastChildSeq);

        modelAndView.setViewName("/board/replyForm");

        return modelAndView;

    }

    /*
    게시판 답글
     */
    @RequestMapping(value = "/board/reply.do", method = RequestMethod.POST)
    public ModelAndView boardReply(HttpServletRequest request, HttpServletResponse response) throws Exception{

        ModelAndView modelAndView = new ModelAndView();

        /*
        수정 : Member Id 를 세션으로 넣을 경우 수정이 필요함
         */
        String memberId = request.getParameter("memberId");
        util.isMemberId(memberId);

        BoardVO boardVO = new BoardVO();
        boardVO.setTitle(request.getParameter("title"));
        boardVO.setContent(request.getParameter("content"));
        int parentBoardNumber = Integer.parseInt(request.getParameter("parentBoardNumber"));

        BoardVO parent = boardService.selectOne(parentBoardNumber);

        checkParent(parent, parentBoardNumber);

        String searchMaxSeqNum = parent.getSequenceNumber();
        String searchMinSeqNum = getSearchMinSeqNum(parent);

        String lastChildSeq = boardService.selectLastSequenceNumber(searchMaxSeqNum, searchMinSeqNum);
        String sequenceNumber = getSequenceNumber(parent, lastChildSeq);
        int userNumber = userService.selectUserNumberByEmail(memberId);

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
    부모 글이 올바른지의 여부를 검사
     */
    private void checkParent(BoardVO parent, int parentNumber) throws BoardNotFoundException, CannotReplyBoardException{
        if(parent == null){
            throw new BoardNotFoundException("부모글이 존재하지 않음 : " + parentNumber);
        }

        int parentLevel = parent.getLevel();
        if(parentLevel == 3){
            throw new CannotReplyBoardException("마지막 레벨 글에는 답글을 달 수 없습니다 : " + parent.getNumber());
        }
    }

    /*
    최소 순서 번호를 반환한다.
     */
    private String getSearchMinSeqNum(BoardVO parent){
        String parentSeqNum = parent.getSequenceNumber();
        DecimalFormat decimalFormat = new DecimalFormat("0000000000000000");
        long parentSeqLongValue = Long.parseLong(parentSeqNum);
        long searchMinLongValue = 0;
        switch (parent.getLevel()){                                             // 0000009999 654321
            case 0:
                searchMinLongValue = parentSeqLongValue / 1000000L * 1000000L;  // 0000009999 000000
                break;
            case 1:
                searchMinLongValue = parentSeqLongValue / 10000L * 10000L;      // 0000009999 650000
                break;
            case 2:
                searchMinLongValue = parentSeqLongValue / 100L * 100L;          // 0000009999 654300
                break;
        }
        return decimalFormat.format(searchMinLongValue);
    }

    /*
    순서 번호를 생성한다.
     */
    private String getSequenceNumber(BoardVO parent, String lastChildSeq) throws  LastChildAleadyExistsException{
        long parentSeqLong = Long.parseLong(parent.getSequenceNumber());
        int parentLevel = parent.getLevel();

        long decUnit = 0;
        if(parentLevel == 0){
            decUnit = 10000L;
        } else if(parentLevel == 1){
            decUnit = 100L;
        } else if(parentLevel ==2){
            decUnit = 1L;
        }

        String sequenceNumber = null;

        DecimalFormat decimalFormat = new DecimalFormat("0000000000000000");
        if(lastChildSeq == null){   // 답변글이 없음
            sequenceNumber = decimalFormat.format(parentSeqLong - decUnit);
        } else {    // 답변글이 있음
            // 마지막 답변글인지 확인
            String orderOfLastChildSeq = null;
            if(parentLevel == 0){
                orderOfLastChildSeq = lastChildSeq.substring(10, 12);       // 0000000000 00 0000
                sequenceNumber = lastChildSeq.substring(0, 12) + "9999";    // 000000000000 + 9999
            } else if(parentLevel == 1){
                orderOfLastChildSeq = lastChildSeq.substring(12, 14);       // 000000000000 00 00
                sequenceNumber = lastChildSeq.substring(0, 14) + "99";      // 00000000000000 + 99
            } else if(parentLevel == 2){
                orderOfLastChildSeq = lastChildSeq.substring(14, 16);       // 00000000000000 00
                sequenceNumber = lastChildSeq;
            }
            if(orderOfLastChildSeq.equals("00")){
                throw new LastChildAleadyExistsException("마지막 자식글이 이미 존재합니다 : " + lastChildSeq);
            }
            long seq = Long.parseLong(sequenceNumber) - decUnit;
            sequenceNumber = decimalFormat.format(seq);
        }
        return sequenceNumber;
    }

    /*
    게시판 삭제
     */
    @RequestMapping(value = "/board/delete.do", method = RequestMethod.POST)
    public ModelAndView boardDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {

        int boardNumber = Integer.parseInt(request.getParameter("boardNumber"));

        /*
        수정 : Member Id 를 세션으로 넣을 경우 수정이 필요함
         */
        String memberId = request.getParameter("memberId");
        util.isMemberId(memberId);

        BoardVO boardVO = boardService.selectOne(boardNumber);
        util.isEqualMemberId(boardVO.getUserEmail(), memberId);

        boardService.delete(boardNumber);

        return (ModelAndView)new ModelAndView("redirect:/board/list.do?s=" + request.getParameter("s")
        +"&p=" + request.getParameter("p"));
    }
}
