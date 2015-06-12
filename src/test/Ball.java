package test;

import java.awt.Color;

public class Ball extends Entity implements Comparable{
	int red, green, blue, speed=2, xVelocity, yVelocity;
	long life, pos;
	boolean directionX, directionY;
	private long time;
	private boolean alive = true;
	boolean immune, predator;
	Ball exception = null;
	int framesSinceCollision=0;
	public int radius = 10;
	private int yFactor = 10000;
	
	public Ball(int xpos, int ypos, int xv, int yv){
		xVelocity = xv;
		yVelocity = yv;
		this.pos = ypos*(yFactor)+xpos;//change 10k to width in later version
		this.directionX = (int)(Math.random() * ((1) + 1)) == 0;
		this.directionY = (int)(Math.random() * ((1) + 1)) == 0;
		time = System.currentTimeMillis();
		red = (int)(Math.random() * ((255) + 1));
		green = (int)(Math.random() * ((255) + 1));
		blue = (int)(Math.random() * ((255) + 1));
		life = (int)(Math.random() * ((25000) + 1)+20000);
		immune = (int)(Math.random() * (100) + 1) >= 75;//5% chance
		if(isDiseased())
			life-=30000;
		radius = (int)(Math.random() * ((18) + 5));
	}
	
	public Ball(int xpos, int ypos, int xv, int yv, int red, int green, int blue){
		xVelocity = xv;
		yVelocity = yv;
		this.pos = ypos*(yFactor)+xpos;//change 10k to width in later version
		this.directionX = (int)(Math.random() * ((1) + 1)) == 0;
		this.directionY = (int)(Math.random() * ((1) + 1)) == 0;
		time = System.currentTimeMillis();
		this.red = red;
		this.green = green;
		this.blue = blue;
		life = (int)(Math.random() * ((25000) + 1)+20000);
		immune = (int)(Math.random() * (100) + 1) >= 75;//5% chance
		if(isDiseased())
			life-=30000;
		radius = (int)(Math.random() * ((18) + 5));
	}
	
	public int getRadius(){
		return radius;
	}
	
	public void move(){
		if(System.currentTimeMillis()-time>life)
			alive = false;
		pos+= yVelocity*yFactor+xVelocity;
		framesSinceCollision++;
	}
	
	public Color getColor(){
		return new Color(red, green, blue);
	}
	
	public int[] getRGB(){
		int[] derp = {red, green, blue};
		return derp;
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	private boolean isDiseased(){
		return red==0 && green==0 && blue==0;
	}
	
	public boolean intersects(Ball z) {
		long zlow = z.getLowestFootprint();
		long zhigh = z.getHighestFootprint();
		long low = getLowestFootprint();
		long high = getHighestFootprint();
		if( (
				(zlow/yFactor>high/yFactor && zlow/yFactor > zhigh/yFactor)
			|| 
				(zlow/yFactor>low/yFactor && low/yFactor > zhigh/yFactor)
			) 
			&& 
			(
				(zlow%yFactor<low%yFactor && low%yFactor < zhigh%yFactor)
			|| 
				(zlow%yFactor<high%yFactor && low%yFactor < zhigh%yFactor)
			)
		)
			return true;
		else
			return false;
	}
	
	public boolean collides(Ball z) {
		if(exception != z || timeout()){
			//test actual collision here
			return true;
		}
		return false;
	}

	private boolean timeout() {
		if(framesSinceCollision>3){
			framesSinceCollision=0;
			exception = null;
		}
		return exception==null;
	}

	public int getX() {
		return (int) (pos%yFactor);
	}

	public int getY() {
		return (int) (pos/yFactor);
	}
	
	public int getWidth() {
		return getRadius();
	}

	public int getHeight() {
		return getRadius();
	}

	public void invertXVelocity() {
		xVelocity*=-1;
	}
	
	public void invertYVelocity() {
		yVelocity*=-1;
	}
	
	private long getLowestFootprint(){//bottom left corner
		return pos+getHeight()*yFactor;
	}
	
	private long getHighestFootprint(){//top right corner
		return pos+getWidth();
	}

	@Override
	public int compareTo(Object arg0) {
		if(((Ball)arg0).pos < this.pos)
			return 1;
		else if(((Ball)arg0).pos == this.pos)
			return 0;
		else
			return -1;
	}

	public void addException(Ball ball) {
		exception = ball;
	}

	public void act(int ballIndex) {
		if( (getX()<=0 && xVelocity<0) || (getX()+getWidth()>=Simulator.mapWidth && xVelocity>0) )
			invertXVelocity();
		else if( (getY()<=0 && yVelocity<0) || (getY()+getHeight()>=Simulator.mapHeight && yVelocity>0) )
			invertYVelocity();
		int lowerBound = Simulator.getLowerBound(ballIndex);
		int higherBound = Simulator.getHigherBound(ballIndex);
		for(int z = lowerBound; z<=higherBound; z++)
			if(z!=ballIndex && intersects((Ball) Simulator.getEntity(z)))
				Simulator.collide(ballIndex, z);
		if(!((Ball) Simulator.getEntity(ballIndex)).isAlive()){//Remove "dead" balls
			Simulator.removeEntity(ballIndex);//remove ball
			return;//Prevents moving of deleted ball
		}
		move();//Move ball
	}
}
