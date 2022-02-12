package jupiterpi.vocabulum.core.interpreter.tokens;

public class Token {
    private Type type;
    private String content;

    public Token(Type type, String content) {
        this.type = type;
        this.content = content;
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
}