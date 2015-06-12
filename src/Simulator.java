import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Simulator {
	
	volatile private static ArrayList<Ball> entities;
	volatile private static ArrayList<Ball> entitiesTempAdd;
	volatile private static ArrayList<Integer> entitiesTempRemove;
	volatile public static Random r = new Random(123456);
	public static int mapWidth = 1000;
	public static int mapHeight = 600;
	
	@SuppressWarnings("unchecked")
	public Simulator(){
		entities = new ArrayList<Ball>();
		entitiesTempAdd = new ArrayList<Ball>();
		entitiesTempRemove = new ArrayList<Integer>();
		for(int i=0; i<100; i++)
			entities.add(new Ball(r.nextInt(mapWidth), r.nextInt(mapHeight), r.nextInt(5)+1, r.nextInt(5)+1));
		Collections.sort(entities);
	}
	
	public static void collide(int i, int z) {
		//Check For Other Object
		if(entities.get(i).collides(entities.get(z))){
			//Add New Ball
			/*
			 *THIS LINE TEMP DISABLED FOR TESTING 
			 *LINES INBETWEEN ADDED FOR TESTING
			 */
			entitiesTempAdd.clear();
			entitiesTempRemove.clear();
			//entitiesTempAdd.add(new Ball(r.nextInt(mapWidth), r.nextInt(mapHeight), r.nextInt(5)+1, r.nextInt(5)+1));	
			//Physics
			int u1x = entities.get(i).xVelocity;
			int u2x = entities.get(z).xVelocity;
			int u1y = entities.get(i).yVelocity;
			int u2y = entities.get(z).yVelocity;
			double m1 = Math.pow(entities.get(i).getRadius(), 2)*Math.PI;
			double m2 = Math.pow(entities.get(z).getRadius(), 2)*Math.PI;
			double mmmmm1 = m1-m2;
			double mmmmm2 = m2-m1;
			double mm1 = m1+m2;
			entities.get(i).xVelocity = (int) ((u1x*mmmmm1+2*m2*u2x)/mm1);
			entities.get(i).yVelocity = (int) ((u1y*mmmmm1+2*m2*u2y)/mm1);
			entities.get(z).xVelocity = (int) ((u2x*mmmmm2+2*m1*u1x)/mm1);
			entities.get(z).yVelocity = (int) ((u2y*mmmmm2+2*m1*u2y)/mm1);
			//Add collision to buffer
			entities.get(i).addException(entities.get(z));
			entities.get(z).addException(entities.get(i));
		}
	}
	
	public static void executeDelayedActions(){
		//remove entities
		//sort entities
		//sorted add new entities
	}
	
	private void sortedAdd(Ball b){//Adds to correct index to maintain the sorted state of the group
		long i = b.pos;
		if(i > entities.get(entities.size()-1).pos){
			entities.add(b);
			return;
		}
		else if(i < entities.get(0).pos){
			entities.add(0, b);
			return;
		}
		int high = entities.size()-1;
		int low = 0;
		int mid = (high+low)/2;
		while(low+1<high){
			if(entities.get(high).pos>= i && i>= entities.get(mid).pos){
				low = mid;
				mid = (high+low)/2;
			}
			else{
				high = mid;
				mid = (high+low)/2;
			}
		}
		entities.add(high, b);
	}
	
	public static Entity getEntity(int index){
		return entities.get(index);
	}
	
	public static void addEntity(){
		
	}
	
	public static void removeEntity(int index){
		entitiesTempRemove.add(index);
	}
	
	public static void remapEntity(){
		
	}
	
	public static int entityCount(){
		return entities.size();
	}

	public static int getLowerBound(int i) {
		if(i==0 || i==1)
			return 0;
		int low = 0;
		int high = i-1;
		int mid = (high+low)/2;
		while(low+1>high){
			if(entities.get(i).intersects(entities.get(mid)))
				break;
			else{
				low = mid;
				mid = (high+low)/2;
			}
		}
		return mid;
	}

	public static int getHigherBound(int i) {
		if(i==entities.size()-2)
			return entities.size()-1;
		int low = i+1;
		int high = entities.size()-1;
		int mid = (high+low)/2;
		while(low+1>high){
			if(entities.get(i).intersects(entities.get(mid)))
				break;
			else{
				high = mid;
				mid = (high+low)/2;
			}
		}
		return mid;
	}
}
