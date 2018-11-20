package me.lc.vodka.file;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

public abstract class CustomFile {

    private Gson gson;
    private File file;

    public CustomFile(Gson gson, File file) {
        this.gson = gson;
        this.file = file;
        makeDirectory();
    }
    Runnable r2 = ()-> System.out.println("233333333");

    private void makeDirectory() {
        if (file != null && !file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ignored) {
            }
        }
    }

    public abstract void loadFile() throws IOException;

    public abstract void saveFile() throws IOException;

    public File getFile() {
        return file;
    }

    public Gson getGson() {
        return gson;
    }
}
