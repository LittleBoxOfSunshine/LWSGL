package test;

import java.util.ArrayList;

public class Frame{

	private ArrayList<Integer> indexes;
	private ArrayList<Long> positions;
	public final int frameID;
	private boolean canDelete;//Flag marking that frame has been saved to the disk and is ok for memory management to remove
	
	public Frame(int frameID){//Create a Frame from scratch
		indexes = new ArrayList<Integer>();
		positions = new ArrayList<Long>();
		this.frameID=frameID;
		canDelete = false;
	}
	
	public Frame(int frameID, int[] id, long[] p){//Insert existing frame data into object form
		this.frameID = frameID;
		indexes = new ArrayList<Integer>();
		positions = new ArrayList<Long>();
		canDelete = false;
		for(int i=0; i<id.length; i++)
			indexes.add(id[i]);
		for(int i=0; i<p.length; i++)
			positions.add(p[i]);
	}
	
	public void addEntity(int id, long position){
		indexes.add(id);
		positions.add(position);
	}
	
	public int getIndex(int index){
		return indexes.get(index);
	}
	
	public long getPosition(int index){
		return positions.get(index);
	}
	
	public int[] getAllIndex(){
		int[] temp = new int[indexes.size()];
		for(int i=0; i<indexes.size(); i++)
			temp[i] = indexes.get(i);
		return temp;
	}
	
	public long[] getAllPosition(){
		long[] temp = new long[positions.size()];
		for(int i=0; i<positions.size(); i++)
			temp[i] = positions.get(i);
		return temp;
	}
	
	public void markForDeletion(){
		canDelete=true;
	}
	
	public boolean canDelete(){
		return canDelete;
	}
}
