package com.bmcatech.lwsgl.game;

import com.bmcatech.lwsgl.exception.LWSGLException;
import com.bmcatech.lwsgl.exception.LWSGLStateException;
import com.bmcatech.lwsgl.gui.Component;

import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;

import javax.swing.JFrame;

public abstract class StateBasedGame extends JFrame implements KeyListener, MouseListener, MouseMotionListener {
	
	private Graphics g;
	private BufferedImage bufferGraphics;
	private Random r = new Random();
	private static boolean playing = true;
	private static boolean pause = false;
	private static ArrayList<Component> componentsWithTypeListeners, componentsWithClickListeners;
	protected static int tps = 30, fps=60 , width= 500, height = 500, activeState, mouseX=0, mouseY=0;
	private static int tickNumber;
	private static boolean leftMouseButton, rightMouseButton, leftMouseClick, rightMouseClick;
	private static KeyBuffer keyBuffer;
	public static boolean[] keys = new boolean[255];
	private static boolean mouseOnScreen;
	private static long stateSwitchDelay = 100;
	private static Map<Integer, GameState> states = new HashMap<Integer, GameState>();
	private String title;
	public static final int LEFT_MOUSE_BUTTON=0, RIGHT_MOUSE_BUTTON=1;

	public StateBasedGame(String title){
		this.title = title;
	}

	public final void init(){//Instantiates Variables and creates and instance of the game class
		keyBuffer = new KeyBuffer();
		componentsWithTypeListeners = new ArrayList<Component>();
		leftMouseButton=false;
		rightMouseButton=false;
		leftMouseClick = false;
		rightMouseClick = false;
		tickNumber=0;
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
		if(keyBuffer.hasEvent()) {
			final char[] keyB = keyBuffer.getKeys();
			for (Component c : componentsWithTypeListeners)
				c.onKeysTyped(keyB);
		}

		tickNumber++;

		states.get(activeState).update();
		leftMouseClick = false;
		rightMouseClick = false;

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

	public static boolean isMouseOnScreen(){
		return mouseOnScreen;
	}
	
	public void keyPressed( KeyEvent e ){
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased( KeyEvent e ){
		keys[e.getKeyCode()] = false;
	}
	public void keyTyped( KeyEvent e ){
		System.out.println(e.getKeyChar());
	}

	public void mouseClicked(MouseEvent e) {
		if(e.getButton()==1)
			leftMouseClick = true;
		if(e.getButton()==3)
			rightMouseClick = true;
	}

	public void mouseEntered(MouseEvent e) {
		this.mouseOnScreen = true;
	}

	public void mouseExited(MouseEvent e) {
		this.mouseOnScreen = false;
	}

	public void mousePressed(MouseEvent e) {
		if(e.getButton()==1)
			leftMouseButton = true;
		if(e.getButton()==3)
			rightMouseButton = true;
	}

	public void mouseReleased(MouseEvent e) {
		if(e.getButton()==1)
			leftMouseButton = false;
		if(e.getButton()==3)
			rightMouseButton = false;
	}

	public void mouseMoved(MouseEvent me){
		mouseX = me.getX();
		mouseY = me.getY();
	}

	public void mouseDragged(MouseEvent me){
		//Integrated mouse Drag implementation isn't useful for this engine but the implemented function is required by the interface
	}

	public static int getMouseX(){
		return mouseX;
	}

	public static int getMouseY(){
		return mouseY;
	}

	public static boolean getLeftMouseButton(){
		return leftMouseButton;
	}

	public static boolean getRightMouseButton(){
		return rightMouseButton;
	}

	public static boolean getLeftMouseClick(){
		return leftMouseClick;
	}

	public static boolean getRightMouseClick(){
		return rightMouseClick;
	}

	protected abstract void initStatesList() throws LWSGLStateException;

	public static int getTickNumber(){
		return tickNumber;
	}

	public static void addComponentTypeListener(Component component){
		componentsWithTypeListeners.add(component);
	}

	public static void addComponentClickListener(Component component){
		componentsWithClickListeners.add(component);
	}
}

class KeyBuffer{
	private static int currentCapacity;
	private static char[] buffer;
	private static final int MAX_BUFFER_SIZE = 1000;
	private static boolean event;

	public KeyBuffer(){
		buffer = new char[MAX_BUFFER_SIZE];
		currentCapacity = 0;
		event = false;
	}

	public static void flush(){
		currentCapacity = 0;
		event = false;
	}

	public static void appendKey(char key){
		buffer[currentCapacity] = key;
		currentCapacity++;
		event = true;
	}

	public static char[] getKeys(){
		event = false;

		if(currentCapacity > 0)
			return Arrays.copyOfRange(buffer, 0, currentCapacity);
		else
			return null;
	}

	public static boolean hasEvent(){
		return event;
	}

}