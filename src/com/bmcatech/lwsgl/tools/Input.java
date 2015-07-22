package com.bmcatech.lwsgl.tools;


import com.bmcatech.lwsgl.exception.LWSGLToolException;
import com.bmcatech.lwsgl.geometry.Point;

import java.awt.event.*;
import java.util.Arrays;

public abstract class Input {

	public static final int LEFT_MOUSE_BUTTON = 0, RIGHT_MOUSE_BUTTON = 1;

	//TODO: This should really be an enum (point of this approach/constants approach is to hide access to these settings from newer programmers
	public static final int UID_MOUSE_X = 5050, UID_MOUSE_Y = 5051, UID_MOUSE_LB = 5052, UID_MOUSE_RB = 5053, UID_MOUSE_LC = 5054,
			UID_MOUSE_RC = 5055, UID_MOUSE_SCREEN = 5056;

	private static boolean rightMouseClick, leftMouseClick, rightMouseButton, leftMouseButton, mouseOnScreen;
	private static int mouseX, mouseY;

	private static boolean[] keys = new boolean[255];

	private static KeyBuffer keyBuffer = new KeyBuffer();
	private static DragBuffer dragBuffer = new DragBuffer();

	public static boolean isKeyDown(int keyID) {
		return keys[keyID];
	}

	public static boolean isKeyDown(char key) {
		return keys[key];
	}

	public static boolean isMouseDown(int keyID) {
		if (keyID == LEFT_MOUSE_BUTTON)
			return leftMouseButton;
		else
			return rightMouseButton;
	}

	public static boolean mouseClicked(int keyID) {
		if (keyID == RIGHT_MOUSE_BUTTON)
			return rightMouseClick;
		else
			return leftMouseClick;
	}

	public static boolean mouseDragged() {
		return true;
	}

	public static int getMouseX() {
		return mouseX;
	}

	public static int getMouseY() {
		return mouseY;
	}

	public static boolean isMouseOnScreen() {
		return mouseOnScreen;
	}

	public static void flush() {
		leftMouseClick = false;
		rightMouseClick = false;

		keyBuffer.flush();
		dragBuffer.flush();
	}

	public static void appendKey(KeyEvent e) {
		keyBuffer.appendKey(e);
	}

	public static void appendDrag(int x, int y) {
		dragBuffer.append(x, y);
	}

	public static void updateData(int memberUID, boolean data) {
		try {
			switch (memberUID) {

				case UID_MOUSE_LB:
					leftMouseButton = data;
					break;

				case UID_MOUSE_RB:
					rightMouseButton = data;
					break;

				case UID_MOUSE_LC:
					leftMouseClick = data;
					break;

				case UID_MOUSE_RC:
					rightMouseClick = data;
					break;

				case UID_MOUSE_SCREEN:
					mouseOnScreen = data;

				default:
					if (memberUID <= 255 && memberUID > 0)
						keys[memberUID] = data;
					else
						throw new LWSGLToolException("Member UID -> " + memberUID + " is not a valid input member uid...");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateData(int memberUID, int data) {
		try {
			switch (memberUID) {

				case UID_MOUSE_X:
					mouseX = data;
					break;

				case UID_MOUSE_Y:
					mouseY = data;
					break;

				default:
					throw new LWSGLToolException("Member UID -> " + memberUID + " is not a valid input member uid...");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class KeyBuffer{
	private static int currentCapacity;
	private static KeyEvent[] buffer;
	private static final int MAX_BUFFER_SIZE = 100;
	private static boolean event;

	public KeyBuffer(){
		buffer = new KeyEvent[MAX_BUFFER_SIZE];
		currentCapacity = 0;
		event = false;
	}

	public static void flush(){
		currentCapacity = 0;
		event = false;
	}

	public static void appendKey(KeyEvent key){
		buffer[currentCapacity] = key;
		currentCapacity++;
		event = true;
	}

	public static KeyEvent[] getKeys(){
		if(currentCapacity > 0)
			return Arrays.copyOfRange(buffer, 0, currentCapacity);
		else
			return null;
	}

	public static boolean hasEvent(){
		return event;
	}

}

class DragBuffer{
	private static int currentCapacity;
	private static Point[] buffer;
	private static final int MAX_BUFFER_SIZE = 1000;
	private static boolean event;

	public DragBuffer(){
		buffer = new Point[MAX_BUFFER_SIZE];
		currentCapacity = 0;
		event = false;
	}

	public static void flush(){
		currentCapacity = 0;
		event = false;
	}

	public static void append(int x, int y){
		buffer[currentCapacity].setX(x);
		buffer[currentCapacity].setY(y);
		currentCapacity++;

		event = true;
	}

	public static Point[] getPoints(){
		if(currentCapacity > 0)
			return Arrays.copyOfRange(buffer, 0, currentCapacity);
		else
			return null;
	}

	public static boolean hasEvent(){
		return event;
	}
}