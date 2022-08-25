package jupiterpi.vocabulum.core.vocabularies.translations.parts;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AbbreviationPartTest {
    @Test
    void getBasicString() {
        AbbreviationPart abbreviationPart = new AbbreviationPart("jmdn", List.of("jemanden", "jemand"));
        assertEquals("jmdn.", abbreviationPart.getBasicString());
    }

    @Test
    void getRegex() {
        AbbreviationPart abbreviationPart = new AbbreviationPart("jmdn", List.of("jemanden", "jemand"));
        assertEquals("(\\Qjmdn.\\E|\\Qjemanden\\E|\\Qjemand\\E)", abbreviationPart.getRegex());
    }
}