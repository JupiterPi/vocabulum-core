package jupiterpi.vocabulum.core.db.wordbase;

import jupiterpi.vocabulum.core.vocabularies.Vocabulary;

import java.util.List;

public interface Wordbase {
    Vocabulary loadVocabulary(String baseForm);

    List<IdentificationResult> identifyWord(String word);

    void saveVocabulary(Vocabulary vocabulary);

    void clearAll();
}
