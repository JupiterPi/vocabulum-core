package jupiterpi.vocabulum.core.vocabularies.inflexible;

import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.translations.VocabularyTranslation;
import org.bson.Document;

import java.util.List;

public class Inflexible extends Vocabulary {
    private String word;

    public Inflexible(String word, List<VocabularyTranslation> translations) {
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
        return assembleWordbaseEntry(null);
    }
}
