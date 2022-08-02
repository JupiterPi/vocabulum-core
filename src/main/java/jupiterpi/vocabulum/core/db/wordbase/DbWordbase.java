package jupiterpi.vocabulum.core.db.wordbase;

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

public class DbWordbase implements Wordbase {
    private Database database;
    public DbWordbase(Database database) {
        this.database = database;
    }

    @Override
    public Vocabulary loadVocabulary(String baseForm) {
        Document vocabularyDocument = database.collection_wordbase.find(new Document("base_form", baseForm)).first();
        return switch (vocabularyDocument.getString("kind")) {
            case "noun" -> WordbaseNoun.readFromDocument(vocabularyDocument);
            case "adjective" -> WordbaseAdjective.readFromDocument(vocabularyDocument);
            case "verb" -> WordbaseVerb.readFromDocument(vocabularyDocument);
            case "inflexible" -> Inflexible.readFromDocument(vocabularyDocument);
            default -> null;
        };
    }

    @Override
    public List<IdentificationResult> identifyWord(String word) {
        List<VocabularyForm> nullForms = new ArrayList<>();
        nullForms.add(null);

        database.collection_wordbase.createIndex(new Document("$**", "text"));
        List<IdentificationResult> results = new ArrayList<>();
        for (Document vocabularyDocument : database.collection_wordbase.find(new Document("$text", new Document("$search", word)))) {
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

    @Override
    public void saveVocabulary(Vocabulary vocabulary) {
        Document document = vocabulary.generateWordbaseEntry();
        database.collection_wordbase.insertOne(document);
    }

    @Override
    public void clearAll() {
        database.collection_wordbase.deleteMany(new Document());
    }
}