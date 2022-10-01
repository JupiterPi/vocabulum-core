package jupiterpi.vocabulum.core.sessions.selection;

import jupiterpi.vocabulum.core.vocabularies.Vocabulary;

import java.util.List;

public class VocabularySelections {
    public static boolean equal(VocabularySelection a, VocabularySelection b) {
        List<Vocabulary> a1 = a.getVocabularies();
        List<Vocabulary> b1 = b.getVocabularies();
        if (a1.size() != b1.size()) return false;
        for (Vocabulary vocabulary : a1) {
            if (!b1.contains(vocabulary)) return false;
        }
        return true;
    }

    public static String getPortionBasedString(VocabularySelection vocabularySelection) {
        if (vocabularySelection instanceof StringifiableVocabularySelection) {
            return ((StringifiableVocabularySelection) vocabularySelection).getString();
        } else {
            return PortionBasedVocabularySelection.fromVocabularySelection(vocabularySelection).getString();
        }
    }
}