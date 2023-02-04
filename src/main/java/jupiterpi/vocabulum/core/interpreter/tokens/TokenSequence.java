package jupiterpi.vocabulum.core.interpreter.tokens;

import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.i18n.I18nException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TokenSequence extends ArrayList<Token> {
    public TokenSequence() {}
    public TokenSequence(Collection<? extends Token> c) {
        super(c);
    }
    public TokenSequence(Token... tokens) {
        super(List.of(tokens));
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
        return "TokenSequence{i18n=" + (getI18n() == null ? "null" : getI18n().getName()) + ",tokens=[" + tokensStr + "]}";
    }
}
