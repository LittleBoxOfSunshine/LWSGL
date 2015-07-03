package com.bmcatech.lwsgl.game;

import com.bmcatech.lwsgl.exception.LWSGLException;
import com.bmcatech.lwsgl.exception.LWSGLSubStateException;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

public abstract class MultiState extends GameState {

	private static Map<Integer, GameState> subStates;
	protected static int activeSubState;

	public MultiState(int id, StateBasedGame sbg) {
		super(id, sbg);
	}

	@Override
	public void init() {
		subStates = new HashMap<Integer, GameState>();
	}

	@Override
	public void render(Graphics g) {
		subStates.get(activeSubState).render(g);
	}

	@Override
	public void update() throws LWSGLException{
		subStates.get(activeSubState).update();
	}
	
	protected void addSubState(GameState state){
		subStates.put(state.getID(), state);
	}
	
	public static void enterSubState(int stateID) throws LWSGLSubStateException{
		//Delay state switch to avoid unintentional user input
		StateBasedGame.stateSwitchDelay();

		//Check if the requested stateID exists, if it doesn't throw and exception
		if(subStates.get(stateID) == null)
			throw new LWSGLSubStateException("The subState id = " + stateID + " does not exist...");
		else//Valid stateID, switch it to the active state
			activeSubState = stateID;

	}
	
	public GameState getSubState(int stateID) throws LWSGLSubStateException{
		GameState tempState = subStates.get(stateID);
		//If null then requested subState does not exist, throw exception else return the requested subState
		if(tempState != null)
			return tempState;
		else
			throw new LWSGLSubStateException("The subState id = " + stateID + " does not exist...");
	}
	
	public int getSubStatesSize(){
		return subStates.size();
	}
	
	public abstract void initStatesList() throws LWSGLSubStateException;

}
