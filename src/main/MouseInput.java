package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class MouseInput implements MouseListener
{
	private static boolean inWindow = false;
	private static ArrayList<Integer> MouseButtons;
	private static ArrayList<Integer> MouseX;
	private static ArrayList<Integer> MouseY;
	
	public MouseInput()
	{
		MouseButtons = new ArrayList<Integer>();
		MouseX = new ArrayList<Integer>();
		MouseY = new ArrayList<Integer>();
	}
	
	public void mouseClicked(MouseEvent event)
	{
		
	}
	
	public void mouseEntered(MouseEvent event)
	{
		inWindow = true;
	}
	
	public void mouseExited(MouseEvent event)
	{
		inWindow = false;
	}
	
	public void mousePressed(MouseEvent event)
	{
		if(!MouseButtons.contains(event.getButton()))
		{
			MouseButtons.add(new Integer(event.getButton()));
			MouseX.add(new Integer(event.getX()));
			MouseY.add(new Integer(event.getY()));
		}
	}
	
	public void mouseReleased(MouseEvent event)
	{
		if(MouseButtons.contains(event.getButton()))
		{
			MouseX.remove(MouseButtons.indexOf(event.getButton()));
			MouseY.remove(MouseButtons.indexOf(event.getButton()));
			MouseButtons.remove(new Integer(event.getButton()));
		}
	}
	
	public static boolean getButton(int key)
	{
		return MouseButtons.contains(new Integer(key));
	}
	
	public static int getButtonX(int key)
	{
		if(MouseButtons.contains(new Integer(key)))
		{
			return MouseX.get(MouseButtons.indexOf(new Integer(key)));
		}
		else
		{
			return -1;
		}
	}
	
	public static int getButtonY(int key)
	{
		if(MouseButtons.contains(new Integer(key)))
		{
			return MouseY.get(MouseButtons.indexOf(new Integer(key)));
		}
		else
		{
			return -1;
		}
	}
	
	public static boolean InWindow()
	{
		return inWindow;
	}
}
