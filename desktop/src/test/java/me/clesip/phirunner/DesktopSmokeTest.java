package me.clesip.phirunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.utils.ScreenUtils;

/** Manual/CI smoke harness: no visible window and no audio playback. */
public class DesktopSmokeTest {
    public static void main(String[] args) {
        final String screenshot = args[0];
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(PhiRunner.WIDTH, PhiRunner.HEIGHT);
        config.setInitialVisible(false);
        config.disableAudio(true);
        config.setForegroundFPS(60);
        new Lwjgl3Application(new PhiRunner() {
            private int frames;

            @Override
            public void render() {
                super.render();
                if (++frames == 3) {
                    Pixmap pixels = ScreenUtils.getFrameBufferPixmap(0, 0, PhiRunner.WIDTH, PhiRunner.HEIGHT);
                    try {
                        boolean hasContent = false;
                        for (int y = 0; y < pixels.getHeight(); y += 8) {
                            for (int x = 0; x < pixels.getWidth(); x += 8) {
                                if ((pixels.getPixel(x, y) & 0xffffff00) != 0) {
                                    hasContent = true;
                                }
                            }
                        }
                        if (!hasContent) {
                            throw new IllegalStateException("Demo rendered an empty framebuffer");
                        }
                        PixmapIO.writePNG(Gdx.files.absolute(screenshot), pixels);
                        Gdx.app.log("Smoke test", "Rendered demo successfully");
                    } finally {
                        pixels.dispose();
                    }
                    Gdx.app.exit();
                }
            }
        }, config);
    }
}
