package jupiterpi.vocabulum.core.sessions;

import jupiterpi.vocabulum.core.vocabularies.Vocabulary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionRound {
    private List<Vocabulary> vocabularies;

    public SessionRound(List<Vocabulary> vocabularies) {
        this.vocabularies = vocabularies;
        
        nextVocabulary = vocabularies.get(0);
    }
    
    /* runtime */
    
    private Vocabulary nextVocabulary;
    private Map<Vocabulary, Session.Feedback> feedback = new HashMap<>();
    private boolean done = false;

    public Vocabulary getNextVocabulary() throws Session.SessionLifecycleException {
        if (!done) {
            return nextVocabulary;
        } else {
            throw new Session.SessionLifecycleException("Cannot get next vocabulary: Round done");
        }
    }
    
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

    public boolean isDone() {
        return done;
    }

    /* fetch result */

    public Map<Vocabulary, Session.Feedback> getFeedback() throws Session.SessionLifecycleException {
        if (done) {
            return feedback;
        } else {
            throw new Session.SessionLifecycleException("Cannot get feedback: Round not done");
        }
    }
}