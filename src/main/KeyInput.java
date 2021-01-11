package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class KeyInput extends KeyAdapter  
{
	private static ArrayList<Integer> keysPressed;
	
	public KeyInput()
	{
		keysPressed = new ArrayList<Integer>();
	}
	
	public void keyPressed(KeyEvent event)
	{
		if(!keysPressed.contains(event.getKeyCode()))
		{
			keysPressed.add(new Integer(event.getKeyCode()));
		}
		
	}
	
	public void keyReleased(KeyEvent event)
	{
		if(keysPressed.contains(new Integer(event.getKeyCode())))
		{
			keysPressed.remove(new Integer(event.getKeyCode()));
		}
	}
	
	public static ArrayList<Integer> getKeysPressed()
	{
		return keysPressed;
	}
	
	public static boolean getKey(int key)
	{
		return keysPressed.contains(new Integer(key));
	}
}
