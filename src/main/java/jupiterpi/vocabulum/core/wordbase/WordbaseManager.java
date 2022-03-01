package jupiterpi.vocabulum.core.wordbase;

import jupiterpi.vocabulum.core.Database;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.WordbaseAdjective;
import jupiterpi.vocabulum.core.vocabularies.declined.nouns.WordbaseNoun;
import org.bson.Document;

public class WordbaseManager {
    public Vocabulary loadVocabulary(String baseForm) {
        Document vocabularyDocument = Database.wordbase.find(new Document("base_form", baseForm)).first();
        Vocabulary vocabulary = switch (vocabularyDocument.getString("kind")) {
            case "noun" -> WordbaseNoun.readFromDocument(vocabularyDocument);
            case "adjective" -> WordbaseAdjective.readFromDocument(vocabularyDocument);
            default -> null;
        };
        return vocabulary;
    }

    public void saveVocabulary(Vocabulary vocabulary) {
        Document document = vocabulary.generateWordbaseEntry();
        Database.wordbase.insertOne(document);
    }
}