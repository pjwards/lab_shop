package net.common.controller;

import java.util.List;

import net.common.service.MainService;
import net.common.vo.ExamVO;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
public class MainController {
	
	@Resource(name = "mainService")
	private MainService mainService;
	
	@RequestMapping(value="/net/common/examList.do")
	public ModelAndView examList() throws Exception{
		ModelAndView modelandview = new ModelAndView();
		
		ExamVO lists = mainService.selectOne();
		modelandview.addObject("lists",lists);
		modelandview.setViewName("/common/user/main");
		
		return modelandview;
	}
}
