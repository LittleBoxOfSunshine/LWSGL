package com.bmcatech.lwagl;
import com.bmcatech.lwagl.exception.LWSGLException;
import com.bmcatech.lwagl.exception.LWSGLStateException;

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
	private static ArrayList<GameState> states = new ArrayList<GameState>();
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
		while(playing){
			if(pause)
				sleep(100);
			else{
				if( (1000 / tps) - (System.currentTimeMillis() - tickTime) <= 0){
                	tickTime = System.currentTimeMillis();
                	update(); 
                }
				if( (1000 / fps) - (System.currentTimeMillis() - lastFrameTime) <= 0) {
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
	
	protected void addState(GameState state){
		states.add(state);
	}
	
	public void enterState(int state){
		sleep(100);
		for(int i = 0; i<states.size(); i++)
			if(states.get(i).getID()==state){
				 activeState = i;
				 return;
			}
		activeState = 0;//REPLACE WITH ERROR CODE IN FUTURE
	}
	
	public GameState getState(int state){
		for(int i = 0; i<states.size(); i++)
			if(states.get(i).getID()==state)
				return states.get(i);
		return null;//REPLACE WITH ERROR CODE IN FUTURE
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
	
	protected abstract void initStatesList();
	
}
