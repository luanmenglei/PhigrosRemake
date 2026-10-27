package me.clesip.phirunner.util;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.files.FileHandle;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;

public class CommonUtilTest {
    private FileHandle fixture() {
        return new FileHandle("audio/test.wav", FileType.Classpath) {};
    }

    @Test
    public void readsDurationFromClasspathWithoutARealFile() throws Exception {
        assertEquals(0.1f, CommonUtil.getSongDuration(fixture()), 0.00001f);
    }

    @Test
    public void readsDurationFromReadOnlyFile() throws Exception {
        Path path = Files.createTempFile("phigros-duration-", ".wav");
        try {
            Files.write(path, fixture().readBytes());
            File file = path.toFile();
            file.setReadOnly();
            assertEquals(0.1f, CommonUtil.getSongDuration(new FileHandle(file)), 0.00001f);
        } finally {
            path.toFile().setWritable(true);
            Files.deleteIfExists(path);
        }
    }

    @Test(expected = javax.sound.sampled.UnsupportedAudioFileException.class)
    public void rejectsInvalidAudio() throws Exception {
        Path path = Files.createTempFile("phigros-invalid-", ".wav");
        try {
            Files.write(path, new byte[]{1, 2, 3, 4});
            CommonUtil.getSongDuration(new FileHandle(path.toFile()));
        } finally {
            Files.deleteIfExists(path);
        }
    }
}
