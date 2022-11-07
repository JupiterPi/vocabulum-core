package jupiterpi.vocabulum.core.vocabularies.translations.parts;

import jupiterpi.vocabulum.core.vocabularies.translations.parts.keywords.Keyword;
import jupiterpi.vocabulum.core.vocabularies.translations.parts.keywords.KeywordPart;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KeywordPartTest {
    @Test
    void getBasicString() {
        KeywordPart keywordPart = new KeywordPart(new Keyword(
                "jmdn.", List.of("jemanden", "jemand"), false
        ));
        assertEquals("jmdn.", keywordPart.getBasicString());
    }

    @Test
    void getRegex() {
        KeywordPart keywordPart = new KeywordPart(new Keyword(
                "jmdn.", List.of("jemanden", "jemand"), true
        ));
        assertEquals("(\\Qjmdn.\\E|\\Qjemanden\\E|\\Qjemand\\E)?", keywordPart.getRegex());
    }
}