package main;

import java.awt.*;

public class PixelColor {
    private int redValue = 0;
    private int greenValue = 0;
    private int blueValue = 0;

    public PixelColor ()
    {

    }

    public PixelColor (int redValue, int greenValue, int blueValue)
    {
        this.redValue = redValue;
        this.greenValue = greenValue;
        this.blueValue = blueValue;
    }

    public PixelColor (double[] colorValues)
    {
        this.redValue = (int)colorValues[0];
        this.greenValue = (int)colorValues[1];
        this.blueValue = (int)colorValues[2];
    }

    public void setRedValue(int redValue) { this.redValue = redValue; }
    public void setGreenValue(int greenValue) { this.greenValue = greenValue; }
    public void setBlueValue(int blueValue) { this.blueValue = blueValue; }

    public int getRedValue() { return redValue; }
    public int getGreenValue() { return greenValue; }
    public int getBlueValue() { return blueValue; }

    public Color getColor(){ return new Color(redValue, greenValue, blueValue);}

    public String toString()
    {
        return "r=" + redValue + "g=" + greenValue + "b=" + blueValue;
    }

    public String toArduinoString()
    {
        return redValue + "." + greenValue + "." + blueValue;
    }
}
