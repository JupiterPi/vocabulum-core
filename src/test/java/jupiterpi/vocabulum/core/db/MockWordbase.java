package jupiterpi.vocabulum.core.db;

import jupiterpi.vocabulum.core.db.wordbase.IdentificationResult;
import jupiterpi.vocabulum.core.db.wordbase.Wordbase;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;

import java.util.List;

public class MockWordbase implements Wordbase {
    //TODO implement

    @Override
    public Vocabulary loadVocabulary(String baseForm) {
        return null;
    }

    @Override
    public List<IdentificationResult> identifyWord(String word) {
        return null;
    }

    @Override
    public void saveVocabulary(Vocabulary vocabulary) {

    }

    @Override
    public void clearAll() {

    }
}
