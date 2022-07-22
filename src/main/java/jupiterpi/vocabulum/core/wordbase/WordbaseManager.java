package jupiterpi.vocabulum.core.wordbase;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.VocabularyForm;
import jupiterpi.vocabulum.core.vocabularies.conjugated.Verb;
import jupiterpi.vocabulum.core.vocabularies.conjugated.WordbaseVerb;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.Adjective;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.WordbaseAdjective;
import jupiterpi.vocabulum.core.vocabularies.declined.nouns.Noun;
import jupiterpi.vocabulum.core.vocabularies.declined.nouns.WordbaseNoun;
import jupiterpi.vocabulum.core.vocabularies.inflexible.Inflexible;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class WordbaseManager {
    public Vocabulary loadVocabulary(String baseForm) {
        Document vocabularyDocument = Database.wordbase.find(new Document("base_form", baseForm)).first();
        return switch (vocabularyDocument.getString("kind")) {
            case "noun" -> WordbaseNoun.readFromDocument(vocabularyDocument);
            case "adjective" -> WordbaseAdjective.readFromDocument(vocabularyDocument);
            case "verb" -> WordbaseVerb.readFromDocument(vocabularyDocument);
            case "inflexible" -> Inflexible.readFromDocument(vocabularyDocument);
            default -> null;
        };
    }

    public static class IdentificationResult {
        private Vocabulary vocabulary;
        private List<VocabularyForm> forms;

        public IdentificationResult(Vocabulary vocabulary, List<VocabularyForm> forms) {
            this.vocabulary = vocabulary;
            this.forms = forms;
        }

        public Vocabulary getVocabulary() {
            return vocabulary;
        }

        public List<VocabularyForm> getForms() {
            return forms;
        }

        @Override
        public String toString() {
            return "IdentificationResult{" +
                    "vocabulary=" + vocabulary +
                    ", forms=" + forms +
                    '}';
        }
    }

    public List<IdentificationResult> identifyWord(String word) {
        List<VocabularyForm> nullForms = new ArrayList<>();
        nullForms.add(null);

        Database.wordbase.createIndex(new Document("$**", "text"));
        List<IdentificationResult> results = new ArrayList<>();
        for (Document vocabularyDocument : Database.wordbase.find(new Document("$text", new Document("$search", word)))) {
            Vocabulary vocabulary = loadVocabulary(vocabularyDocument.getString("base_form"));
            List<VocabularyForm> forms = switch (vocabulary.getKind()) {
                case NOUN -> new ArrayList<>(((Noun) vocabulary).identifyForm(word));
                case ADJECTIVE -> new ArrayList<>(((Adjective) vocabulary).identifyForm(word));
                case VERB -> new ArrayList<>(((Verb) vocabulary).identifyForm(word));
                case INFLEXIBLE -> new ArrayList<>(nullForms);
            };
            results.add(new IdentificationResult(vocabulary, forms));
        }
        return results;
    }

    public void saveVocabulary(Vocabulary vocabulary) {
        Document document = vocabulary.generateWordbaseEntry();
        Database.wordbase.insertOne(document);
    }

    public void clearAll() {
        Database.wordbase.deleteMany(new Document());
    }
}