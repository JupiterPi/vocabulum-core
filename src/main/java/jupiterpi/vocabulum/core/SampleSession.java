package jupiterpi.vocabulum.core;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.sessions.Session;
import jupiterpi.vocabulum.core.sessions.SessionRound;
import jupiterpi.vocabulum.core.util.ConsoleInterface;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;

import java.util.List;

public class SampleSession extends ConsoleInterface {
    public void run() throws Session.SessionLifecycleException {
        Session session = new Session(Database.get().getPortions().getPortion("A"));
        session.start();
        while (true) {
            SessionRound round = new SessionRound(session.getCurrentVocabularies());
            do {
                Vocabulary vocabulary = round.getNextVocabulary();
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
                out(vocabulary.vocabularyToString());
                round.provideFeedback(vocabulary, passed);
            } while (!round.isDone());
            session.provideFeedback(round.getFeedback());
            if (session.isDone()) break;
            out("Done with score: " + session.getResult().getScore());
            out("Repeating wrong vocabularies...");
        }
        out("All done with score: " + session.getResult().getScore());
    }
}
