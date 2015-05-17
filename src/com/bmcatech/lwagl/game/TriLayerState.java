package com.bmcatech.lwagl.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class TriLayerState extends LayeredState {

    public static final int BACKGROUND = 0;
    public static final int FOREGROUND = 1;
    public static final int HIGHGROUND = 2;

    public TriLayerState(int id, StateBasedGame sbg) {
        super(id, sbg);
    }

    @Override
    protected void initLayers() {
        addLayer(BACKGROUND);
        addLayer(FOREGROUND);
        addLayer(HIGHGROUND);
    }

    @Override
    protected BufferedImage renderLayer(int index) {
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
        else if(index==HIGHGROUND){
            BufferedImage temp = new BufferedImage(StateBasedGame.width, StateBasedGame.height, BufferedImage.TYPE_INT_ARGB);
            renderHighground(temp.getGraphics());
            return temp;
        }
        else
            return null;//REPLACE WITH EXCEPTION IN FUTURE
    }

    public abstract void renderBackground(Graphics g);
    public abstract void renderForeground(Graphics g);
    public abstract void renderHighground(Graphics g);
}
