package net.shop.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.shop.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
	public ModelAndView showMain(Authentication auth) throws Exception{
		//log.debug("Test Logger");
		ModelAndView modelAndView = new ModelAndView();
		if(auth != null){
			UserDetails vo = (UserDetails) auth.getPrincipal();
			modelAndView.addObject("vo", vo);
		}
		modelAndView.setViewName("/main/main");
		return modelAndView;
	}
	
	@RequestMapping("/main/admin.do")
	public String showAdmin() throws Exception{
		return "/main/admin";
	}
	
	@RequestMapping("/main/login.do")
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,HttpServletResponse response,HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		/*if (error != null) {
			try{
				modelAndView.addObject("error", error);
				response.sendRedirect(request.getContextPath()+"/main/login.do");
			}catch(Exception e){
				e.printStackTrace();
			}
		}*/
		modelAndView.setViewName("/main/login");
		return modelAndView;
	}
}
