package net.shop.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.shop.service.UserService;
import net.shop.vo.UserVO;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView showMain() throws Exception{
		//log.debug("Test Logger");
		ModelAndView modelAndView = new ModelAndView();
		
		/*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email =  (String) auth.getPrincipal();
		if(email != null){
			modelAndView.addObject("auth", email);
		}*/
		modelAndView.setViewName("/main/main");
		return modelAndView;
	}
	
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
}
