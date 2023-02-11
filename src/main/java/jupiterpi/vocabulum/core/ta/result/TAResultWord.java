package jupiterpi.vocabulum.core.ta.result;

import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.VocabularyForm;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a word/vocabulary that's part of a translation assistance result.
 * @see TAResult
 */
public class TAResultWord implements TAResult.TAResultItem {
    private String word;
    private List<PossibleWord> possibleWords;

    public TAResultWord(String word, List<PossibleWord> possibleWords) {
        this.word = word;
        this.possibleWords = possibleWords;
    }

    /**
     * Equivalent to <code>getItem()</code>.
     * @return the word as it is in the sentence
     */
    public String getWord() {
        return word;
    }

    @Override
    public String toString() {
        return word + ": " + String.join(", ", possibleWords.stream().map(possibleWord -> possibleWord.getVocabulary().toString()).toList());
    }

    /**
     * @return a list of possible vocabularies that this word might be
     */
    public List<PossibleWord> getPossibleWords() {
        return possibleWords;
    }

    public static class PossibleWord {
        private Vocabulary vocabulary;
        private List<VocabularyForm> forms;

        public PossibleWord(Vocabulary vocabulary, List<VocabularyForm> forms) {
            this.vocabulary = vocabulary;
            this.forms = forms;
        }

        /**
         * @return the forms of this vocabulary that the word might be
         */
        public List<VocabularyForm> getFormsRaw() {
            return forms;
        }

        /**
         * @return this vocabulary, which the word might be
         */
        public Vocabulary getVocabulary() {
            return vocabulary;
        }

        /**
         * @return the forms of this vocabulary that the word might be
         */
        public List<String> getForms() {
            List<String> forms = new ArrayList<>();
            for (VocabularyForm form : this.forms) {
                if (form != null) forms.add(form.formToString());
            }
            return forms;
        }

        /**
         * Equivalent to <code>getVocabulary().getTranslationsToString()</code>.
         * @return the possible translations of this vocabulary
         */
        public List<String> getTranslations() {
            return vocabulary.getTranslationsToString();
        }
    }

    // ---

    @Override
    public String getItem() {
        return getWord();
    }

    @Override
    public List<String> getLines() {
        List<String> lines = new ArrayList<>();
        for (PossibleWord word : possibleWords) {
            lines.addAll(word.getForms());
            lines.addAll(word.getTranslations());
            lines.add("");
        }
        if (possibleWords.size() > 0) lines = lines.subList(0, lines.size()-1);
        return lines;
    }
}
