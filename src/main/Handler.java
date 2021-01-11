package main;

import main.entities.ColorCalculator;
import main.entities.Matrix;

import java.awt.Color;
import java.awt.Graphics;

public class Handler
{
	private Matrix matrix;
	private ColorCalculator colorCalculator;
	
	public Handler()
	{
		matrix = new Matrix(this, 0, 0, 8, 8);
		colorCalculator = new ColorCalculator(this, 8, 8);;

	}
	
	public void tick()
	{
		colorCalculator.setMode('L');

		colorCalculator.tick();
		matrix.tick(colorCalculator.GetMatirxColors());
	}
	
	public void render(Graphics graphics)
	{
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		colorCalculator.render(graphics);
		matrix.render(graphics);
	}
}
