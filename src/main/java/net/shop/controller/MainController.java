package net.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MainController {

	@RequestMapping("/main/main.do")
	public String showMain() throws Exception{
		return "/main/main";
	}
}
