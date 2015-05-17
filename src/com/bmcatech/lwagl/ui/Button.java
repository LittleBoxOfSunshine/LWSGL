package com.bmcatech.lwagl.ui;

import java.awt.*;

public class Button extends Component{
	
	public Image i, hover, norm;
	public String label, padding="   ";
	
	public Button(String imageLocation, String l, int xpos, int ypos){
			SmartImage i = new SmartImage(imageLocation);
			label = l;
			x = xpos;
			y = ypos;
			width = i.getWidth();
			height = i.getHeight();
			hover = i.clone();
			norm = i.clone();
			this.i = i.clone();
	}
	
	public Button(String imageLocation, String hoverLocation, String l, int xpos, int ypos){
		SmartImage i = new SmartImage(imageLocation);
		label = l;
		x = xpos;
		y = ypos;
		width = i.getWidth();
		height = i.getHeight();
		SmartImage tempHover = new SmartImage(hoverLocation);
		hover = tempHover.clone();
		norm = i.clone();
		this.i = i.clone();
	}
	
	protected void onHover(boolean hover){
		if(hover)
			i=SmartImage.cloneImage(this.hover);
		else
			i=SmartImage.cloneImage(norm);
	}
	
	protected void onClick(boolean click) {
		
	}
	
	public void paint(Graphics g){
		g.drawImage(i, x, y, null);
		g.drawString(padding+label+padding, x+width/4, y + height/2);
	}
	
	public void setLabel(String label){
		this.label = label;
	}
}
