package com.bmcatech.lwagl;

import com.bmcatech.lwagl.exception.LWSGLException;
import com.bmcatech.lwagl.exception.LWSGLSubStateException;

import java.awt.Graphics;
import java.util.ArrayList;

public abstract class MultiState extends GameState{

	private static ArrayList<GameState> subStates;
	protected static int activeState;
	
	public MultiState(int id, StateBasedGame sbg) {
		super(id, sbg);
	}

	@Override
	public void init() {
		subStates = new ArrayList<GameState>();
	}

	@Override
	public void render(Graphics g) {
		subStates.get(activeState).render(g);
	}

	@Override
	public void update() throws LWSGLException{
		subStates.get(activeState).update();
	}
	
	protected void addSubState(GameState state){
		subStates.add(state);
	}
	
	public static void enterSubState(int state) throws LWSGLSubStateException{
		StateBasedGame.sleep(100);
		for(int i = 0; i<subStates.size(); i++)
			if(subStates.get(i).getID()==state){
				 activeState = i;
				 return;
			}
		throw new LWSGLSubStateException("The substate id = " + state + " does not exist...");
	}
	
	public GameState getSubState(int state) throws LWSGLSubStateException{
		for(int i = 0; i<subStates.size(); i++)
			if(subStates.get(i).getID()==state)
				return subStates.get(i);
		throw new LWSGLSubStateException("The substate id = " + state + " does not exist...");
	}
	
	public int getSubStatesSize(){
		return subStates.size();
	}
	
	public abstract void initStatesList() throws LWSGLSubStateException;

}
