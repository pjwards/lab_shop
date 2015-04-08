package net.shop.controller;

import javax.servlet.http.HttpServletRequest;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	/* For showing main page */
	@RequestMapping(value="/main/main.do")
	public ModelAndView showMain(Authentication auth) throws Exception{
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
	public String login(@RequestParam(value = "error", required = false) String error,
			HttpServletRequest request,Model model,Authentication auth) throws Exception{
		
		if (error != null) {
			model.addAttribute("say", "Check your Email and Password again");
			model.addAttribute("url", request.getContextPath()+"/main/login.do");
			return "/error/alert";
		}
		return "/main/login";
	}
}
