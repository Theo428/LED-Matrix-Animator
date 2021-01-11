package main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas
{
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;

	public static final String WINDOW_NAME = "";

	private Handler handler;

	public Game()
	{
		super();
		handler = new Handler();

		this.setFocusable(true);
		KeyInput keyInput = new KeyInput();
		MouseInput mouseInput = new MouseInput();

		this.addKeyListener(keyInput);
		this.addMouseListener(mouseInput);

		new Window(WIDTH, HEIGHT, WINDOW_NAME, this);
	}

	public void run()
	{
		long lastTime = System.nanoTime();
		double TickPerSecond = 60;
		double NanosecondPerTick = 1000000000 / TickPerSecond;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;


		while(true)
		{
			long now = System.nanoTime();
			delta += (now - lastTime)/ NanosecondPerTick;
			lastTime = now;
			while(delta >= 1)
			{
				tick();
				delta--;
			}

			render();

			frames++;
			if(System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;

			}

		}
	}

	private void tick()
	{
		handler.tick();
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

		handler.render(graphics);

		graphics.dispose();
		bfs.show();
	}
}