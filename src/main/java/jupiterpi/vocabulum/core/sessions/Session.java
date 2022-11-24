package jupiterpi.vocabulum.core.sessions;

import jupiterpi.vocabulum.core.sessions.selection.VocabularySelection;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Session {
    //TODO write test?

    private List<Vocabulary> originalVocabularies;

    public Session(VocabularySelection vocabularySelection) {
        this.originalVocabularies = vocabularySelection.getVocabularies();
    }

    /* runtime */

    private List<Vocabulary> currentVocabularies = null;
    private Result result = null;

    public void start() throws SessionLifecycleException {
        if (currentVocabularies != null) throw new SessionLifecycleException("Cannot start: Is running!");
        currentVocabularies = new ArrayList<>(originalVocabularies);
        restart_2nd();
    }

    public void restart() throws SessionLifecycleException {
        if (!isDone()) throw new SessionLifecycleException("Cannot restart: Is not done!");
        currentVocabularies = originalVocabularies;
        restart_2nd();
    }

    private void restart_2nd() {
        Collections.shuffle(currentVocabularies);
    }

    public List<Vocabulary> getCurrentVocabularies() throws SessionLifecycleException {
        result = null;
        if (currentVocabularies == null) {
            throw new SessionLifecycleException("Cannot get next vocabularies: Not started yet");
        }
        return currentVocabularies;
    }

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

    public Result getResult() {
        return result;
    }

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

        public boolean isDone() {
            return done;
        }

        public float getScore() {
            return score;
        }
    }
}