package jupiterpi.vocabulum.core.ta;

import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.VocabularyForm;

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
        private VocabularyForm form;

        public TAWord(String word, Vocabulary vocabulary, VocabularyForm form) {
            this.word = word;
            this.vocabulary = vocabulary;
            this.form = form;
        }

        public String getWord() {
            return word;
        }

        public String getForm(I18n i18n) {
            if (form != null) return form.formToString(i18n);
            else return "";
        }

        public List<String> getTranslations() {
            return vocabulary.getTranslations();
        }

        // ---

        @Override
        public String getItem() {
            return getWord();
        }

        @Override
        public List<String> getLines(I18n i18n) {
            List<String> lines = new ArrayList<>();
            lines.add(getForm(i18n));
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