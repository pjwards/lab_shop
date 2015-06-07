package net.shop.controller;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.shop.service.AdminService;
import net.shop.service.GoodsService;
import net.shop.service.UserService;
import net.shop.util.Util;
import net.shop.vo.GoodsVO;
import net.shop.vo.OrdersVO;
import net.shop.vo.PagingVO;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/*
First Editor : Jisung Jeon (cbajs20@gmail.com)
Last Editor  :
Date         : 2015-04-21
*/

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	@Resource(name = "adminService")
	private AdminService adminService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "goodsService")
	private GoodsService goodsService;
	
	@Resource(name = "util")
    private Util util;
	
	@RequestMapping("/search.do")
	public ModelAndView searchUser(@RequestParam(value="p",required=false) String p,
			@RequestParam(value="q",required=false) String q,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		ModelAndView modelandview = new ModelAndView();
		
		String requestPageString = p;		//paging
		String keyword = q;					//searching
		
		if(keyword == null || keyword.equals("")) {
        	keyword = null;
        	modelandview.addObject("lists", null);
        	modelandview.setViewName("/admin/searchUser");
        	return modelandview;
        }
		if(requestPageString == null || requestPageString.equals("")) {
            requestPageString = "1";
        }
		
		int requestPage = Integer.parseInt(requestPageString);
		
        if(requestPage <= 0){
            throw new IllegalArgumentException("requestPage <= 0 : " + requestPage);
        }
        
		int totalCount = adminService.count(keyword);
		
		/*Paging 메소드의 사용 */
        PagingVO pagingVO = util.paging(requestPage, 5, totalCount);
        modelandview.addObject("pagingVO", pagingVO);
        modelandview.setViewName("/admin/searchUser");
        if(totalCount == 0){
            modelandview.addObject("lists", null);
            return modelandview;
        }
        
		List<OrdersVO> lists = adminService.selectList(pagingVO.getFirstRow()-1,pagingVO.getEndRow(),keyword);
		
		if(lists.isEmpty()){
			modelandview.addObject("lists", null);
            return modelandview;
		}
		modelandview.addObject("lists", lists);
		modelandview.addObject("keyword", keyword);
		return modelandview;
	}

	/*
	First Editor : Donghyun Seo (egaoneko@naver.com)
	Last Editor  :
	Date         : 2015-06-06
	*/
	@RequestMapping("/orderList.do")
	public ModelAndView orderList(@RequestParam(value="p",required=false) String p,
								  @RequestParam(value="q",required=false) String keyword) throws Exception{

		ModelAndView modelandview = new ModelAndView();
		String requestPageString = p;		//paging

		if(requestPageString == null || requestPageString.equals("")) {
			requestPageString = "1";
		}

		int requestPage = Integer.parseInt(requestPageString);

		if(requestPage <= 0){
			throw new IllegalArgumentException("requestPage <= 0 : " + requestPage);
		}

		if(keyword == null || keyword.equals("")) {
			keyword = null;
		}

		int totalCount = userService.orderCountTotalList(keyword);
		modelandview.addObject("totalCount", totalCount);

		/*Paging 메소드의 사용 */
		PagingVO pagingVO = util.paging(requestPage, 10, totalCount);
		modelandview.addObject("pagingVO", pagingVO);
		modelandview.setViewName("/admin/orderList");

		int totalPrice = 0;

		if(totalCount == 0){
			modelandview.addObject("lists", null);
			modelandview.addObject("totalPrice", totalPrice);
			return modelandview;
		}

		List<OrdersVO> lists = userService.ordersTotalList(pagingVO.getFirstRow()-1,pagingVO.getEndRow(), keyword);

		if(lists.isEmpty()){
			modelandview.addObject("lists", null);
			modelandview.addObject("totalPrice", totalPrice);
			return modelandview;
		}

		for(OrdersVO list : lists){
			totalPrice += list.getQuantity()*list.getTotalPrice();
		}
		modelandview.addObject("lists", lists);
		modelandview.addObject("totalPrice", totalPrice);

		return modelandview;
	}

	/*
	First Editor : Donghyun Seo (egaoneko@naver.com)
	Last Editor  :
	Date         : 2015-06-06
	*/
	@RequestMapping(value = "/goodsList.do")
	public ModelAndView goodsList(@RequestParam(value = "p", required = false) String requestPageString,
								  @RequestParam(value="q",required=false) String keyword) throws Exception{

		ModelAndView modelAndView = new ModelAndView();

		if(requestPageString == null || requestPageString.equals("")) requestPageString = "1";
		int requestPage = Integer.parseInt(requestPageString);
		if(requestPage <= 0) return (ModelAndView)new ModelAndView("redirect:/goods/list.do");

		if(keyword == null || keyword.equals("")) {
			keyword = null;
		}

		int totalCount = goodsService.selectCountForStock(10, 0, keyword);


        /*Paging 메소드의 사용 */
		PagingVO pagingVO = util.paging(requestPage, 10, totalCount);
		modelAndView.addObject("pagingVO", pagingVO);
		modelAndView.setViewName("/admin/goodsList");

        /*
        상품이 없을 때 수행된다.
         */
		if(totalCount == 0){
			modelAndView.addObject("goodsVOList", Collections.<GoodsVO>emptyList());
			modelAndView.addObject("hasGoods", false);
			return modelAndView;
		}

		List<GoodsVO> goodsVOList = goodsService.selectListForStock(10, 0, pagingVO.getFirstRow(), pagingVO.getEndRow(), keyword);

		modelAndView.addObject("goodsVOList", goodsVOList);
		modelAndView.addObject("hasGoods", true);

		return modelAndView;
	}

	@RequestMapping(value = "/statistics.do")
	public String statistics(){
		return "/admin/statistics";
	}
}
