package me.clesip.phirunner.chart.file;

public class JudgeLineMoveEvent {
    private double startTime;
    private double endTime;
    private double start;
    private double end;
    private double start2;
    private double end2;

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

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }

    public double getStart2() {
        return start2;
    }

    public void setStart2(double start2) {
        this.start2 = start2;
    }

    public double getEnd2() {
        return end2;
    }

    public void setEnd2(double end2) {
        this.end2 = end2;
    }

    public JudgeLineMoveEvent() {
        this.start = 0.5;
        this.end = 0.5;
        this.start2 = 0.5;
        this.end2 = 0.5;
    }
}
