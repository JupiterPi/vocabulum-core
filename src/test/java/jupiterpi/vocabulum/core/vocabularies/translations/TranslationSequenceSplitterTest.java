package jupiterpi.vocabulum.core.vocabularies.translations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TranslationSequenceSplitterTest {

    @Test
    @DisplayName("split at comma")
    void splitAtComma() {
        assertEquals(List.of(
                "*der Freund*", "der Kamerad"
        ), new TranslationSequenceSplitter("*der Freund*, der Kamerad").getResult());
    }

    @Test
    @DisplayName("do not split inside parens")
    void doNotSplitInsideParens() {
        assertEquals(List.of(
                "(an-, er-, zu)hören", "lauschen"
        ), new TranslationSequenceSplitter("(an-, er-, zu)hören, lauschen").getResult());
    }

}