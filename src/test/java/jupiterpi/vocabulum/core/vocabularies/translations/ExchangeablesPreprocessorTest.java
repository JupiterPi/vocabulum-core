package jupiterpi.vocabulum.core.vocabularies.translations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExchangeablesPreprocessorTest {

    @Nested
    @DisplayName("case 1)")
    class Case1 {

        @Test
        @DisplayName("simple")
        void simple() {
            assertEquals(List.of(
                    "anhören", "erhören", "zuhören", "hören"
            ), new ExchangeablesPreprocessor("(an, er, zu)hören").getResult());
        }

        @Test
        @DisplayName("with minuses")
        void withMinuses() {
            assertEquals(List.of(
                    "anhören", "erhören", "zuhören", "hören"
            ), new ExchangeablesPreprocessor("(an-, er-, zu)hören").getResult());
        }

    }

    @Nested
    @DisplayName("case 2)")
    class Case2 {

        @Test
        @DisplayName("simple")
        void simple() {
            assertEquals(List.of(
                    "ich sage nicht", "ich behaupte nicht"
            ), new ExchangeablesPreprocessor("ich sage/behaupte nicht").getResult());
        }

        @Test
        @DisplayName("without words around")
        void withoutWordsAround() {
            assertEquals(List.of(
                    "sage", "behaupte"
            ), new ExchangeablesPreprocessor("sage/behaupte").getResult());
        }

        @Test
        @DisplayName("with brackets")
        void withBrackets() {
            assertEquals(List.of(
                    "ich renne weg nicht", "ich flüchte weg nicht"
            ), new ExchangeablesPreprocessor("ich [renne weg/flüchte weg] nicht").getResult());
        }

        @Test
        @DisplayName("with brackets, without words around")
        void withBracketsWithoutWordsAround() {
            assertEquals(List.of(
                    "renne weg", "flüchte weg"
            ), new ExchangeablesPreprocessor("[renne weg/flüchte weg]").getResult());
        }

    }

    @Nested
    @DisplayName("case 3)")
    class Case3 {

        @Test
        @DisplayName("simple")
        void simple() {
            assertEquals(List.of(
                    "irgendein", "irgendeine"
            ), new ExchangeablesPreprocessor("irgendein(e)").getResult());
        }

    }

    @Test
    @DisplayName("no modifications")
    void noModifications() {
        assertEquals(List.of("test"), new ExchangeablesPreprocessor("test").getResult());
    }

}