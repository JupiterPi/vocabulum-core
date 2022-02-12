package jupiterpi.vocabulum.core;

import jupiterpi.tools.ui.ConsoleInterface;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.declinated.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declinated.Noun;
import jupiterpi.vocabulum.core.vocabularies.declinated.form.DeclinedForm;

public class Terminal extends ConsoleInterface {
    public void run() throws ParserException, DeclinedFormDoesNotExistException, LexerException {
        out("----- Vocabulum Terminal -----");

        out("");
        while (true) {
            String nounInput = in("> ");
            if (nounInput.equals("")) break;
            Noun noun = (Noun) Vocabulary.fromString(nounInput);
            while (true) {
                String formInput = in(noun.getBaseForm() + " > ");
                if (formInput.equals("")) break;
                DeclinedForm form = DeclinedForm.formString(formInput);
                out(noun.getForm(form));
            }
        }
        out("Done. ");
    }
}