package jupiterpi.vocabulum.core.db.lectures;

import org.bson.Document;

public class Lecture {
    private String name;
    private String text;

    public Lecture(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public static Lecture readFromDocument(Document document) {
        return new Lecture(
                document.getString("name"),
                document.getString("text")
        );
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        String excerpt = text.length() > 30 ? text.substring(0, 30) + "..." : text;
        excerpt = excerpt.replaceAll("\n", " // ");
        return "Lecture{name=" + name + ",text=\"" + excerpt + "\"}";
    }
}