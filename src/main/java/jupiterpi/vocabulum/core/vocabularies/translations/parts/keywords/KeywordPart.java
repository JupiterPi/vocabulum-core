package jupiterpi.vocabulum.core.vocabularies.translations.parts.keywords;

import jupiterpi.vocabulum.core.vocabularies.translations.parts.TranslationPart;

import java.util.List;
import java.util.regex.Pattern;

public class KeywordPart extends TranslationPart {
    private Keyword keyword;

    public KeywordPart(Keyword keyword) {
        this.keyword = keyword;
    }

    @Override
    public String getBasicString() {
        return keyword.getPrimaryKeyword();
    }

    @Override
    public String getRegex() {
        List<String> strings = keyword.getAllKeywords();
        strings = strings.stream().map(Pattern::quote).toList();
        return "(" + String.join("|", strings) + ")" + (keyword.isOptional() ? "?" : "");
    }

    @Override
    public String getNonNullRegex() {
        return getRegex();
    }
}