package com.bmcatech.lwsgl.gui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.bmcatech.lwsgl.tools.Input;

public abstract class Component {
	
	protected int x, y, width, height;
	protected boolean visible=true;
	
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

		if(Input.mouseClicked(Input.LEFT_MOUSE_BUTTON) && mouseX >= x && mouseX <= x+width && mouseY >= y && mouseY<y+height){
			onClick();
			return true;
		}
		else
			return false;
	}

	public final boolean isRightClicked(){
		int mouseX = Input.getX();
		int mouseY = Input.getY();

		if(Input.mouseClicked(Input.RIGHT_MOUSE_BUTTON) && mouseX >= x && mouseX <= x+width && mouseY >= y && mouseY<y+height){
			onRightClick();
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

	//Rather than being made abstract and forcing empty implementations in subclasses the empty implementation
	//is provided once here and can be overridden if the functionality is desired

	protected void onHover(boolean hover){

	}

	protected void onClick(){

	}

	protected void onRightClick(){

	}

	public void onKeysTyped(final KeyEvent[] keys){
		
	}

}
