package com.bmcatech.lwagl.gui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SmartImage {

	BufferedImage i;
	
	public SmartImage(String path){
		try {
			i = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public SmartImage(Image temp){
		i = (BufferedImage) temp;
	}
	
	public SmartImage(BufferedImage temp){
		i = temp;
	}

	public int getWidth() {
		return i.getWidth();
	}

	public int getHeight() {
		return i.getHeight();
	}
	
	public Image clone(){
		ColorModel cm = i.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = i.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	public static BufferedImage cloneImage(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	public static BufferedImage cloneImage(Image biO) {
		 BufferedImage bi = (BufferedImage)biO;
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
}
