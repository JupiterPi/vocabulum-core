package jupiterpi.vocabulum.core.db.lectures;

import jupiterpi.vocabulum.core.ta.TranslationAssistance;
import jupiterpi.vocabulum.core.ta.result.TAResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a lecture in the textbook.
 */
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

    /* getters, accessors */

    /**
     * @return the name of the lecture (e. g. "L1")
     */
    public String getName() {
        return name;
    }

    /**
     * @return the text of the lecture by line
     */
    public List<String> getLines() {
        return lines;
    }

    /**
     * @return the text of the lecture as a string with line breaks
     */
    public String getText() {
        return String.join("\n", lines);
    }

    /* process lines */

    private List<TAResult> processedLines = null;

    /**
     * Processes the lines in the lecture using the translation assistance.
     * @return the processed lines
     * @see TranslationAssistance
     */
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