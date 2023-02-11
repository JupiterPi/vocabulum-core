package jupiterpi.vocabulum.core.db.wordbase;

import jupiterpi.vocabulum.core.vocabularies.Vocabulary;

import java.util.List;

/**
 * Provides an interface to search for vocabularies in the database.
 * @deprecated Might be deprecated soon.
 */
public interface Wordbase {
    Vocabulary loadVocabulary(String baseForm);

    List<IdentificationResult> identifyWord(String word, boolean partialSearch);

    void saveVocabulary(Vocabulary vocabulary);

    void clearAll();
}
