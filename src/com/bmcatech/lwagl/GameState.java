package com.bmcatech.lwagl;
import com.bmcatech.lwagl.exception.LWSGLException;

import java.awt.Graphics;

public abstract class GameState {
	//Instance Variables
	private int id;
	protected StateBasedGame sbg;
	//Abstract Methods
	public abstract void init();
	public abstract void render(Graphics g);
	public abstract void update() throws LWSGLException;
	//Other Methods
	public GameState(int id, StateBasedGame sbg){
		this.id = id;
		this.sbg = sbg;
	}
	public int getID(){
		return id;
	}
}
