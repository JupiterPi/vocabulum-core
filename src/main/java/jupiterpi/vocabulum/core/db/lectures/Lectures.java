package jupiterpi.vocabulum.core.db.lectures;

import org.bson.Document;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
}