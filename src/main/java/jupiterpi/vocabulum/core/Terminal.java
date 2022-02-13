package jupiterpi.vocabulum.core;

import jupiterpi.tools.ui.ConsoleInterface;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.declinated.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declinated.adjectives.Adjective;
import jupiterpi.vocabulum.core.vocabularies.declinated.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declinated.nouns.Noun;

public class Terminal extends ConsoleInterface {
    public void run() throws ParserException, DeclinedFormDoesNotExistException, LexerException {
        out("----- Vocabulum Terminal -----");

        out("");
        out("Usage: Type a vocabulary after \">\" (e. g. \"amicus, amici m.\", \"laetus, laeta, laetum\" or \"felix, Gen. felicis\"). Then after the indented \">\", type the form you want to generate (e. g. \"Nom. Sg.\" or \"Gen. Pl. f.\"). To go back at any time, press enter without typing something on a prompt. Note: \"Akk.\" is \"Acc.\"!");
        out("");

        while (true) {
            String wordInput = in("> ");
            if (wordInput.equals("")) break;
            Vocabulary vocabulary = Vocabulary.fromString(wordInput);
            if (vocabulary.getKind() == Vocabulary.Kind.NOUN) {
                Noun noun = (Noun) vocabulary;
                while (true) {
                    String formInput = in("[NOUN] " + noun.getBaseForm() + " > ");
                    if (formInput.equals("")) break;
                    DeclinedForm form = DeclinedForm.fromString(formInput);
                    out(noun.makeForm(form));
                }
            } else if (vocabulary.getKind() == Vocabulary.Kind.ADJECTIVE) {
                Adjective adjective = (Adjective) vocabulary;
                while (true) {
                    String formInput = in("[ADJECTIVE] " + adjective.getBaseForm() + " > ");
                    if (formInput.equals("")) break;
                    DeclinedForm form = DeclinedForm.fromString(formInput);
                    out(adjective.makeForm(form));
                }
            }
        }
        out("Done. ");
    }
}