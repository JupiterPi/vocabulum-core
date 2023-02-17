package jupiterpi.vocabulum.core.db.portions;

import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.VocabularyForm;
import jupiterpi.vocabulum.core.vocabularies.conjugated.Verb;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbForm;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.Adjective;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.AdjectiveForm;
import jupiterpi.vocabulum.core.vocabularies.declined.nouns.Noun;
import jupiterpi.vocabulum.core.vocabularies.declined.nouns.NounForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides methods for interacting with vocabularies in <code>Portions</code>.
 */
public class Dictionary {
    private Portions portions;
    private Map<String, Vocabulary> vocabularies;
    private Map<Vocabulary, List<String>> allFormsToString;

    public Dictionary(Portions portions) {
        this.portions = portions;

        vocabularies = new HashMap<>();
        for (Portion portion : portions.getPortions().values()) {
            for (Vocabulary vocabulary : portion.getVocabularies()) {
                vocabularies.put(vocabulary.getBaseForm(), vocabulary);
            }
        }

        allFormsToString = new HashMap<>();
        for (Vocabulary vocabulary : vocabularies.values()) {
            allFormsToString.put(vocabulary, vocabulary.getAllFormsToString());
        }
    }

    /**
     * Finds the instance of a vocabulary in a portion.
     * @param base_form the base form of the looked for vocabulary
     * @return the vocabulary instance
     */
    public Vocabulary getVocabulary(String base_form) {
        return vocabularies.get(base_form);
    }

    /**
     * Finds vocabularies that a word might be.
     * @param word          the word in question
     * @param partialSearch whether it should look also for vocabularies whose forms contain the word in question
     * @return vocabularies that the word might be
     */
    public List<IdentificationResult> identifyWord(String word, boolean partialSearch) {
        List<VocabularyForm> nullForms = new ArrayList<>();
        nullForms.add(null);

        List<Dictionary.IdentificationResult> results = new ArrayList<>();
        allFormsToString.forEach((vocabulary, allFormsToString) -> {
            boolean isResult = false;
            for (String form : allFormsToString) {
                if (partialSearch ? form.contains(word) : form.equals(word)) {
                    isResult = true;
                    break;
                }
            }

            if (isResult) {
                List<VocabularyForm> forms = switch (vocabulary.getKind()) {
                    case NOUN -> new ArrayList<>(((Noun) vocabulary).identifyForm(word, partialSearch));
                    case ADJECTIVE -> new ArrayList<>(((Adjective) vocabulary).identifyForm(word, partialSearch));
                    case VERB -> new ArrayList<>(((Verb) vocabulary).identifyForm(word, partialSearch));
                    case INFLEXIBLE -> new ArrayList<>(nullForms);
                };
                results.add(new Dictionary.IdentificationResult(vocabulary, forms));
            }
        });
        return results;
    }

    /**
     * Represents a vocabulary as a result of a word identification.
     * @see #identifyWord(String, boolean)
     */
    public static class IdentificationResult {
        private Vocabulary vocabulary;
        private List<VocabularyForm> forms;

        public IdentificationResult(Vocabulary vocabulary, List<VocabularyForm> forms) {
            this.vocabulary = vocabulary;
            this.forms = forms;
        }

        /**
         * @return the vocabulary of this identification
         */
        public Vocabulary getVocabulary() {
            return vocabulary;
        }

        /**
         * @return the forms of the vocabulary that the word to identify might be
         */
        public List<VocabularyForm> getForms() {
            return forms;
        }

        /* shortcuts */

        /**
         * @return the made string of the first form in <code>getForms()</code>
         */
        public String makePrimaryFoundForm() {
            if (forms.size() == 0) {
                return vocabulary.getBaseForm();
            }
            VocabularyForm form = forms.get(0);
            return switch (vocabulary.getKind()) {
                case NOUN -> ((Noun) vocabulary).makeForm((NounForm) form).toString();
                case ADJECTIVE -> ((Adjective) vocabulary).makeForm((AdjectiveForm) form).toString();
                case VERB -> ((Verb) vocabulary).makeForm((VerbForm) form).toString();
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
}