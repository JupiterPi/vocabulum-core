package jupiterpi.vocabulum.core.ta;

import jupiterpi.vocabulum.core.Main;
import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.wordbase.Wordbase;
import jupiterpi.vocabulum.core.i18n.I18n;

import java.util.ArrayList;
import java.util.List;

public class TranslationAssistance {
    public static TAResult runTranslationAssistance(String sentence, I18n i18n) throws TAException {
        sentence = sentence.replace(",", " <comma> ");
        sentence = sentence.replace(".", " <dot>");
        String[] words = sentence.split(" ");

        List<TAResult.TAResultItem> items = new ArrayList<>();
        for (String word : words) {
            if (word.isEmpty()) continue;
            if (word.equals("<comma>")) {
                items.add(new TAResult.TAPunctuation(","));
            } else if (word.equals("<dot>")) {
                items.add(new TAResult.TAPunctuation("."));
            } else {
                List<Wordbase.IdentificationResult> results = Database.get().getWordbase().identifyWord(word.toLowerCase());
                if (results.size() == 0) {
                    throw new TAException("Cannot identify word: " + word.toLowerCase());
                } else if (results.size() > 1) {
                    List<String> resultsList = new ArrayList<>();
                    results.forEach((result) -> resultsList.add(result.getVocabulary().getBaseForm()));
                    String resultsString = String.join(", ", resultsList);
                    throw new TAException("Cannot definitely identify word: " + word.toLowerCase() + ". Found: " + resultsString);
                }
                Wordbase.IdentificationResult result = results.get(0);
                items.add(new TAResult.TAWord(word, result.getVocabulary(), result.getForms()));
            }
        }
        return new TAResult(items);
    }
}