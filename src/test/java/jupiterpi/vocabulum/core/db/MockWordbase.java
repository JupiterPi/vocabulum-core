package jupiterpi.vocabulum.core.db;

import jupiterpi.vocabulum.core.db.wordbase.IdentificationResult;
import jupiterpi.vocabulum.core.db.wordbase.Wordbase;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;

import java.util.List;

public class MockWordbase implements Wordbase {
    //TODO implement actual mock wordbase?

    @Override
    public Vocabulary loadVocabulary(String baseForm) {
        throw new RuntimeException("Not supposed to call mocked Wordbase");
    }

    @Override
    public List<IdentificationResult> identifyWord(String word) {
        throw new RuntimeException("Not supposed to call mocked Wordbase");
    }

    @Override
    public void saveVocabulary(Vocabulary vocabulary) {
        throw new RuntimeException("Not supposed to call mocked Wordbase");
    }

    @Override
    public void clearAll() {
        throw new RuntimeException("Not supposed to call mocked Wordbase");
    }
}
