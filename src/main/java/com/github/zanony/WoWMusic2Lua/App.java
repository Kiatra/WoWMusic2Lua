package com.github.zanony.WoWMusic2Lua;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;


/**
 * Create a playlist of mp3/ogg files for EpicMusicPlayer!
 *
 */
public class App
{
    public File getListFile() {
        return listFile;
    }

    @Parameter(names = "-listfile", converter = FileConverter.class, validateWith = FilePathValidator.class, required = true)
    private File listFile = new File("listfile.csv");

    @Parameter(names = "-musicfolder", converter = FileConverter.class, validateWith = FilePathValidator.class, required = true)
    private File musicFolder = new File("music");

    @Parameter(names = "--help", help = true)
    private boolean help;

    public static void main( String[] args )
    {
        App app = new App();
        JCommander.newBuilder()
                .addObject(app)
                .build()
                .parse(args);

        app.run();
    }

    private void run() {
        //fetchFiles(musicFolder, this::processFile);
        try {
            fetchFiles2(musicFolder, this::processFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processFile(File f){
        System.out.println(f.getAbsolutePath());
    }

    public static void fetchFiles(File dir, Consumer<File> fileConsumer) {

        if (dir.isDirectory()) {
            for (File file1 : dir.listFiles()) {
                fetchFiles(file1, fileConsumer);
            }
        } else {
            fileConsumer.accept(dir);
        }
    }

    public static void fetchFiles2(File dir, Consumer<File> fileConsumer) throws IOException {

        Files.walk(Paths.get(dir.getPath()))
                .filter(p -> p.toString().endsWith(".mp3") || p.toString().endsWith(".ogg"))
                .forEach(fileConsumer.accept(dir));
    }


}


class FileConverter implements IStringConverter<File> {
    @Override
    public File convert(String value) {
        return new File(value);
    }
}