package jupiterpi.vocabulum.core;

import jupiterpi.tools.ui.ConsoleInterface;
import jupiterpi.vocabulum.core.i18n.I18nException;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.conjugated.Verb;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbForm;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.Adjective;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.AdjectiveForm;
import jupiterpi.vocabulum.core.vocabularies.declined.nouns.Noun;
import jupiterpi.vocabulum.core.vocabularies.declined.nouns.NounForm;
import org.bson.Document;

public class Terminal extends ConsoleInterface {
    public void run() {
        out("----- Vocabulum Terminal -----");

        Document texts = (Document) Main.i18n.getTexts().get("terminal");

        out("");
        out(texts.getString("help-text"));
        out("");

        final String NOUN = texts.getString("noun");
        final String ADJECTIVE = texts.getString("adjective");
        final String VERB = texts.getString("verb");
        final String ERROR = texts.getString("error");

        while (true) {
            try {
                String wordInput = in("> ");
                if (wordInput.equals("")) break;
                Vocabulary vocabulary = Vocabulary.fromString(wordInput, Main.i18n);
                if (vocabulary.getKind() == Vocabulary.Kind.NOUN) {
                    Noun noun = (Noun) vocabulary;
                    while (true) {
                        try {
                            String formInput = in("[" + NOUN + "] " + noun.getBaseForm() + " > ");
                            if (formInput.equals("")) break;
                            NounForm form = NounForm.fromString(formInput, Main.i18n);
                            out(noun.makeForm(form));
                        } catch (Exception e) {
                            out(ERROR + ": " + e.getClass().getSimpleName() + " \"" + e.getMessage() + "\"");
                        }
                    }
                } else if (vocabulary.getKind() == Vocabulary.Kind.ADJECTIVE) {
                    Adjective adjective = (Adjective) vocabulary;
                    while (true) {
                        try {
                            String formInput = in("[" + ADJECTIVE + "] " + adjective.getBaseForm() + " > ");
                            if (formInput.equals("")) break;
                            AdjectiveForm form = AdjectiveForm.fromString(formInput, Main.i18n);
                            out(adjective.makeForm(form));
                        } catch (Exception e) {
                            out(ERROR + ": " + e.getClass().getSimpleName() + " \"" + e.getMessage() + "\"");
                        }
                    }
                } else if (vocabulary.getKind() == Vocabulary.Kind.VERB) {
                    Verb verb = (Verb) vocabulary;
                    while (true) {
                        try {
                            String formInput = in("[" + VERB + "] " + verb.getBaseForm() + " > ");
                            if (formInput.equals("")) break;
                            VerbForm form = VerbForm.fromString(formInput, Main.i18n);
                            out(verb.makeForm(form));
                        } catch (Exception e) {
                            out(ERROR + ": " + e.getClass().getSimpleName() + " \"" + e.getMessage() + "\"");
                        }
                    }
                }
            } catch (Exception e) {
                out(ERROR + ": " + e.getClass().getSimpleName() + " \"" + e.getMessage() + "\"");
            }
        }
        out(texts.getString("done"));
    }
}