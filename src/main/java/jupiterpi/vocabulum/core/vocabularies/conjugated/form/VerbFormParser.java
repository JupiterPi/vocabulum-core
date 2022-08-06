package jupiterpi.vocabulum.core.vocabularies.conjugated.form;

import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;

public class VerbFormParser {
    private VerbForm verbForm;

    public VerbFormParser(TokenSequence tokens) {
        this.verbForm = parseVerbForm(tokens);
    }

    public VerbForm getVerbForm() {
        return verbForm;
    }

    /* parser */

    private VerbForm parseVerbForm(TokenSequence tokens) {
        //TODO implement
        return null;
    }
}