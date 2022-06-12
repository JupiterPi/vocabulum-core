package jupiterpi.vocabulum.core.ta;

import jupiterpi.vocabulum.core.Main;
import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbForm;
import jupiterpi.vocabulum.core.vocabularies.declined.nouns.NounForm;

import java.util.ArrayList;
import java.util.List;

public class TranslationAssistance {
    public static TAResult runTranslationAssistance(I18n i18n) {
        try {
            List<TAResult.TAResultItem> items = new ArrayList<>();

            items.add(new TAResult.TAWord(
                    "Asinus",
                    Main.wordbaseManager.loadVocabulary("asinus"),
                    NounForm.fromString("Nom. Sg. m.", i18n)
            ));

            items.add(new TAResult.TAWord(
                    "stat",
                    Main.wordbaseManager.loadVocabulary("stare"),
                    VerbForm.fromString("3. Pers. Sg.", i18n)
            ));

            items.add(new TAResult.TAPunctuation(","));

            items.add(new TAResult.TAWord(
                    "et",
                    Main.wordbaseManager.loadVocabulary("et"),
                    null
            ));

            items.add(new TAResult.TAWord(
                    "exspectat",
                    Main.wordbaseManager.loadVocabulary("exspectare"),
                    VerbForm.fromString("3. Pers. Sg.", i18n)
            ));

            return new TAResult(items);

        } catch (ParserException | LexerException e) {
            e.printStackTrace();
        }
        return null;
    }
}