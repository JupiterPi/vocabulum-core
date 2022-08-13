package jupiterpi.vocabulum.core.vocabularies.translations.parts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlainTextPartTest {
    @Test
    void getRegex() {
        PlainTextPart plainTextPart = new PlainTextPart("Freund.");
        assertEquals("\\QFreund.\\E", plainTextPart.getRegex().pattern());
    }
}