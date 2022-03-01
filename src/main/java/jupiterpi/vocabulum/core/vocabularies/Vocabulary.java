package jupiterpi.vocabulum.core.vocabularies;

import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.i18n.I18nException;
import jupiterpi.vocabulum.core.interpreter.lexer.Lexer;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.Parser;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;

import java.util.Arrays;
import java.util.List;

public abstract class Vocabulary {
    protected int lesson;
    protected int part;

    protected List<String> translations;

    public static Vocabulary fromString(String str, I18n i18n) throws LexerException, ParserException, DeclinedFormDoesNotExistException, I18nException {
        String[] parts = str.split(" - ");
        String latinStr = parts[0];
        String translationStr = parts[1];

        Lexer lexer = new Lexer(latinStr, i18n);
        TokenSequence tokens = lexer.getTokens();
        Parser parser = new Parser(tokens);
        Vocabulary vocabulary = parser.getVocabulary();

        vocabulary.translations = Arrays.asList(translationStr.split(", "));
        return vocabulary;
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

    public List<String> getTranslations() {
        return translations;
    }
    public String getTopTranslation() {
        if (translations.size() == 0) return null;
        return translations.get(0);
    }

    @Override
    public String toString() {
        return getKind().toString().toLowerCase() + "(\"" + getBaseForm() + "\")";
    }
}