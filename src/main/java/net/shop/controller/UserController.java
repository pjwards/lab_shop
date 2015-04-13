package net.shop.controller;

import java.io.File;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import net.shop.service.LoginService;
import net.shop.service.UserService;
import net.shop.util.ImageUtil;
import net.shop.util.Util;
import net.shop.vo.CommentVO;
import net.shop.vo.PagingVO;
import net.shop.vo.UserVO;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * First Editor : Jisung Jeon (cbajs20@gmail.com)
 * Last Editor  :
 * Date         : 2015-03-23
 * Description  :
 * Copyright ⓒ 2013-2015 Jisung Jeon All rights reserved.
 * version      :
 */

@Controller
public class UserController {
	public static final String loadPath = "/home/jisung/git/lab_shop/src/main/webapp/resource/upload/";				

	@Resource(name = "userService")
	private UserService userService;
	
	@Resource(name = "loginService")
	private LoginService loginService;
	
	@Resource(name = "util")
    private Util util;
	
	@Resource(name = "imageUtil")
	private ImageUtil imageUtil;
	
	@RequestMapping(value= "/user/userList.do")
	public ModelAndView userList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		/*
        		웹 브라우저가 게시글 목록을 캐싱하지 않도록 캐시 관련 헤더를 설정
         */
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.addHeader("Cache-Control", "no-store");
        response.setDateHeader("Expire", 1L);
        
		ModelAndView modelandview = new ModelAndView();
		
		String requestPageString = request.getParameter("p");

        if(requestPageString == null || requestPageString.equals("")) {
            requestPageString = "1";
        }

        int requestPage = Integer.parseInt(requestPageString);
		
        if(requestPage <= 0){
            throw new IllegalArgumentException("requestPage <= 0 : " + requestPage);
        }
        
        int totalCount = userService.count();
        
        /*Paging 메소드의 사용 */
        PagingVO pagingVO = util.paging(requestPage, 5, totalCount);
        modelandview.addObject("pagingVO", pagingVO);
        modelandview.setViewName("/user/userList");
        
        if(totalCount == 0){
            modelandview.addObject("userVOList", Collections.<CommentVO>emptyList());
            request.setAttribute("hasUser", new Boolean(false));
            return modelandview;
        }
        
		List<UserVO> lists = userService.selectList(pagingVO.getFirstRow()-1,pagingVO.getEndRow());
		modelandview.addObject("userVOList", lists);
		request.setAttribute("hasUser", new Boolean(true));
		
		return modelandview;
	}
	
	@RequestMapping("/user/userAdd.do")
	public String userAdd() throws Exception{
		return "/user/userAdd";
	}
	@SuppressWarnings("static-access")
	@RequestMapping(value= "/user/userAdd.do", method=RequestMethod.POST)
	public String userAdd(@RequestParam("firstName")String firstName,
			@RequestParam("lastName")String lastName,
			@RequestParam("email")String email,
			@RequestParam("password")String password,
			@RequestParam(value="thumnail",required=false) MultipartFile multipartFile,
			Model model,HttpServletRequest request) throws Exception{
		
		String imagePath = "default.jpg";
		
		if(firstName.length() < 3 || password.length()<3|| email.length()<6 || lastName.length() < 3){
			model.addAttribute("say", "Wrong Input");
			model.addAttribute("url", request.getContextPath()+"/user/userAdd.do");
			return "/error/alert";
		}
		
		if (userService.selectOne(email)){
			model.addAttribute("say", "It has already used");
			model.addAttribute("url", request.getContextPath()+"/user/userAdd.do");
			return "/error/alert";
		}
		
		//upload thumnail
		if ( multipartFile.getSize() > 0 ) {
			String fileName = multipartFile.getOriginalFilename();
			if ( imageUtil.isImageFile ( fileName))
			{
				Calendar cal = Calendar.getInstance();
				//String loadPath = "/home/jisung/git/lab_shop/src/main/webapp/resource/upload/";				
				String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length()).toLowerCase();
				File uploadFile =  File.createTempFile( "/home/jisung/", fileType);
				multipartFile.transferTo( uploadFile);
				String tempName =  cal.getTimeInMillis() + "";
				String replaceName = tempName+"_thum"+ fileType;
				File thumbnail =  new File (loadPath+replaceName);
				imageUtil.uploadImage( uploadFile, thumbnail, 100, 100);
				imagePath = replaceName;
			}
			else{
				model.addAttribute("say", "Wrong Image");
				model.addAttribute("url", request.getContextPath()+"/user/userAdd.do");
				return "/error/alert";
			}
		}
		
		String encode = loginService.encoding(password);
		UserVO userVO = new UserVO(firstName,lastName,email,encode,imagePath);
		userService.insert(userVO);
		
		return "redirect:/main/main.do";
	}
	
	@RequestMapping("/user/userEdit.do")
	public String userEdit(Authentication auth,Model model) throws Exception{
		UserDetails vo = (UserDetails) auth.getPrincipal();
		UserVO userVO = userService.selectOneVo(vo.getUsername());
		model.addAttribute("uservo", userVO);
		return "/user/userEdit";
	}
	@SuppressWarnings("static-access")
	@RequestMapping(value= "/user/userEdit.do", method=RequestMethod.POST)
	public String userEdit(@RequestParam("firstName")String firstName,
			@RequestParam("lastName")String lastName,
			@RequestParam("password")String password,
			@RequestParam(value="thumnail",required=false) MultipartFile multipartFile,
			Model model,Authentication auth,HttpServletRequest request) throws Exception{
		
		UserDetails vo = (UserDetails) auth.getPrincipal();
		String email = vo.getUsername();
		UserVO userVO = userService.selectOneVo(email);
		String imagePath = userVO.getImagePath();
		
		if(firstName.isEmpty() || lastName.isEmpty()){
			firstName = userVO.getFirstName();
			lastName = userVO.getLastName();
		}
		
		if(firstName.length() < 3 || password.length()<3 || lastName.length() < 3){
			model.addAttribute("say", "Wrong Input");
			model.addAttribute("url", request.getContextPath()+"/user/userEdit.do");
			return "/error/alert";
		}
		
		//upload thumnail
		if ( multipartFile.getSize() > 0 ) {
			String fileName = multipartFile.getOriginalFilename();
			if ( imageUtil.isImageFile (fileName))
			{
				Calendar cal = Calendar.getInstance();
				//String loadPath = "/home/jisung/git/lab_shop/src/main/webapp/resource/upload/";				
				String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length()).toLowerCase();
				File uploadFile =  File.createTempFile( "/home/jisung/", fileType);
				multipartFile.transferTo( uploadFile);
				String tempName =  cal.getTimeInMillis() + "";
				String replaceName = tempName+"_thum"+ fileType;
				File thumbnail =  new File (loadPath+replaceName);
				imageUtil.uploadImage( uploadFile, thumbnail, 100, 100);
				imagePath = replaceName;
			}
			else{
				model.addAttribute("say", "Wrong Image");
				model.addAttribute("url", request.getContextPath()+"/user/userAdd.do");
				return "/error/alert";
			}
		}
		
		
		String encode = loginService.encoding(password);

		UserVO loadVO = new UserVO(firstName,lastName,email,encode,imagePath);
		userService.update(loadVO);
	
		return "redirect:/main/main.do";
	}
	
	@RequestMapping(value= "/user/userDelete.do")
	public String userDelete(Authentication auth,HttpServletRequest request) throws Exception{
		UserDetails vo = (UserDetails) auth.getPrincipal();
		String email = vo.getUsername();
		userService.delete(email);
		request.getSession().invalidate();
		return "redirect:/main/main.do";
	}
}
