package jupiterpi.vocabulum.core.vocabularies.declined.nouns;

import jupiterpi.vocabulum.core.Main;
import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.interpreter.lexer.Lexer;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;
import jupiterpi.vocabulum.core.vocabularies.VocabularyForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;

public class NounForm implements VocabularyForm {
    private DeclinedForm declinedForm;

    public NounForm(DeclinedForm declinedForm) {
        this.declinedForm = declinedForm;
    }

    public static NounForm fromString(String expr, I18n i18n) throws LexerException, ParserException {
        return fromTokens(new Lexer(expr, i18n).getTokens());
    }

    public static NounForm fromTokens(TokenSequence tokens) throws ParserException {
        DeclinedForm form = DeclinedForm.fromTokens(tokens);
        return new NounForm(form);
    }

    public DeclinedForm getDeclinedForm() {
        return declinedForm;
    }

    // to string

    @Override
    public String formToString(I18n i18n) {
        String str = "";
        str += declinedForm.formToString(i18n);
        return str;
    }

    @Override
    public String toString() {
        return "Noun{form=" + declinedForm.toString() + "}";
    }
}