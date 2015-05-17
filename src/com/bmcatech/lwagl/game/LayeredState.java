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
		for(int i=0; i<images.size(); i++)
			g.drawImage(images.get(i).image, 0, 0, (ImageObserver) this);
	}
	
	protected void addLayer(int id){
		images.add(new Layer(id));
	}
	
	protected void setLayerImage(BufferedImage image, int index){
		for(int i=0; i<images.size(); i++)
			if(images.get(i).id==index)
				images.get(i).image=image;
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