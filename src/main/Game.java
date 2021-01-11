package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas
{
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	private Map map;
	
	public Game()
	{
		super();
		
		this.setFocusable(true);
		
		new Window(WIDTH, HEIGHT, "LED Matrix Animator", this);
	}
	
	public void run()
	{
		

		
		while(true)
		{
			render();
		}
	}
	
	private void render() 
	{
		BufferStrategy bfs = this.getBufferStrategy();
		if(bfs == null)
		{
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics graphics = bfs.getDrawGraphics();
		
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		map.render(graphics);
		
		graphics.dispose();
		bfs.show();
	}
}
