package test;/*
public class Launcher extends GameState{
	
	boolean gui;
	double progress;
	World world;
	Thread worldThread;
	Button menu, pause, save, exit;
	String pauseText;
	ArrayList<Thread> threads;
	
	public Launcher(int id, StateBasedGame sbg) {
		super(id, sbg);
	}
	
	public void init(boolean gui){
		init();
		this.gui = gui;
		int processors = Runtime.getRuntime().availableProcessors();
		if(!gui){//Create World on own thread
			worldThread = new Thread(world);
			worldThread.start();
			processors--;
		}	
		for(int i=0; i<processors; i++){//Create Workers
			threads.add(new Thread(new Worker(i)));
			threads.get(i).start();
		}
		menu = new Button("button_base.png", "button_base_hover.png", "Back To Main Menu", 250, 400);
		pause = new Button("button_base.png", "button_base_hover.png", pauseText, 250, 400);
		save = new Button("button_base.png", "button_base_hover.png", "Save and Exit", 450, 400);
		exit = new Button("button_base.png", "button_base_hover.png", "Exit Without Saving", 650, 400);
		world.getProgress();
	}
	
	public void render(Graphics g){
		if(gui){//GUI Display
			world.render(g);
		}else{//No GUI display
			if(progress!=100){
				g.drawString("Simulating World... "+progress+"% Complete!", 250, 250);
				pause.draw(g);
				save.draw(g);
				exit.draw(g);
			}
			else{
				g.drawString("Simulation Complete! Load "+world.getFileName()+" to view it!", 250, 250);
				menu.draw(g);
			}
		}
	}
	
	public void update(){
		if(gui)//show world
			world.step();
		else{
			progress = world.getProgress();
			menu.setVisible(progress>=100);
			pause.setVisible(progress<100);
			if(menu.isClicked())
				sbg.enterState(Core.MENU);
			else if(pause.isClicked()){
				world.togglePause();
				if(pauseText.equals("Pause"))
					pauseText="Play";
				else
					pauseText="Pause";
				pause.setLabel(pauseText);
			}
			else if(save.isClicked()){
				world.save();
				this.init();
				sbg.enterState(Core.MENU);
			}
			else if(exit.isClicked()){
				this.init();
				sbg.enterState(Core.MENU);
			}
		}
	}

	@Override
	public void init() {
		pauseText = "Pause";
		worldThread = null;//Clears old thread if being reset
		while(threads!=null && threads.size()>0){//clears old threads if any exit
			threads.get(0).interrupt();//kill the thread
			threads.remove(0);
		}
		threads = new ArrayList<Thread>();
		world = new World(100);
	}

}*/
