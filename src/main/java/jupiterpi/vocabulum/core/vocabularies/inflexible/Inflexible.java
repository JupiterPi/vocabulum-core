package jupiterpi.vocabulum.core.vocabularies.inflexible;

import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import org.bson.Document;

import java.util.List;
import java.util.Objects;

public class Inflexible extends Vocabulary {
    private String word;

    public Inflexible(String word, TranslationSequence translations, String portion) {
        super(translations, portion);
        this.word = word;
    }

    public static Inflexible readFromDocument(Document document) {
        String word = document.getString("base_form");
        return new Inflexible(word, TranslationSequence.readFromDocument(document), document.getString("portion"));
    }

    @Override
    public String getBaseForm() {
        return word;
    }

    @Override
    public String getDefinition() {
        return word;
    }

    @Override
    public Kind getKind() {
        return Kind.INFLEXIBLE;
    }

    @Override
    public Document generateWordbaseEntrySpecificPart() {
        return new Document();
    }

    @Override
    protected List<String> getAllFormsToString() {
        return List.of(word);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inflexible that = (Inflexible) o;
        return Objects.equals(word, that.word);
    }
}
