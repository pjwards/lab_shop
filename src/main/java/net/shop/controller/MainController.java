package net.shop.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MainController {
	//private Logger log = Logger.getLogger(this.getClass());
	
	@RequestMapping("/main/main.do")
	public String showMain() throws Exception{
		//log.debug("Test Logger");
		return "/main/main";
	}
}
