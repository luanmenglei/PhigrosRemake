package me.clesip.phirunner.util;

public class PhigrosUtil {
    public static double gameBeatToTime(double gameBeat, double bpm) {
        return gameBeat / bpm * 1.875;
    }

    public static double timeToGameBeat(double time, double bpm) {
        return time * bpm / 1.875;
    }

    public static double getEventProgress(double bpm, double musicTime, double startBeat, double endBeat) {
        return 1 - (musicTime - gameBeatToTime(startBeat, bpm)) / (gameBeatToTime(endBeat, bpm) - gameBeatToTime(startBeat, bpm)); //  感觉精度有点神奇
    }
}
