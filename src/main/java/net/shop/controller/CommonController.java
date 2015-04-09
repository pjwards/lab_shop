package net.shop.controller;

import java.io.File;
import java.util.Calendar;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shop.util.Util;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * First Editor : Jisung Jeon (cbajs20@gmail.com)
 * Last Editor  :
 * Date         : 2015-04-09
 * Description  :
 * Copyright ⓒ 2013-2015 Jisung Jeon All rights reserved.
 * version      :
 */

@Controller
public class CommonController {
	
	@Resource(name = "util")
    private Util util;
	
	@RequestMapping(value="/upload.do")
	public String upload() throws Exception{
		return "/common/upload";
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value="/upload.do",method=RequestMethod.POST)
	public String upload(@RequestParam(value="file1",required=false) MultipartFile multipartFile) throws Exception{
		Calendar cal = Calendar.getInstance();
		String loadPath = "/home/jisung/tempRepo/";
	    String fileName = multipartFile.getOriginalFilename();
	    String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
	    String replaceName = cal.getTimeInMillis() + fileType;
	    util.fileUpload(multipartFile, loadPath, replaceName);
	    
		return "/common/upload";
	}
	
	@RequestMapping(value = "/download.do", method = RequestMethod.GET)
	public ModelAndView download(HttpServletRequest request){
	  File file = new File("/home/jisung/tempRepo/","1428543551897.jpg");
	  request.setAttribute("fileName", "picture.jpg");   //다운 받을 시 이름을 결정합니다. 빼게되면 기존에 저장된 이름으로 받습니다.  
	  
	  return new ModelAndView("fileDownloadView","filedown", file);  
	}

}
