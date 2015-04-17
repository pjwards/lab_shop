package net.shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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
@RequestMapping(value="/main")
public class MainController {
	//private Logger log = Logger.getLogger(this.getClass());
	
	/* For showing main page */
	@RequestMapping(value="/main.do")
	public ModelAndView showMain(Authentication auth) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		
		if(auth != null){
			UserDetails vo = (UserDetails) auth.getPrincipal();
			modelAndView.addObject("vo", vo);
		}
		
		modelAndView.setViewName("/main/main");
		return modelAndView;
	}
	
	@RequestMapping("/admin.do")
	public String showAdmin() throws Exception{
		return "/main/admin";
	}
	
	@RequestMapping("/login.do")
	public String login(@RequestParam(value = "error", required = false) String error,
			HttpServletRequest request,Model model,Authentication auth) throws Exception{
		
		if (error != null) {
			//login form for update page
            //if login error, get the targetUrl from session again.
			String targetUrl = getRememberMeTargetUrlFromSession(request);
			System.out.println(targetUrl);
			
			if(StringUtils.hasText(targetUrl)){
				model.addAttribute("targetUrl", targetUrl);
				model.addAttribute("loginUpdate", true);
				return "/user/userEdit";
			}		
			
			model.addAttribute("say", "Check your Email and Password again");
			model.addAttribute("url", request.getContextPath()+"/main/login.do");
			return "/error/alert";
		}
		return "/main/login";
	}
	
	/**
	 * get targetURL from session
	 */
	private String getRememberMeTargetUrlFromSession(HttpServletRequest request){
		String targetUrl = "";
		HttpSession session = request.getSession(false);
		if(session!=null){
			targetUrl = session.getAttribute("targetUrl")==null?"":session.getAttribute("targetUrl").toString();
		}
		return targetUrl;
	}
}
