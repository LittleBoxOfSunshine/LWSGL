package com.unobtainumsoftware.lwsgl.map;

import java.util.Map;

/**
 * Created by cahenk on 5/17/15.
 */
class Layer{
    private int[][] tiles;
    private Map<String, String> properties;

    public Layer(int[][] tiles, Map<String, String> properties){
        this.tiles = tiles;
        this.properties = properties;
    }

    public int getHeight(){
        return this.tiles.length;
    }

    public int getWidth(){
        return this.tiles[0].length;
    }

    public int getTileID(int row, int col){
        return this.tiles[row][col];
    }
}