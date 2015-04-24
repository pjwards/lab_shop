package net.shop.controller;

import net.shop.error.GoodsNotFoundException;
import net.shop.service.GoodsService;
import net.shop.service.UserService;
import net.shop.util.Util;
import net.shop.vo.GoodsVO;
import net.shop.vo.PagingVO;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

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
    public ModelAndView goodsList(@RequestParam(value = "p", required = false) String requestPageString,
                                  Authentication auth) throws Exception{

        ModelAndView modelAndView = new ModelAndView();

        String memberId = util.isMemberId(auth);

        if(requestPageString == null || requestPageString.equals("")) requestPageString = "1";
        int requestPage = Integer.parseInt(requestPageString);
        if(requestPage <= 0) return (ModelAndView)new ModelAndView("redirect:/goods/list.do");

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
            modelAndView.addObject("hasGoods", false);
            return modelAndView;
        }

        List<GoodsVO> goodsVOList = goodsService.selectList(memberId, pagingVO.getFirstRow(), pagingVO.getEndRow());

        modelAndView.addObject("goodsVOList", goodsVOList);
        modelAndView.addObject("hasGoods", true);

        return modelAndView;
    }

    /*
    상품 등록 폼
     */
    @RequestMapping(value = "/write.do")
    public String goodsWrite(Authentication auth) throws Exception{

        util.isMemberId(auth);
        return "/goods/writeForm";
    }

    /*
    상품 등록
     */
    @RequestMapping(value = "/write.do", method = RequestMethod.POST)
    public String goodsWrite(@RequestParam(value = "name", required = true) String name,
                             @RequestParam(value = "size", required = true) String size,
                             @RequestParam(value = "material", required = true) String material,
                             @RequestParam(value = "component", required = true) String component,
                             @RequestParam(value = "options", required = true) String options,
                             @RequestParam(value = "manufacturer", required = true) String manufacturer,
                             @RequestParam(value = "madein", required = true) String madein,
                             @RequestParam(value = "description", required = true) String description,
                             @RequestParam(value = "price", required = true) Integer price,
                             @RequestParam(value = "stock", required = true) Integer stock,
                             Authentication auth) throws Exception{

        String memberId = util.isMemberId(auth);

        int userNumber = userService.selectOneNo(memberId);

        GoodsVO goodsVO = new GoodsVO();
        goodsVO.setName(name);
        goodsVO.setSize(size);
        goodsVO.setMaterial(material);
        goodsVO.setComponent(component);
        goodsVO.setOptions(options);
        goodsVO.setManufacturer(manufacturer);
        goodsVO.setMadein(madein);
        goodsVO.setDescription(description);
        goodsVO.setPrice(price);
        goodsVO.setStock(stock);
        goodsVO.setUserNumber(userNumber);
        goodsVO.setUserEmail(memberId);

        goodsService.insert(goodsVO);

        return "redirect:/goods/list.do";
    }

    /*
    상품 읽기
     */
    @RequestMapping(value = "/read.do")
    public ModelAndView goodsRead(@RequestParam(value = "goodsNumber", required = true) Integer goodsNumber)
            throws Exception {

        ModelAndView modelAndView = new ModelAndView();

        GoodsVO goodsVO = goodsService.selectOne(goodsNumber);

        if(goodsVO == null) throw new GoodsNotFoundException("상품이 존재하지 않음 : " + goodsNumber);

        modelAndView.addObject("goodsVO", goodsVO);
        modelAndView.setViewName("/goods/read");

        return modelAndView;
    }

    /*
    상품 수정 폼
     */
    @RequestMapping(value = "/update.do")
    public ModelAndView goodsUpdate(@RequestParam(value = "goodsNumber", required = true) Integer goodsNumber,
                                    Authentication auth) throws Exception {

        ModelAndView modelAndView = new ModelAndView();

        String memberId = util.isMemberId(auth);

        GoodsVO goodsVO = goodsService.selectOne(goodsNumber);
        if(goodsVO == null) throw new GoodsNotFoundException("상품이 존재하지 않음 : " + goodsNumber);

        util.isEqualMemberId(goodsVO.getUserEmail(), memberId);

        modelAndView.addObject("goodsVO", goodsVO);
        modelAndView.setViewName("/goods/updateForm");
        return modelAndView;
    }

    /*
    상품 수정
     */
    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    public String goodsUpdate(@RequestParam(value = "goodsNumber", required = true) Integer goodsNumber,
                              @RequestParam(value = "name", required = true) String name,
                              @RequestParam(value = "size", required = true) String size,
                              @RequestParam(value = "material", required = true) String material,
                              @RequestParam(value = "component", required = true) String component,
                              @RequestParam(value = "options", required = true) String options,
                              @RequestParam(value = "manufacturer", required = true) String manufacturer,
                              @RequestParam(value = "madein", required = true) String madein,
                              @RequestParam(value = "description", required = true) String description,
                              @RequestParam(value = "price", required = true) Integer price,
                              @RequestParam(value = "stock", required = true) Integer stock,
                              @RequestParam(value = "p", required = false) String page,
                              Authentication auth) throws Exception {

        String memberId = util.isMemberId(auth);

        GoodsVO goodsVO = goodsService.selectOne(goodsNumber);
        if(goodsVO == null) throw new GoodsNotFoundException("상품이 존재하지 않음 : " + goodsNumber);

        util.isEqualMemberId(goodsVO.getUserEmail(), memberId);

        goodsVO = new GoodsVO();
        goodsVO.setNumber(goodsNumber);

        goodsVO.setName(name);
        goodsVO.setSize(size);
        goodsVO.setMaterial(material);
        goodsVO.setComponent(component);
        goodsVO.setOptions(options);
        goodsVO.setManufacturer(manufacturer);
        goodsVO.setMadein(madein);
        goodsVO.setDescription(description);
        goodsVO.setPrice(price);
        goodsVO.setStock(stock);

        int updateCount = goodsService.update(goodsVO);
        if (updateCount == 0) throw new GoodsNotFoundException("상품이 존재하지 않음 : " + goodsNumber);

        goodsService.selectOne(goodsNumber);

        return "redirect:/goods/read.do?p=" + page + "&goodsNumber=" + goodsNumber;
    }

    /*
    상품 삭제
     */
    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    public String goodsDelete(@RequestParam(value = "goodsNumber", required = true) Integer goodsNumber,
                              @RequestParam(value = "p", required = false) String page,
                              Authentication auth) throws Exception {

        String memberId = util.isMemberId(auth);

        GoodsVO goodsVO = goodsService.selectOne(goodsNumber);
        if(goodsVO == null) throw new GoodsNotFoundException("상품이 존재하지 않음 : " + goodsNumber);

        util.isEqualMemberId(goodsVO.getUserEmail(), memberId);
        goodsService.delete(goodsNumber);

        return "redirect:/goods/list.do?p=" + page;
    }
    
}
