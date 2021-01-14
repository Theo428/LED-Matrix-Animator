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

    private int maxDrawHeight = Game.HEIGHT;
    private int maxDrawWidth = Game.WIDTH;


    private int squareWidth = 0;
    private Color borderColor = Color.gray;
    private int matrixMajorDimension = width;

    public Matrix(Handler handler, double x, double y, int width, int height)
    {
        super(handler, x, y);

        this.width = width;
        this.height = height;

        Matrix = new PixelColor[width][height];

        this.squareWidth = setSquareSize();

        initMatrix();

        if(width > height)
        {
            matrixMajorDimension = width;
        }
        else
        {
            matrixMajorDimension = height;
        }
    }

    //setters
    public void setWidth(int width)
    {
        this.width = width;
        Matrix = new PixelColor[width][height];

        this.squareWidth = setSquareSize();

        initMatrix();

        if(width > height)
        {
            matrixMajorDimension = width;
        }
        else
        {
            matrixMajorDimension = height;
        }
    }

    public void setHeight(int height)
    {
        this.height = height;
        Matrix = new PixelColor[width][height];

        this.squareWidth = setSquareSize();

        initMatrix();

        if(width > height)
        {
            matrixMajorDimension = width;
        }
        else
        {
            matrixMajorDimension = height;
        }
    }

    public void setMaxDrawHeight(int maxDrawHeight)
    {
        this.maxDrawHeight = maxDrawHeight;

        this.squareWidth = setSquareSize();
    }

    public void setMaxDrawWidth(int maxDrawWidth)
    {
        this.maxDrawWidth = maxDrawWidth;

        this.squareWidth = setSquareSize();
    }

    private int setSquareSize()
    {
        if((int)((maxDrawHeight)/matrixMajorDimension) > (int)((maxDrawWidth)/matrixMajorDimension))
            return (int)((maxDrawWidth)/matrixMajorDimension);
        else
            return (int)((maxDrawHeight)/matrixMajorDimension);
    }

    //Getters
    public int getSquareWidth() { return squareWidth; }
    public int getMatrixMajorDimension() { return matrixMajorDimension; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public String toString()
    {
        String output = "";
        for(int x = 0; x < Matrix.length; x++)
        {
            for(int y = 0; y < Matrix[x].length; y++)
            {
                output += Matrix[x][y].toArduinoString() + ",";
            }
        }
        output += "n";
        return output;
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
        Graphics2D graphics2D = (Graphics2D)graphics;

        for(int x = 0; x < Matrix.length; x++)
        {
            for(int y = 0; y < Matrix[x].length; y++)
            {
                graphics2D.setColor(Matrix[x][y].getColor());
                graphics2D.fillRect((int)getX() + (x * squareWidth), (int)getY() + (((height-1)-y) * squareWidth), squareWidth, squareWidth);
            }
        }
        graphics2D.setColor(borderColor);
        graphics2D.setStroke(new BasicStroke(7));
        graphics2D.drawRect((int)getX(),(int)getY(),squareWidth*width,squareWidth*height);
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
