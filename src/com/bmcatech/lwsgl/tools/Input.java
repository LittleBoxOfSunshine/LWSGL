package com.bmcatech.lwsgl.tools;


import com.bmcatech.lwsgl.game.StateBasedGame;

public abstract class Input{
	
	public static boolean isButtonDown(int keyID){
		return StateBasedGame.keys[keyID];
	}
	
	public static boolean isMouseButtonDown(int keyID){
		if(keyID==0)
			return StateBasedGame.mouse0;
		else
			return StateBasedGame.mouse1;
	}
	
	public static int getX(){
		return StateBasedGame.getMouseX();
	}
	
	public static int getY(){
		return StateBasedGame.getMouseY();
	}

}
