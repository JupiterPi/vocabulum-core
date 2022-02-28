package jupiterpi.vocabulum.core.vocabularies.declinated.form;

import jupiterpi.vocabulum.core.i18n.I18n;
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

    public DeclinedForm(Casus casus, Number number) {
        this.casus = casus;
        this.number = number;
        this.gender = null;
    }

    private DeclinedForm() {}

    public static DeclinedForm fromString(String str, I18n i18n) throws LexerException, ParserException {
        return fromTokens(new Lexer(str, i18n).getTokens());
    }
    public static DeclinedForm get(String str) {
        try {
            return fromString(str, I18n.internal);
        } catch (ParserException | LexerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static DeclinedForm fromTokens(TokenSequence tokens) throws ParserException {
        DeclinedForm form = new DeclinedForm();
        if (tokens.size() < 2 || tokens.size() > 3) {
            throw new ParserException("Invalid form: " + tokens);
        }
        if (tokens.get(0).getType() == Token.Type.CASUS && tokens.get(1).getType() == Token.Type.NUMBER) {
            form.casus = DeclinedFormAspects.casusFromString(tokens.get(0).getContent(), tokens.getI18n());
            form.number = DeclinedFormAspects.numberFromString(tokens.get(1).getContent(), tokens.getI18n());
            if (tokens.size() > 2) {
                if (tokens.get(2).getType() == Token.Type.GENDER) {
                    form.gender = DeclinedFormAspects.genderFromString(tokens.get(2).getContent(), tokens.getI18n());
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
        normalizeGender(Gender.MASC);
    }
    public void normalizeGender(Gender normalizeTo) {
        if (!hasGender()) {
            gender = normalizeTo;
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