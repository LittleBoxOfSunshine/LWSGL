package test;

import java.awt.Color;
import java.awt.Graphics;

import com.bmcatech.lwsgl.exception.LWSGLException;
import com.bmcatech.lwsgl.game.GameState;
import com.bmcatech.lwsgl.game.StateBasedGame;
import com.bmcatech.lwsgl.gui.element.Button;

public class Create extends GameState {
	
	Button menu, gui, noGui;
	
	public Create(int id, StateBasedGame sbg) {
		super(id, sbg);
	}
	
	public void init(){
		menu = new Button("res/button_base.png", "res/button_base_hover.png", "Back To Main Menu", 130, 230);
		gui = new Button("res/button_base.png", "res/button_base_hover.png", "Show GUI", 130, 130);
		noGui = new Button("res/button_base.png", "res/button_base_hover.png", "No GUI/Pre Render", 450, 130);
	}
	
	public void render(Graphics g){
		g.setColor(Color.RED);
		gui.draw(g);
		noGui.draw(g);
		g.drawString("Add more settings here", 200, 400);
		menu.draw(g);
	}
	
	public void update() throws LWSGLException{
		if(menu.isClicked())
			sbg.enterState(Core.MENU);
		else if(gui.isClicked()){
			((Game)sbg.getState(Core.GAME)).init(true);
			sbg.enterState(Core.GAME);
		}
		else if(noGui.isClicked()){
			((Game)sbg.getState(Core.GAME)).init(false);
			sbg.enterState(Core.GAME);
		}
	}

}
