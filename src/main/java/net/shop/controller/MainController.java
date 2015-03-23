package net.shop.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * First Editor : Jisung Jeon (cbajs20@gmail.com)
 * Last Editor  :
 * Date         : 2015-03-23
 * Description  :
 * Copyright ⓒ 2013-2015 Jisung Jeon All rights reserved.
 * version      :
 */

@Controller
public class MainController {
	//private Logger log = Logger.getLogger(this.getClass());
	
	/* For showing main page */
	@RequestMapping("/main/main.do")
	public String showMain() throws Exception{
		//log.debug("Test Logger");
		return "/main/main";
	}
}
