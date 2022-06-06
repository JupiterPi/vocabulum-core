package jupiterpi.vocabulum.core;

import jupiterpi.tools.ui.ConsoleInterface;
import jupiterpi.vocabulum.core.i18n.I18nException;
import jupiterpi.vocabulum.core.interpreter.lexer.Lexer;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.AdjectiveForm;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.ComparativeForm;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.RuntimeAdjective;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.nouns.RuntimeNoun;
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
        final String ERROR = texts.getString("error");

        while (true) {
            try {
                String wordInput = in("> ");
                if (wordInput.equals("")) break;
                Vocabulary vocabulary = Vocabulary.fromString(wordInput, Main.i18n);
                if (vocabulary.getKind() == Vocabulary.Kind.NOUN) {
                    RuntimeNoun noun = (RuntimeNoun) vocabulary;
                    while (true) {
                        try {
                            String formInput = in("[" + NOUN + "] " + noun.getBaseForm() + " > ");
                            if (formInput.equals("")) break;
                            NounForm form = NounForm.fromString(formInput, Main.i18n);
                            out(noun.makeForm(form));
                        } catch (ParserException | DeclinedFormDoesNotExistException | LexerException e) {
                            out(ERROR + ": " + e.getClass().getSimpleName() + " \"" + e.getMessage() + "\"");
                        }
                    }
                } else if (vocabulary.getKind() == Vocabulary.Kind.ADJECTIVE) {
                    RuntimeAdjective adjective = (RuntimeAdjective) vocabulary;
                    while (true) {
                        try {
                            String formInput = in("[" + ADJECTIVE + "] " + adjective.getBaseForm() + " > ");
                            if (formInput.equals("")) break;
                            AdjectiveForm form = AdjectiveForm.fromString(formInput, Main.i18n);
                            out(adjective.makeForm(form));
                        } catch (ParserException | DeclinedFormDoesNotExistException | LexerException e) {
                            out(ERROR + ": " + e.getClass().getSimpleName() + " \"" + e.getMessage() + "\"");
                        }
                    }
                }
            } catch (ParserException | DeclinedFormDoesNotExistException | I18nException | LexerException e) {
                out(ERROR + ": " + e.getClass().getSimpleName() + " \"" + e.getMessage() + "\"");
            }
        }
        out(texts.getString("done"));
    }
}