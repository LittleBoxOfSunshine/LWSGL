package com.bmcatech.lwsgl.game;
import com.bmcatech.lwsgl.exception.LWSGLException;
import com.bmcatech.lwsgl.exception.LWSGLMapException;
import com.bmcatech.lwsgl.exception.LWSGLStateException;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.*;

import javax.swing.JFrame;

public abstract class StateBasedGame extends JFrame implements KeyListener, MouseListener, MouseMotionListener{
	
	private Graphics g;
	private BufferedImage bufferGraphics;
	private Random r = new Random();
	private static boolean playing = true;
	private static boolean pause = false;
	protected static int tps = 30, fps=60 , width= 500, height = 500, activeState, mouseX=0, mouseY=0;
	public static boolean mouse0, mouse1;
	public static boolean[] keys = new boolean[255];
	private static long stateSwitchDelay = 100;
	private static Map<Integer, GameState> states = new HashMap<Integer, GameState>();
	private String title;
	
	public StateBasedGame(String title){
		this.title = title;
	}

	public final void init(){//Instantiates Variables and creates and instance of the game class
		mouse0=false;
		mouse1=false;
		setSize(width, height);
		bufferGraphics = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		//done with buffering code	
		//Launches new thread for method calls to allow key listener to function properly
		setTitle(title); 
        setSize(width, height); 
        setResizable(false); 
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
        setVisible(true);
	}
	
	//Contains GameLoop which is launched when thread is started
	public void start() throws LWSGLException{
		initStatesList();
		g = getGraphics();
		long tickTime = System.currentTimeMillis();
		long lastFrameTime = tickTime;
		long tpsTime = 1000 / tps;
		long fpsTime = 1000 / fps;
		while(playing){
			if(pause)
				sleep(100);
			else{
				try{
					if(tpsTime - (System.currentTimeMillis() - tickTime) > 0)
						Thread.sleep(tpsTime - (System.currentTimeMillis() - tickTime));
					else if( fpsTime - (System.currentTimeMillis() - lastFrameTime) > 0)
						Thread.sleep(fpsTime - (System.currentTimeMillis() - lastFrameTime));
				}catch(Exception e){e.printStackTrace();}
				if( tpsTime - (System.currentTimeMillis() - tickTime) <= 0){
                	tickTime = System.currentTimeMillis();
                	update();
                }
				if( fpsTime - (System.currentTimeMillis() - lastFrameTime) <= 0) {
					lastFrameTime = System.currentTimeMillis();
					render();
				}
			}
		}
	}
	public void render(){
		//clear buffer of previous image
		bufferGraphics.getGraphics().clearRect(0, 0, width, height);
		states.get(activeState).render(bufferGraphics.getGraphics());//call user defined render method
		g.drawImage(bufferGraphics, 0, 0, this);//draw updated buffer to screen
	}
	public void update() throws LWSGLException{
		states.get(activeState).update();
	}
	//Sleeps for x milliseconds
	public static void sleep(long x){
		try{
			Thread.sleep(x);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	protected void addState(GameState state) throws LWSGLStateException{
		//Check if id already exists, if it does throw an exception, if not then the new state has been successfully added
		if(states.put(state.getID(), state) != null)
			throw new LWSGLStateException("Error: There is already a state with id = " + state.getID() + "...");
	}
	
	public void enterState(int stateID) throws LWSGLStateException{
		//Delay state switch to avoid unintentional user input
		StateBasedGame.stateSwitchDelay();

		//Check if the requested stateID exists, if it doesn't throw and exception
		if(this.states.get(stateID) == null)
			throw new LWSGLStateException("Error: There is no stat with id = " + stateID + "...");
		else//Valid stateID, switch it to the active state
			this.activeState = stateID;
	}
	
	public GameState getState(int stateID) throws LWSGLStateException{
		GameState tempState = states.get(stateID);
		//If null then requested state does not exist, throw exception else return the requested state
		if(tempState != null)
			return tempState;
		else
			throw new LWSGLStateException("The subState id = " + stateID + " does not exist...");
	}

	//Delay state switch to avoid unintentional user input
	public static void stateSwitchDelay(){
		sleep(StateBasedGame.stateSwitchDelay);
	}

	public static void setStateSwitchDelay(long delay){
		StateBasedGame.stateSwitchDelay = delay;
	}
	
	protected static void setScreenSize(int x, int y){
		width = x;
		height = y;
	}
	
	protected static void setTPS(int x){
		tps = x;
	}
	
	protected static void setFPS(int x){
		fps = x;
	}
	
	public void keyPressed( KeyEvent e ){
		keys[e.getKeyCode()] = true;
	}
	public void keyReleased( KeyEvent e ){
		keys[e.getKeyCode()] = false;
	}
	public void keyTyped( KeyEvent e ){
	}
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mousePressed(MouseEvent e) {
		if(e.getButton()==1)
			mouse0 = true;
		if(e.getButton()==3)
			mouse1 = true;
	}
	public void mouseReleased(MouseEvent e) {
		if(e.getButton()==1)
			mouse0 = false;
		if(e.getButton()==3)
			mouse1 = false;
	}
	public void mouseMoved(MouseEvent me){
		mouseX = me.getX();
		mouseY = me.getY();
	}
	public void mouseDragged(MouseEvent me){
		
	}
	public static int getMouseX(){
		return mouseX;
	}
	public static int getMouseY(){
		return mouseY;
	}
	
	protected abstract void initStatesList() throws LWSGLStateException;
	
}
