package net.shop.controller;

import net.shop.service.BoardService;
import net.shop.vo.BoardVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-03-20
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */

@Controller
public class BoardController {

    @Resource(name = "boardService")
    private BoardService boardService;

    private static final int COUNT_PER_PAGE = 10;

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

        String requestPageNumberString = request.getParameter("p");

        if(requestPageNumberString == null || requestPageNumberString.equals("")) {
            requestPageNumberString = "1";
        }

        int requestPageNumber = Integer.parseInt(requestPageNumberString);

        if(requestPageNumber < 0){
            throw new IllegalArgumentException("page number < 0 : " + requestPageNumber);
        }

        int totalBoardCount = boardService.selectCount();

        if(totalBoardCount == 0){
            setListAttributes(modelAndView);
            modelAndView.setViewName("/jsp/view/board/list.jsp");
            return modelAndView;
        }

        int totalPageCount = calculateTotalPageCount(totalBoardCount);

        int firstRow = (requestPageNumber - 1) * COUNT_PER_PAGE + 1;
        int endRow = firstRow + COUNT_PER_PAGE - 1; // 자기 자신도 포함되니 하나를 빼주어야 한다.

        if(endRow > totalBoardCount){
            endRow = totalBoardCount;
        }

        List<BoardVO> boardVOList = boardService.selectList(firstRow, endRow);
        setListAttributes(modelAndView, boardVOList, requestPageNumber, totalPageCount, firstRow, endRow);
        modelAndView.setViewName("/jsp/view/board/list.jsp");
        return modelAndView;
    }

    /*
    전체 게시글 개수로부터 전체 페이지 개수를 구해주는 메서드
     */
    private int calculateTotalPageCount(int totalArticleCount){

        if(totalArticleCount == 0)  return 0;

        // 총 게시글 : 31 / 페이지 당 글 : 10 일때
        int pageCount = totalArticleCount / COUNT_PER_PAGE;         // pageCount : 3
        if(totalArticleCount % COUNT_PER_PAGE > 0) {
            pageCount++;                                            // 나머지가 1이므로 pageCount : 4
        }

        return pageCount;
    }

    /*
    List 을 생성하는데 필요한 자원들을 속성에 저장한다. 이 메소드는 게시글이 없을 때 수행된다.
     */
    private void setListAttributes(ModelAndView modelAndView){
        modelAndView.addObject("requestPage", new Integer(0));
        modelAndView.addObject("totalPageCount", new Integer(0));
        modelAndView.addObject("startRow", new Integer(0));
        modelAndView.addObject("endRow", new Integer(0));
        modelAndView.addObject("boardVOList", Collections.<BoardVO>emptyList());
        modelAndView.addObject("hasBoard", new Boolean(false));
    }

    /*
    List 을 생성하는데 필요한 자원들을 속성에 저장한다.
     */
    private void setListAttributes(ModelAndView modelAndView, List<BoardVO> boardVOList, int requestPage,
                                   int totalPageCount, int startRow, int endRow){

        modelAndView.addObject("requestPage", new Integer(requestPage));
        modelAndView.addObject("totalPageCount", new Integer(totalPageCount));
        modelAndView.addObject("startRow", new Integer(startRow));
        modelAndView.addObject("endRow", new Integer(endRow));
        modelAndView.addObject("boardVOList", boardVOList);
        modelAndView.addObject("hasBoard", new Boolean(true));

        int beginPageNumber = (requestPage - 1) / COUNT_PER_PAGE * COUNT_PER_PAGE + 1;
        int endPageNumber = beginPageNumber + COUNT_PER_PAGE - 1;
        if(endPageNumber > totalPageCount){
            endPageNumber = totalPageCount;
        }
        modelAndView.addObject("beginPage", beginPageNumber);
        modelAndView.addObject("endPage", endPageNumber);
    }

}
