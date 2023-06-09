package jupiterpi.vocabulum.core.vocabularies.overrides;

import jupiterpi.vocabulum.core.db.Resources;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import org.bson.Document;

import java.util.Arrays;
import java.util.List;

public class OverrideVocabularies {
    public static Vocabulary createOverrideVocabulary(String portion, String definition, String template, String punctuationSign, TranslationSequence translations) throws ParserException {
        List<TemplatePart> templateParts = parseTemplateString(template);
        if (templateParts.stream()
                .filter(part -> part instanceof OverrideTemplatePart)
                .count() != 1) throw new ParserException("invalid template: " + template);

        OverrideTemplatePart overridePart = (OverrideTemplatePart) templateParts.stream()
                .filter(part -> part instanceof OverrideTemplatePart)
                .findFirst().get();
        Document override = Resources.get().getOverride(overridePart.getOverrideName());
        String kind = override.getString("kind");

        if (kind.equalsIgnoreCase("verb")) {
            return new OverrideVerb(portion, definition, templateParts, punctuationSign, translations, override);
        }

        return null;
    }

    // template parts

    private static List<TemplatePart> parseTemplateString(String templateString) {
        return Arrays.stream(templateString.split("\\+"))
                .map(part -> part.startsWith("\"")
                        ? new TextTemplatePart(part.substring(1, part.length() - 1))
                        : new OverrideTemplatePart(part)
                )
                .toList();
    }

    interface TemplatePart {}

    static class TextTemplatePart implements TemplatePart {
        private String text;

        public TextTemplatePart(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    static class OverrideTemplatePart implements TemplatePart {
        private String overrideName;

        public OverrideTemplatePart(String overrideName) {
            this.overrideName = overrideName;
        }

        public String getOverrideName() {
            return overrideName;
        }
    }
}