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

    private int matrixSquareWidth = 0;

    private long lastTickNanoTime = 0;

    //Fade Pixel Variables
    private ArrayList<FadeData> fadingData = new ArrayList<FadeData>();

    //Random Color Variables
    private long lastRandomTime = 0;
    private double randomRate = 10;
    private double fadeTime = 1;

    //Line Animation Variables
    private double lineAngle = 0;
    private double lineSpacing = 5;
    private double lineFadeDistance = 5;
    private double lineSpeed = 2;
    private ArrayList<PixelColor> lineColors = new ArrayList<PixelColor>();
    private boolean fadeLeadingEdge = true;
    private boolean fadeTrailingEdge = true;
    private double lineDistance = 0;

    private int matrixMajorDimension = width;

    public ColorCalculator(Handler handler, int width, int height)
    {
        super(handler, 0, 0);
        this.width = width;
        this.height = height;

        Matrix = new PixelColor[width][height];

        initMatrix();

        if(width > height)
        {
            matrixMajorDimension = width;
        }
        else
        {
            matrixMajorDimension = height;
        }

        lastTickNanoTime = System.nanoTime();
    }

    public void setMode(char mode) { this.mode = mode; }

    public void setWidth(int width)
    {
        this.width = width;
        Matrix = new PixelColor[width][height];

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

    public PixelColor[][] GetMatrixColors()
    {
        return Matrix;
    }

    public void setMatrixSquareWidth(int matrixSquareWidth) { this.matrixSquareWidth = matrixSquareWidth; }

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
                CalculateLineAnimation();
                break;
            case 'R':
                CalculateRandomColors();
                CalculateFade();
                break;
        }

        lastTickNanoTime = System.nanoTime();
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
        double lineXSlope = Math.cos(Math.toRadians(lineAngle));
        double lineYSlope = Math.sin(Math.toRadians(lineAngle));

        for(int x = 0; x < Matrix.length; x++)
        {
            for(int y = 0; y < Matrix[x].length; y++)
            {
                int redValue = 0;
                int greenValue = 0;
                int blueValue = 0;

                double currentLineDistance = lineDistance;
                for(int i = 0; currentLineDistance >= -(lineSpacing * 2 * matrixMajorDimension); i++)
                {
                    PixelColor lineColor = lineColors.get(i % lineColors.size());

                    double distanceToLine = ((lineXSlope*(x + .5)) + (lineYSlope*(y + .5)) - currentLineDistance)/(Math.sqrt(Math.pow(lineXSlope, 2) + Math.pow(lineYSlope, 2)));


                    if(Math.abs(distanceToLine) <= lineFadeDistance)
                    {
                        if((fadeLeadingEdge && distanceToLine >= 0) || (fadeTrailingEdge && distanceToLine <= 0)) {
                            double fadePercentage = 1 - Math.abs(distanceToLine / lineFadeDistance);

                            redValue += (lineColor.getRedValue() * fadePercentage);
                            greenValue += (lineColor.getGreenValue() * fadePercentage);
                            blueValue += (lineColor.getBlueValue() * fadePercentage);
                        }
                        else if(!fadeLeadingEdge && !fadeTrailingEdge)
                        {
                            redValue += lineColor.getRedValue();
                            greenValue += lineColor.getGreenValue();
                            blueValue += lineColor.getBlueValue();
                        }
                    }
                    currentLineDistance -= lineSpacing;
                }

                Matrix[x][y] = new PixelColor(normalizeValues(new double[]{redValue, greenValue, blueValue}, 255));

            }
        }

        double lastTickSeconds = (System.nanoTime()-lastTickNanoTime)/1000000000.0;

        lineDistance += lastTickSeconds * lineSpeed;

        if(lineDistance >= ((lineSpacing * 3 * matrixMajorDimension) + lineSpacing * lineColors.size()))
        {
            lineDistance -= lineSpacing * lineColors.size();
            System.out.println("reset");
        }

    }

    private void renderLines(Graphics graphics)
    {
        double drawDistance = lineDistance;

        int lineNumber = 0;
        while(drawDistance >= -(lineSpacing * 2 * matrixMajorDimension))
        {
            double lineXSlope = Math.cos(Math.toRadians(lineAngle));
            double lineYSlope = Math.sin(Math.toRadians(lineAngle));
            double[][] intersections = new double[4][2];

            intersections[0] = calculateIntersection(lineXSlope, lineYSlope, -drawDistance, 1, 0, 0);
            intersections[1] = calculateIntersection(lineXSlope, lineYSlope, -drawDistance, 1, 0, -width);
            intersections[2] = calculateIntersection(lineXSlope, lineYSlope, -drawDistance, 0, 1, 0);
            intersections[3] = calculateIntersection(lineXSlope, lineYSlope, -drawDistance, 0, 1, -height);

            ArrayList<double[]> finalIntersections = new ArrayList<double[]>();

            for(int i = 0; i < intersections.length; i++)
            {
                if(intersections[i] != null && isInsideMatrix(intersections[i]))
                {
                    finalIntersections.add(intersections[i]);
                }
            }

            if(finalIntersections.size() >= 2)
            {
                int matrixDrawHeight = matrixSquareWidth * height;

                Graphics2D graphics2D = (Graphics2D)graphics;

                graphics2D.setColor(lineColors.get(lineNumber % lineColors.size()).getColor());
                graphics2D.setStroke(new BasicStroke(3));
                graphics2D.drawLine((int)(getX()+(finalIntersections.get(0)[0] * matrixSquareWidth)), (int)(getY()+ (matrixDrawHeight - (finalIntersections.get(0)[1] * matrixSquareWidth))), (int)(getX()+(finalIntersections.get(1)[0] * matrixSquareWidth)), (int)(getY() + (matrixDrawHeight - (finalIntersections.get(1)[1] * matrixSquareWidth))));
            }

            drawDistance -= lineSpacing;
            lineNumber++;
        }
    }

    public void setFadeLeadingEdge(boolean fadeLeadingEdge) { this.fadeLeadingEdge = fadeLeadingEdge; }
    public void setFadeTrailingEdge(boolean fadeTrailingEdge) { this.fadeTrailingEdge = fadeTrailingEdge; }
    public void setLineAngle(double lineAngle) { this.lineAngle = lineAngle; }
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

    private double[] calculateIntersection(double a1, double b1, double c1, double a2, double b2, double c2)
    {
        double bottom = (a1*b2) - (a2*b1);

        double xTop = (b1*c2) - (b2*c1);
        double yTop = (a2*c1) - (a1*c2);

        if(bottom == 0)
            return null;
        else
            return new double[]{xTop / bottom, yTop / bottom};
    }

    private boolean isInsideMatrix(double[] point)
    {
        boolean horizontal = (point[0] >= 0) && (point[0] <= width);
        boolean vertical = (point[1] >= 0) && (point[1] <= height);
        return horizontal && vertical;
    }

    private double[] normalizeValues(double[] values, double limit)
    {
        double maxValue = 0;

        for(int i = 0; i < values.length; i++)
        {
            if(values[i] > maxValue)
                maxValue = values[i];
        }

        if(maxValue > limit)
        {
            for (int i = 0; i < values.length; i++)
            {
                values[i] = (values[i]/maxValue) * limit;
            }
        }
        return values;
    }
}
