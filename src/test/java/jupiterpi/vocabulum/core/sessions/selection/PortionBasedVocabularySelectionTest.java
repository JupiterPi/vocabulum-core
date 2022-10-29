package jupiterpi.vocabulum.core.sessions.selection;

import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import org.junit.jupiter.api.Test;

import java.util.List;

import static jupiterpi.vocabulum.core.testutil.TestUtil.makeVocabulary;
import static jupiterpi.vocabulum.core.testutil.TestUtil.makeVocabularySelection;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PortionBasedVocabularySelectionTest {
    @Test
    void getVocabularies() {
        Vocabulary a = makeVocabulary("a");
        Vocabulary b = makeVocabulary("b");
        Vocabulary c = makeVocabulary("c");
        PortionBasedVocabularySelection selection = new PortionBasedVocabularySelection(List.of(
                new PortionBasedVocabularySelection.Part(false, makeVocabularySelection(a)),
                new PortionBasedVocabularySelection.Part(false, makeVocabularySelection(b, c)),
                new PortionBasedVocabularySelection.Part(true, makeVocabularySelection(a, c))
        ));
        assertEquals(List.of(b), selection.getVocabularies());
    }

    @Test
    void getString() {
        SingleVocabulary v = new SingleVocabulary(makeVocabulary("v"));
        PortionBasedVocabularySelection selection = new PortionBasedVocabularySelection(List.of(
                new PortionBasedVocabularySelection.Part(false, v),
                new PortionBasedVocabularySelection.Part(false, v),
                new PortionBasedVocabularySelection.Part(true, v)
        ));
        assertEquals("v,v-v", selection.getString());
    }
}