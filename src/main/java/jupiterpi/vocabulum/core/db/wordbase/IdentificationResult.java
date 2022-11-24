package jupiterpi.vocabulum.core.db.wordbase;

import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.VocabularyForm;
import jupiterpi.vocabulum.core.vocabularies.conjugated.Verb;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbForm;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.Adjective;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.AdjectiveForm;
import jupiterpi.vocabulum.core.vocabularies.declined.nouns.Noun;
import jupiterpi.vocabulum.core.vocabularies.declined.nouns.NounForm;

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

    /* shortcuts */

    public String makePrimaryFoundForm() {
        if (forms.size() == 0) {
            return vocabulary.getBaseForm();
        }
        VocabularyForm form = forms.get(0);
        return switch (vocabulary.getKind()) {
            case NOUN -> ((Noun) vocabulary).makeFormOrDash((NounForm) form);
            case ADJECTIVE -> ((Adjective) vocabulary).makeFormOrDash((AdjectiveForm) form);
            case VERB -> ((Verb) vocabulary).makeFormOrDash((VerbForm) form);
            case INFLEXIBLE -> vocabulary.getBaseForm();
        };
    }

    @Override
    public String toString() {
        return "IdentificationResult{" +
                "vocabulary=" + vocabulary +
                ", forms=" + forms +
                '}';
    }
}
