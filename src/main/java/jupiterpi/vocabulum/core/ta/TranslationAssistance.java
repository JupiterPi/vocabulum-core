package jupiterpi.vocabulum.core.ta;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.wordbase.IdentificationResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TranslationAssistance {
    private TAResult result;

    public TranslationAssistance(String sentence) throws TAException {
        runTranslationAssistance(sentence);
    }

    public TAResult getResult() {
        return result;
    }

    /* run */

    private void runTranslationAssistance(String sentence) throws TAException {
        List<TAToken> tokens = tokenize(sentence);
        List<TAResult.TAResultItem> items = new ArrayList<>();
        for (TAToken token : tokens) {
            if (token.getType() == TAToken.TAWordType.PUNCTUATION) {
                items.add(new TAResult.TAPunctuation(token.getContent()));
            } else {
                String word = token.getContent();
                List<IdentificationResult> results = Database.get().getWordbase().identifyWord(word.toLowerCase(), false);
                if (results.size() == 0) {
                    throw new TAException("Cannot identify word: " + word.toLowerCase());
                } else if (results.size() > 1) {
                    List<String> resultsList = new ArrayList<>();
                    results.forEach((result) -> resultsList.add(result.getVocabulary().getBaseForm()));
                    String resultsString = String.join(", ", resultsList);
                    throw new TAException("Cannot definitely identify word: " + word.toLowerCase() + ". Found: " + resultsString);
                }
                IdentificationResult result = results.get(0);
                items.add(new TAResult.TAWord(word, result.getVocabulary(), result.getForms()));
            }
        }
        result = new TAResult(items);
    }

    private List<TAToken> tokenize(String sentence) {
        sentence = sentence.replace(",", " <comma> ");
        sentence = sentence.replace(".", " <dot>");
        String[] words = sentence.split(" ");

        List<TAToken> tokens = new ArrayList<>();
        for (String word : words) {
            if (word.isEmpty()) continue;
            tokens.add(switch (word) {
                case "<comma>" -> new TAToken(TAToken.TAWordType.PUNCTUATION, ",");
                case "<dot>" -> new TAToken(TAToken.TAWordType.PUNCTUATION, ".");
                default -> new TAToken(TAToken.TAWordType.WORD, word);
            });
        }
        return tokens;
    }

    public static class TAToken {
        public enum TAWordType {
            WORD, PUNCTUATION
        }

        private TAWordType type;
        private String content;

        public TAToken(TAWordType type, String content) {
            this.type = type;
            this.content = content;
        }

        public TAWordType getType() {
            return type;
        }

        public String getContent() {
            return content;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TAToken taToken = (TAToken) o;
            return type == taToken.type && Objects.equals(content, taToken.content);
        }
    }
}