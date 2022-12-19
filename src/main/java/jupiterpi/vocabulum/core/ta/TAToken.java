package jupiterpi.vocabulum.core.ta;

import java.util.Objects;

public class TAToken {
    public enum TAWordType {
        WORD, PUNCTUATION
    }

    private TAWordType type;
    private String content;

    public TAToken(TAWordType type, String content) {
        this.type = type;
        this.content = content;
    }

    public TAWordType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TAToken taToken = (TAToken) o;
        return type == taToken.type && Objects.equals(content, taToken.content);
    }
}
