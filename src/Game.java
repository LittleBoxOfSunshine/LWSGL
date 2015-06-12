import java.awt.Graphics;

import com.bmcatech.lwsgl.exception.LWSGLException;
import com.bmcatech.lwsgl.exception.LWSGLStateException;
import com.bmcatech.lwsgl.exception.LWSGLSubStateException;
import com.bmcatech.lwsgl.game.GameState;
import com.bmcatech.lwsgl.game.MultiState;
import com.bmcatech.lwsgl.game.StateBasedGame;
import com.bmcatech.lwsgl.gui.*;

public class Game extends MultiState {
	
	Button loadGame;
	public static final int NOGUI = 0;
	public static final int GUI = 1;
	public  static final int SETTINGS = 2;
	private NoGUI noGui;
	private GUI gui;
	private Settings s;
	public static boolean guiS;
	
	public Game(int id, StateBasedGame sbg) {
		super(id, sbg);
	}
	
	public void init(boolean guiS) throws LWSGLException{
		this.guiS = guiS;
		noGui = new NoGUI(NOGUI, sbg);
		gui = new GUI(GUI, sbg);
		s = new Settings(SETTINGS, sbg);
		addSubState(noGui);
		addSubState(gui);
		addSubState(s);
		enterSubState(SETTINGS);
		initStatesList();
	}

	@Override
	public void initStatesList() throws LWSGLSubStateException{
		for(int i=0; i<getSubStatesSize(); i++)
			getSubState(i).init();
	}

}

class Settings extends GameState {
	
	//Store options from user here before switching either here as public static or make a wrapper class to 
	//hold this data in public static form for the substates to properly load the data.
	Button temp; 
	
	public Settings(int id, StateBasedGame sbg) {
		super(id, sbg);
	}

	@Override
	public void init() {
		temp = new Button("button_base.png", "button_base_hover.png", "New", 250, 100);
	}

	@Override
	public void render(Graphics g) {
		temp.draw(g);
	}

	@Override
	public void update() throws LWSGLStateException{
		if(temp.isClicked()){
			if(Game.guiS)
				Game.enterSubState(Game.GUI);
			else
				Game.enterSubState(Game.NOGUI);
		}
	}
}

class NoGUI extends GameState{
	
	World world;
	Button pause, stop;
	boolean isPaused;
	private final long timeStarted=System.currentTimeMillis();
	
	public NoGUI(int id, StateBasedGame sbg) {
		super(id, sbg);
		isPaused = true;
	}

	@Override
	public void init() {
		world = new World(150);//Load users selected number of steps (frames)
		pause = new Button("button_base.png", "button_base_hover.png", "Start", 250, 100);
		stop = new Button("button_base.png", "button_base_hover.png", "Stop", 250, 200);
		world.start(true);
	}

	@Override
	public void render(Graphics g) {
		pause.draw(g);
		stop.draw(g);
		g.drawString(world.getProgress()+"% Complete...", 250, 275);
		g.drawString("Elapsed: "+formatTime(world.getProcessingTime(),3)+":"+formatTime(world.getProcessingTime(),2)+":"+formatTime(world.getProcessingTime(),1)+":"+formatTime(world.getProcessingTime(),0), 350, 275);
		g.drawString("ETR: N/A", 475, 275);
		String[] out = world.getOutput();
		for(int i=0; i<out.length; i++)
			g.drawString(out[i], 250, 300+i*25);
	}

	@Override
	public void update() throws LWSGLException{
		if(pause.isClicked()){
			if(isPaused){
				pause.setLabel("Pause");
				isPaused = false;
				world.togglePause();
			}
			else{
				pause.setLabel("Resume");
				isPaused = true;
				world.togglePause();
			}
		}
		else if(stop.isClicked()){
			world.stop();
			sbg.enterState(Core.MENU);
		}
	}
	
	private String formatTime(long t, int place){
		//places: 0-sec, 1-minute, 2-hour, 3-day
		byte temp;
		switch(place){
		case 0:
			temp = (byte) (t%60000/1000);
			return (temp<10?"0":"")+temp+"";
		case 1:
			temp = (byte) (t/60000);
			return (temp<10?"0":"")+temp+"";
		case 2:
			temp = (byte) (t/360000);
			return (temp<10?"0":"")+temp+"";
		case 3:
			temp = (byte) (t/8640000);
			return (temp<10?"0":"")+temp+"";
		default:
			System.out.println("ERROR, UNSUPPORTED TIME FORMAT TYPE");
			return null;
		}
	}
	
}

class GUI extends GameState{

	public GUI(int id, StateBasedGame sbg) {
		super(id, sbg);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawString("test gui", 50, 50);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}