import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.bmcatech.lwsgl.game.GameState;
import com.bmcatech.lwsgl.game.StateBasedGame;


public class TestArea extends GameState{
	
	ArrayList<Ball> balls;
	ArrayList<Ball> ballsTemp;
	Random r = new Random(123456);
	int mapWidth = 1000;
	int mapHeight = 600;
	
	public TestArea(int id, StateBasedGame sbg) {
		super(id, sbg);
		balls = new ArrayList<Ball>();
	}
	
	public void init(){
		balls = new ArrayList<Ball>();
		for(int i=0; i<50; i++)
			balls.add(new Ball(r.nextInt(mapWidth), r.nextInt(mapHeight), r.nextInt(5)+1, r.nextInt(5)+1));
		Collections.sort(balls);
	}
	
	public void render(Graphics g){
		for(int i=0; i<balls.size(); i++){
			g.setColor(balls.get(i).getColor());
			g.fillOval(balls.get(i).getX(), balls.get(i).getY(), balls.get(i).getRadius(), balls.get(i).getRadius());
		}
		if(balls.size()==0){
			g.setColor(Color.RED);
			g.drawString("BALL SPECIES IS NOW EXTINCT", 250, 300);
		}
	}
	
	public void update(){//THIS CODE BELONGS IN UPDATE WORLD SECTION OF CONTROLLER STEP FUNCTION
		long timeStarted = System.currentTimeMillis();
		ballsTemp = new ArrayList<Ball>();
		//Move all balls
		for(int i=0; i<balls.size()-1; i++){
			Ball temp = balls.get(i);
			//Check For Wall Collisions
			if( (temp.getX()<=0 && temp.xVelocity<0) || (temp.getX()+temp.getWidth()>=mapWidth && temp.xVelocity>0) )
				temp.invertXVelocity();
			else if( (temp.getY()<=0 && temp.yVelocity<0) || (temp.getY()+temp.getHeight()>=mapHeight && temp.yVelocity>0) )
				temp.invertYVelocity();
			int lowerBound = getLowerBound(i);
			int higherBound = getHigherBound(i);
			for(int z = lowerBound; z<=higherBound; z++)
				if(z!=i && balls.get(i).intersects(balls.get(z)))
					collide(i, z);
			if(!balls.get(i).isAlive()){//Remove "dead" balls
				balls.remove(i);//remove ball
				i--;//Prevent ball from being skipped
				continue;//Prevents moving of deleted ball
			}
			balls.get(i).move();//Move ball
		}
		//Max Population enforcement
		while(balls.size()>100){
			balls.remove((int)(Math.random() * (balls.size()-1) + 1));
		}
		if(balls.size()==0){
			Core.sleep(5000);
			for(int q=0; q<10; q++)
				balls.add(new Ball(r.nextInt(mapWidth), r.nextInt(mapHeight), r.nextInt(5)+1, r.nextInt(5)+1));		
			Collections.sort(balls);
		}
		//Sort Code (insert into sorted position for faster operation
		//for(int i=0; i<ballsTemp.size(); i++)
			//add(ballsTemp.get(i));
		//Debug code
		double ratio = (System.currentTimeMillis()-timeStarted)/((double)balls.size());
		System.out.println(System.currentTimeMillis()-timeStarted + " - size -> " + balls.size() + "  " + ratio);
	}

	private void collide(int i, int z) {
		//Check For Other Object
		if(balls.get(i).collides(balls.get(z))){
			//Add New Ball
			ballsTemp.add(new Ball(r.nextInt(mapWidth), r.nextInt(mapHeight), r.nextInt(5)+1, r.nextInt(5)+1));	
			//Physics
			int u1x = balls.get(i).xVelocity;
			int u2x = balls.get(z).xVelocity;
			int u1y = balls.get(i).yVelocity;
			int u2y = balls.get(z).yVelocity;
			double m1 = Math.pow(balls.get(i).getRadius(), 2)*Math.PI;
			double m2 = Math.pow(balls.get(z).getRadius(), 2)*Math.PI;
			double mmmmm1 = m1-m2;
			double mmmmm2 = m2-m1;
			double mm1 = m1+m2;
			balls.get(i).xVelocity = (int) ((u1x*mmmmm1+2*m2*u2x)/mm1);
			balls.get(i).yVelocity = (int) ((u1y*mmmmm1+2*m2*u2y)/mm1);
			balls.get(z).xVelocity = (int) ((u2x*mmmmm2+2*m1*u1x)/mm1);
			balls.get(z).yVelocity = (int) ((u2y*mmmmm2+2*m1*u2y)/mm1);
			//Add collision to buffer
			balls.get(i).addException(balls.get(z));
			balls.get(z).addException(balls.get(i));
		}
	}
	
	private void add(Ball b){
		long i = b.pos;
		if(i > balls.get(balls.size()-1).pos){
			balls.add(b);
			return;
		}
		else if(i < balls.get(0).pos){
			balls.add(0, b);
			return;
		}
		int high = balls.size()-1;
		int low = 0;
		int mid = (high+low)/2;
		while(low+1<high){
			if(balls.get(high).pos>= i && i>= balls.get(mid).pos){
				low = mid;
				mid = (high+low)/2;
			}
			else{
				high = mid;
				mid = (high+low)/2;
			}
		}
		balls.add(high, b);
	}

	private int getLowerBound(int i) {
		if(i==0 || i==1)
			return 0;
		int low = 0;
		int high = i-1;
		int mid = (high+low)/2;
		while(low+1>high){
			if(balls.get(i).intersects(balls.get(mid)))
				break;
			else{
				low = mid;
				mid = (high+low)/2;
			}
		}
		return mid;
	}

	private int getHigherBound(int i) {
		if(i==balls.size()-2)
			return balls.size()-1;
		int low = i+1;
		int high = balls.size()-1;
		int mid = (high+low)/2;
		while(low+1>high){
			if(balls.get(i).intersects(balls.get(mid)))
				break;
			else{
				high = mid;
				mid = (high+low)/2;
			}
		}
		return mid;
	}

}
