package com.bmcatech.lwsgl.tools;


import com.bmcatech.lwsgl.game.StateBasedGame;

import javax.swing.plaf.nimbus.State;

public abstract class Input{

	public static final int LEFT_MOUSE_BUTTON=StateBasedGame.LEFT_MOUSE_BUTTON, RIGHT_MOUSE_BUTTON=StateBasedGame.RIGHT_MOUSE_BUTTON;
	
	public static boolean isKeyDown(int keyID){
		return StateBasedGame.keys[keyID];
	}

	public static boolean isKeyDown(char key){
		return StateBasedGame.keys[key];
	}
	
	public static boolean isMouseDown(int keyID){
		if(keyID==LEFT_MOUSE_BUTTON)
			return StateBasedGame.getLeftMouseButton();
		else
			return StateBasedGame.getRightMouseButton();
	}

	public static boolean mouseClicked(int keyID){
		if(keyID==RIGHT_MOUSE_BUTTON)
			return StateBasedGame.getRightMouseClick();
		else
			return StateBasedGame.getLeftMouseClick();
	}

	public static boolean mouseDragged(){
		return true;
	}
	
	public static int getX(){
		return StateBasedGame.getMouseX();
	}
	
	public static int getY(){
		return StateBasedGame.getMouseY();
	}

}
