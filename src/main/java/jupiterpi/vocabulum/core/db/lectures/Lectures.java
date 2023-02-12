package jupiterpi.vocabulum.core.db.lectures;

import jupiterpi.vocabulum.core.ta.result.TAResult;
import jupiterpi.vocabulum.core.ta.result.TAResultWord;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import org.bson.Document;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Collection of all available <code>Lecture</code>s.
 * @see Lecture
 */
public class Lectures {
    private Map<String, Lecture> lectures;

    public void loadLectures(Iterable<Document> documents) {
        lectures = new HashMap<>();
        for (Document document : documents) {
            Lecture lecture = Lecture.readFromDocument(document);
            lectures.put(lecture.getName(), lecture);
        }
    }

    /**
     * @return all available lectures
     */
    public Collection<Lecture> getLectures() {
        return lectures.values();
    }

    /**
     * @param name the name of the lecture (e. g. "L1")
     * @return the lecture with the specified name
     */
    public Lecture getLecture(String name) {
        return lectures.get(name);
    }

    /* feature: example lines */

    /**
     * Goes through all lines of all lectures to find all sentences where a vocabulary is mentioned.
     * @param vocabulary the vocabulary to search for
     * @return the found example lines
     * @see ExampleLine
     * @deprecated use <code>getAllExampleLines()</code> instead (otherwise severe performance issues)
     */
    @Deprecated
    public List<ExampleLine> getExampleLines(Vocabulary vocabulary) {
        List<ExampleLine> exampleLines = new ArrayList<>();
        for (Lecture lecture : lectures.values()) {
            for (int i = 0; i < lecture.getProcessedLines().size(); i++) {
                TAResult processedLine = lecture.getProcessedLines().get(i);
                String line = lecture.getLines().get(i);

                TAResult.TAResultItem foundItem = null;
                for (TAResult.TAResultItem item : processedLine.getItems()) {
                    if (item instanceof TAResultWord) {
                        TAResultWord word = (TAResultWord) item;
                        for (TAResultWord.PossibleWord possibleWord : word.getPossibleWords()) {
                            if (possibleWord.getVocabulary() != null && possibleWord.getVocabulary().getBaseForm().equals(vocabulary.getBaseForm())) {
                                foundItem = item;
                                break;
                            }
                        }
                    }
                    if (foundItem != null) break;
                }
                if (foundItem != null) {
                    String itemStr = foundItem.getItem();

                    Matcher matcher = Pattern.compile("\\b" + Pattern.quote(itemStr) + "\\b").matcher(line);
                    matcher.find();
                    int index = matcher.start();

                    exampleLines.add(new ExampleLine(
                            line, index, index + itemStr.length(),
                            lecture, i
                    ));
                }
            }
        }
        return exampleLines;
    }

    /**
     * Goes through all lines of all lectures and constructs a map of all sentences where a vocabulary is mentioned for all vocabularies.
     * @return a map of all occurring vocabularies and all sentences where they're used
     * @see ExampleLine
     */
    public Map<Vocabulary, List<ExampleLine>> getAllExampleLines() {
        Map<Vocabulary, List<ExampleLine>> allExampleLines = new HashMap<>();
        for (Lecture lecture : lectures.values()) {
            for (int lineIndex = 0; lineIndex < lecture.getProcessedLines().size(); lineIndex++) {
                TAResult processedLine = lecture.getProcessedLines().get(lineIndex);
                String line = lecture.getLines().get(lineIndex);

                Set<Vocabulary> vocabulariesDone = new HashSet<>();
                for (TAResult.TAResultItem item : processedLine.getItems()) {
                    if (item instanceof TAResultWord word) {
                        for (TAResultWord.PossibleWord possibleWord : word.getPossibleWords()) {
                            Vocabulary vocabulary = possibleWord.getVocabulary();
                            if (!vocabulariesDone.contains(vocabulary)) {
                                vocabulariesDone.add(vocabulary);

                                Matcher matcher = Pattern.compile("\\b" + Pattern.quote(word.getWord()) + "\\b").matcher(line);
                                matcher.find();
                                int startIndex = matcher.start();

                                ExampleLine exampleLine = new ExampleLine(
                                        line, startIndex, startIndex + word.getWord().length(),
                                        lecture, lineIndex
                                );
                                if (allExampleLines.containsKey(vocabulary)) {
                                    allExampleLines.get(vocabulary).add(exampleLine);
                                } else {
                                    allExampleLines.put(vocabulary, new ArrayList<>(List.of(exampleLine)));
                                }
                            }
                        }
                    }
                }
            }
        }
        return allExampleLines;
    }

    public static class ExampleLine {
        private String line;
        private int startIndex;
        private int endIndex;
        private Lecture lecture;
        private int lineIndex;

        public ExampleLine(String line, int startIndex, int endIndex, Lecture lecture, int lineIndex) {
            this.line = line;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.lecture = lecture;
            this.lineIndex = lineIndex;
        }

        /**
         * @return the full line from the lecture
         */
        public String getLine() {
            return line;
        }

        /**
         * @return the index at the beginning of the substring of the line that matches the vocabulary
         */
        public int getStartIndex() {
            return startIndex;
        }

        /**
         * @return the index at the end of the substring of the line that matches the vocabulary
         */
        public int getEndIndex() {
            return endIndex;
        }

        /**
         * @return the lecture in which the line was found
         */
        public Lecture getLecture() {
            return lecture;
        }

        /**
         * @return the index of the line within the lecture (0-based)
         */
        public int getLineIndex() {
            return lineIndex;
        }
    }
}