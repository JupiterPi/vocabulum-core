package jupiterpi.vocabulum.core.ta;

import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.VocabularyForm;
import jupiterpi.vocabulum.core.vocabularies.translations.VocabularyTranslation;

import java.util.ArrayList;
import java.util.List;

public class TAResult {
    private List<TAResultItem> items;

    public TAResult(List<TAResultItem> items) {
        this.items = items;
    }

    public List<TAResultItem> getItems() {
        return items;
    }

    // items

    public interface TAResultItem {
        String getItem();
        List<String> getLines(I18n i18n);
    }

    public static class TAWord implements TAResultItem {
        private String word;
        private Vocabulary vocabulary;
        private List<VocabularyForm> forms;

        public TAWord(String word, Vocabulary vocabulary, List<VocabularyForm> forms) {
            this.word = word;
            this.vocabulary = vocabulary;
            this.forms = forms;
        }

        public String getWord() {
            return word;
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

        // ---

        @Override
        public String getItem() {
            return getWord();
        }

        @Override
        public List<String> getLines(I18n i18n) {
            List<String> lines = new ArrayList<>();
            lines.addAll(getForms(i18n));
            lines.addAll(getTranslations());
            return lines;
        }
    }

    public static class TAPunctuation implements TAResultItem {
        private String punctuation;

        public TAPunctuation(String punctuation) {
            this.punctuation = punctuation;
        }

        public String getPunctuation() {
            return punctuation;
        }

        // ---

        @Override
        public String getItem() {
            return getPunctuation();
        }

        @Override
        public List<String> getLines(I18n i18n) {
            return new ArrayList<>();
        }
    }
}