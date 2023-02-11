package jupiterpi.vocabulum.core.sessions.selection;

/**
 * A vocabulary selection that can be easily described using a short string, like a portion based vocabulary selection.
 * @see PortionBasedVocabularySelection
 */
public interface StringifiableVocabularySelection extends VocabularySelection {
    /**
     * @return a short string that describes the selection, like a portion based vocabulary selection string.
     */
    String getString();
}