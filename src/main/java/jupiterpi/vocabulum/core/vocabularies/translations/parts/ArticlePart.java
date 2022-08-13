package jupiterpi.vocabulum.core.vocabularies.translations.parts;

import java.util.regex.Pattern;

public class ArticlePart extends TranslationPart {
    private String article;

    public ArticlePart(String article) {
        this.article = article;
    }

    @Override
    public String getBasicString() {
        return article;
    }

    @Override
    public Pattern getRegex() {
        return Pattern.compile("(" + Pattern.quote(article) + ")?");
    }
}
