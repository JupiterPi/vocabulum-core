package jupiterpi.vocabulum.core.vocabularies.conjugated.form;

import jupiterpi.vocabulum.core.Main;
import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.interpreter.lexer.Lexer;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.interpreter.tokens.Token;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;

import java.util.Objects;

public class ConjugatedForm {
    private Person person;
    private CNumber number;

    public ConjugatedForm(Person person, CNumber number) {
        this.person = person;
        this.number = number;
    }

    private ConjugatedForm() {}

    public static ConjugatedForm fromString(String str, I18n i18n) throws LexerException, ParserException {
        return fromTokens(new Lexer(str, i18n).getTokens());
    }
    public static ConjugatedForm get(String str) {
        try {
            return fromString(str, Main.i18nManager.internal);
        } catch (LexerException | ParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ConjugatedForm fromTokens(TokenSequence tokens) throws ParserException {
        if (tokens.size() == 2 && tokens.fitsStartsWith(TokenSequence.fromTypes(Token.Type.PERSON, Token.Type.NUMBER))) {
            Person person = tokens.getI18n().personFromSymbol(tokens.get(0).getContent());
            CNumber number = tokens.getI18n().cNumberFromSymbol(tokens.get(1).getContent());
            return new ConjugatedForm(person, number);
        } else {
            throw new ParserException("Invalid conjugated form: " + tokens);
        }
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

    @Override
    public int hashCode() {
        return Objects.hash(person, number);
    }

    // to string

    @Override
    public String toString() {
        return "{" + formToString(Main.i18nManager.internal, false) + "}";
    }

    public String formToString(I18n i18n, boolean userFriendly) {
        return switch (person) {
            case FIRST -> "1. ";
            case SECOND -> "2. ";
            case THIRD -> "3. ";
        }
            + (userFriendly ? i18n.getPersonCosmetic() + ". " : "")
            + number.toString().substring(0, 1).toUpperCase() + number.toString().substring(1).toLowerCase()
            + ".";
    }
}