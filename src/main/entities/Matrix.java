package main.entities;

import main.Game;
import main.Handler;
import main.PixelColor;

import java.awt.*;

public class Matrix extends GameObject
{
    private int width = 16;
    private int height = 16;
    private PixelColor[][] Matrix = new PixelColor[width][height];

    private int MaxHeight = Game.HEIGHT;

    public Matrix(Handler handler, double x, double y, int width, int height)
    {
        super(handler, x, y);

        this.width = width;
        this.height = height;

        Matrix = new PixelColor[width][height];

        initMatrix();
    }

    public void setWidth(int width)
    {
        this.width = width;
        Matrix = new PixelColor[width][height];
    }

    public void setHeight(int height)
    {
        this.height = height;
        Matrix = new PixelColor[width][height];
    }

    @Override
    public void tick()
    {

    }

    public void tick(PixelColor[][] Matrix)
    {
        this.Matrix = Matrix;
    }

    @Override
    public void render(Graphics graphics)
    {
        int squareWidth = (int)((MaxHeight-32)/width);
        for(int x = 0; x < Matrix.length; x++)
        {
            for(int y = 0; y < Matrix[x].length; y++)
            {
                graphics.setColor(Matrix[x][y].getColor());
                graphics.fillRect((int)getX() + (x * squareWidth), (int)getY() + (y * squareWidth), squareWidth, squareWidth);
            }
        }
    }

    public void initMatrix()
    {
        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++)
            {
                Matrix[x][y] = new PixelColor();
            }
        }
    }
}
