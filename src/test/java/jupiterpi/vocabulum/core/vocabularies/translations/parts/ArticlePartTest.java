package jupiterpi.vocabulum.core.vocabularies.translations.parts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArticlePartTest {
    @Test
    void getBasicString() {
        ArticlePart articlePart = new ArticlePart("der");
        assertEquals("der", articlePart.getBasicString());
    }

    @Test
    void getRegex() {
        ArticlePart articlePart = new ArticlePart("der");
        assertEquals("(\\Qder\\E)?", articlePart.getRegex());
    }
}