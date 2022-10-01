package jupiterpi.vocabulum.core.sessions.selection;

import jupiterpi.vocabulum.core.db.portions.Portion;
import jupiterpi.vocabulum.core.testutil.TestUtil;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PortionBasedVocabularySelectionParserTest {
    Vocabulary a_asinus = TestUtil.makeVocabulary("a");
    Vocabulary b_stare = TestUtil.makeVocabulary("b");
    Vocabulary c_et = TestUtil.makeVocabulary("c");
    Vocabulary d_exspectare = TestUtil.makeVocabulary("d");

    Portion pA = TestUtil.makePortionWithBlocks(List.of(a_asinus, b_stare));
    Portion p37 = TestUtil.makePortionWithBlocks(List.of(c_et, d_exspectare));
    Portion p38 = TestUtil.makePortionWithBlocks(
            List.of(a_asinus, b_stare),
            List.of(),
            List.of(c_et, d_exspectare)
    );

    @Nested
    @DisplayName("different forms")
    class DifferentForms {

        @Test
        @DisplayName("portion designated by number")
        void portionDesignatedByNumber() {
            PortionBasedVocabularySelection selection = makeParser("37").getPortionBasedVocabularySelection();
            assertEquals(List.of(c_et, d_exspectare), selection.getVocabularies());
        }
        
        @Test
        @DisplayName("portion designated by string")
        void portionDesignatedByString() {
            PortionBasedVocabularySelection selection = makeParser("'A'").getPortionBasedVocabularySelection();
            assertEquals(List.of(a_asinus, b_stare), selection.getVocabularies());
        }

        @Test
        @DisplayName("joined portions")
        void joinedPortions() {
            PortionBasedVocabularySelection selection = makeParser("'A'+37").getPortionBasedVocabularySelection();
            assertEquals(List.of(a_asinus, b_stare, c_et, d_exspectare), selection.getVocabularies());
        }

        @Test
        @DisplayName("single block specified")
        void singleBlockSpecified() {
            PortionBasedVocabularySelection selection = makeParser("38_1").getPortionBasedVocabularySelection();
            assertEquals(List.of(a_asinus, b_stare), selection.getVocabularies());
        }

        @Test
        @DisplayName("multiple blocks specified")
        void multipleBlocksSpecified() {
            PortionBasedVocabularySelection selection = makeParser("38_1,3").getPortionBasedVocabularySelection();
            assertEquals(List.of(a_asinus, b_stare, c_et, d_exspectare), selection.getVocabularies());
        }

        @Test
        @DisplayName("vocabularies")
        void vocabularies() {
            PortionBasedVocabularySelection selection = makeParser("asinus").getPortionBasedVocabularySelection();
            assertEquals(List.of(a_asinus), selection.getVocabularies());
        }

        @Test
        @DisplayName("subtractions")
        void subtractions() {
            PortionBasedVocabularySelection selection = makeParser("37-et").getPortionBasedVocabularySelection();
            assertEquals(List.of(d_exspectare), selection.getVocabularies());
        }

        @Test
        @DisplayName("multiple subtractions")
        void multipleSubtractions() {
            PortionBasedVocabularySelection selection = makeParser("38-asinus-et").getPortionBasedVocabularySelection();
            assertEquals(List.of(b_stare, d_exspectare), selection.getVocabularies());
        }

        @Test
        @DisplayName("everything")
        void everything() {
            PortionBasedVocabularySelection selection = makeParser("38_1-asinus").getPortionBasedVocabularySelection();
            assertEquals(List.of(b_stare), selection.getVocabularies());
        }

    }

    private PortionBasedVocabularySelectionParser makeParser(String str) {
        return new PortionBasedVocabularySelectionParser(str) {
            @Override
            protected Vocabulary retrieveVocabulary(String base_form) {
                return switch (base_form) {
                    case "asinus" -> a_asinus;
                    case "stare" -> b_stare;
                    case "et" -> c_et;
                    case "exspectare" -> d_exspectare;
                    default -> null;
                };
            }

            @Override
            protected Portion retrievePortion(String name) {
                return switch (name) {
                    case "A" -> pA;
                    case "37" -> p37;
                    case "38" -> p38;
                    default -> null;
                };
            }
        };
    }
}