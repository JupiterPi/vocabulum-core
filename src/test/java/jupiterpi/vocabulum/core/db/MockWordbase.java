package jupiterpi.vocabulum.core.db;

import jupiterpi.vocabulum.core.db.wordbase.IdentificationResult;
import jupiterpi.vocabulum.core.db.wordbase.Wordbase;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.VocabularyForm;
import jupiterpi.vocabulum.core.vocabularies.conjugated.Verb;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.Adjective;
import jupiterpi.vocabulum.core.vocabularies.declined.nouns.Noun;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MockWordbase implements Wordbase {
    //TODO implement actual mock wordbase?

    @Override
    public Vocabulary loadVocabulary(String baseForm) {
        return Database.get().getPortions().getVocabularyInPortion(baseForm);
    }

    @Override
    public List<IdentificationResult> identifyWord(String word, boolean partialSearch) {
        try {
            List<IdentificationResult> results = new ArrayList<>();

            List<Vocabulary> vocabularies = new ArrayList<>();
            Method getAllFormsToString = Vocabulary.class.getDeclaredMethod("getAllFormsToString");
            getAllFormsToString.setAccessible(true);
            Database.get().getPortions().getPortions().forEach((s, portion) -> vocabularies.addAll(portion.getVocabularies()));
            vocabularies.forEach(vocabulary -> {
                try {
                    String allFormsString = String.join(";", (List<String>) getAllFormsToString.invoke(vocabulary));
                    boolean matches = partialSearch
                            ? allFormsString.contains(word)
                            : (";" + allFormsString + ";").contains(";" + word + ";");
                    List<VocabularyForm> forms = new ArrayList<>(switch (vocabulary.getKind()) {
                        case NOUN -> ((Noun) vocabulary).identifyForm(word, partialSearch);
                        case ADJECTIVE -> ((Adjective) vocabulary).identifyForm(word, partialSearch);
                        case VERB -> ((Verb) vocabulary).identifyForm(word, partialSearch);
                        case INFLEXIBLE -> new ArrayList<VocabularyForm>();
                    });
                    if (matches) results.add(new IdentificationResult(vocabulary, forms));
                } catch (IllegalAccessException | InvocationTargetException ignored) {}
            });

            return results;
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    @Override
    public void saveVocabulary(Vocabulary vocabulary) {
        throw new RuntimeException("Not supposed to call mocked Wordbase#saveVocabulary");
    }

    @Override
    public void clearAll() {
        throw new RuntimeException("Not supposed to call mocked Wordbase#clearAll");
    }
}
