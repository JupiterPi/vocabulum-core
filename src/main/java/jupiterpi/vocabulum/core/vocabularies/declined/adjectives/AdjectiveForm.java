package jupiterpi.vocabulum.core.vocabularies.declined.adjectives;

import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.interpreter.lexer.Lexer;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.interpreter.tokens.Token;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;
import jupiterpi.vocabulum.core.vocabularies.VocabularyForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;

import java.util.Objects;

public class AdjectiveForm implements VocabularyForm {
    private boolean adverb;
    private DeclinedForm declinedForm;
    private ComparativeForm comparativeForm;


    /* constructors */

    // full
    public AdjectiveForm(boolean adverb, DeclinedForm declinedForm, ComparativeForm comparativeForm) {
        this.adverb = adverb;
        this.declinedForm = declinedForm;
        this.comparativeForm = comparativeForm;
    }

    // for adjectives
    public AdjectiveForm(DeclinedForm declinedForm, ComparativeForm comparativeForm) {
        this.adverb = false;
        this.declinedForm = declinedForm;
        this.comparativeForm = comparativeForm;
    }

    // for adverbs
    public AdjectiveForm(boolean adverb, ComparativeForm comparativeForm) {
        this.adverb = adverb;
        this.declinedForm = null;
        this.comparativeForm = comparativeForm;
    }


    /* parser */

    public static AdjectiveForm fromString(String expr, I18n i18n) throws ParserException, LexerException {
        return fromTokens(new Lexer(expr, i18n).getTokens());
    }

    public static final ComparativeForm DEFAULT_COMPARATIVE_FORM = ComparativeForm.POSITIVE;

    public static AdjectiveForm fromTokens(TokenSequence tokens) throws ParserException {
        int adverbFlagIndex = tokens.indexOf(new Token(Token.Type.ADV_FLAG));
        boolean isAdverb = adverbFlagIndex >= 0;
        if (isAdverb) {
            tokens.remove(adverbFlagIndex);
        }

        int comparativeFormIndex = tokens.indexOf(new Token(Token.Type.COMPARATIVE_FORM));
        ComparativeForm comparativeForm = DEFAULT_COMPARATIVE_FORM;
        if (comparativeFormIndex >= 0) {
            comparativeForm = tokens.getI18n().comparativeFormFromSymbol(tokens.get(comparativeFormIndex).getContent());
            tokens.remove(comparativeFormIndex);
        }

        DeclinedForm declinedForm = DeclinedForm.fromTokens(tokens);

        return new AdjectiveForm(isAdverb, declinedForm, comparativeForm);
    }


    /* getters, equals, toString */

    public boolean isAdverb() {
        return adverb;
    }

    public DeclinedForm getDeclinedForm() {
        return declinedForm;
    }

    public ComparativeForm getComparativeForm() {
        return comparativeForm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjectiveForm that = (AdjectiveForm) o;
        return adverb == that.adverb && Objects.equals(declinedForm, that.declinedForm) && comparativeForm == that.comparativeForm;
    }

    // to string

    @Override
    public String formToString(I18n i18n) {
        String str = "";
        if (adverb) {
            str += i18n.getAdverbFlag() + ".";
        } else {
            str += declinedForm.formToString(i18n);
        }
        str += (comparativeForm == DEFAULT_COMPARATIVE_FORM ? "" : " " + i18n.getComparativeFormSymbol(comparativeForm) + ".");
        return str;
    }

    @Override
    public String toString() {
        String adverbDeclinedFormStr = adverb ? "adverb" : "form=" + declinedForm.toString();
        return "Adjective{" + adverbDeclinedFormStr + ",comparative=" + comparativeForm.toString().toLowerCase() + "}";
    }
}