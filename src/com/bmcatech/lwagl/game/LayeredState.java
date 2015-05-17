package com.bmcatech.lwagl.game;

import com.bmcatech.lwagl.exception.LWSGLLayerException;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

public abstract class LayeredState extends GameState {
	
	private ArrayList<Layer> images;

	private int lastUpdated;

	//layer just container of buffered image and an id
	public LayeredState(int id, StateBasedGame sbg) {
		super(id, sbg);
	}

	@Override
	public void init() {
		images = new ArrayList<Layer>();
		initLayers();
	}

	@Override
	public void render(Graphics g) {
		try {
			while (lastUpdated < images.size()) {
				if(lastUpdated != 0)
					images.get(lastUpdated).image = images.get(lastUpdated-1).image;
				else
					images.get(lastUpdated).image = new BufferedImage(StateBasedGame.width, StateBasedGame.height, BufferedImage.TYPE_INT_RGB);

				images.get(lastUpdated).image.getGraphics().drawImage(renderLayer(lastUpdated), 0, 0, null);
				lastUpdated++;
			}
			g.drawImage(images.get(images.size() - 1).image, 0, 0, (ImageObserver) this);
		}catch(Exception e){System.out.println("A renderLayer exception has occurred...");}
	}
	
	protected void addLayer(int id){
		images.add(new Layer(id));
	}
	
	protected abstract void initLayers();
	//user creates layers in this method
	protected abstract BufferedImage renderLayer(int index) throws LWSGLLayerException;
	//user calls this when layer is updated and is used as argument for rerenderLayer method
}

class Layer{
	public BufferedImage image;
	public int id;
	public Layer(int index){
		this.id = index;
		image = new BufferedImage(StateBasedGame.width, StateBasedGame.height, BufferedImage.TYPE_INT_RGB);
	}
}