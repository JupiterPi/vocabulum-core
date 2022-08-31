package jupiterpi.vocabulum.core.sessions.selection;

import jupiterpi.vocabulum.core.vocabularies.Vocabulary;

import java.util.List;
import java.util.Objects;

public class BasicVocabularySelection implements VocabularySelection {
    private List<Vocabulary> vocabularies;

    public BasicVocabularySelection(List<Vocabulary> vocabularies) {
        this.vocabularies = vocabularies;
    }

    public BasicVocabularySelection(Vocabulary... vocabularies) {
        this.vocabularies = List.of(vocabularies);
    }

    public List<Vocabulary> getVocabularies() {
        return vocabularies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicVocabularySelection that = (BasicVocabularySelection) o;
        return Objects.equals(vocabularies, that.vocabularies);
    }

    @Override
    public String toString() {
        return "BasicVocabularySelection{" + vocabularies + "}";
    }
}