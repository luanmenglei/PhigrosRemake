package me.clesip.phirunner.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import java.io.BufferedInputStream;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class CommonUtil {
    public static float getSongDuration(FileHandle file) throws IOException, UnsupportedAudioFileException {
        // Streams work with both loose assets and resources inside a JAR.
        try (BufferedInputStream input = new BufferedInputStream(file.read());
             AudioInputStream audio = AudioSystem.getAudioInputStream(input)) {
            long frames = audio.getFrameLength();
            float frameRate = audio.getFormat().getFrameRate();
            if (frames <= 0 || frameRate <= 0) {
                throw new IOException("WAV has no usable duration: " + file.path());
            }
            return frames / frameRate;
        }
    }

    public static Vector2 getRotatedPosition(Vector2 center, double deltaX, double deltaY, double rotateDegree) {
        if (deltaX == 0 && deltaY == 0) return center;
        double lineSin = Math.sin(Math.toRadians(rotateDegree));
        double lineCos = Math.cos(Math.toRadians(rotateDegree));
        double hypotenuse = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        double deltaSin = deltaY / hypotenuse;
        double deltaCos = deltaX / hypotenuse;
        double sin = deltaSin * lineCos + deltaCos * lineSin;
        double cos = lineCos * deltaCos - lineSin * deltaSin;
        return new Vector2((float) (center.x + hypotenuse * cos), (float) (center.y + sin * hypotenuse));
    }

    public static void drawTextureWithCenterScale(SpriteBatch batch, Vector2 center, Texture texture, float scale, float rotateDegree) {
        batch.draw(texture, center.x - texture.getWidth() * scale / 2, center.y - texture.getHeight() * scale / 2,
                texture.getWidth() * scale / 2,texture.getHeight() * scale / 2, texture.getWidth() * scale,
                texture.getHeight() * scale,1,1, rotateDegree, 0, 0, texture.getWidth(),
                texture.getHeight(),false,false);
    }

    public static void drawTextureWithCenterSize(SpriteBatch batch, Vector2 center, Texture texture, float width, float height, float rotateDegree) {
        batch.draw(texture, center.x - width / 2, center.y - height / 2,
                width / 2,height / 2, width,
               height,1,1, rotateDegree, 0, 0, texture.getWidth(),
                texture.getHeight(),false,false);
    }

    public static void drawTextureWithCenterSizeRealSize(SpriteBatch batch, Vector2 center, Texture texture, float width, float height, float rotateDegree, int srcX, int srcY, int srcWidth, int srcHeight) {
        batch.draw(texture, center.x - width / 2, center.y - height / 2,
                srcWidth / 2f,srcHeight / 2f, width,
                height,1,1, rotateDegree, srcX, srcY, srcWidth, srcHeight,false,false);
    }
}
