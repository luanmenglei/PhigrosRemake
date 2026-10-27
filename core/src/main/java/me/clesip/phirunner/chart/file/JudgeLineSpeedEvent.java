package me.clesip.phirunner.chart.file;

public class JudgeLineSpeedEvent {
    private double startTime;
    private double endTime;
    private double floorPosition;
    private double value;

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public double getFloorPosition() {
        return floorPosition;
    }

    public void setFloorPosition(double floorPosition) {
        this.floorPosition = floorPosition;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
