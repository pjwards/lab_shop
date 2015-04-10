package net.shop.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
	
}
