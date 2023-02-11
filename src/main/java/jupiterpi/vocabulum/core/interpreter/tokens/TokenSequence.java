package jupiterpi.vocabulum.core.interpreter.tokens;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a sequence of tokens and provides some utility methods.
 * @see Token
 */
public class TokenSequence extends ArrayList<Token> {
    public TokenSequence() {}
    public TokenSequence(Collection<? extends Token> c) {
        super(c);
    }
    public TokenSequence(Token... tokens) {
        super(List.of(tokens));
    }

    /**
     * Constructs a sequence of tokens with a specific type, but no content.
     * Useful for using <code>fits()</code> on other tokens.
     * @param types the types to construct the tokens in this sequence from
     * @return the token sequence
     * @see Token#fits(Token)
     */
    public static TokenSequence fromTypes(Token.Type... types) {
        TokenSequence tokens = new TokenSequence();
        for (Token.Type type : types) {
            tokens.add(new Token(type));
        }
        return tokens;
    }

    /**
     * Extracts a sub-sequence from this token sequence and returns it.
     * Functionally equivalent to <code>List.subList(fromIndex, toIndex)</code>, but returns the correct type.
     * @param fromIndex the index at which the sub-sequence shall start
     * @param toIndex   the index at which the sub-sequence shall end
     * @return the sub-sequence
     */
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

    /**
     * Equivalent to <code>subsequence(fromIndex, this.size()-1)</code>.
     * @param fromIndex the index at which the sub-sequence shall start
     * @return the sub-sequence
     * @see #subsequence(int, int)
     */
    public TokenSequence subsequence(int fromIndex) {
        return subsequence(fromIndex, this.size());
    }

    /**
     * Checks whether a token is contained in this sequence.
     * @param token the token to check for
     * @return whether the token is contained in this sequence
     */
    public boolean contains(Token token) {
        return indexOf(token) >= 0;
    }

    /**
     * Checks for the index at which a token is contained in this sequence.
     * @param token the token to check for
     * @return the index (or -1 if it's not found)
     */
    public int indexOf(Token token) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).fits(token)) return i;
        }
        return -1;
    }

    /**
     * Checks whether the first tokens of this sequence fit into all tokens in the target sequence.
     * @param target the target sequence to test against
     * @return whether all tokens fit
     */
    public boolean fitsStartsWith(TokenSequence target) {
        if (target.size() > this.size()) return false;
        for (int i = 0; i < target.size(); i++) {
            Token token = this.get(i);
            Token targetToken = target.get(i);
            if (!token.fits(targetToken)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String tokensStr = this.stream().map(Token::toString).collect(Collectors.joining(", "));
        return "TokenSequence{[" + tokensStr + "]}";
    }
}
