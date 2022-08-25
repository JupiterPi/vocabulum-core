package jupiterpi.vocabulum.core.vocabularies.translations.parts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlainTextPartTest {
    @Test
    void getBasicString() {
        PlainTextPart plainTextPart = new PlainTextPart("Freund");
        assertEquals("Freund", plainTextPart.getBasicString());
    }

    @Test
    void getRegex() {
        PlainTextPart plainTextPart = new PlainTextPart("Freund?");
        assertEquals("\\QFreund?\\E", plainTextPart.getRegex());
    }
}