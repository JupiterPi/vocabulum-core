package jupiterpi.vocabulum.core.sessions;

import jupiterpi.vocabulum.core.sessions.selection.VocabularySelection;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Abstracted blueprint for a trainer session.
 * <em>Usage:</em>
 * Initialize the session with a vocabulary selection and <code>start()</code> it,
 * then for each round call <code>getCurrentVocabularies()</code> to get a list of vocabularies,
 * and send feedback for them (whether and how well the user knew them) using <code>provideFeedback()</code>,
 * get the result using <code>getResult()</code>.
 * Check if the session <code>isDone()</code> and then <code>restart()</code> it if necessary,
 * otherwise all wrong vocabularies will be put into a new round, and you can call <code>getCurrentVocabularies()</code> again.
 */
public class Session {
    private List<Vocabulary> originalVocabularies;

    /**
     * Constructs a new session with the specified vocabulary selection.
     * @param vocabularySelection the vocabulary selection for this session
     */
    public Session(VocabularySelection vocabularySelection) {
        this.originalVocabularies = vocabularySelection.getVocabularies();
    }

    /* runtime */

    private List<Vocabulary> currentVocabularies = null;
    private Result result = null;

    /**
     * Starts a newly created session.
     * @throws SessionLifecycleException if it is already running
     * @see #restart()
     */
    public void start() throws SessionLifecycleException {
        if (currentVocabularies != null) throw new SessionLifecycleException("Cannot start: Is running!");
        currentVocabularies = new ArrayList<>(originalVocabularies);
        restart_2nd();
    }

    /**
     * Restarts a running session with a new round
     * @throws SessionLifecycleException if the current round is not done yet
     */
    public void restart() throws SessionLifecycleException {
        if (!isDone()) throw new SessionLifecycleException("Cannot restart: Is not done!");
        currentVocabularies = originalVocabularies;
        restart_2nd();
    }

    private void restart_2nd() {
        Collections.shuffle(currentVocabularies);
    }

    /**
     * @return the list of vocabularies in the current round
     * @throws SessionLifecycleException if there's no current round
     */
    public List<Vocabulary> getCurrentVocabularies() throws SessionLifecycleException {
        result = null;
        if (currentVocabularies == null) {
            throw new SessionLifecycleException("Cannot get next vocabularies: Not started yet");
        }
        return currentVocabularies;
    }

    /**
     * Provides feedback for the vocabularies in the current round.
     * Then restarts for a new round with all the wrong vocabularies.
     * @param feedback the feedback for each vocabulary in this round (whether the user know them)
     * @throws SessionLifecycleException if you cannot provide feedback now, or you're trying to provide feedback for a vocabulary that was not in the round
     */
    public void provideFeedback(Map<Vocabulary, Feedback> feedback) throws SessionLifecycleException {
        if (currentVocabularies == null) {
            throw new SessionLifecycleException("Cannot provide feedback now: First vocabulary not drawn");
        }
        List<Vocabulary> wrongVocabularies = new ArrayList<>();
        for (Vocabulary vocabulary : feedback.keySet()) {
            if (!currentVocabularies.contains(vocabulary)) {
                throw new SessionLifecycleException("Cannot provide feedback for " + vocabulary + ", it's not in the current vocabularies.");
            }
            if (!feedback.get(vocabulary).isPassed()) {
                wrongVocabularies.add(vocabulary);
            }
        }
        boolean done = wrongVocabularies.size() == 0;
        result = new Result(done, (done ? 1f : 1f - (((float) wrongVocabularies.size()) / ((float) currentVocabularies.size()))));
        if (!done) {
            currentVocabularies = wrongVocabularies;
            restart_2nd();
        }
    }

    /**
     * @return the result of the last round
     */
    public Result getResult() {
        return result;
    }

    /**
     * @return whether there are now wrong vocabularies left
     */
    public boolean isDone() {
        if (result == null) return false;
        return result.isDone();
    }

    /* classes */

    public static class SessionLifecycleException extends Exception {
        public SessionLifecycleException(String message) {
            super(message);
        }
    }

    public static class Feedback {
        private boolean passed;

        /**
         * @param passed whether the user knew the vocabulary
         */
        public Feedback(boolean passed) {
            this.passed = passed;
        }

        public boolean isPassed() {
            return passed;
        }
    }

    public static class Result {
        private boolean done;
        private float score;

        public Result(boolean done, float score) {
            this.done = done;
            this.score = score;
        }

        /**
         * @return whether there are no wrong vocabularies left
         */
        public boolean isDone() {
            return done;
        }

        /**
         * @return the proportion of vocabularies in this round that the user knew
         */
        public float getScore() {
            return score;
        }
    }
}