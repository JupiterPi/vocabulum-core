package jupiterpi.vocabulum.core.db.lectures;

import jupiterpi.vocabulum.core.ta.result.TAResult;
import jupiterpi.vocabulum.core.ta.result.TAResultWord;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import org.bson.Document;

import java.util.*;

public class Lectures {
    private Map<String, Lecture> lectures;

    public void loadLectures(Iterable<Document> documents) {
        lectures = new HashMap<>();
        for (Document document : documents) {
            Lecture lecture = Lecture.readFromDocument(document);
            lectures.put(lecture.getName(), lecture);
        }
    }

    public Collection<Lecture> getLectures() {
        return lectures.values();
    }

    public Lecture getLecture(String name) {
        return lectures.get(name);
    }

    /* feature: example lines */

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
                    exampleLines.add(new ExampleLine(line, line.indexOf(itemStr), line.indexOf(itemStr) + itemStr.length()));
                }
            }
        }
        return exampleLines;
    }

    public static class ExampleLine {
        private String line;
        private int startIndex;
        private int endIndex;

        public ExampleLine(String line, int startIndex, int endIndex) {
            this.line = line;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        public String getLine() {
            return line;
        }

        public int getStartIndex() {
            return startIndex;
        }

        public int getEndIndex() {
            return endIndex;
        }
    }
}