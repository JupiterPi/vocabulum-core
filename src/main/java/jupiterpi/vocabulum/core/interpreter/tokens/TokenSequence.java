package jupiterpi.vocabulum.core.interpreter.tokens;

import jupiterpi.tools.util.AppendingList;
import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.Main;
import jupiterpi.vocabulum.core.i18n.I18nException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class TokenSequence extends ArrayList<Token> {
    public TokenSequence() {}
    public TokenSequence(Collection<? extends Token> c) {
        super(c);
    }
    public TokenSequence(Token... tokens) {
        super(Arrays.asList(tokens));
    }

    public I18n getI18n() {
        if (this.size() == 0) return null;
        I18n i18n = null;
        for (Token token : this) {
            if (token.getI18n() == null) continue;
            if (i18n == null) i18n = token.getI18n();
            else {
                if (i18n != token.getI18n()) I18nException.mismatch(i18n, token.getI18n()).printStackTrace();
            }
        }
        return i18n;
    }

    public static TokenSequence fromTypes(Token.Type... types) {
        TokenSequence tokens = new TokenSequence();
        for (Token.Type type : types) {
            tokens.add(new Token(type));
        }
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

    public boolean contains(Token token) {
        return indexOf(token) >= 0;
    }

    public int indexOf(Token token) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).fits(token)) return i;
        }
        return -1;
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
        return "TokenSequence{i18n=" + (getI18n() == null ? "null" : getI18n().getName()) + ",tokens=[" + tokensStr.render(", ") + "]}";
    }
}
