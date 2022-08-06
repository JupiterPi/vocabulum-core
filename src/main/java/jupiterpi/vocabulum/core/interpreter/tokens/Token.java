package jupiterpi.vocabulum.core.interpreter.tokens;

import jupiterpi.vocabulum.core.i18n.I18n;

import java.util.Objects;

public class Token {
    private Type type;
    private String content;
    private I18n i18n;

    public Token(Type type, String content, I18n i18n) {
        this.type = type;
        this.content = content;
        this.i18n = i18n;
    }

    public Token(Type type) {
        this.type = type;
        this.content = null;
        this.i18n = null;
    }

    public Type getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public enum Type {
        WORD, COMMA,
        CASUS, NUMBER, GENDER, COMPARATIVE_FORM, ADV_FLAG,
        PERSON, MODE, TENSE, VOICE,
        INFINITIVE_FLAG, NOUN_LIKE_FORM
    }

    public I18n getI18n() {
        return i18n;
    }

    @Override
    public String toString() {
        return type + " \"" + content + "\"";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        if (token.i18n != this.i18n) return false;
        return type == token.type && Objects.equals(content, token.content);
    }

    public boolean fits(Token target) {
        if (!(target.getContent() == null || target.getContent().isEmpty())) {
            if (!(target.getI18n() == null)) {
                if (this.getI18n() != target.getI18n()) return false;
            }
            if (!this.getContent().equals(target.getContent())) return false;
        }
        return this.getType() == target.getType();
    }
}