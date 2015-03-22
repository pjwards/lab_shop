package net.shop.controller;

import java.util.List;

import net.shop.service.UserService;
import net.shop.vo.UserVO;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author jisung
 * 
 *
 */

@Controller
public class UserController {
	
	@Resource(name = "userService")
	private UserService userService;
	
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
	public String userAdd(UserVO userVO) throws Exception{
		userService.insert(userVO);
		
		return "redirect:/user/userList.do";
	}
	
	@RequestMapping("/user/userEdit.do")
	public String userEdit() throws Exception{
		return "/user/userEdit";
	}
	@RequestMapping(value= "/user/userEdit.do", method=RequestMethod.POST)
	public String userEdit(UserVO userVO) throws Exception{
		userService.update(userVO);
		
		return "redirect:/user/userList.do";
	}
	
	@RequestMapping(value= "/user/userDelete.do", method=RequestMethod.POST)
	public String userDelete() throws Exception{
		userService.delete();
		
		return "redirect:/user/userList.do";
	}
}
