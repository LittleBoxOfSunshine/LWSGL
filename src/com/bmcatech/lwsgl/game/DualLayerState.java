package com.bmcatech.lwsgl.game;

import com.bmcatech.lwsgl.exception.LWSGLLayerException;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class DualLayerState extends LayeredState {

	public static final int BACKGROUND = 0;
	public static final int FOREGROUND = 1;
	
	public DualLayerState(int id, StateBasedGame sbg) {
		super(id, sbg);
	}

	@Override
	protected void initLayers() {
		addLayer(BACKGROUND);
		addLayer(FOREGROUND);
	}

	@Override
	protected BufferedImage renderLayer(int index) throws LWSGLLayerException{
		if(index==BACKGROUND){
			BufferedImage temp = new BufferedImage(StateBasedGame.width, StateBasedGame.height, BufferedImage.TYPE_INT_ARGB);
			renderBackground(temp.getGraphics());
			return temp;
		}
		else if(index==FOREGROUND){
			BufferedImage temp = new BufferedImage(StateBasedGame.width, StateBasedGame.height, BufferedImage.TYPE_INT_ARGB);
			renderForeground(temp.getGraphics());
			return temp;
		}
		else
			throw new LWSGLLayerException("The layer with id =" + index + " does not exist...");
	}
	
	public abstract void renderBackground(Graphics g);
	public abstract void renderForeground(Graphics g);
}
