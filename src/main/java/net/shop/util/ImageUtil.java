package net.shop.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.springframework.stereotype.Component;

/**
 * First Editor : Jisung Jeon (cbajs20@gmail.com)
 * Last Editor  :
 * Date         : 2015-04-10
 * Description  :
 * Copyright ⓒ 2013-2015 Jisung Jeon All rights reserved.
 * version      :
 */

@Component("imageUtil")
public class ImageUtil {
	
	public static final int SAME = -1;
	public static final int RATIO = 0;
	/*
	 * check that is image file or not
	 */
	public static boolean isImageFile ( String fileType)
	{
		
		String imageFormat = "";
		if ( fileType.indexOf( ".") > 0)
		{
			//Only get format of image in the image name
			imageFormat = fileType.substring( fileType.lastIndexOf(".") + 1, fileType.length()).toLowerCase();
		}
		else
		{
			imageFormat = fileType;
		}
		
		if ( "jpg".equalsIgnoreCase( imageFormat) || "jpeg".equalsIgnoreCase( imageFormat) 
			 ||"gif".equalsIgnoreCase( imageFormat) || "png".equalsIgnoreCase( imageFormat))
		{
			return true;
		}
		
		return false;
	}
	
	/*
	 * resize image
	 */
	public static void resizeImage(String loadFile, String saveFile, int maxDim) throws IOException 
	{
		File save = new File(saveFile.replaceAll("/", "\\" + File.separator));
		FileInputStream fis = new FileInputStream(loadFile.replaceAll("/", "\\"+ File.separator));
			  
		BufferedImage im = ImageIO.read(fis);
		Image inImage = new ImageIcon(loadFile).getImage();
			  
		double scale = (double) maxDim / (double) inImage.getHeight(null);
			  
		if (inImage.getWidth(null) > inImage.getHeight(null)) {
			scale = (double) maxDim / (double) inImage.getWidth(null);
		}
			  
		int scaledW = (int) (scale * inImage.getWidth(null));
		int scaledH = (int) (scale * inImage.getHeight(null));
			  
		//allocate information for making image
		BufferedImage thumb = new BufferedImage(scaledW, scaledH,BufferedImage.TYPE_INT_RGB);
			  
		//draw image
		Graphics2D g2 = thumb.createGraphics();
		g2.drawImage(im, 0, 0, scaledW, scaledH, null);
			  
		ImageIO.write(thumb, "jpg", save);//make image
	}
	
	/*
	 * upload image 
	 */
	public static void uploadImage(File src, File dest, int width, int height) throws IOException{
		FileInputStream srcIs = null;
		
		try {
			srcIs = new FileInputStream(src);
			ImageUtil.uploadImage(srcIs, dest, width, height);
		} finally{
			if (srcIs != null){
				try {
					srcIs.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
	}
	public static void uploadImage(InputStream src, File dest, int width, int height) throws IOException {
		
		BufferedImage srcImg = ImageIO.read(src);
		
		int srcWidth = srcImg.getWidth();
		int srcHeight = srcImg.getHeight();
		
		int destWidth = -1;
		int destHeight = -1;
		
		if(width == SAME){
			destWidth = srcWidth;
		}else if (width > 0){
			destWidth = width;
		}
		
		if(height == SAME){
			destHeight = srcHeight;
		}else if (width > 0){
			destHeight = height;
		}
		
		if(width == RATIO && height == RATIO){
			destWidth = srcWidth;
			destHeight = srcHeight;
		}else if (width == RATIO){
			double ratio = ((double)destHeight) / ((double)destHeight);
			destWidth = (int)((double)srcWidth * ratio);
		}else if (height == RATIO){
			double ratio = ((double)destWidth) / ((double)destWidth);
			destHeight = (int)((double)srcHeight * ratio);
		}
		/***************************************/
		
		BufferedImage destImg = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_3BYTE_BGR);
		
		Graphics2D g = destImg.createGraphics();
		g.drawImage(srcImg, 0, 0, destWidth, destHeight, null);
		
		ImageIO.write(destImg, "jpg", dest);
	}
	
}
