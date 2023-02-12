package jupiterpi.vocabulum.core.util;

import org.bson.Document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextFile {
    private List<String> lines;

    public TextFile(List<String> lines) {
        this.lines = lines;
    }

    /* getters */

    public String getFile() {
        return String.join("\n", lines);
    }

    public List<String> getLines() {
        return lines;
    }

    /* read */

    public static TextFile readFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return new TextFile(List.of());
        } else {
            return readFile(file);
        }
    }

    public static TextFile readResourceFile(String path) {
        return readFile(new File(ClassLoader.getSystemClassLoader().getResource(path).getFile()));
    }

    public static Document readJsonResourceFile(String path) {
        return Document.parse(readResourceFile(path).getFile());
    }

    public static TextFile readFile(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String> lines = new ArrayList<>();
            while (true) {
                String line;
                if ((line = reader.readLine()) != null) {
                    lines.add(line);
                } else {
                    break;
                }
            }
            reader.close();

            return new TextFile(lines);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}