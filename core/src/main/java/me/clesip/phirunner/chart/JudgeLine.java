package me.clesip.phirunner.chart;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import me.clesip.phirunner.Assets;
import me.clesip.phirunner.PhiRunner;
import me.clesip.phirunner.chart.file.*;
import me.clesip.phirunner.util.CommonUtil;
import me.clesip.phirunner.util.Judgement;
import me.clesip.phirunner.util.PhigrosUtil;

import java.util.ArrayList;
import java.util.List;

public class JudgeLine {
    private JudgeLineFile fileData;
    private float positionX;
    private float positionY;
    private float opacity;
    private float rotateDegree;
    private final Sprite judgeLineSprite;
    private int judgeLineID;
    private double floorPosition;
    private List<Judgement> judgements;

    public JudgeLine(JudgeLineFile fileData, int judgeLineID) {
        this.judgeLineID = judgeLineID;
        this.fileData = fileData;
        this.fileData.init();
        judgements = new ArrayList<>();
        for (NoteFile note : fileData.getNotesAbove()) {
            judgements.add(new Judgement(note, fileData.getBpm()));
        }
        for (NoteFile note : fileData.getNotesBelow()) {
            judgements.add(new Judgement(note, fileData.getBpm()));
        }

        this.judgeLineSprite = new Sprite(Assets.JUDGE_LINE);
        judgeLineSprite.setOriginCenter();
    }

