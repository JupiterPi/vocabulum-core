package jupiterpi.vocabulum.core.vocabularies.translations.exchangeables;

import java.util.ArrayList;
import java.util.List;

/**
 * Checks for three different cases:
 * <p>
 * 1) (an-, er-, zu)hören --> anhören, erhören, zuhören, hören
 * <p>
 * 2) ich sage/behaupte --> ich sage, ich behaupte // ich [renne weg/flüchte --> ich renne weg, ich flüchte
 * <p>
 * 3) irgendein(e) --> irgendein, irgendeine
 * */
public class ExchangeablesPreprocessor {
    private List<String> result;

    public ExchangeablesPreprocessor(String str) {
        this.result = preprocess(str);
    }

    public List<String> getResult() {
        return result;
    }

    /* preprocessor */

    private List<String> preprocess(String str) {
        if (str.contains("(")) {

            int start = str.indexOf("(") + 1;
            int end = str.indexOf(")");
            String beforeParens = str.substring(0, start - 1);
            String afterParens = str.substring(end + 1);
            String inParens = str.substring(start, end);
            if (inParens.contains(",")) {

                // case 1)
                List<String> resolvedStrings = new ArrayList<>();
                for (String exchangeable : inParens.split(" *, *")) {
                    if (exchangeable.endsWith("-")) exchangeable = exchangeable.substring(0, exchangeable.length() - 1);
                    resolvedStrings.add(beforeParens + exchangeable + afterParens);
                }
                resolvedStrings.add(beforeParens + afterParens);
                return resolvedStrings;

            } else {

                // case 3)
                int beforeParenIndex = str.indexOf("(") - 1;
                int afterParenIndex = str.indexOf(")") + 1;
                if (
                        (beforeParenIndex >= 0 && str.charAt(beforeParenIndex) != ' ')
                        || (afterParenIndex < str.length() && str.charAt(afterParenIndex) != ' ')
                ) {
                    return List.of(
                            beforeParens + afterParens,
                            beforeParens + inParens + afterParens
                    );
                }

            }
        }

        // case 2)
        if (str.contains("/")) {
            String[] parts = str.split(" */ *");
            String beforeSlash = parts[0];
            String afterSlash = parts[1];

            int beforeSlashSeparatorIndex = beforeSlash.indexOf(beforeSlash.contains("[") ? "[" : " ");
            String wordsBefore;
            String firstSlashClause;
            if (beforeSlashSeparatorIndex >= 0) {
                wordsBefore = beforeSlash.substring(0, beforeSlashSeparatorIndex).trim() + " ";
                firstSlashClause = beforeSlash.substring(beforeSlashSeparatorIndex + 1);
            } else {
                wordsBefore = "";
                firstSlashClause = beforeSlash;
            }

            int afterSlashSeparatorIndex = afterSlash.indexOf(afterSlash.contains("]") ? "]" : " ");
            String wordsAfter;
            String secondSlashClause;
            if (afterSlashSeparatorIndex >= 0) {
                wordsAfter = " " + afterSlash.substring(afterSlashSeparatorIndex + 1).trim();
                secondSlashClause = afterSlash.substring(0, afterSlashSeparatorIndex);
            } else {
                wordsAfter = "";
                secondSlashClause = afterSlash;
            }

            return List.of(
                    (wordsBefore + firstSlashClause.trim() + wordsAfter).trim(),
                    (wordsBefore + secondSlashClause.trim() + wordsAfter).trim()
            );
        }

        return List.of(str);
    }
}