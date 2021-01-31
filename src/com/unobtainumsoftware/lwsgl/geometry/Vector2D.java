package com.unobtainumsoftware.lwsgl.geometry;

import com.unobtainumsoftware.lwsgl.geometry.cartesian.Point;

/**
 * Created by cahenk on 7/22/15.
 */
public class Vector2D {

    private double x, y;

    public Vector2D(){

    }

    public Vector2D(Point start, Point end){
        this.x = start.getX() - end.getY();
        this.y = start.getY() - end.getY();
    }
}