    public void update(float musicTime) {
        try {
            fileData.update(musicTime);
            double eventProgress;

            JudgeLineMoveEvent currentMoveEvent = fileData.getCurrentMoveEvent();
            eventProgress = PhigrosUtil.getEventProgress(fileData.getBpm(), musicTime, currentMoveEvent.getStartTime(), currentMoveEvent.getEndTime());
            positionX = (float) (PhiRunner.WIDTH * 1.0 * (
                    currentMoveEvent.getStart() * eventProgress + currentMoveEvent.getEnd() * (1.0 - eventProgress)
            ));
            positionY = (float) (1.0 * PhiRunner.HEIGHT * 1.0 * (
                    currentMoveEvent.getStart2() * eventProgress + currentMoveEvent.getEnd2() * (1.0 - eventProgress)
            ));

            JudgeLineDisappearEvent currentDisappearEvent = fileData.getCurrentDisappearEvent();
            eventProgress = PhigrosUtil.getEventProgress(fileData.getBpm(), musicTime, currentDisappearEvent.getStartTime(), currentDisappearEvent.getEndTime());
            opacity = (float) (currentDisappearEvent.getStart() * eventProgress  + currentDisappearEvent.getEnd() * (1 - eventProgress));

            JudgeLineRotateEvent currentRotateEVent = fileData.getCurrentRotateEvent();
            eventProgress = PhigrosUtil.getEventProgress(fileData.getBpm(), musicTime, currentRotateEVent.getStartTime(), currentRotateEVent.getEndTime());
            rotateDegree = (float) (currentRotateEVent.getStart() * eventProgress  + currentRotateEVent.getEnd() * (1 - eventProgress));

            JudgeLineSpeedEvent currentSpeedEvent = fileData.getCurrentSpeedEvent();
            floorPosition = (musicTime - PhigrosUtil.gameBeatToTime(currentSpeedEvent.getStartTime(), fileData.getBpm())) * currentSpeedEvent.getValue() + currentSpeedEvent.getFloorPosition();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static float scale = 0.15f;

    public void drawLine(SpriteBatch batch, float songTime) {
        if (judgeLineID >= 21 && judgeLineID <= 24) return;
        judgeLineSprite.setPosition(positionX - Assets.JUDGE_LINE.getWidth() / 2.0f, positionY - Assets.JUDGE_LINE.getHeight());
        judgeLineSprite.setAlpha(opacity);
        judgeLineSprite.setRotation(rotateDegree);

        judgeLineSprite.draw(batch);
    }

    public void specialUpdate(SpriteBatch batch) {
        if (judgeLineID == 21) { // 对于sp铺面的特殊判定，读取line.csv的部分就不写了
            if (opacity == 1) {
                PhigrosChart.showBackground = true;
            }
        } else if (judgeLineID == 22) {
            if (opacity == 1) {
                PhigrosChart.showBackground = true;
                batch.draw(Assets.PIC_2, PhiRunner.WIDTH / 2f - Assets.PIC_2.getWidth() * 0.55f / 2f, PhiRunner.HEIGHT / 2f - Assets.PIC_2.getHeight() * 0.55f / 2f, Assets.PIC_2.getWidth() * 0.55f, Assets.PIC_2.getHeight() * 0.55f);
            }
        } else if (judgeLineID == 23) {
            if (opacity == 1) {
                PhigrosChart.showBackground = false;
                batch.draw(Assets.PIC_3, 0, 0, PhiRunner.WIDTH, PhiRunner.HEIGHT);
            }
        } else if (judgeLineID == 24) {
            if (opacity == 1) {
                PhigrosChart.showBackground = false;
                batch.draw(Assets.PIC_4, 0, 0, PhiRunner.WIDTH, PhiRunner.HEIGHT);
            }
        }
    }

    public void draw(SpriteBatch batch, float songTime) {
        for (NoteFile note : fileData.getNotesAbove()) {
            double deltaX = note.getPositionX() * PhiRunner.WIDTH / 18;
            double time = note.getFloorPosition() - floorPosition; // 计算中剩下的y （类似于时间）
            double deltaY = time  * PhiRunner.HEIGHT * 0.6; // 实际上剩下的y

            Vector2 pos = CommonUtil.getRotatedPosition(new Vector2(positionX, positionY), deltaX, deltaY, rotateDegree);

            if (note.getType() == 1) {
                if (time >= 0) {
                    if (note.isHightlight())
                        CommonUtil.drawTextureWithCenterScale(batch, pos, Assets.TAP_HIGHTLIGHT, scale, rotateDegree);
                    else
                        CommonUtil.drawTextureWithCenterScale(batch, pos, Assets.TAP, scale, rotateDegree);
                }
            } else if (note.getType() ==  2) {
//                if (time < -0.16) continue;
                if (time < 0) continue;
                if (note.isHightlight())
                    CommonUtil.drawTextureWithCenterScale(batch, pos, Assets.DRAG_HIGHLIGHT, scale, rotateDegree);
                else
                    CommonUtil.drawTextureWithCenterScale(batch, pos, Assets.DRAG, scale, rotateDegree);
            } else if (note.getType() == 4) {
                if (time < 0) continue;
                if (note.isHightlight())
                    CommonUtil.drawTextureWithCenterScale(batch, pos, Assets.FLICK_HIGHLIGHT, scale, rotateDegree);
                else
                    CommonUtil.drawTextureWithCenterScale(batch, pos, Assets.FLICK, scale, rotateDegree);
            }

        }
        for (Judgement judgement : judgements) {
            double deltaX = judgement.getNote().getPositionX() * PhiRunner.WIDTH / 18;

            judgement.udpate(songTime);
            if (judgement.isJudged() && songTime - judgement.getTime() <= 0.5) {
                double percent = (songTime - judgement.getTime()) / 0.5;
                Vector2 pos = CommonUtil.getRotatedPosition(new Vector2(positionX, positionY), deltaX, 0, rotateDegree);
                batch.setColor(judgement.getColor());
                CommonUtil.drawTextureWithCenterSizeRealSize(batch, pos, Assets.CLICK_EFFECTS, 180, 180, rotateDegree, 0, ((int) (percent * 30) - 1) * 256, 256, 256);
                double boxSize = 30 * (((0.2078 * percent - 1.6524) * percent + 1.6399) * percent + 0.4988) * 0.8; //方块大小
                int cnt = 0;
                for (Double key : judgement.getRandMap().keySet()) {
                    ++ cnt;
                    if (cnt > judgement.getBoxLength()) break;

                    double distance = key * (9 * percent / (8 * percent + 1)) * 0.8; //打击点距离
                    deltaX += distance * Math.cos(judgement.getRandMap().get(key));
                    double deltaY = distance * Math.sin(judgement.getRandMap().get(key)) * 0.8;
                    pos = CommonUtil.getRotatedPosition(new Vector2(positionX, positionY), deltaX, deltaY, rotateDegree);
                    CommonUtil.drawTextureWithCenterSizeRealSize(batch, pos, Assets.EMPTY_BOX, (float) boxSize, (float) boxSize, rotateDegree, 0, 0, 1, 1);
                }

                batch.setColor(1, 1, 1 , 1);
            }
        }
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public float getOpacity() {
        return opacity;
    }

    public JudgeLineFile getFileData() {
        return fileData;
    }

    public float getRotateDegree() {
        return rotateDegree;
    }

    public double getFloorPosition() {
        return floorPosition;
    }
}
