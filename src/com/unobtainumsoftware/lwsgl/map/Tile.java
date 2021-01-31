package com.unobtainumsoftware.lwsgl.map;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cahenk on 5/17/15.
 */
class Tile {
    private BufferedImage image;
    private int id;
    private Map<String, String> properties;

    public Tile(int id, BufferedImage image){
        this.id = id;
        this.image = image;
        properties = new HashMap<String, String>();
    }

    public void addProperty(String key, String value){
        this.properties.put(key, value);
    }

    public String getProperty(String key){
        return this.properties.get(key);
    }

    public int getID(){
        return this.id;
    }

    public void drawTile(int x, int y, Graphics g){
        g.drawImage(this.image, x, y, null);
    }
}
