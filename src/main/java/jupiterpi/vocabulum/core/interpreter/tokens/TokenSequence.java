package jupiterpi.vocabulum.core.interpreter.tokens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class TokenSequence extends ArrayList<Token> {
    public TokenSequence() {}
    public TokenSequence(Collection<? extends Token> c) { super(c); }
    public TokenSequence(Token... tokens) {
        super(Arrays.asList(tokens));
    }

    public TokenSequence subsequence(int fromIndex, int toIndex) {
        try {
            TokenSequence tokens = new TokenSequence();
            for (int i = fromIndex; i < toIndex; i++) {
                tokens.add(this.get(i));
            }
            return tokens;
        } catch (Exception e) {
            return new TokenSequence();
        }
    }

    public TokenSequence subsequence(int fromIndex) {
        return subsequence(fromIndex, this.size());
    }
}
