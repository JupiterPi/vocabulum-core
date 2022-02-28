package jupiterpi.vocabulum.core.vocabularies;

import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.i18n.I18nException;
import jupiterpi.vocabulum.core.interpreter.lexer.Lexer;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.Parser;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;
import jupiterpi.vocabulum.core.vocabularies.declinated.DeclinedFormDoesNotExistException;

public abstract class Vocabulary {
    protected int lesson;
    protected int part;

    public static Vocabulary fromString(String str, I18n i18n) throws LexerException, ParserException, DeclinedFormDoesNotExistException, I18nException {
        Lexer lexer = new Lexer(str, i18n);
        TokenSequence tokens = lexer.getTokens();
        Parser parser = new Parser(tokens);
        return parser.getVocabulary();
    }

    public int getLesson() {
        return lesson;
    }

    public int getPart() {
        return part;
    }

    public String getPortion() {
        String lessonStr = Integer.toString(lesson);
        if (lessonStr.length() == 1) lessonStr = "0" + lessonStr;
        return lessonStr + "." + part;
    }

    public abstract String getBaseForm();

    public abstract Kind getKind();
    public enum Kind {
        NOUN, ADJECTIVE
    }
}