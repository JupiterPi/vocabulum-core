package jupiterpi.vocabulum.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextFile {
    public static String readFile(String path) {
        return String.join("\n", readLines(path));
    }

    public static List<String> readLines(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
                return List.of();
            }

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

            return lines;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}