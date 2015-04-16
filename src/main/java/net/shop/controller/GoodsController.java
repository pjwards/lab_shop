package net.shop.controller;

import net.shop.error.BoardNotFoundException;
import net.shop.error.GoodsNotFoundException;
import net.shop.service.GoodsService;
import net.shop.service.UserService;
import net.shop.util.Util;
import net.shop.vo.GoodsVO;
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
import java.util.Collections;
import java.util.List;

/**
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  :
 * Date         : 4/16/15 | 11:30 AM
 * Description  :
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      :
 */
@Controller
@RequestMapping(value = "/goods")
public class GoodsController {

    @Resource(name = "goodsService")
    private GoodsService goodsService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "util")
    private Util util;

    /*
    상품 리스트
     */
    @RequestMapping(value = "/list.do")
    public ModelAndView goodsList(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws Exception{
        ModelAndView modelAndView = new ModelAndView();

        UserDetails vo = (UserDetails) auth.getPrincipal();
        String memberId = vo.getUsername();
        util.isMemberId(memberId);

        /*
        웹 브라우저가 상품 목록을 캐싱하지 않도록 캐시 관련 헤더를 설정
         */
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.addHeader("Cache-Control", "no-store");
        response.setDateHeader("Expire", 1L);

        String requestPageString = request.getParameter("p");

        if(requestPageString == null || requestPageString.equals("")) {
            requestPageString = "1";
        }

        int requestPage = Integer.parseInt(requestPageString);

        if(requestPage <= 0){
            return (ModelAndView)new ModelAndView("redirect:/goods/list.do");
        }

        int totalCount = goodsService.selectCount(memberId);


        /*Paging 메소드의 사용 */
        PagingVO pagingVO = util.paging(requestPage, 10, totalCount);
        modelAndView.addObject("pagingVO", pagingVO);
        modelAndView.setViewName("/goods/list");

        /*
        상품이 없을 때 수행된다.
         */
        if(totalCount == 0){
            modelAndView.addObject("goodsVOList", Collections.<GoodsVO>emptyList());
            modelAndView.addObject("hasGoods", new Boolean(false));
            return modelAndView;
        }

        List<GoodsVO> goodsVOList = goodsService.selectList(memberId, pagingVO.getFirstRow(), pagingVO.getEndRow());

        modelAndView.addObject("goodsVOList", goodsVOList);
        modelAndView.addObject("hasGoods", new Boolean(true));

        return modelAndView;
    }

    /*
    상품 등록 폼
     */
    @RequestMapping(value = "/write.do")
    public ModelAndView goodsWrite(HttpServletRequest request, Authentication auth) throws Exception{

        UserDetails vo = (UserDetails) auth.getPrincipal();
        String memberId = vo.getUsername();
        util.isMemberId(memberId);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/goods/writeForm");
        return modelAndView;
    }

    /*
    상품 등록
     */
    @RequestMapping(value = "/write.do", method = RequestMethod.POST)
    public ModelAndView goodsWrite(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws Exception{

        UserDetails vo = (UserDetails) auth.getPrincipal();
        String memberId = vo.getUsername();
        util.isMemberId(memberId);

        int userNumber = userService.selectOneNo(memberId);

        GoodsVO goodsVO = new GoodsVO();
        goodsVO.setName(request.getParameter("name"));
        goodsVO.setSize(request.getParameter("size"));
        goodsVO.setMaterial(request.getParameter("material"));
        goodsVO.setComponent(request.getParameter("component"));
        goodsVO.setOptions(request.getParameter("options"));
        goodsVO.setManufacturer(request.getParameter("manufacturer"));
        goodsVO.setMadein(request.getParameter("madein"));
        goodsVO.setDescription(request.getParameter("description"));
        goodsVO.setPrice(Integer.parseInt(request.getParameter("price")));
        goodsVO.setStock(Integer.parseInt(request.getParameter("stock")));
        goodsVO.setUserNumber(userNumber);
        goodsVO.setUserEmail(memberId);

        goodsService.insert(goodsVO);

        return (ModelAndView)new ModelAndView("redirect:/goods/list.do");
    }

    /*
    상품 읽기
     */
    @RequestMapping(value = "/read.do")
    public ModelAndView goodsRead(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        /*
        웹 브라우저가 상품 목록을 캐싱하지 않도록 캐시 관련 헤더를 설정
         */
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.addHeader("Cache-Control", "no-store");
        response.setDateHeader("Expire", 1L);

        int goodsNumber = Integer.parseInt(request.getParameter("goodsNumber"));

        GoodsVO goodsVO = goodsService.selectOne(goodsNumber);

        if(goodsVO == null){
            throw new GoodsNotFoundException("상품이 존재하지 않음 : " + goodsNumber);
        }

        request.setAttribute("goodsVO", goodsVO);
        modelAndView.setViewName("/goods/read");

        return modelAndView;
    }

    /*
    상품 수정 폼
     */
    @RequestMapping(value = "/update.do")
    public ModelAndView goodsUpdate(HttpServletRequest request, Authentication auth) throws Exception {

        ModelAndView modelAndView = new ModelAndView();

        int goodsNumber = Integer.parseInt(request.getParameter("goodsNumber"));

        UserDetails vo = (UserDetails) auth.getPrincipal();
        String memberId = vo.getUsername();
        util.isMemberId(memberId);


        GoodsVO goodsVO = goodsService.selectOne(goodsNumber);
        if(goodsVO == null){
            throw new GoodsNotFoundException("상품이 존재하지 않음 : " + goodsNumber);
        }

        util.isEqualMemberId(goodsVO.getUserEmail(), memberId);

        modelAndView.addObject("goodsVO", goodsVO);
        modelAndView.setViewName("/goods/updateForm");
        return modelAndView;
    }

    /*
    상품 수정
     */
    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    public ModelAndView goodsUpdate(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws Exception {

        int goodsNumber = Integer.parseInt(request.getParameter("goodsNumber"));

        UserDetails vo = (UserDetails) auth.getPrincipal();
        String memberId = vo.getUsername();
        util.isMemberId(memberId);


        GoodsVO goodsVO = goodsService.selectOne(goodsNumber);
        if(goodsVO == null){
            throw new GoodsNotFoundException("상품이 존재하지 않음 : " + goodsNumber);
        }

        util.isEqualMemberId(goodsVO.getUserEmail(), memberId);

        goodsVO = new GoodsVO();
        goodsVO.setNumber(goodsNumber);

        goodsVO.setName(request.getParameter("name"));
        goodsVO.setSize(request.getParameter("size"));
        goodsVO.setMaterial(request.getParameter("material"));
        goodsVO.setComponent(request.getParameter("component"));
        goodsVO.setOptions(request.getParameter("options"));
        goodsVO.setManufacturer(request.getParameter("manufacturer"));
        goodsVO.setMadein(request.getParameter("madein"));
        goodsVO.setDescription(request.getParameter("description"));
        goodsVO.setPrice(Integer.parseInt(request.getParameter("price")));
        goodsVO.setStock(Integer.parseInt(request.getParameter("stock")));

        int updateCount = goodsService.update(goodsVO);
        if (updateCount == 0) {
            throw new GoodsNotFoundException("상품이 존재하지 않음 : " + goodsNumber);
        }

        goodsVO = goodsService.selectOne(goodsNumber);

        //modelAndView.addObject("commentVO", commentVO);
        //modelAndView.setViewName("/comment/update");
        //return modelAndView;

        return (ModelAndView)new ModelAndView("redirect:/goods/read.do?p=" + request.getParameter("p")
                +"&goodsNumber=" + request.getParameter("goodsNumber"));
    }

    /*
    상품 삭제
     */
    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    public ModelAndView goodsDelete(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws Exception {

        int goodsNumber = Integer.parseInt(request.getParameter("goodsNumber"));

        UserDetails vo = (UserDetails) auth.getPrincipal();
        String memberId = vo.getUsername();
        util.isMemberId(memberId);

        GoodsVO goodsVO = goodsService.selectOne(goodsNumber);
        if(goodsVO == null){
            throw new GoodsNotFoundException("상품이 존재하지 않음 : " + goodsNumber);
        }

        util.isEqualMemberId(goodsVO.getUserEmail(), memberId);

        goodsService.delete(goodsNumber);

        return (ModelAndView)new ModelAndView("redirect:/goods/list.do?p=" + request.getParameter("p"));
    }
}
