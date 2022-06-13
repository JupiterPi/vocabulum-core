package jupiterpi.vocabulum.core.ta;

import jupiterpi.vocabulum.core.Main;
import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.wordbase.WordbaseManager;

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
                List<WordbaseManager.IdentificationResult> results = Main.wordbaseManager.identifyWord(word.toLowerCase());
                if (results.size() != 1) throw new TAException("Cannot (definitively) identify word: " + word.toLowerCase());
                WordbaseManager.IdentificationResult result = results.get(0);
                items.add(new TAResult.TAWord(word, result.getVocabulary(), result.getForms()));
            }
        }
        return new TAResult(items);
    }
}