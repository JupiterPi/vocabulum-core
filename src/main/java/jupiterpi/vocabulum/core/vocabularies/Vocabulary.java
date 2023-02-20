package jupiterpi.vocabulum.core.vocabularies;

import jupiterpi.vocabulum.core.interpreter.lexer.Lexer;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.interpreter.parser.VocabularyParser;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.overrides.OverrideVocabularies;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import jupiterpi.vocabulum.core.vocabularies.translations.VocabularyTranslation;

import java.util.ArrayList;
import java.util.List;

public abstract class Vocabulary {
    protected String portion;

    protected TranslationSequence translations;

    protected Vocabulary(TranslationSequence translations, String portion) {
        this.translations = translations;
        this.portion = portion;
    }

    public static Vocabulary fromString(String str, String portion) throws LexerException, ParserException, DeclinedFormDoesNotExistException, VerbFormDoesNotExistException {
        String[] parts = str.split(" -- ");
        String latinStr = parts[0];
        String translationsStr = parts[1];

        TranslationSequence translations = TranslationSequence.fromString(translationsStr);

        if (latinStr.contains("[")) { // has template

            int index = latinStr.indexOf("[");
            String definition = latinStr.substring(0, index).trim();
            String template = latinStr.substring(index+1, latinStr.indexOf("]")).trim();

            return OverrideVocabularies.createOverrideVocabulary(portion, definition, template, translations);

        } else {

            Lexer lexer = new Lexer(latinStr);
            TokenSequence tokens = lexer.getTokens();
            VocabularyParser parser = new VocabularyParser(tokens, translations, portion);
            return parser.getVocabulary();

        }
    }

    public String getPortion() {
        return portion;
    }

    public abstract String getBaseForm();

    public abstract String getDefinition();

    public abstract Kind getKind();
    public enum Kind {
        NOUN, ADJECTIVE, VERB, INFLEXIBLE
    }

    public TranslationSequence getTranslations() {
        return translations;
    }

    public List<String> getTranslationsToString() {
        List<String> strings = new ArrayList<>();
        for (VocabularyTranslation translation : getTranslations()) {
            strings.add(translation.toString());
        }
        return strings;
    }

    public VocabularyTranslation getTopTranslation() {
        for (VocabularyTranslation translation : translations) {
            if (!translation.isImportant()) continue;
            return translation;
        }
        return null;
    }

    public abstract List<String> getAllFormsToString();

    public String vocabularyToString() {
        List<String> translationsStr = new ArrayList<>();
        for (VocabularyTranslation translation : translations) {
            translationsStr.add(translation.getFormattedTranslation());
        }
        return getDefinition() + " - " + String.join(", ", translationsStr);
    }

    @Override
    public String toString() {
        return getKind().toString().toLowerCase() + "(\"" + getBaseForm() + " - " + getTopTranslation().toString() + "\")";
    }
}