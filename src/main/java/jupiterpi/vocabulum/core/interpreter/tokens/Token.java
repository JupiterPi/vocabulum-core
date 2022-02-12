package jupiterpi.vocabulum.core.interpreter.tokens;

import java.util.Objects;

public class Token {
    private Type type;
    private String content;

    public Token(Type type, String content) {
        this.type = type;
        this.content = content;
    }

    public Token(Type type) {
        this.type = type;
        this.content = null;
    }

    public Type getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public enum Type {
        WORD, COMMA, CASUS, NUMBER, GENDER
    }

    @Override
    public String toString() {
        return "{" +
                "type=" + type +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return type == token.type && Objects.equals(content, token.content);
    }

    public boolean fits(Token target) {
        if (!(target.getContent() == null || target.getContent().isEmpty())) {
            if (!this.getContent().equals(target.getContent())) return false;
        }
        return this.getType() == target.getType();
    }
}