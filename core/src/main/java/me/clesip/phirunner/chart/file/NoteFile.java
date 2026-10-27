package me.clesip.phirunner.chart.file;

public class NoteFile {
    private int type;
    private int time;
    private double positionX;
    private double holdTime;
    private double speed;
    private double floorPosition;

    public boolean isHightlight() {
        return hightlight;
    }

    public void setHightlight(boolean hightlight) {
        this.hightlight = hightlight;
    }

    private boolean hightlight;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getHoldTime() {
        return holdTime;
    }

    public void setHoldTime(double holdTime) {
        this.holdTime = holdTime;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getFloorPosition() {
        return floorPosition;
    }

    public void setFloorPosition(double floorPosition) {
        this.floorPosition = floorPosition;
    }
}
