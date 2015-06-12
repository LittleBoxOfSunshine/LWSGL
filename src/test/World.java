package test;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;


public class World{

	public static boolean paused;
	private final int steps;
	public static int currentStep;
	private static ArrayList<String> output = new ArrayList<String>();
	public static Controller controller;
	public static FrameTable frameTable;
	private static ArrayList<Frame> frames;
	public static Simulator simulator;
	
	public World(int steps){
		paused = true;
		this.steps = steps;
		currentStep=0;
		frames = new ArrayList<Frame>();
		simulator = new Simulator();
	}

	public int getProgress() {
		return (int) (1.0*currentStep/steps*100);
	}

	public String getFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void step() {
		// TODO Auto-generated method stub
		
	}
	
	//render function goes here
	
	public void start(boolean allSteps){
		controller = new Controller(steps, allSteps);
		if(allSteps){
			Thread controllerThread = new Thread(controller);
			controllerThread.start();
			out("Controller Created On New Thread");
		}
		else
			out("Controller Created On Main Thread");
	}

	public void togglePause() {
		out("World Pause Toggled");
		paused = !paused;
	}

	public void save() {
		// TODO Auto-generated method stub
		
	}
	
	public void stop(){
		
	}

	public String[] getOutput() {
		while(output.size()>10)
			output.remove(0);
		String[] temp = new String[output.size()];
		for(int i=0; i<output.size(); i++)
			temp[i]=output.get(i);
		return temp;
	}
	
	public static void out(String text){
		output.add(text);
	}
	
	public static void createFrame(){//Creates frame of current universe state
		
	}
	
	public static Frame getFrame(){//used by player? or just remove
		return null;
	}

	public static void manageMemory() {
		// TODO Auto-generated method stub
		
	}
	
	public long getProcessingTime(){
		return controller.getProcessingTime();
	}
}


class Controller implements Runnable{

	private final int steps;
	private final boolean allSteps;
	public static ArrayList<Worker> workers;
	private static int lastWorkerAssigned=0, jobsThisRotation=0;
	private long timeRendering=0;
	
	public Controller(int steps, boolean allSteps){
		this.steps = steps;
		this.allSteps = allSteps;
		workers = new ArrayList<Worker>();
		//Create Workers
		int i = 0;
		for(; i<7; i++)//create standard worker threads
			workers.add(new Worker(i));
		if(allSteps)//Add extra worker if no gui is present
			workers.add(new Worker(i));
		for(int z=0; z<workers.size(); z++)//Add each worker to thread and launch it
			new Thread(workers.get(z)).start();
	}
	
	@Override
	public void run() {//only called when run as separate thread. Equivalent to allSteps function
		while(World.currentStep<steps){
			if(!World.paused){
				step();
			}
			else
				Core.sleep(100);
		}
	}
	
	public Frame step(){
		long timeStarted = System.currentTimeMillis();
		World.currentStep++;
		//Run step of universe 
		for(int i=0; i<8; i++)//This is all temp code (this loop)
			addJob(3,-1,0);//Create Groups on one thread (grouper adds jobs itself)
		//more grouping jobs here in future
		//////Code that is thread safe and management related goes well here as controller is idling until job queue is empty
		World.manageMemory();//Call World memory management function
		/////End of optimal location for code
		while(getJobCount()>0)//Halt thread until workers are finished
			Core.sleep(5);
		Simulator.executeDelayedActions();//Run actions delayed to help threads safe (entity adds, deletes, etc)
		World.createFrame();//turn current state of universe into a frame
		World.out("Frame "+World.currentStep+" completed");//Delete this line?
		//Debug code
		double ratio = (System.currentTimeMillis()-timeStarted)/((double)Simulator.entityCount());
		World.out(System.currentTimeMillis()-timeStarted + " - size -> " + Simulator.entityCount() + "  " + ratio);
		timeRendering+=System.currentTimeMillis()-timeStarted;
		return null;
	}
	
