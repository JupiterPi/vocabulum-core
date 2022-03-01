package jupiterpi.vocabulum.core.portions;

import org.bson.Document;

import java.util.List;

public class Portion {
    private String name;
    private int lesson;
    private int part;
    private List<String> vocabularies;

    private Portion() {}
    public static Portion readFromDocument(Document document) {
        Portion portion = new Portion();

        String nameStr = document.getString("name");
        portion.name = nameStr;
        if (nameStr.matches("..-.")) {
            String[] nameStrParts = nameStr.split("-");
            portion.lesson = Integer.parseInt(nameStrParts[0]);
            portion.part = Integer.parseInt(nameStrParts[1]);
        }

        portion.vocabularies = (List<String>) document.get("vocabularies");

        return portion;
    }

    public String getName() {
        return name;
    }

    public int getLesson() {
        return lesson;
    }

    public int getPart() {
        return part;
    }

    public List<String> getVocabularies() {
        return vocabularies;
    }
}