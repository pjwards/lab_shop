package net.shop.controller;

import java.io.File;
import java.util.Calendar;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shop.util.ImageUtil;
import net.shop.util.Util;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	@Resource(name = "imageUtil")
	private ImageUtil imageUtil;
	
	@RequestMapping(value="/upload.do")
	public String upload() throws Exception{
		return "/common/upload";
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value="/upload.do",method=RequestMethod.POST)
	public String upload(@RequestParam(value="file1",required=false) MultipartFile multipartFile, Model model) throws Exception{
		Calendar cal = Calendar.getInstance();
		String loadPath = "/home/jisung/git/lab_shop/src/main/webapp/resource/upload/";
	    String fileName = multipartFile.getOriginalFilename();
	    String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length()).toLowerCase();
	    String tempName =  cal.getTimeInMillis() + "";
	    String replaceName = tempName + fileType;
	    util.fileUpload(multipartFile, loadPath, replaceName);
	    
		return "/common/upload";
	}
	
	@RequestMapping(value = "/download.do", method = RequestMethod.GET)
	public ModelAndView download(HttpServletRequest request){
	  File file = new File("/home/jisung/git/lab_shop/src/main/webapp/resource/upload/","images.jpg");
	  request.setAttribute("fileName", "picture.jpg");   //change a name for image. if this line isn't, use the original name
	  
	  return new ModelAndView("fileDownloadView","filedown", file);  
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping(value="/imageUpload.do",method=RequestMethod.POST)
	public String imageUpload(@RequestParam(value="file1",required=false) MultipartFile multipartFile, Model model) throws Exception{
		
		if ( multipartFile == null) return "/main/main.do";
		
		String fileExt = multipartFile.getOriginalFilename().substring( multipartFile.getOriginalFilename().lastIndexOf( ".") + 1, multipartFile.getOriginalFilename().length());
		
		File uploadFile =  File.createTempFile( "/home/jisung/", "." + fileExt);
		multipartFile.transferTo( uploadFile);
		
		File imageFile =  File.createTempFile( "/home/jisung/", "." + fileExt);
		//File thumbnail =  new File ("/home/jisung/a.jpg");
		if ( imageUtil.isImageFile ( fileExt))
		{
			imageUtil.uploadImage( uploadFile,imageFile, 100, 100);
			String imageBase64 = imageUtil.encodeToString( imageFile, fileExt);
			model.addAttribute("imageBase64", "data:image/png;base64," + imageBase64);
		}
		
		model.addAttribute("fileName", multipartFile.getOriginalFilename());		
		return "/common/upload";
	}
}
