package jupiterpi.vocabulum.core.util;

import org.bson.Document;

import java.io.*;
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
        if (!path.startsWith("/")) path = "/" + path;
        return readFile(TextFile.class.getResourceAsStream(path));
    }

    public static Document readJsonResourceFile(String path) {
        return Document.parse(readResourceFile(path).getFile());
    }

    public static TextFile readFile(File file) {
        try {
            return new TextFile(new BufferedReader(new FileReader(file))
                    .lines().toList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private static TextFile readFile(InputStream is) {
        return new TextFile(new BufferedReader(new InputStreamReader(is))
                .lines().toList());
    }
}