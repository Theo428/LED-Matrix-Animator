package main;

import main.entities.ColorCalculator;
import main.entities.Matrix;
import main.entities.SerialManager;

import java.awt.Color;
import java.awt.Graphics;

public class Handler
{
	private Matrix matrix;
	private ColorCalculator colorCalculator;
	private SerialManager serialManager;
	
	public Handler()
	{
		matrix = new Matrix(this, 0, 0, 2, 8);
		colorCalculator = new ColorCalculator(this, matrix.getWidth(), matrix.getHeight());
		serialManager = new SerialManager(this, 0, 0);

		colorCalculator.addLineColor(new PixelColor(255, 0, 0));
		colorCalculator.addLineColor(new PixelColor(0, 255, 0));
		colorCalculator.addLineColor(new PixelColor(0, 0, 255));


	}
	
	public void tick()
	{

		matrix.setX(0.475 * Game.WIDTH);
		matrix.setY(0.04 * Game.HEIGHT);
		matrix.setMaxDrawHeight((int)(0.87*Game.HEIGHT));

		colorCalculator.setMode('R');
		colorCalculator.setMatrixSquareWidth(matrix.getSquareWidth());
		colorCalculator.setX(matrix.getX());
		colorCalculator.setY(matrix.getY());

		colorCalculator.tick();
		matrix.tick(colorCalculator.GetMatrixColors());
		serialManager.tick(matrix.toString());
	}
	
	public void render(Graphics graphics)
	{
		graphics.setColor(new Color(200, 200, 200));
		graphics.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

		matrix.render(graphics);
		colorCalculator.render(graphics);
		serialManager.render(graphics);
	}
}
