package jupiterpi.vocabulum.core.vocabularies.translations.parts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DotsPartTest {
    @Test
    void getBasicString() {
        DotsPart dotsPart = new DotsPart();
        assertEquals("...", dotsPart.getBasicString());
    }

    @Test
    void getRegex() {
        DotsPart dotsPart = new DotsPart();
        assertEquals("(\\.\\.\\.)?", dotsPart.getRegex());
    }
}