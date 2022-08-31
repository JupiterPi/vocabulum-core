package jupiterpi.vocabulum.core.sessions.selection;

import jupiterpi.vocabulum.core.vocabularies.Vocabulary;

import java.util.ArrayList;
import java.util.List;

public class VocabularySelections {
    public static VocabularySelection union(VocabularySelection a, VocabularySelection b) {
        List<Vocabulary> selection = new ArrayList<>();
        selection.addAll(a.getVocabularies());
        selection.addAll(b.getVocabularies());
        return new BasicVocabularySelection(selection);
    }
}