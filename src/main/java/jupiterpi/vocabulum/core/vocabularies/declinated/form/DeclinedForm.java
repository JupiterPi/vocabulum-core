package jupiterpi.vocabulum.core.vocabularies.declinated.form;

import jupiterpi.vocabulum.core.interpreter.lexer.Lexer;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.interpreter.tokens.Token;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;

public class DeclinedForm {
    private Casus casus;
    private Number number;
    private Gender gender;

    public DeclinedForm(Casus casus, Number number, Gender gender) {
        this.casus = casus;
        this.number = number;
        this.gender = gender;
    }

    private DeclinedForm() {}

    public static DeclinedForm fromString(String str) throws LexerException, ParserException {
        return fromString(new Lexer(str).getTokens());
    }
    public static DeclinedForm get(String str) {
        try {
            return fromString(str);
        } catch (ParserException | LexerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static DeclinedForm fromString(TokenSequence tokens) throws ParserException {
        DeclinedForm form = new DeclinedForm();
        if (tokens.size() < 2 || tokens.size() > 3) {
            throw new ParserException("Invalid form: " + tokens);
        }
        if (tokens.get(0).getType() == Token.Type.CASUS && tokens.get(1).getType() == Token.Type.NUMBER) {
            form.casus = Casus.valueOf(tokens.get(0).getContent().toUpperCase());
            form.number = Number.valueOf(tokens.get(1).getContent().toUpperCase());
            if (tokens.size() > 2) {
                if (tokens.get(2).getType() == Token.Type.GENDER) {
                    form.gender = Genders.fromSymbol(tokens.get(2).getContent());
                } else {
                    throw new ParserException("Invalid form: " + tokens);
                }
            }
        } else {
            throw new ParserException("Invalid form: " + tokens);
        }
        return form;
    }

    public Casus getCasus() {
        return casus;
    }

    public Number getNumber() {
        return number;
    }

    public Gender getGender() {
        return gender;
    }

    public boolean hasGender() {
        return gender != null;
    }

    @Deprecated
    public boolean fitsGender(DeclinedForm target) {
        if (target.gender == null) {
            return true;
        } else {
            return this.gender == target.gender;
        }
    }

    public void normalizeGender() {
        if (!hasGender()) {
            gender = Gender.MASC;
        }
    }

    public boolean isCasus(Casus casus) {
        return this.casus == casus;
    }

    public boolean isNumber(Number number) {
        return this.number == number;
    }

    public boolean isGender(Gender gender) {
        return this.gender == gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeclinedForm that = (DeclinedForm) o;
        return casus == that.casus && number == that.number && gender == that.gender;
    }

    public boolean fits(DeclinedForm target) {
        if (this.casus == target.casus && this.number == target.number) {
            if (target.gender != null) {
                return this.gender == target.gender;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    // to string

    @Override
    public String toString() {
        return "{" + formToString() + "}";
    }

    public String formToString() {
        String str = capitalize(casus.toString()) + ". " + capitalize(number.toString()) + ".";
        if (gender != null) {
            str += " " + gender.toString().substring(0, 1).toLowerCase() + ".";
        }
        return str;
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}