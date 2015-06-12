package test;

import java.awt.Graphics;

import com.bmcatech.lwsgl.exception.LWSGLException;
import com.bmcatech.lwsgl.game.GameState;
import com.bmcatech.lwsgl.game.StateBasedGame;
import com.bmcatech.lwsgl.gui.*;

public class MainMenu extends GameState {
	
	Button newGame, loadGame, options, about, exit;
	DropDown d;
	String[] testOptions = {"One","Two","Three"};
	
	public MainMenu(int id, StateBasedGame sbg) {
		super(id, sbg);
	}
	
	public void init(){
		newGame = new Button("button_base.png", "button_base_hover.png", "New", 250, 100);
		loadGame = new Button("button_base.png", "button_base_hover.png", "Load", 250, 200);
		options = new Button("button_base.png", "button_base_hover.png", "Options", 250, 300);
		about = new Button("button_base.png", "button_base_hover.png", "About", 250, 400);
		exit = new Button("button_base.png", "button_base_hover.png", "Exit", 250, 500);
	}
	
	public void render(Graphics g){
		newGame.draw(g);
		loadGame.draw(g);
		options.draw(g);
		about.draw(g);
		exit.draw(g);
	}
	
	public void update() throws LWSGLException{
			if (newGame.isClicked())
				sbg.enterState(Core.CREATE);
			else if (loadGame.isClicked())
				sbg.enterState(Core.LOAD);
			else if (options.isClicked())
				sbg.enterState(Core.CONFIG);
			else if (about.isClicked())
				sbg.enterState(Core.ABOUT);
			else if (exit.isClicked())
				System.exit(0);
	}

}
