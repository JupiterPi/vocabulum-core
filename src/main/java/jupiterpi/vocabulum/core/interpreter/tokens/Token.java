package jupiterpi.vocabulum.core.interpreter.tokens;

import java.util.Objects;

/**
 * Represents a smallest logical bit of text.
 * Consists of a type and, optionally, content.
 */
public class Token {
    private Type type;
    private String content;

    /**
     * @param type    the token type
     * @param content the content
     */
    public Token(Type type, String content) {
        this.type = type;
        this.content = content;
    }

    /**
     * Constructs a token with no content.
     * @param type the token type
     */
    public Token(Type type) {
        this.type = type;
        this.content = null;
    }

    /**
     * @return the token type
     */
    public Type getType() {
        return type;
    }

    /**
     * @return this token's content
     */
    public String getContent() {
        return content;
    }

    public enum Type {
        WORD, COMMA,
        CASUS, NUMBER, GENDER, COMPARATIVE_FORM, ADV_FLAG,
        PERSON, MODE, TENSE, VOICE,
        IMPERATIVE_FLAG, INFINITIVE_FLAG, NOUN_LIKE_FORM
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
        return type == token.type && Objects.equals(content, token.content);
    }

    /**
     * Determines whether this token "fits" into a target token.
     * This is true if 1) this token equals the target token or 2) this token's type equals the target's type and the target's content is empty.
     * Useful if you want to test for a token's type and <em>optionally</em> the content simultaneously.
     * <br>
     * Example:
     * <br><code>new Token(Type.WORD, "abc").fits(new Token(Type.WORD, "abc")) == true</code>
     * <br><code>new Token(Type.WORD, "whatever").fits(new Token(Type.WORD)) == true</code>
     * @param target the target token to test against
     * @return whether this token "fits" into the target token
     */
    public boolean fits(Token target) {
        if (!(target.getContent() == null || target.getContent().isEmpty())) {
            if (!this.getContent().equals(target.getContent())) return false;
        }
        return this.getType() == target.getType();
    }
}