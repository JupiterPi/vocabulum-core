package jupiterpi.vocabulum.core.ta;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.portions.Dictionary;
import jupiterpi.vocabulum.core.ta.result.TAResult;
import jupiterpi.vocabulum.core.ta.result.TAResultPunctuation;
import jupiterpi.vocabulum.core.ta.result.TAResultWord;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that identifies and specifies words in a whole sentence.
 * Initializing it and calling <code>getResult()</code> provides
 * you with a detailed representation of the vocabularies in that sentence,
 * which you can then, for example, present to the user.
 */
public class TranslationAssistance {
    private TAResult result;

    /**
     * Constructs a new translation assistance instance and runs the analysis.
     * @param sentence the sentence to analyze
     */
    public TranslationAssistance(String sentence) {
        runTranslationAssistance(sentence);
    }

    /**
     * @return the result
     */
    public TAResult getResult() {
        return result;
    }

    /* run */

    private void runTranslationAssistance(String sentence) {
        List<TAToken> tokens = tokenize(sentence);
        List<TAResult.TAResultItem> items = new ArrayList<>();
        for (TAToken token : tokens) {
            if (token.getType() == TAToken.TAWordType.PUNCTUATION) {
                items.add(new TAResultPunctuation(token.getContent()));
            } else {
                String word = token.getContent();
                List<Dictionary.IdentificationResult> results = Database.get().getDictionary().identifyWord(word.toLowerCase(), false);
                List<TAResultWord.PossibleWord> possibleWords = results.stream()
                        .map(r -> new TAResultWord.PossibleWord(r.getVocabulary(), r.getForms()))
                        .toList();
                items.add(new TAResultWord(word, possibleWords));
            }
        }
        result = new TAResult(items);
    }

    private List<TAToken> tokenize(String sentence) {
        Pattern regex = Pattern.compile("(\\.+|,|;|-+|[?!]+|\"|'|'')");
        Matcher matcher = regex.matcher(sentence);
        StringBuffer sb = new StringBuffer();
        if (matcher.find()) {
            do {
                matcher.appendReplacement(sb, " <" + matcher.group().trim() + "> ");
            } while (matcher.find());
            sentence = sb.toString();
        }

        String[] words = sentence.split(" ");

        List<TAToken> tokens = new ArrayList<>();
        for (String word : words) {
            if (word.isEmpty()) continue;
            if (word.matches("<.*>")) {
                tokens.add(new TAToken(TAToken.TAWordType.PUNCTUATION, word.substring(1, word.length()-1)));
            } else {
                tokens.add(new TAToken(TAToken.TAWordType.WORD, word));
            }
        }
        return tokens;
    }
}