package com.bmcatech.lwagl.ui;
import java.awt.Graphics;

import com.bmcatech.lwagl.Input;

public abstract class Component {
	
	protected int x, y, width, height;
	protected boolean visible=true;
	private long lastClicked=69;
	
	protected final void hoverCheck(){
		int mouseX = Input.getX();
		int mouseY = Input.getY();
		if(mouseX >= x && mouseX <= x+width && mouseY >= y && mouseY<y+height)
			onHover(true);
		else
			onHover(false);
	}
	
	public final boolean isClicked(){
		int mouseX = Input.getX();
		int mouseY = Input.getY();
		boolean isClicked = Input.isMouseButtonDown(0);
		if(!visible || lastClicked+250>System.currentTimeMillis())
			return false;
		else if(mouseX >= x && mouseX <= x+width && mouseY >= y && mouseY<y+height && isClicked){
			lastClicked = System.currentTimeMillis();
			onClick(true);
			return true;
		}
		else
			return false;
	}
	
	public final void draw(Graphics g){
		hoverCheck();
		if(visible)
			paint(g);
	}
	
	public final void setVisible(boolean b){
		visible = b;
	}
	
	protected abstract void paint(Graphics g);
	protected abstract void onHover(boolean hover);
	protected abstract void onClick(boolean click);

}
