package me.clesip.phirunner.chart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Json;
import me.clesip.phirunner.Assets;
import me.clesip.phirunner.PhiRunner;
import me.clesip.phirunner.chart.file.ChartFile;
import me.clesip.phirunner.chart.file.JudgeLineFile;
import me.clesip.phirunner.chart.file.NoteFile;
import me.clesip.phirunner.util.CommonUtil;
import me.clesip.phirunner.util.Judgement;
import me.clesip.phirunner.util.PhigrosUtil;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PhigrosChart {
    public static boolean showBackground = true;
    private final String dir;
    private final String name;
    private final String path;
    private final Music song;
    private final float songDuration;
    private final Texture backgroundTexture;
    private final Image backgroundImage;
    private final ChartFile chartFile;
    private float currentSecond;
    private List<JudgeLine> judgeLines;
    private boolean playing;

    public PhigrosChart(String dir, String name) {
        long t = System.currentTimeMillis();

        Assets.init();

        this.dir = dir;
        this.name = name;
        this.path = dir + "/" + name;
        try {

            this.songDuration = CommonUtil.getSongDuration(Gdx.files.internal(path + ".wav"));
        } catch (IOException | UnsupportedAudioFileException e) {
            throw new RuntimeException("Cannot read WAV duration: " + path + ".wav", e);
        }
        Gdx.app.log("Song Duration", String.valueOf(songDuration));
        this.song = Gdx.audio.newMusic(Gdx.files.internal(path + ".wav"));
        this.backgroundTexture = new Texture(Gdx.files.internal(path + ".png"));
        this.backgroundImage = new Image(backgroundTexture);
        this.backgroundImage.setBounds(0, 0, PhiRunner.WIDTH , PhiRunner.HEIGHT);
        this.backgroundImage.setOrigin(PhiRunner.WIDTH / 2f, PhiRunner.WIDTH / 2f);
        shapes = new ShapeRenderer();

        Json json = new Json();
        this.chartFile = json.fromJson(ChartFile.class, Gdx.files.internal(path + ".json"));

        judgeLines = new ArrayList<>();
        int ID = 0;
        Map<Integer, NoteFile> hightlightMap = new HashMap<>();
        for (JudgeLineFile file : chartFile.getJudgeLineList()) {
            judgeLines.add(new JudgeLine(file, ++ ID));
            for (NoteFile note : file.getNotesAbove()) { // TODO add note below
                if (!hightlightMap.containsKey(note.getTime())) {
                    hightlightMap.put(note.getTime(), note);
                } else {
                    note.setHightlight(true);
                    hightlightMap.get(note.getTime()).setHightlight(true);
                }
            }
            for (NoteFile note : file.getNotesBelow()) {
                if (!hightlightMap.containsKey(note.getTime())) {
                    hightlightMap.put(note.getTime(), note);
                } else {
                    note.setHightlight(true);
                    hightlightMap.get(note.getTime()).setHightlight(true);
                }
            }
        }
        Gdx.app.log("JudgeLine Total", String.valueOf(ID));

        Gdx.app.log("Load Time", String.valueOf((System.currentTimeMillis() - t) / 1000.0));
    }

    public boolean play() {
        this.playing = true;
        this.song.setVolume(0.2f);
        this.song.play();
//        this.song.setPosition(80);
//        this.song.setPosition(108);


        return playing;
    }

    public static ShapeRenderer shapes;

    private void drawHold(SpriteBatch batch) {
        for (JudgeLine judgeLine : judgeLines) {
            float positionX = judgeLine.getPositionX();
            float positionY = judgeLine.getPositionY();
            float rotateDegree = judgeLine.getRotateDegree();
            float scale = JudgeLine.scale;
            double floorPosition = judgeLine.getFloorPosition();

            Gdx.gl.glClearDepthf(1f);
            Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);

            Gdx.gl.glDepthFunc(GL20.GL_LESS);

            Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

            Gdx.gl.glDepthMask(true);
            Gdx.gl.glColorMask(false, false, false, false);

            shapes.begin(ShapeRenderer.ShapeType.Filled);

            shapes.setColor(1f, 0f, 0f, 0.5f);
            shapes.identity();
            double tan = Math.tan(Math.toRadians(rotateDegree));
            if (rotateDegree >= 0) {
                shapes.triangle(PhiRunner.WIDTH, (float) (positionY + 0.5f * tan * PhiRunner.WIDTH),
                        0, (float) (positionY - 0.5f * tan * PhiRunner.WIDTH), 0, (float) (positionY + 0.5f * tan * PhiRunner.WIDTH));
                shapes.rect(0, (float) (positionY + 0.5 * tan * PhiRunner.WIDTH), PhiRunner.WIDTH,
                        PhiRunner.HEIGHT -  (float) (positionY + 0.5f * tan * PhiRunner.WIDTH));
            } else {
                shapes.triangle(PhiRunner.WIDTH, (float) (positionY + 0.5f * tan * PhiRunner.WIDTH),
                        0, (float) (positionY - 0.5f * tan * PhiRunner.WIDTH), PhiRunner.WIDTH, (float) (positionY - 0.5f * tan * PhiRunner.WIDTH));
                shapes.rect(0, (float) (positionY - 0.5 * tan * PhiRunner.WIDTH), PhiRunner.WIDTH,
                        PhiRunner.HEIGHT -  (float) (positionY - 0.5f * tan * PhiRunner.WIDTH));
            }



            shapes.end();


            batch.begin();
            Gdx.gl.glColorMask(true, true, true, true);
            Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
            Gdx.gl.glDepthFunc(GL20.GL_EQUAL);

            for (NoteFile note : judgeLine.getFileData().getNotesAbove()) {
                if (note.getType() == 3) {

                    double deltaX = note.getPositionX() * PhiRunner.WIDTH / 18;
                    double time = note.getFloorPosition() - floorPosition; // 计算中剩下的y （类似于时间）
                    double deltaY = time  * PhiRunner.HEIGHT * 0.6; // 实际上剩下的y

                    float fullValue = (float) (PhigrosUtil.gameBeatToTime(note.getHoldTime(),
                            judgeLine.getFileData().getBpm()) * note.getSpeed() * PhiRunner.HEIGHT * 0.6);
                    float elapsedValue = (Assets.HOLD_HEAD.getHeight() + Assets.HOLD_END.getHeight()) * scale;

                    // 绘制
                    if (note.isHightlight()) {
                        elapsedValue = (Assets.HOLD_HEAD_HIGHLIGHT.getHeight() * 2) * scale;
                        Vector2 pos = CommonUtil.getRotatedPosition(new Vector2(positionX, positionY), deltaX, deltaY, rotateDegree);
                        CommonUtil.drawTextureWithCenterScale(batch, pos, Assets.HOLD_HEAD_HIGHLIGHT, scale, rotateDegree);
                        pos = CommonUtil.getRotatedPosition(new Vector2(positionX, positionY), deltaX, deltaY + fullValue / 2,  rotateDegree);
                        CommonUtil.drawTextureWithCenterSize(batch, pos, Assets.HOLD_HIGHTLIGHT, scale * Assets.HOLD_HIGHTLIGHT.getWidth(),
                                fullValue - elapsedValue / 2, rotateDegree);
                        pos = CommonUtil.getRotatedPosition(new Vector2(positionX, positionY), deltaX, deltaY + fullValue, rotateDegree);
                        CommonUtil.drawTextureWithCenterSize(batch, pos, Assets.HOLD_END, 144,
                                Assets.HOLD_HEAD_HIGHLIGHT.getHeight() * scale + 1, rotateDegree);
                    } else {
                        Vector2 pos = CommonUtil.getRotatedPosition(new Vector2(positionX, positionY), deltaX, deltaY, rotateDegree);
                        CommonUtil.drawTextureWithCenterScale(batch, pos, Assets.HOLD_HEAD, scale, rotateDegree);
                        pos = CommonUtil.getRotatedPosition(new Vector2(positionX, positionY), deltaX, deltaY + fullValue / 2,  rotateDegree);
                        CommonUtil.drawTextureWithCenterSize(batch, pos, Assets.HOLD, scale * Assets.HOLD.getWidth(),
                                fullValue - elapsedValue / 2, rotateDegree);
                        pos = CommonUtil.getRotatedPosition(new Vector2(positionX, positionY), deltaX, deltaY + fullValue, rotateDegree);
                        CommonUtil.drawTextureWithCenterScale(batch, pos, Assets.HOLD_END, scale, rotateDegree);
                    }
                }
            }
            batch.end();
            Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
        }
    }

    public void update(SpriteBatch batch) {

        currentSecond = song.getPosition();

        // calculations & rendering
        batch.begin();
        if (showBackground) backgroundImage.draw(batch, 0.3f);
        showBackground = true;
        batch.end();
        batch.begin();
        batch.setColor(1, 1, 1, 1);
        for (JudgeLine judgeLine : judgeLines) {
            judgeLine.update(currentSecond);
            judgeLine.specialUpdate(batch);
        }
        for (JudgeLine judgeLine : judgeLines) {
            judgeLine.drawLine(batch, currentSecond);
        }
        batch.end();

        drawHold(batch);

        batch.begin();
        for (JudgeLine judgeLine : judgeLines) {
            judgeLine.draw(batch, currentSecond);
        }
        batch.end();

        float progress = currentSecond / songDuration;
        SimpleDateFormat dateFormater = new SimpleDateFormat("mm:ss");
        Date now = new Date();
        now.setTime((long) (currentSecond * 1000));
        Date all = new Date();
        all.setTime((long) (songDuration * 1000));
        batch.begin();
        Assets.FONT.draw(batch, dateFormater.format(now) + "/" + dateFormater.format(all), 0, PhiRunner.HEIGHT * (1 - 0.01f));
        batch.draw(Assets.PROGRESS_BAR, (progress - 1) * PhiRunner.WIDTH, PhiRunner.HEIGHT * (1 - 0.01f), PhiRunner.WIDTH, PhiRunner.HEIGHT * (1 - 0.01f));
        batch.end();
    }

    public boolean isPlaying() {
        return playing;
    }

    public void dispose() {
        song.dispose();
        backgroundTexture.dispose();
        Assets.dispose();
    }

    public float getCurrentSecond() {
        return currentSecond;
    }
}
