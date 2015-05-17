package com.bmcatech.lwagl.game;

import com.bmcatech.lwagl.exception.LWSGLLayerException;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public abstract class LayeredState extends GameState {
	/*  TO DO:
		replace arraylist with better data structure (hash table)
		caching of frames:
			keep track of the lowest change rendered
			have cacheFrame be the rendered image of all unchanged layers (those beneath lowest change)
			change render to draw the cache then start the loop at lowest change instead of 0
			render also needs to update cacheFrame based on changes to lowest change
			render should reset lowest change each frame

			cache frame should be an array of frames, whenever cache frame is updated it starts at the change and builds up

			following this method then render would just call the end of the cache, not each step past lowest
	 */
	
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
			while (lastUpdated < images.size())
				images.get(lastUpdated).image = renderLayer(lastUpdated++);
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
	}
}