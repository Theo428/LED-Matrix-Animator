package main;

import java.awt.Color;
import java.awt.Graphics;

public class Handler
{
	
	public Handler()
	{

	}
	
	public void tick()
	{

	}
	
	public void render(Graphics graphics)
	{
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		

	}
}
