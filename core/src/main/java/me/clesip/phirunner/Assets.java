package me.clesip.phirunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets {
    public static Texture PROGRESS_BAR;
    public static Texture JUDGE_LINE;
    public static Texture DRAG;
    public static Texture DRAG_HIGHLIGHT;
    public static Texture FLICK;
    public static Texture FLICK_HIGHLIGHT;
    public static Texture HOLD;
    public static Texture HOLD_HIGHTLIGHT;
    public static Texture HOLD_END;
    public static Texture HOLD_HEAD;
    public static Texture HOLD_HEAD_HIGHLIGHT;
    public static Texture TAP;
    public static Texture TAP_BAD;
    public static Texture TAP_HIGHTLIGHT;
    public static Texture PIC_1;
    public static Texture PIC_2;
    public static Texture PIC_3;
    public static Texture PIC_4;
    public static BitmapFont FONT;
    public static Sound TAP_SOUND;
    public static Sound DRAG_SOUND;
    public static Sound FLICK_SOUND;
    public static Texture CLICK_EFFECTS;
    public static Texture EMPTY_BOX;

    public static void init() {
        PROGRESS_BAR = new Texture(Gdx.files.internal("ProgressBar.png"));
        JUDGE_LINE = new Texture(Gdx.files.internal("JudgeLine.png"));
        DRAG = new Texture(Gdx.files.internal("Drag.png"));
        DRAG_HIGHLIGHT = new Texture(Gdx.files.internal("DragHL.png"));
        FLICK = new Texture(Gdx.files.internal("Flick.png"));
        FLICK_HIGHLIGHT = new Texture(Gdx.files.internal("FlickHL.png"));
        HOLD = new Texture(Gdx.files.internal("Hold.png"));
        HOLD_HIGHTLIGHT = new Texture(Gdx.files.internal("HoldHL.png"));
        HOLD_END = new Texture(Gdx.files.internal("HoldEnd.png"));
        HOLD_HEAD = new Texture(Gdx.files.internal("HoldHead.png"));
        HOLD_HEAD_HIGHLIGHT = new Texture(Gdx.files.internal("HoldHeadHL.png"));
        TAP = new Texture(Gdx.files.internal("Tap.png"));
        TAP_BAD = new Texture(Gdx.files.internal("Tap2.png"));
        TAP_HIGHTLIGHT = new Texture(Gdx.files.internal("TapHL.png"));
        PIC_1 = new Texture(Gdx.files.internal("demo/.0.png"));
        PIC_2 = new Texture(Gdx.files.internal("demo/.1.png"));
        PIC_3 = new Texture(Gdx.files.internal("demo/.2.png"));
        PIC_4 = new Texture(Gdx.files.internal("demo/.3.png"));
        FONT = new BitmapFont();
        TAP_SOUND = Gdx.audio.newSound(Gdx.files.internal("HitSong0.ogg"));
        DRAG_SOUND = Gdx.audio.newSound(Gdx.files.internal("HitSong1.ogg"));
        FLICK_SOUND = Gdx.audio.newSound(Gdx.files.internal("HitSong2.ogg"));
        CLICK_EFFECTS = new Texture(Gdx.files.internal("clickRaw.png"));
        EMPTY_BOX = new Texture(Gdx.files.internal("0.png"));
    }

    public static void dispose() {
        PROGRESS_BAR.dispose();
        JUDGE_LINE.dispose();
        DRAG.dispose();
        DRAG_HIGHLIGHT.dispose();
        FLICK.dispose();
        FLICK_HIGHLIGHT.dispose();
        HOLD.dispose();
        HOLD_HIGHTLIGHT.dispose();
        HOLD_END.dispose();
        HOLD_HEAD.dispose();
        HOLD_HEAD_HIGHLIGHT.dispose();
        TAP.dispose();
        TAP_BAD.dispose();
        TAP_HIGHTLIGHT.dispose();
        FONT.dispose();
        PIC_1.dispose();
        PIC_2.dispose();
        PIC_3.dispose();
        PIC_4.dispose();
        TAP_SOUND.dispose();
        FLICK_SOUND.dispose();
        DRAG_SOUND.dispose();
        CLICK_EFFECTS.dispose();
        EMPTY_BOX.dispose();
    }
}
