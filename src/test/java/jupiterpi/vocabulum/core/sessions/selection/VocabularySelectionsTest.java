package jupiterpi.vocabulum.core.sessions.selection;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.testutil.TestUtil;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockDatabaseSetup.class)
class VocabularySelectionsTest {
    @Nested
    @DisplayName("equal()")
    class Equal {

        Vocabulary a = TestUtil.makeVocabulary("a");
        Vocabulary b = TestUtil.makeVocabulary("b");
        Vocabulary c = TestUtil.makeVocabulary("c");

        @Test
        @DisplayName("different size")
        void differentSize() {
            assertFalse(VocabularySelections.equal(
                    TestUtil.makeVocabularySelection(a, b),
                    TestUtil.makeVocabularySelection(a, b, c)
            ));
        }

        @Test
        @DisplayName("different order")
        void differentOrder() {
            assertTrue(VocabularySelections.equal(
                    TestUtil.makeVocabularySelection(a, b),
                    TestUtil.makeVocabularySelection(b, a)
            ));
        }

    }

    @Nested
    @DisplayName("getPortionBasedString()")
    class GetString {

        @Test
        @DisplayName("StringifiableVocabularySelection")
        void stringifiableVocabularySelection() {
            StringifiableVocabularySelection stringifiableVocabularySelection = new StringifiableVocabularySelection() {
                @Override
                public List<Vocabulary> getVocabularies() {
                    return List.of();
                }

                @Override
                public String getString() {
                    return "string";
                }
            };
            assertEquals("string", VocabularySelections.getPortionBasedString(stringifiableVocabularySelection));
        }

        @Test
        @DisplayName("other VocabularySelection")
        void otherVocabularySelection() {
            VocabularySelection vocabularySelection = TestUtil.makeVocabularySelection(
                    TestUtil.makeVocabulary("a"),
                    TestUtil.makeVocabulary("b")
            );
            assertEquals("a+b", VocabularySelections.getPortionBasedString(vocabularySelection));
        }

    }
}