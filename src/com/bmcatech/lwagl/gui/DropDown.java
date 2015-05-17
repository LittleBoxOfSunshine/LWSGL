package com.bmcatech.lwagl.gui;

import java.awt.*;

public class DropDown extends Component{
		public Button[] options;
		private boolean isOpen;
		public Image i, hover, norm;
		public String label, padding="   ";
		
		public DropDown(String imageLocation, String hoverLocation, String l, int xpos, int ypos, String optionLocation, String optionHoverLocation, String[] options){
			SmartImage i = new SmartImage(imageLocation);
			label = l;
			x = xpos;
			y = ypos;
			width = i.getWidth();
			height = i.getHeight();
			SmartImage tempHover = new SmartImage(hoverLocation);
			hover = tempHover.clone();
			norm = i.clone();
			this.i = i.clone();
			this.options = new Button[options.length];
			isOpen = false;
			int[] positions = centerOptions(ypos, options.length, new SmartImage(optionLocation).getHeight(), height);
			for(int z=0; z<options.length; z++)
				this.options[z] = new Button(optionLocation, optionHoverLocation, options[z], xpos+width-((new SmartImage(optionLocation)).getWidth()), positions[z]);
		}
		
		public DropDown(String imageLocation, String l, int xpos, int ypos, String optionLocation, String optionHoverLocation, String[] options){
			SmartImage i = new SmartImage(imageLocation);
			label = l;
			x = xpos;
			y = ypos;
			width = i.getWidth();
			height = i.getHeight();
			SmartImage tempHover = new SmartImage(imageLocation);
			hover = tempHover.clone();
			norm = i.clone();
			this.i = i.clone();
			this.options = new Button[options.length];
			isOpen = false;
			int[] positions = centerOptions(ypos, options.length, new SmartImage(optionLocation).getHeight(), height);
			for(int z=0; z<options.length; z++)
				this.options[z] = new Button(optionLocation, optionHoverLocation, options[z], xpos+width-((new SmartImage(optionLocation)).getWidth()), positions[z]);
		}
		
		public DropDown(String imageLocation, String l, int xpos, int ypos, String optionLocation, String[] options){
			SmartImage i = new SmartImage(imageLocation);
			label = l;
			x = xpos;
			y = ypos;
			width = i.getWidth();
			height = i.getHeight();
			SmartImage tempHover = new SmartImage(imageLocation);
			hover = tempHover.clone();
			norm = i.clone();
			this.i = i.clone();
			this.options = new Button[options.length];
			isOpen = false;
			int[] positions = centerOptions(ypos, options.length, new SmartImage(optionLocation).getHeight(), height);
			for(int z=0; z<options.length; z++)
				this.options[z] = new Button(optionLocation, options[z], xpos+width-((new SmartImage(optionLocation)).getWidth()), positions[z]);
		}
		
		public boolean isOptionClicked(int index){
			if(options[index].isClicked() && isOpen)
				return true;
			else
				return false;//None are clicked (false)
		}
		
		private void invertOpen(){
			isOpen = !isOpen;
		}
		
		public void paint(Graphics g){
			isClicked();
			if(!isOpen){
				g.drawImage(i, x, y, null);
				//g.setFont("Arial");//Apply change font settings here (get default font from appropriate static class
				g.drawString(padding+label+padding, x, y + height/6);
				g.setColor(Color.black);
				g.drawRect(x, y, width, height);
			}
			else{
				g.drawImage(i, x, y, null);
				//g.setFont("Arial");//Apply change font settings here (get default font from appropriate static class
				g.drawString(padding+label+padding, x, y + height/6);
				g.setColor(Color.black);
				g.drawRect(x, y, width, height);
				for(int i=0; i<options.length; i++)
					options[i].draw(g);
			}
			
		}
		
		private int[] centerOptions(int ypos, int count, int height, int buttonHeight){
			int[] nums = new int[count];
			for(int i=0; i<count; i++)
				nums[i]= buttonHeight+ypos+height*i;
			return nums;
		}

		@Override
		protected void onHover(boolean hover) {
			if(hover)
				i=SmartImage.cloneImage(this.hover);
			else
				i=SmartImage.cloneImage(norm);
		}

		@Override
		protected void onClick(boolean click) {
			invertOpen();
		}
		
		
}
