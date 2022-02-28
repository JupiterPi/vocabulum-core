package jupiterpi.vocabulum.core.interpreter.tokens;

import jupiterpi.tools.util.AppendingList;
import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.Main;
import jupiterpi.vocabulum.core.i18n.I18nException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class TokenSequence extends ArrayList<Token> {
    private I18n i18n = Main.i18n;

    public TokenSequence() {}
    public TokenSequence(Collection<? extends Token> c) throws I18nException {
        super(c);
        this.i18n = evaluateI18n(this);
    }
    public TokenSequence(Token... tokens) throws I18nException {
        super(Arrays.asList(tokens));
        this.i18n = evaluateI18n(this);
    }
    private static I18n evaluateI18n(TokenSequence sequence) throws I18nException {
        I18n i18n = null;
        for (Token token : sequence) {
            if (i18n == null) i18n = token.getI18n();
            else {
                if (i18n != token.getI18n()) throw I18nException.mismatch(i18n, token.getI18n());
            }
        }
        if (i18n == null) throw new I18nException("cannot find i18n for token sequence: " + sequence);
        return i18n;
    }

    public I18n getI18n() {
        return i18n;
    }

    public static TokenSequence fromTypes(Token.Type... types) {
        TokenSequence tokens = new TokenSequence();
        for (Token.Type type : types) {
            tokens.add(new Token(type));
        }
        tokens.i18n = null;
        return tokens;
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

    public boolean fitsStartsWith(TokenSequence tokens) {
        if (this.size() > tokens.size()) return false;
        for (int i = 0; i < this.size(); i++) {
            Token token1 = this.get(i);
            Token token2 = tokens.get(i);
            if (!token1.fits(token2)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        AppendingList tokensStr = new AppendingList();
        for (Token token : this) {
            tokensStr.add(token.toString());
        }
        return "TokenSequence{i18n=" + i18n.getName() + ",tokens=[" + tokensStr.render(",") + "]}";
    }
}
