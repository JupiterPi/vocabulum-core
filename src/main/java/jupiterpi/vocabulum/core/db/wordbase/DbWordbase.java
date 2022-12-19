package jupiterpi.vocabulum.core.db.wordbase;

import com.mongodb.client.FindIterable;
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
    //TODO write test?

    private Database database;
    public DbWordbase(Database database) {
        this.database = database;
    }

    private static Vocabulary parseDocument(Document vocabularyDocument) {
        return switch (vocabularyDocument.getString("kind")) {
            case "noun" -> WordbaseNoun.readFromDocument(vocabularyDocument);
            case "adjective" -> WordbaseAdjective.readFromDocument(vocabularyDocument);
            case "verb" -> WordbaseVerb.readFromDocument(vocabularyDocument);
            case "inflexible" -> Inflexible.readFromDocument(vocabularyDocument);
            default -> null;
        };
    }

    @Override
    public Vocabulary loadVocabulary(String baseForm) {
        Document vocabularyDocument = database.collection_wordbase.find(new Document("base_form", baseForm)).first();
        return parseDocument(vocabularyDocument);
    }

    @Override
    public List<IdentificationResult> identifyWord(String word, boolean partialSearch) {
        List<VocabularyForm> nullForms = new ArrayList<>();
        nullForms.add(null);

        List<IdentificationResult> results = new ArrayList<>();
        FindIterable<Document> vocabularyDocuments;
        if (partialSearch) {
            vocabularyDocuments = database.collection_wordbase.find(new Document("allFormsIndex", new Document("$regex", ".*" + word + ".*")));
        } else {
            /*database.collection_wordbase.createIndex(new Document("$**", "text"));
            vocabularyDocuments = database.collection_wordbase.find(new Document("$text", new Document("$search", word)));*/
            vocabularyDocuments = database.collection_wordbase.find(new Document("allFormsIndex", new Document("$regex", "\\b" + word + "\\b")));
        }
        for (Document vocabularyDocument : vocabularyDocuments) {
            Vocabulary vocabulary = loadVocabulary(vocabularyDocument.getString("base_form"));
            List<VocabularyForm> forms = switch (vocabulary.getKind()) {
                case NOUN -> new ArrayList<>(((Noun) vocabulary).identifyForm(word, partialSearch));
                case ADJECTIVE -> new ArrayList<>(((Adjective) vocabulary).identifyForm(word, partialSearch));
                case VERB -> new ArrayList<>(((Verb) vocabulary).identifyForm(word, partialSearch));
                case INFLEXIBLE -> new ArrayList<>(nullForms);
            };
            if (forms.size() == 0) continue;
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