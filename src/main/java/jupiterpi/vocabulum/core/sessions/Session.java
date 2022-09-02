package jupiterpi.vocabulum.core.sessions;

import jupiterpi.vocabulum.core.sessions.selection.VocabularySelection;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Session {
    //TODO write test?

    private List<Vocabulary> originalVocabularies;

    public Session(VocabularySelection vocabularySelection) {
        this.originalVocabularies = vocabularySelection.getVocabularies();
    }

    /* runtime */

    private List<Vocabulary> currentVocabularies = null;
    private List<Vocabulary> wrongVocabularies;
    private Vocabulary nextVocabulary = null;
    private Result result = null;

    public void start() throws SessionLifecycleException {
        if (currentVocabularies != null) throw new SessionLifecycleException("Cannot start: Is running!");
        currentVocabularies = new ArrayList<>(originalVocabularies);
        restart_2nd();
    }

    public void restart() throws SessionLifecycleException {
        if (!isAllDone()) throw new SessionLifecycleException("Cannot restart: Is not all done!");
        currentVocabularies = originalVocabularies;
        restart_2nd();
    }

    private void restart_2nd() throws SessionLifecycleException {
        Collections.shuffle(currentVocabularies);
        wrongVocabularies = new ArrayList<>();
        nextVocabulary = null;
        nextVocabulary();
    }

    public Vocabulary getNextVocabulary() throws SessionLifecycleException {
        result = null;
        if (nextVocabulary == null) {
            throw new SessionLifecycleException("Cannot get next vocabulary: Not started yet");
        }
        return nextVocabulary;
    }

    private void nextVocabulary() throws SessionLifecycleException {
        if (nextVocabulary == null) {
            nextVocabulary = currentVocabularies.get(0);
        } else {
            int currentIndex = currentVocabularies.indexOf(nextVocabulary);
            if (currentIndex != currentVocabularies.size() - 1) {
                nextVocabulary = currentVocabularies.get(currentIndex + 1);
            } else {
                boolean done = wrongVocabularies.size() == 0;
                result = new Result(done, (done ? 1f : 1f - (((float) wrongVocabularies.size()) / ((float) currentVocabularies.size()))));
                if (!done) {
                    currentVocabularies = wrongVocabularies;
                    restart_2nd();
                }
            }
        }
    }

    public void provideFeedback(Vocabulary vocabulary, boolean passed) throws SessionLifecycleException {
        if (nextVocabulary == null) {
            throw new SessionLifecycleException("Cannot provide feedback now: First vocabulary not drawn");
        }
        if (nextVocabulary != vocabulary) {
            throw new SessionLifecycleException("Current vocabulary is " + nextVocabulary + ", cannot provide feedback for: " + vocabulary);
        }
        if (!passed) {
            wrongVocabularies.add(vocabulary);
        }
        nextVocabulary();
    }

    public boolean isRoundDone() {
        return result != null;
    }
    public boolean isAllDone() {
        if (result == null) return false;
        return result.isDone();
    }

    public Result getResult() {
        return result;
    }

    public static class SessionLifecycleException extends Exception {
        public SessionLifecycleException(String message) {
            super(message);
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