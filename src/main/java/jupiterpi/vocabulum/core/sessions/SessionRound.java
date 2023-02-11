package jupiterpi.vocabulum.core.sessions;

import jupiterpi.vocabulum.core.vocabularies.Vocabulary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class in combination with <code>Session</code> for vocabulary flow inside a round.
 * <em>Usage:</em>
 * Initialize the session round with the vocabularies provided by <code>Session#getCurrentVocabularies()</code>.
 * Then call <code>getNextVocabulary()</code> for the next vocabulary and provide feedback using <code>provideFeedback()</code>.
 * Repeat that until the round <code>isDone()</code>, then call <code>getFeedback()</code> for the final result that you can pass into <code>Session#provideFeedback()</code>.
 * @see Session
 */
public class SessionRound {
    private List<Vocabulary> vocabularies;

    /**
     * Constructs and starts a new session round with the specified vocabularies provided by <code>Session#getCurrentVocabularies()</code>.
     * @param vocabularies the specified vocabularies for this round
     */
    public SessionRound(List<Vocabulary> vocabularies) {
        this.vocabularies = vocabularies;
        
        nextVocabulary = vocabularies.get(0);
    }
    
    /* runtime */
    
    private Vocabulary nextVocabulary;
    private Map<Vocabulary, Session.Feedback> feedback = new HashMap<>();
    private boolean done = false;

    /**
     * @return the next vocabulary
     * @throws Session.SessionLifecycleException if the round is done and there are no vocabularies left
     */
    public Vocabulary getNextVocabulary() throws Session.SessionLifecycleException {
        if (!done) {
            return nextVocabulary;
        } else {
            throw new Session.SessionLifecycleException("Cannot get next vocabulary: Round done");
        }
    }

    /**
     * Provides feedback for the current vocabulary.
     * @param vocabulary the current vocabulary to provide feedback for
     * @param passed     whether the user knew the vocabulary
     * @throws Session.SessionLifecycleException if the round is done and there are no vocabularies left to provide feedback for
     */
    public void provideFeedback(Vocabulary vocabulary, boolean passed) throws Session.SessionLifecycleException {
        if (!done) {
            feedback.put(vocabulary, new Session.Feedback(passed));

            int currentIndex = vocabularies.indexOf(nextVocabulary);
            if (currentIndex != vocabularies.size() - 1) {
                nextVocabulary = vocabularies.get(currentIndex + 1);
            } else {
                done = true;
            }
        } else {
            throw new Session.SessionLifecycleException("Cannot provide feedback: Round done");
        }
    }

    /**
     * @return whether this session round is done
     */
    public boolean isDone() {
        return done;
    }

    /* fetch result */

    /**
     * @return the total feedback for this round, which you can pass into <code>Session#provideFeedback()</code>
     * @throws Session.SessionLifecycleException if the round is not done yet
     */
    public Map<Vocabulary, Session.Feedback> getFeedback() throws Session.SessionLifecycleException {
        if (done) {
            return feedback;
        } else {
            throw new Session.SessionLifecycleException("Cannot get feedback: Round not done");
        }
    }
}