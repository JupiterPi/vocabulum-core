package jupiterpi.vocabulum.core.db.lectures;

import jupiterpi.vocabulum.core.ta.TranslationAssistance;
import jupiterpi.vocabulum.core.ta.result.TAResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Lecture {
    private String name;
    private List<String> lines;

    /* constructors */

    public Lecture(String name, List<String> lines) {
        this.name = name;
        this.lines = lines;
    }

    public static Lecture fromTextString(String name, String text) {
        return new Lecture(
                name,
                List.of(text.split("\n"))
        );
    }

    public static Lecture readFromDocument(Document document) {
        return new Lecture(
                document.getString("name"),
                List.of(document.getString("text").split("\n"))
        );
    }

    /* getters, accessors, toString */

    public String getName() {
        return name;
    }

    public List<String> getLines() {
        return lines;
    }

    public String getText() {
        return String.join("\n", lines);
    }

    /* process lines */

    private List<TAResult> processedLines = null;

    public List<TAResult> getProcessedLines() {
        if (processedLines == null) {
            processedLines = new ArrayList<>();
            for (String line : lines) {
                TranslationAssistance ta = new TranslationAssistance(line);
                processedLines.add(ta.getResult());
            }
        }
        return processedLines;
    }

    @Override
    public String toString() {
        String text = getText();
        String excerpt = text.length() > 30 ? text.substring(0, 30) + "..." : text;
        excerpt = excerpt.replaceAll("\n", " // ");
        return "Lecture{name=" + name + ",text=\"" + excerpt + "\"}";
    }
}