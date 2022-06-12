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
        private VocabularyForm form;

        public IdentificationResult(Vocabulary vocabulary, VocabularyForm form) {
            this.vocabulary = vocabulary;
            this.form = form;
        }

        public Vocabulary getVocabulary() {
            return vocabulary;
        }

        public VocabularyForm getForm() {
            return form;
        }
    }

    public List<IdentificationResult> identifyWord(String word) {
        Database.wordbase.createIndex(new Document("$**", "text"));
        List<IdentificationResult> results = new ArrayList<>();
        for (Document vocabularyDocument : Database.wordbase.find(new Document("$text", new Document("$search", word)))) {
            Vocabulary vocabulary = loadVocabulary(vocabularyDocument.getString("base_form"));
            VocabularyForm form = switch (vocabulary.getKind()) {
                case NOUN -> ((Noun) vocabulary).identifyForm(word);
                case ADJECTIVE -> ((Adjective) vocabulary).identifyForm(word);
                case VERB -> ((Verb) vocabulary).identifyForm(word);
                case INFLEXIBLE -> null;
            };
            results.add(new IdentificationResult(vocabulary, form));
        }
        return results;
    }

    public void saveVocabulary(Vocabulary vocabulary) {
        Document document = vocabulary.generateWordbaseEntry();
        Database.wordbase.insertOne(document);
    }
}