package net.common.user.controller;

import java.util.List;

import net.common.user.service.UserService;
import net.common.user.vo.UserVO;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
	
	@Resource(name = "userService")
	private UserService userService;
	
	@RequestMapping(value="/net/common/user/userList.do")
	public ModelAndView examList(HttpServletRequest request) throws Exception{
		ModelAndView modelandview = new ModelAndView();
		int start = 1;
		int end = 5;
		
		if(request.getAttribute("page") != null){
			String mid = (String) request.getAttribute("page");
			start = Integer.parseInt(mid);
		}
		
		List<UserVO> lists = userService.selectList((start-1)*end,end);
		modelandview.addObject("lists", lists);
		int total = userService.count();
		int pages = (int)Math.ceil(total*(1.0)/end);
		modelandview.addObject("pages",pages);
		modelandview.addObject("page",start);
		modelandview.setViewName("/common/user/userList");
		
		return modelandview;
	}
	
}
