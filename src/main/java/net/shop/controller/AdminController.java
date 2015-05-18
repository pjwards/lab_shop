package net.shop.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.shop.service.AdminService;
import net.shop.util.Util;
import net.shop.vo.OrdersVO;
import net.shop.vo.PagingVO;

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
}
