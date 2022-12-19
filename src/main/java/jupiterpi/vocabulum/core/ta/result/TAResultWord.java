package jupiterpi.vocabulum.core.ta.result;

import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.VocabularyForm;

import java.util.ArrayList;
import java.util.List;

public class TAResultWord implements TAResult.TAResultItem {
    private String word;
    private List<PossibleWord> possibleWords;

    public TAResultWord(String word, List<PossibleWord> possibleWords) {
        this.word = word;
        this.possibleWords = possibleWords;
    }

    public String getWord() {
        return word;
    }

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

        public List<VocabularyForm> getFormsRaw() {
            return forms;
        }

        public Vocabulary getVocabulary() {
            return vocabulary;
        }

        public List<String> getForms(I18n i18n) {
            List<String> forms = new ArrayList<>();
            for (VocabularyForm form : this.forms) {
                if (form != null) forms.add(form.formToString(i18n));
            }
            return forms;
        }

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
    public List<String> getLines(I18n i18n) {
        List<String> lines = new ArrayList<>();
        for (PossibleWord word : possibleWords) {
            lines.addAll(word.getForms(i18n));
            lines.addAll(word.getTranslations());
            lines.add("");
        }
        if (possibleWords.size() > 0) lines = lines.subList(0, lines.size()-1);
        return lines;
    }
}
