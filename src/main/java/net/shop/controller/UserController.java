package net.shop.controller;

import java.util.List;

import net.shop.service.LoginService;
import net.shop.service.UserService;
import net.shop.vo.UserVO;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * First Editor : Jisung Jeon (cbajs20@gmail.com)
 * Last Editor  :
 * Date         : 2015-03-23
 * Description  :
 * Copyright ⓒ 2013-2015 Jisung Jeon All rights reserved.
 * version      :
 */

@Controller
public class UserController {
	
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "loginService")
	private LoginService loginService;
	
	@RequestMapping(value= "/user/userList.do")
	public ModelAndView userList(HttpServletRequest request) throws Exception{
		ModelAndView modelandview = new ModelAndView();
		int start = 1;
		int end = 5;
		if(request.getParameter("page")!= null){
			String mid = (String)request.getParameter("page");
			start = Integer.parseInt(mid);
		}
		
		List<UserVO> lists = userService.selectList((start-1)*end,end);
		modelandview.addObject("lists", lists);
		int total = userService.count();
		int pages = (int)Math.ceil(total*(1.0)/end);
		modelandview.addObject("pages",pages);
		modelandview.addObject("page",start);
		modelandview.setViewName("/user/userList");
		
		return modelandview;
	}
	
	@RequestMapping("/user/userAdd.do")
	public String userAdd() throws Exception{
		return "/user/userAdd";
	}
	@RequestMapping(value= "/user/userAdd.do", method=RequestMethod.POST)
	public String userAdd(@RequestParam("firstName")String firstName,
			@RequestParam("lastName")String lastName,
			@RequestParam("email")String email,
			@RequestParam("password")String password,
			Model model,HttpServletRequest request) throws Exception{
		
		if(firstName.length() < 3 || password.length()<3|| email.length()<6 || lastName.length() < 3){
			model.addAttribute("say", "Wrong Input");
			model.addAttribute("url", request.getContextPath()+"/user/userAdd.do");
			return "/error/alert";
		}
		
		if (userService.selectOne(email)){
			model.addAttribute("say", "It has already used");
			model.addAttribute("url", request.getContextPath()+"/user/userAdd.do");
			return "/error/alert";
		}
		
		String encode = loginService.encoding(password);
		//System.out.println(encode);
		UserVO userVO = new UserVO(firstName,lastName,email,encode);
		userService.insert(userVO);
		
		return "redirect:/main/main.do";
	}
	
	@RequestMapping("/user/userEdit.do")
	public String userEdit() throws Exception{
		return "/user/userEdit";
	}
	@RequestMapping(value= "/user/userEdit.do", method=RequestMethod.POST)
	public String userEdit(@RequestParam("firstName")String firstName,
			@RequestParam("lastName")String lastName,
			@RequestParam("password")String password,
			Model model,Authentication auth,HttpServletRequest request) throws Exception{
		
		if(firstName.length() < 3 || password.length()<3 || lastName.length() < 3){
			model.addAttribute("say", "Wrong Input");
			model.addAttribute("url", request.getContextPath()+"/user/userEdit.do");
			return "/error/alert";
		}
		
		UserDetails vo = (UserDetails) auth.getPrincipal();
		String email = vo.getUsername();
		String encode = loginService.encoding(password);
		UserVO userVO = new UserVO(firstName,lastName,email,encode);
		userService.update(userVO);
		
		return "redirect:/main/main.do";
	}
	
	@RequestMapping(value= "/user/userDelete.do")
	public String userDelete(Authentication auth,HttpServletRequest request) throws Exception{
		UserDetails vo = (UserDetails) auth.getPrincipal();
		String email = vo.getUsername();
		userService.delete(email);
		request.getSession().invalidate();
		return "redirect:/main/main.do";
	}
}
