package jupiterpi.vocabulum.core.vocabularies.conjugated.form;

import jupiterpi.vocabulum.core.interpreter.lexer.Lexer;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.interpreter.tokens.Token;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;
import jupiterpi.vocabulum.core.symbols.Symbols;

import java.util.Objects;

public class ConjugatedForm implements Comparable<ConjugatedForm> {
    private Person person;
    private CNumber number;

    public ConjugatedForm(Person person, CNumber number) {
        this.person = person;
        this.number = number;
    }

    private ConjugatedForm() {}

    public static ConjugatedForm fromString(String str) throws LexerException, ParserException {
        return fromTokens(new Lexer(str).getTokens());
    }

    public static ConjugatedForm fromTokens(TokenSequence tokens) throws ParserException {
        if (tokens.size() == 2 && tokens.fitsStartsWith(TokenSequence.fromTypes(Token.Type.PERSON, Token.Type.NUMBER))) {
            Person person = Symbols.get().personFromSymbol(tokens.get(0).getContent());
            CNumber number = Symbols.get().cNumberFromSymbol(tokens.get(1).getContent());
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
        return "{" + formToString(false) + "}";
    }

    public String formToString(boolean userFriendly) {
        return switch (person) {
            case FIRST -> "1. ";
            case SECOND -> "2. ";
            case THIRD -> "3. ";
        }
            + (userFriendly ? Symbols.get().getPersonCosmetic() + ". " : "")
            + number.toString().substring(0, 1).toUpperCase() + number.toString().substring(1).toLowerCase()
            + ".";
    }

    // compare

    @Override
    public int compareTo(ConjugatedForm o) {
        return (number.compareTo(o.getNumber()) * Person.values().length)
               + (person.compareTo(o.getPerson()));
    }
}