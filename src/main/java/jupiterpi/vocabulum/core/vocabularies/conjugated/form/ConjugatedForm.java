package jupiterpi.vocabulum.core.vocabularies.conjugated.form;

import jupiterpi.vocabulum.core.Main;
import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.interpreter.lexer.Lexer;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;

public class ConjugatedForm {
    private Person person;
    private CNumber number;

    public ConjugatedForm(Person person, CNumber number) {
        this.person = person;
        this.number = number;
    }

    private ConjugatedForm() {}

    public static ConjugatedForm fromString(String str, I18n i18n) throws LexerException {
        return fromTokens(new Lexer(str, i18n).getTokens());
    }
    public static ConjugatedForm get(String str) {
        try {
            return fromString(str, Main.i18nManager.internal);
        } catch (ParserException | LexerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ConjugatedForm fromTokens(TokenSequence tokens) throws ParserException {
        ConjugatedForm form = new ConjugatedForm();
        return form;
        //TODO implement
    }

    public Person getPerson() {
        return person;
    }

    public CNumber getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConjugatedForm that = (ConjugatedForm) o;
        return person == that.person && number == that.number;
    }

    // to string


    @Override
    public String toString() {
        return "{" + formToString(Main.i18nManager.internal) + "}";
    }

    public String formToString(I18n i18n) {
        return switch (person) {
            case FIRST -> "1. ";
            case SECOND -> "2. ";
            case THIRD -> "3. ";
        } + i18n.getPersonCosmetic()
                + " "
                + person.toString().substring(0, 1).toUpperCase() + person.toString().substring(1).toLowerCase()
                + ".";
    }
}