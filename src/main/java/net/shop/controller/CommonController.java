package net.shop.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.shop.service.CommonService;
import net.shop.util.ImageUtil;
import net.shop.util.Util;
import net.shop.vo.EmailVO;
import net.shop.vo.FileVO;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
	
	@Resource(name = "commonService")
	private CommonService commonService;
	
	@RequestMapping(value="/upload.do")
	public String upload() throws Exception{
		return "/common/upload";
	}

	//file upload
	@SuppressWarnings("static-access")
	@RequestMapping(value="/upload.do",method=RequestMethod.POST)
	public String upload(@RequestParam(value="file1",required=false) MultipartFile multipartFile, Model model,
			Authentication auth) throws Exception{
		Calendar cal = Calendar.getInstance();
		UserDetails vo = (UserDetails) auth.getPrincipal();
		String loadPath = "/home/jisung/git/lab_shop/src/main/webapp/resource/upload/";
		String uploader = vo.getUsername();
	    String fileName = multipartFile.getOriginalFilename();
	    //String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length()).toLowerCase();
		String fileExt = multipartFile.getOriginalFilename().substring( multipartFile.getOriginalFilename().lastIndexOf( ".") + 1, multipartFile.getOriginalFilename().length());
	    String tempName =  cal.getTimeInMillis() + "";
	    String replaceName = tempName +"."+ fileExt;
	    util.fileUpload(multipartFile, loadPath, replaceName);
	    FileVO fileVO = new FileVO(fileName, tempName, fileExt, uploader, 0);
	    commonService.insert(fileVO);
		return "/common/upload";
	}
	
	//download
	@RequestMapping(value = "/download.do", method = RequestMethod.GET)
	public ModelAndView download(HttpServletRequest request) throws Exception{
	  File file = new File("/home/jisung/git/lab_shop/src/main/webapp/resource/upload/","images.jpg");
	  int boardNumber = 0;
	  FileVO fileVO = commonService.selectOneVo(boardNumber);
	  String fileName = fileVO.getName()+"."+fileVO.getExt();
	  request.setAttribute("fileName", fileName);   //change a name for image. if this line isn't, use the original name
	  
	  return new ModelAndView("fileDownloadView","filedown", file);  
	}
	
	//imageupload
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

	/**
	 * First Editor : Donghyun Seo (egaoneko@naver.com)
	 * Last Editor  :
	 * Date         : 2015-05-04
	 * Description  :
	 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
	 * version      :
	 */
	@RequestMapping(value = "/isLogin.do")
	public String isLogin(Authentication auth) throws Exception {

		util.isMemberId(auth);

		return "common/success";
	}
	
	//daumeditor image upload
	@SuppressWarnings("static-access")
	@RequestMapping(value="/fileUpload.do",method=RequestMethod.POST)
	public void fileUpload(@RequestParam(value="image_file",required=false) MultipartFile multipartFile,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
	
		PrintWriter pw = response.getWriter();
        //String filename = multipartFile.getName().substring(multipartFile.getName().lastIndexOf(".")+1);
		String fileExt = multipartFile.getOriginalFilename().substring( multipartFile.getOriginalFilename().lastIndexOf( ".") + 1, multipartFile.getOriginalFilename().length());
		String path = "/home/jisung/git/lab_shop/src/main/webapp/resource/upload/";
		String realname = UUID.randomUUID().toString() + "." + fileExt;
		
		if(!imageUtil.isImageFile(fileExt)){
			pw.print("400");
			pw.flush();
			pw.close();
			return;
        }

		if(multipartFile.getSize() > 0) {
			util.editorUpload(multipartFile, path, realname);
		}else{
			pw.print("400");
			pw.flush();
			pw.close();
			return;
		}
		
		response.setContentType("text/plain; charset=UTF-8");
	    
	    //json string 값으로 callback
	    //json 값으로 넘기는 필요 값
	    //imageurl, filename,filesize,imagealign
	    pw.print("{\"imageurl\" : \"/lab_shop/resource/upload/"+realname+"\",\"filename\":\""+realname+"\",\"filesize\": 600,\"imagealign\":\"C\"}");
	    pw.flush();
	    pw.close();
	}
	
	@RequestMapping("/email.do")
	public String email() throws Exception{
		return "/common/testEmail";
	}
	@RequestMapping(value="/email.do",method=RequestMethod.POST)
	public String email(@RequestParam("reciver")String reciver,
			@RequestParam("title")String title,
			@RequestParam("content")String content,
			Model model, HttpServletRequest request) throws Exception {
	 
		EmailVO emailVO = new EmailVO();
	    
		if(reciver.isEmpty() || title.isEmpty() || content.isEmpty()){
			model.addAttribute("say", "Please fill all fields!");
			model.addAttribute("url", request.getContextPath()+"/email.do");
		}
	         
		emailVO.setReciver(reciver);
		emailVO.setSubject(title);
		emailVO.setContent(content);
		util.SendEmail(emailVO);
	         
		model.addAttribute("say", "Send it successfully");
		model.addAttribute("url", request.getContextPath()+"/email.do");	
		return "/error/alert";	
	}
}
