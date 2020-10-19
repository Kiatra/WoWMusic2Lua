package com.github.zanony.WoWMusic2Lua;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.beust.jcommander.ParameterException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private File testListFile;
    private File testMusicFolder;

    public AppTest() throws IOException {
    }

    @Before
    public void createTestFiles() throws IOException {

        final Path destinationPath = testFolder.newFolder("destination").toPath();
        testMusicFolder = testFolder.newFolder("musicFolder");
        new File(testMusicFolder.getAbsolutePath() + "/A.mp3").createNewFile();
        new File(testMusicFolder.getAbsolutePath() + "/B.mp3").createNewFile();
        new File(testMusicFolder.getAbsolutePath() + "/B.ogg").createNewFile();
        new File(testMusicFolder.getAbsolutePath() + "/B.txt").createNewFile();

        File sub1 = new File(testMusicFolder + "/sub1");
        sub1.mkdir();
        new File(sub1.getAbsolutePath() + "/A.mp3").createNewFile();

        testListFile = testFolder.newFile("listfile.csv");

    }

    private void assertAppHasMinimalParameters(App app) throws IOException {
        assertEquals(testListFile.getAbsolutePath(), app.getListFile().getAbsolutePath());
    }

    @Test
    public void runWithMinimalParameters() throws IOException
    {
        String[] args = new String[]{"-listfile", testListFile.getAbsolutePath(), "-musicfolder", testMusicFolder.getAbsolutePath()};
        App app = new App();
        app.main(args);
        //assertAppHasMinimalParameters(app);
    }

    @Test(expected = ParameterException.class)
    public void runWithMissingParameters() throws IOException
    {
        String[] args = new String[]{};
        App app = new App();
        app.main(args);
    }

}