	public void addJob(int t, int l, int h){
		//if(++jobsThisRotation > 5){
		//	jobsThisRotation = 0;
			if(++lastWorkerAssigned>=workers.size())//if next worker number doesn't exist
				lastWorkerAssigned=0;//revert back to first worker
		//}
		workers.get(lastWorkerAssigned).addJob((byte)t, l, h);
	}
	
	private int getJobCount(){
		int temp = 0;
		for(int i=0; i<workers.size(); i++)
			temp+=workers.get(i).getJobCount();
		return temp;
	}
	
	public long getProcessingTime(){
		return timeRendering;
	}

}

class Worker implements Runnable{

	private Queue<Job> jobs;
	public final int id;
	
	public Worker(int id){
		this.id = id;
		jobs = new LinkedList<Job>();
	}
	
	@Override
	public void run() {
		World.out("Worker "+id+" Launched!\n");
		while(!Thread.currentThread().isInterrupted()){
		    try{
		    	if(World.paused){
		        	World.out("Worker "+id+" Paused!\n");
		        	while(World.paused)
		        		Thread.sleep(100);
		        	World.out("Worker "+id+" Resumed!\n");
		        }
		    	Job job = null;
		    	if(!jobs.isEmpty())
		    		job = jobs.remove();
		    	
		    	if(job==null)//Prevent CPU Spam while controller is enqueing jobs
		    		Core.sleep(10);
		    	else if(job.type==0)
		    		groupExec(job.lower, job.higher);
		    	else if(job.type==1)
		    		saveFrames(job.lower, job.higher);
		    	else if(job.type==2)
		    		memoryManage(job.lower, job.higher);
		    	else if(job.type==3)
		    		group(job.lower, job.higher);
		    	else
		    		World.out("ERROR: INVALID JOB IN QUEUE");
		    }
		    catch(InterruptedException e){
		        Thread.currentThread().interrupt();
		        break; //optional, since the while loop conditional should detect the interrupted state
		    }
		    catch(Exception e){
		        e.printStackTrace();
		    }
		}
		World.out("Worker "+id+" Killed!\n");
	}

	private void groupExec(int lower, int higher){//job type 0
		for(int i=lower; i<higher; i++){
			((Ball) Simulator.getEntity((i+1)*id)).act((i+1)*id);
		}
	}
	
	private void saveFrames(int lower, int higher){//job type 1
		
	}
	
	private void memoryManage(int lower, int higher){//job type 2
		
	}
	
	private void group(int lower, int higher){//job type 3
		//TEMP CODE
		byte lastWorkerAssigned=0;
		for(int i=0; i<250000; i++){
				boolean derp;
				for(int z=0; z<500; z++)
					derp = z*3<4369;
			addJob((byte)0,1,10);
		}
	}
	
	public int getJobCount(){
		return jobs.size();
	}
	
	public void addJob(byte t, int h, int l){
		jobs.add(new Job(t,l,h));
	}
}

///////////Steps for group processing
//check if dead
//check collisions and apply appropriate action
//call move/act function

class Job{
	public byte type;
	public int lower, higher;
	//Lower also functions as subtype flag
	//Higher also functions as priority flag (whether or not to wait for large group or to process partial group of frames)
	public Job(byte type, int lower){
		this.type = type;
		this.lower = lower;
		higher = 0;
	}
	public Job(byte type, int lower, int higher){
		this.type = type;
		this.lower = lower;
		this.higher = higher;
	}
	/*
	 * Flag Definitions/Combos:
	 * Type		lower		higher			Description
	 * 0		starting e	ending e		Execute entity actions on each member in range
	 * 1		0			priority flag	Save All Frames (meant as easier restart point/restart simulator in identical state)
	 * 1		1			priority flag	Save all unsaved flags
	 * 2		0			priority flag	Memory Management (Only run if memory target/threshold is being exceeded [meant for gui mode])
	 * 2		1			priority flag	Memory Management (Remove all obsolete frames, entities, etc currently in memory)
	 * 3		-1,0,1,2,3		N/A				Group Entities (lower marks starting position and direction; -1 is single thread)
	 */
}