package test;

import java.awt.Color;
import java.awt.Graphics;

import com.bmcatech.lwsgl.exception.LWSGLException;
import com.bmcatech.lwsgl.game.GameState;
import com.bmcatech.lwsgl.game.StateBasedGame;
import com.bmcatech.lwsgl.gui.*;

public class About extends GameState {
	
	Button back;
	
	public About(int id, StateBasedGame sbg) {
		super(id, sbg);
	}
	
	public void init(){
		back = new Button("button_base.png", "button_base_hover.png", "Back To Main Menu", 130, 230);
	}
	
	public void render(Graphics g){
		g.setColor(Color.RED);
		g.drawString("Add info here", 200, 150);
		back.draw(g);
	}
	
	public void update() throws LWSGLException {
		if(back.isClicked())
			sbg.enterState(Core.MENU);
	}

}
