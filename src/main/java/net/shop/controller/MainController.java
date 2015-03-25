package net.shop.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.shop.service.UserService;
import net.shop.vo.UserVO;

import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * First Editor : Jisung Jeon (cbajs20@gmail.com)
 * Last Editor  : Jisung Jeon
 * Date         : 2015-03-23
 * Description  :
 * Copyright ⓒ 2013-2015 Jisung Jeon All rights reserved.
 * version      :
 */

@Controller
public class MainController {
	//private Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name = "userService")
	private UserService userService;
	
	/* For showing main page */
	@RequestMapping(value="/main/main.do")
	public String showMain() throws Exception{
		//log.debug("Test Logger");
		return "/main/main";
	}
	
	//@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping("/main/admin.do")
	public String showAdmin() throws Exception{
		return "/main/admin";
	}
	
	@RequestMapping("/main/login.do")
	public String login(@RequestParam(value = "error", required = false) String error,HttpServletResponse response) throws Exception{
		if (error != null) {
			try{
				response.setCharacterEncoding("utf-8");
				response.getWriter().println("error");
				response.getWriter().close();	
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return "/main/login";
	}
	/*
	@RequestMapping(value="/main/login.do", method=RequestMethod.POST)
	public String login(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UserVO userVO = userService.selectOne(email, password);
		if(userVO != null){
			HttpSession session = request.getSession();
			session.setAttribute("loginInfo", userVO);
			return "redirect:/main/main.do";
		}else{
			try{
				response.setCharacterEncoding("utf-8");
				response.getWriter().println("fail");
				response.getWriter().close();	
			}catch(Exception e){
				e.printStackTrace();
			}
			return "redirect:/main/login.do";
		}
	}
	
	@RequestMapping("/main/logout.do")
	public String logout(HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/main/main.do";
	}*/
}
