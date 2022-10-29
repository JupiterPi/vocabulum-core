package jupiterpi.vocabulum.core.sessions.selection;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.db.portions.Portion;
import jupiterpi.vocabulum.core.testutil.TestUtil;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockDatabaseSetup.class)
class PortionBlocksSelectionTest {
    Vocabulary a = TestUtil.makeVocabulary("a");
    Vocabulary b = TestUtil.makeVocabulary("b");
    Vocabulary c = TestUtil.makeVocabulary("c");
    Vocabulary d = TestUtil.makeVocabulary("d");

    @Test
    void getVocabularies() {
        PortionBlocksSelection blocksSelection = new PortionBlocksSelection(TestUtil.makePortionWithBlocks(
                List.of(a, b),
                List.of(c),
                List.of(d)
        ), List.of(0, 2));
        assertEquals(List.of(a, b, d), blocksSelection.getVocabularies());
    }

    @Nested
    @DisplayName("getString()")
    class GetString {

        @Test
        @DisplayName("string name")
        void stringName() {
            PortionBlocksSelection blocksSelection = new PortionBlocksSelection(new Portion("A", List.of(
                    List.of(a, b),
                    List.of(c),
                    List.of(d)
            )), List.of(0, 2));
            assertEquals("'A':1_3", blocksSelection.getString());
        }

        @Test
        @DisplayName("number name")
        void numberName() {
            PortionBlocksSelection blocksSelection = new PortionBlocksSelection(new Portion("37", List.of(
                    List.of(a, b),
                    List.of(c),
                    List.of(d)
            )), List.of(0, 2));
            assertEquals("37:1_3", blocksSelection.getString());
        }

    }
}