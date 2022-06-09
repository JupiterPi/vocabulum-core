package jupiterpi.vocabulum.core.wordbase;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.conjugated.WordbaseVerb;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.WordbaseAdjective;
import jupiterpi.vocabulum.core.vocabularies.declined.nouns.WordbaseNoun;
import org.bson.Document;

public class WordbaseManager {
    public Vocabulary loadVocabulary(String baseForm) {
        Document vocabularyDocument = Database.wordbase.find(new Document("base_form", baseForm)).first();
        return switch (vocabularyDocument.getString("kind")) {
            case "noun" -> WordbaseNoun.readFromDocument(vocabularyDocument);
            case "adjective" -> WordbaseAdjective.readFromDocument(vocabularyDocument);
            case "verb" -> WordbaseVerb.readFromDocument(vocabularyDocument);
            default -> null;
        };
    }

    public void saveVocabulary(Vocabulary vocabulary) {
        Document document = vocabulary.generateWordbaseEntry();
        Database.wordbase.insertOne(document);
    }
}