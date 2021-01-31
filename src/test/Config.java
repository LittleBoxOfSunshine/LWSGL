package test;

import java.awt.Color;
import java.awt.Graphics;

import com.unobtainumsoftware.lwsgl.exception.LWSGLException;
import com.unobtainumsoftware.lwsgl.game.GameState;
import com.unobtainumsoftware.lwsgl.game.StateBasedGame;
import com.unobtainumsoftware.lwsgl.gui.element.Button;

public class Config extends GameState {
	
	Button loadGame;
	
	public Config(int id, StateBasedGame sbg) {
		super(id, sbg);
	}
	
	public void init(){
		loadGame = new Button("button_base.png", "button_base_hover.png", "Back To Main Menu", 130, 230);
	}
	
	public void render(Graphics g){
		g.setColor(Color.RED);
		g.drawString("Add info here", 200, 150);
		loadGame.draw(g);
	}
	
	public void update() throws LWSGLException{
		if(loadGame.isClicked())
			sbg.enterState(Core.MENU);
	}

}
