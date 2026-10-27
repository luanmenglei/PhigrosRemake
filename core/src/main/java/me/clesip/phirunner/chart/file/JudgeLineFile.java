package me.clesip.phirunner.chart.file;

import me.clesip.phirunner.util.PhigrosUtil;

import java.util.Comparator;
import java.util.List;

public class JudgeLineFile {
    private int numOfNotes;
    private int numOfNotesAbove;
    private int numOfNotesBelow;
    private double bpm;
    private List<JudgeLineSpeedEvent> speedEvents;
    private List<JudgeLineDisappearEvent> judgeLineDisappearEvents;
    private List<JudgeLineMoveEvent> judgeLineMoveEvents;
    private List<JudgeLineRotateEvent> judgeLineRotateEvents;
    private List<NoteFile> notesAbove;
    private List<NoteFile> notesBelow;
    private JudgeLineSpeedEvent currentSpeedEvent;
    private int currentSpeedEventID;
    private JudgeLineMoveEvent currentMoveEvent;
    private int currentMoveEventID;
    private JudgeLineDisappearEvent currentDisappearEvent;
    private int currentDisappearEventID;
    private JudgeLineRotateEvent  currentRotateEvent;
    private int currentRotateEventID;

    public void init() {
        judgeLineMoveEvents.sort(Comparator.comparingDouble(JudgeLineMoveEvent::getStartTime));
        judgeLineDisappearEvents.sort(Comparator.comparingDouble(JudgeLineDisappearEvent::getStartTime));
        judgeLineRotateEvents.sort(Comparator.comparingDouble(JudgeLineRotateEvent::getStartTime));
        speedEvents.sort(Comparator.comparingDouble(JudgeLineSpeedEvent::getStartTime));

        currentSpeedEventID = 0;
        currentDisappearEventID = 0;
        currentMoveEventID = 0;
        currentRotateEventID = 0;
        if (!speedEvents.isEmpty())
            currentSpeedEvent = speedEvents.get(0);
        if (!judgeLineRotateEvents.isEmpty())
            currentRotateEvent = judgeLineRotateEvents.get(0);
        if (!judgeLineMoveEvents.isEmpty())
            currentMoveEvent = judgeLineMoveEvents.get(0);
        if (!judgeLineDisappearEvents.isEmpty())
            currentDisappearEvent = judgeLineDisappearEvents.get(0);
    }

    public void update(float musicTime) {
        double currentGameBeat = PhigrosUtil.timeToGameBeat(musicTime, bpm);

        if (currentSpeedEvent != null) {
            while (currentSpeedEvent.getEndTime() < currentGameBeat) {
                ++ currentSpeedEventID;
                currentSpeedEvent = speedEvents.get(currentSpeedEventID);
            }
        }

        if (currentRotateEvent != null) {
            while (currentRotateEvent.getEndTime() < currentGameBeat) {
                ++ currentRotateEventID;
                currentRotateEvent = judgeLineRotateEvents.get(currentRotateEventID);
            }
        }

        if (currentMoveEvent != null) {
            while (currentMoveEvent.getEndTime() < currentGameBeat) {
                ++ currentMoveEventID;
                currentMoveEvent = judgeLineMoveEvents.get(currentMoveEventID);
            }
        }

        if (currentDisappearEvent != null) {
            while (currentDisappearEvent.getEndTime() < currentGameBeat) {
                ++ currentDisappearEventID;
                currentDisappearEvent = judgeLineDisappearEvents.get(currentDisappearEventID);
            }
        }
    }

    public int getCurrentSpeedEventID() {
        return currentSpeedEventID;
    }

    public int getCurrentMoveEventID() {
        return currentMoveEventID;
    }

    public int getCurrentDisappearEventID() {
        return currentDisappearEventID;
    }

    public int getCurrentRotateEventID() {
        return currentRotateEventID;
    }

    public JudgeLineSpeedEvent getCurrentSpeedEvent() {
        if (currentSpeedEvent == null)
            return DEFAULT_SPEED_EVENT;
        return currentSpeedEvent;
    }

    public JudgeLineMoveEvent getCurrentMoveEvent() {
        if (currentMoveEvent == null)
            return DEFAULT_MOVE_EVENT;
        return currentMoveEvent;
    }

    public JudgeLineDisappearEvent getCurrentDisappearEvent() {
        if (currentDisappearEvent == null)
            return DEFAULT_DISAPPEAR_EVENT;
        return currentDisappearEvent;
    }

    public JudgeLineRotateEvent getCurrentRotateEvent() {
        if (currentRotateEvent == null)
            return DEFAULT_ROTATE_EVENT;
        return currentRotateEvent;
    }

    public int getNumOfNotes() {
        return numOfNotes;
    }

    public void setNumOfNotes(int numOfNotes) {
        this.numOfNotes = numOfNotes;
    }

    public int getNumOfNotesAbove() {
        return numOfNotesAbove;
    }

    public void setNumOfNotesAbove(int numOfNotesAbove) {
        this.numOfNotesAbove = numOfNotesAbove;
    }

    public int getNumOfNotesBelow() {
        return numOfNotesBelow;
    }

    public void setNumOfNotesBelow(int numOfNotesBelow) {
        this.numOfNotesBelow = numOfNotesBelow;
    }

    public double getBpm() {
        return bpm;
    }

    public void setBpm(double bpm) {
        this.bpm = bpm;
    }

    public List<JudgeLineSpeedEvent> getSpeedEvents() {
        return speedEvents;
    }

    public void setSpeedEvents(List<JudgeLineSpeedEvent> speedEvents) {
        this.speedEvents = speedEvents;
    }

    public List<JudgeLineDisappearEvent> getJudgeLineDisappearEvents() {
        return judgeLineDisappearEvents;
    }

    public void setJudgeLineDisappearEvents(List<JudgeLineDisappearEvent> judgeLineDisappearEvents) {
        this.judgeLineDisappearEvents = judgeLineDisappearEvents;
    }

    public List<JudgeLineMoveEvent> getJudgeLineMoveEvents() {
        return judgeLineMoveEvents;
    }

    public void setJudgeLineMoveEvents(List<JudgeLineMoveEvent> judgeLineMoveEvents) {
        this.judgeLineMoveEvents = judgeLineMoveEvents;
    }

    public List<JudgeLineRotateEvent> getJudgeLineRotateEvents() {
        return judgeLineRotateEvents;
    }

    public void setJudgeLineRotateEvents(List<JudgeLineRotateEvent> judgeLineRotateEvents) {
        this.judgeLineRotateEvents = judgeLineRotateEvents;
    }

    public List<NoteFile> getNotesAbove() {
        return notesAbove;
    }

    public void setNotesAbove(List<NoteFile> notesAbove) {
        this.notesAbove = notesAbove;
    }

    public List<NoteFile> getNotesBelow() {
        return notesBelow;
    }

    public void setNotesBelow(List<NoteFile> notesBelow) {
        this.notesBelow = notesBelow;
    }


    public static JudgeLineMoveEvent DEFAULT_MOVE_EVENT = new JudgeLineMoveEvent();
    public static JudgeLineRotateEvent DEFAULT_ROTATE_EVENT = new JudgeLineRotateEvent();
    public static JudgeLineDisappearEvent DEFAULT_DISAPPEAR_EVENT = new JudgeLineDisappearEvent();
    public static JudgeLineSpeedEvent DEFAULT_SPEED_EVENT = new JudgeLineSpeedEvent();
}
