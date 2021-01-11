package main.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import main.Handler;

public abstract class GameObject 
{
	private double x, y;
	private Handler handler;
	
	public GameObject(Handler handler, double x, double y)
	{
		this.x = x;
		this.y = y;
		this.handler = handler;
	}
	
	public abstract void tick();
	public abstract void render(Graphics graphics);

	public double getX() 
	{
		return x;
	}

	public void setX(double x) 
	{
		this.x = x;
	}

	public double getY() 
	{
		return y;
	}

	public void setY(double y) 
	{
		this.y = y;
	}

	public Handler getHandler() 
	{
		return handler;
	}

	public void setHandler(Handler handler) 
	{
		this.handler = handler;
	}

}
