package main.entities;

import main.PixelColor;

public class FadeData {

    private int x = 0;
    private int y = 0;
    private double fadeTime = 0;
    private PixelColor initialColor;
    private long startTime = 0;

    public FadeData(int x, int y, double fadeTime, PixelColor initialColor, long startTime)
    {
        this.x = x;
        this.y = y;
        this.fadeTime = fadeTime;
        this.initialColor = initialColor;
        this.startTime = startTime;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getFadeTime() {
        return fadeTime;
    }

    public void setFadeTime(double fadeTime) {
        this.fadeTime = fadeTime;
    }

    public PixelColor getInitialColor() {
        return initialColor;
    }

    public void setInitialColor(PixelColor initialColor) {
        this.initialColor = initialColor;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
