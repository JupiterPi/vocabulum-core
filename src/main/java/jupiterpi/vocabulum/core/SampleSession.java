package jupiterpi.vocabulum.core;

import jupiterpi.tools.ui.ConsoleInterface;
import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.sessions.Session;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;

import java.util.List;

public class SampleSession extends ConsoleInterface {
    public void run(I18n i18n) throws Session.SessionLifecycleException {
        Session session = new Session(Database.get().getPortions().getPortion("A"));
        session.start();
        while (true) {
            do {
                Vocabulary vocabulary = session.getNextVocabulary();
                String prompt = vocabulary.getBaseForm();
                String input = in(prompt + " > ");
                List<TranslationSequence.ValidatedTranslation> translations = vocabulary.getTranslations().validateInput(input);
                int amountRight = 0;
                for (TranslationSequence.ValidatedTranslation translation : translations) {
                    if (translation.isValid()) {
                        amountRight++;
                    }
                }
                float score = ((float) amountRight) / ((float) translations.size());
                boolean passed = score >= 0.5f;
                out(passed ? "✅" : "❌");
                out(vocabulary.vocabularyToString(i18n));
                session.provideFeedback(vocabulary, passed);
            } while (!session.isRoundDone());
            if (session.isAllDone()) break;
            out("Done with score: " + session.getResult().getScore());
            out("Repeating wrong vocabularies...");
        }
        out("All done with score: " + session.getResult().getScore());
    }
}
