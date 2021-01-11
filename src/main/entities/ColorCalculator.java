package main.entities;

import com.sun.net.httpserver.Authenticator;
import main.Handler;
import main.PixelColor;
import main.entities.GameObject;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ColorCalculator extends GameObject {

    private int width = 16;
    private int height = 16;

    private PixelColor[][] Matrix = new PixelColor[width][height];

    private char mode = 'R';

    //Fade Pixel Variables
    private ArrayList<FadeData> fadingData = new ArrayList<FadeData>();

    //Random Color Variables
    private long lastRandomTime = 0;
    private double randomRate = 1;
    private double fadeTime = 0;

    //Line Animation Variables
    private double lineSlope = 0;
    private double lineSpacing = 1.5;
    private double lineSpeed = 0.2;
    private ArrayList<PixelColor> lineColors = new ArrayList<PixelColor>();
    private boolean fadeLeadingEdge = true;
    private boolean fadeTrailingEdge = true;
    private double lineDistance = 0;

    public ColorCalculator(Handler handler, int width, int height)
    {
        super(handler, 0, 0);
        this.width = width;
        this.height = height;

        Matrix = new PixelColor[width][height];

        initMatrix();

    }

    public void setMode(char mode) { this.mode = mode; }

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

    public PixelColor[][] GetMatirxColors()
    {
        return Matrix;
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


    @Override
    public void tick()
    {
        switch(mode)
        {
            case 'L':
                break;
            case 'R':
                CalculateRandomColors();
                CalculateFade();
                break;
        }
    }

    @Override
    public void render(Graphics graphics)
    {
        switch(mode)
        {
            case 'L':
                renderLines(graphics);
                break;
            case 'R':
                break;
        }
    }

    //Modes
    //Random Colors
    private void CalculateRandomColors()
    {
        if((System.nanoTime()-lastRandomTime)/1000000000.0 >= 1/randomRate)
        {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);

            if(Matrix[x][y].getRedValue() == 0 && Matrix[x][y].getGreenValue() == 0 && Matrix[x][y].getBlueValue() == 0)
            {
                PixelColor color = new PixelColor((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));

                Matrix[x][y] = color;

                fadingData.add(new FadeData(x, y, fadeTime, color, System.nanoTime()));
            }
            lastRandomTime = System.nanoTime();
        }
    }

    public void setRandomRate(double randomRate) { this.randomRate = randomRate; }
    public void setFadTime(double fadeTime) { this.fadeTime = fadeTime; }

    //Line Animation
    private void CalculateLineAnimation()
    {

    }

    private void renderLines(Graphics graphics)
    {
        double drawDistance = lineDistance;

        while(lineDistance >= 0)
        {


            int x1 = 0;
            int y1 = 0;
            int x2 = 0;
            int y2 = 0;

            graphics.drawLine(0,0,0,0);
        }
    }

    public void setFadeLeadingEdge(boolean fadeLeadingEdge) { this.fadeLeadingEdge = fadeLeadingEdge; }
    public void setFadeTrailingEdge(boolean fadeTrailingEdge) { this.fadeTrailingEdge = fadeTrailingEdge; }
    public void setLineSlope(double lineSlope) { this.lineSlope = lineSlope; }
    public void setLineSpacing(double lineSpacing) { this.lineSpacing = lineSpacing; }
    public void setLineSpeed(double lineSpeed) { this.lineSpeed = lineSpeed; }
    public void addLineColor(PixelColor color) { lineColors.add(color); }
    public void removeLineColor(int index) { lineColors.remove(index); }

    //Calculation Utilities
    private void CalculateFade()
    {
        for(int i = 0; i < fadingData.size(); i++)
        {
            double fadePercentage = 1 - (((double)System.nanoTime() - (double)fadingData.get(i).getStartTime())/(fadingData.get(i).getFadeTime() * 1000000000.0));

            if(fadePercentage <= 0)
            {
                Matrix[fadingData.get(i).getX()][fadingData.get(i).getY()] = new PixelColor(0, 0, 0);
                fadingData.remove(i);
                i--;
            }
            else
            {
                int redValue = (int)(fadingData.get(i).getInitialColor().getRedValue() * fadePercentage);
                int greenValue = (int)(fadingData.get(i).getInitialColor().getGreenValue() * fadePercentage);
                int blueValue = (int)(fadingData.get(i).getInitialColor().getBlueValue() * fadePercentage);

                Matrix[fadingData.get(i).getX()][fadingData.get(i).getY()] = new PixelColor(redValue, greenValue, blueValue);
            }
        }
    }
}
