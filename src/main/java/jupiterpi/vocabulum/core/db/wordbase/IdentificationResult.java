package jupiterpi.vocabulum.core.db.wordbase;

import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.VocabularyForm;

import java.util.List;

public class IdentificationResult {
    private Vocabulary vocabulary;
    private List<VocabularyForm> forms;

    public IdentificationResult(Vocabulary vocabulary, List<VocabularyForm> forms) {
        this.vocabulary = vocabulary;
        this.forms = forms;
    }

    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    public List<VocabularyForm> getForms() {
        return forms;
    }

    @Override
    public String toString() {
        return "IdentificationResult{" +
                "vocabulary=" + vocabulary +
                ", forms=" + forms +
                '}';
    }
}
