package jupiterpi.vocabulum.core.sessions.selection;

import jupiterpi.vocabulum.core.vocabularies.Vocabulary;

import java.util.List;

/**
 * Any collection of vocabularies, like a portion or a vocabulary selection.
 * @see PortionBasedVocabularySelection
 */
public interface VocabularySelection {
    List<Vocabulary> getVocabularies();
}