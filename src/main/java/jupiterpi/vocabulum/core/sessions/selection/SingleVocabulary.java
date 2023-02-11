package jupiterpi.vocabulum.core.sessions.selection;

import jupiterpi.vocabulum.core.vocabularies.Vocabulary;

import java.util.List;

/**
 * A vocabulary selection that contains a single vocabulary.
 */
public class SingleVocabulary implements StringifiableVocabularySelection {
    private Vocabulary vocabulary;

    public SingleVocabulary(Vocabulary vocabulary) {
        this.vocabulary = vocabulary;
    }

    @Override
    public List<Vocabulary> getVocabularies() {
        return List.of(vocabulary);
    }

    @Override
    public String getString() {
        return vocabulary.getBaseForm();
    }
}
