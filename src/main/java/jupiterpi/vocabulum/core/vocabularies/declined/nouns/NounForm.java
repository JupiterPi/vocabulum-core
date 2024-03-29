package jupiterpi.vocabulum.core.vocabularies.declined.nouns;

import jupiterpi.vocabulum.core.interpreter.lexer.Lexer;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;
import jupiterpi.vocabulum.core.vocabularies.VocabularyForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;

import java.util.Comparator;
import java.util.Objects;

public class NounForm implements VocabularyForm {
    private DeclinedForm declinedForm;

    public NounForm(DeclinedForm declinedForm) {
        this.declinedForm = declinedForm;
    }

    public static NounForm fromString(String expr) throws LexerException, ParserException {
        return fromTokens(new Lexer(expr).getTokens());
    }

    public static NounForm fromTokens(TokenSequence tokens) throws ParserException {
        DeclinedForm form = DeclinedForm.fromTokens(tokens);
        return new NounForm(form);
    }

    public DeclinedForm getDeclinedForm() {
        return declinedForm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NounForm nounForm = (NounForm) o;
        return Objects.equals(declinedForm, nounForm.declinedForm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(declinedForm);
    }

    // to string

    @Override
    public String formToString() {
        String str = "";
        str += declinedForm.formToString();
        return str;
    }

    @Override
    public String toString() {
        return "Noun{form=" + declinedForm.toString() + "}";
    }

    // compare

    /*@Override
    public int compareTo(NounForm o) {
        return getDeclinedForm().compareTo(o.getDeclinedForm());
    }*/

    public static Comparator<NounForm> comparator() {
        return Comparator
                .comparing((NounForm o) -> o.getDeclinedForm().getCasus())
                .thenComparing(o -> o.getDeclinedForm().getNumber())
                .thenComparing(o -> o.getDeclinedForm().getGender());
    }
}