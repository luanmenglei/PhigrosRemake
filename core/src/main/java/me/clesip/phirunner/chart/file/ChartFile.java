package me.clesip.phirunner.chart.file;

import java.util.List;

public class ChartFile {
    private String formatVersion;
    private int numOfNotes;
    private double offset;
    private List<JudgeLineFile> judgeLineList;

    public String getFormatVersion() {
        return formatVersion;
    }

    public void setFormatVersion(String formatVersion) {
        this.formatVersion = formatVersion;
    }

    public int getNumOfNotes() {
        return numOfNotes;
    }

    public void setNumOfNotes(int numOfNotes) {
        this.numOfNotes = numOfNotes;
    }

    public double getOffset() {
        return offset;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    public List<JudgeLineFile> getJudgeLineList() {
        return judgeLineList;
    }

    public void setJudgeLineList(List<JudgeLineFile> judgeLineList) {
        this.judgeLineList = judgeLineList;
    }
}
