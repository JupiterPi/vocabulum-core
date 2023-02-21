package jupiterpi.vocabulum.core.vocabularies.inflexible;

import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;

import java.util.List;
import java.util.Objects;

public class Inflexible extends Vocabulary {
    private String word;

    public Inflexible(String word, TranslationSequence translations, String portion) {
        super(translations, portion);
        this.word = word;
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
    public List<String> getAllFormsToString() {
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
