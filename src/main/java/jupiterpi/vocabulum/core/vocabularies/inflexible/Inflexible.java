package jupiterpi.vocabulum.core.vocabularies.inflexible;

import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import org.bson.Document;

import java.util.List;

public class Inflexible extends Vocabulary {
    private String word;

    public Inflexible(String word, List<String> translations) {
        super(translations);
        this.word = word;
    }

    public static Inflexible readFromDocument(Document document) {
        String word = document.getString("base_form");
        return new Inflexible(word, readTranslations(document));
    }

    @Override
    public String getBaseForm() {
        return word;
    }

    @Override
    public Kind getKind() {
        return Kind.INFLEXIBLE;
    }

    @Override
    public Document generateWordbaseEntry() {
        Document document = new Document();
        document.put("kind", "inflexible");
        document.put("base_form", word);
        document.put("translations", translations);
        return document;
    }
}
