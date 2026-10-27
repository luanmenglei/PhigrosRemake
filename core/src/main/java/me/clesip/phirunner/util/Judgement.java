package me.clesip.phirunner.util;

import com.badlogic.gdx.graphics.Color;
import me.clesip.phirunner.Assets;
import me.clesip.phirunner.chart.PhigrosChart;
import me.clesip.phirunner.chart.file.NoteFile;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Judgement {
    private final NoteFile note;
    private final float time;
    private boolean judged;
    private JudgementType type;
    private Map<Double, Double> randMap = new HashMap<>();

    public Judgement(NoteFile note, double bpm) {
        this.note = note;
        this.time = (float) PhigrosUtil.gameBeatToTime(note.getTime(), bpm);
        this.judged = false;
        this.type = JudgementType.Perfect;
        for (int i = 0; i < 4; i ++) {
            double rand = new Random().nextDouble();
            randMap.put(rand * 80 + 185, rand * 2 * Math.PI);
        }
    }

    public void udpate(float songTime) {
        if (!judged) {
            if (songTime > time) {
                judged = true;
                if (note.getType() == 1 || note.getType() == 3) {
                    Assets.TAP_SOUND.play(0.2f);
                } else if (note.getType() == 2) {
                    Assets.DRAG_SOUND.play(0.2f);
                } else if (note.getType() == 4) {
                    Assets.FLICK_SOUND.play(0.2f);
                }
            }
        }
    }

    public Color getColor() {
        switch (type) {
            case Perfect:
                return new Color(255 / 255f,236 / 255f,160 / 255f,0.8823529f);
            case Bad:
                return new Color(168 / 255f,255 / 255f,177 / 255f,0.9016907f);
            case Good:
                return new Color(180 / 255f,225 / 255f,255 / 255f,0.9215686f);
        }
        return new Color(0, 0, 0, 1);
    }

    public int getBoxLength() {
        switch (type) {
            case Perfect:
                return 4;
            case Bad:
                return 4;
            case Good:
                return 3;
        }
        return 0;
    }

    public boolean isJudged() {
        return judged;
    }

    public void setJudged(boolean judged) {
        this.judged = judged;
    }

    public NoteFile getNote() {
        return note;
    }

    public float getTime() {
        return time;
    }

    public Map<Double, Double> getRandMap() {
        return randMap;
    }

    public enum JudgementType {
        Perfect, Good, Bad
    }
}
