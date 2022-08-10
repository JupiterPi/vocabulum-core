package jupiterpi.vocabulum.core.vocabularies;

import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.i18n.I18nException;
import jupiterpi.vocabulum.core.interpreter.lexer.Lexer;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.interpreter.parser.VocabularyParser;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import jupiterpi.vocabulum.core.vocabularies.translations.VocabularyTranslation;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public abstract class Vocabulary {
    protected String portion;

    protected TranslationSequence translations;

    protected Vocabulary(TranslationSequence translations, String portion) {
        this.translations = translations;
        this.portion = portion;
    }

    protected static TranslationSequence readTranslations(Document document) {
        List<String> translationsStr = document.getList("translations", String.class);
        TranslationSequence translations = new TranslationSequence();
        for (String translationStr : translationsStr) {
            translations.add(VocabularyTranslation.fromString(translationStr));
        }
        return translations;
    }

    public static Vocabulary fromString(String str, I18n i18n, String portion) throws LexerException, ParserException, DeclinedFormDoesNotExistException, I18nException, VerbFormDoesNotExistException {
        String[] parts = str.split(" - ");
        String latinStr = parts[0];
        String translationsStr = parts[1];

        TranslationSequence translations = new TranslationSequence();
        for (String translationStr : translationsStr.split(", ")) {
            VocabularyTranslation translation = VocabularyTranslation.fromString(translationStr);
            translations.add(translation);
        }

        Lexer lexer = new Lexer(latinStr, i18n);
        TokenSequence tokens = lexer.getTokens();
        VocabularyParser parser = new VocabularyParser(tokens, translations, portion);
        Vocabulary vocabulary = parser.getVocabulary();

        return vocabulary;
    }

    public String getPortion() {
        return portion;
    }

    public abstract String getBaseForm();

    public abstract String getDefinition(I18n i18n);

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

    public Document generateWordbaseEntry() {
        Document document = generateWordbaseEntrySpecificPart();
        document.put("kind", getKind().toString().toLowerCase());
        document.put("base_form", getBaseForm());
        document.put("portion", portion);
        document.put("translations", getTranslationsToString());
        return document;
    }

    protected abstract Document generateWordbaseEntrySpecificPart();

    @Override
    public String toString() {
        return getKind().toString().toLowerCase() + "(\"" + getBaseForm() + " - " + getTopTranslation().toString() + "\")";
    }
}