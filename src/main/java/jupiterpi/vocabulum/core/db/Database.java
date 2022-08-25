package jupiterpi.vocabulum.core.db;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jupiterpi.vocabulum.core.db.classes.ConjugationClasses;
import jupiterpi.vocabulum.core.db.classes.DeclensionClasses;
import jupiterpi.vocabulum.core.db.portions.Portion;
import jupiterpi.vocabulum.core.db.portions.Portions;
import jupiterpi.vocabulum.core.db.wordbase.DbWordbase;
import jupiterpi.vocabulum.core.db.wordbase.Wordbase;
import jupiterpi.vocabulum.core.i18n.I18nException;
import jupiterpi.vocabulum.core.i18n.I18ns;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import org.bson.Document;

public class Database {
    private static Database instance = null;
    public static Database get() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }
    protected static void inject(Database instance) {
        Database.instance = instance;
    }

    /////

    public MongoClient mongoClient;
    public MongoDatabase database;

    public MongoCollection<Document> collection_declension_schemas;
    public MongoCollection<Document> collection_conjugation_schemas;
    public MongoCollection<Document> collection_other;
    public MongoCollection<Document> collection_i18ns;
    public MongoCollection<Document> collection_portions;
    public MongoCollection<Document> collection_wordbase;

    public void connectAndLoad(String mongoConnectUrl) throws LoadingDataException, ParserException, DeclinedFormDoesNotExistException, I18nException, LexerException, VerbFormDoesNotExistException {
        connect(mongoConnectUrl);
        load();
    }

    protected void connect(String mongoConnectUrl) {
        mongoClient = MongoClients.create(mongoConnectUrl);
        database = mongoClient.getDatabase("vocabulum_data");

        collection_declension_schemas = database.getCollection("declension_schemas");
        collection_conjugation_schemas = database.getCollection("conjugation_schemas");
        collection_other = database.getCollection("other");
        collection_i18ns = database.getCollection("i18ns");
        collection_portions = database.getCollection("portions");
        collection_wordbase = database.getCollection("wordbase");
    }

    protected void load() throws LoadingDataException, ParserException, DeclinedFormDoesNotExistException, I18nException, LexerException, VerbFormDoesNotExistException {
        loadI18ns();
        loadDeclensionClasses();
        loadConjugationClasses();
        loadPortions();

        loadWordbase();
    }

    public void prepareWordbase() {
        wordbase.clearAll();
        for (Portion portion : portions.getPortions().values()) {
            for (Vocabulary vocabulary : portion.getVocabularies()) {
                wordbase.saveVocabulary(vocabulary);
            }
        }
    }

    /* ----- provide access for non-DB classes ----- */

    public Document getAdjectivesDocument() {
        return collection_other.find(new Document("id", "adjectives")).first();
    }

    public Document getVerbsDocument() {
        return collection_other.find(new Document("id", "verbs")).first();
    }

    public Document getTranslationsDocument() {
        return collection_other.find(new Document("id", "translations")).first();
    }
    
    /* ----- objects that read the database ----- */

    // I18ns

    protected I18ns i18ns;

    protected void loadI18ns() {
        i18ns = new I18ns();
        Iterable<Document> documents = collection_i18ns.find();
        i18ns.loadI18ns(documents);
    }

    public I18ns getI18ns() {
        return i18ns;
    }

    // DeclensionClasses

    protected DeclensionClasses declensionClasses;

    protected void loadDeclensionClasses() throws LoadingDataException {
        declensionClasses = new DeclensionClasses();
        Iterable<Document> documents = collection_declension_schemas.find();
        declensionClasses.loadDeclensionSchemas(documents);
    }

    public DeclensionClasses getDeclensionClasses() {
        return declensionClasses;
    }

    // ConjugationClasses

    protected ConjugationClasses conjugationClasses;

    protected void loadConjugationClasses() throws LoadingDataException {
        conjugationClasses = new ConjugationClasses();
        Iterable<Document> documents = collection_conjugation_schemas.find();
        conjugationClasses.loadConjugationSchemas(documents);
    }

    public ConjugationClasses getConjugationClasses() {
        return conjugationClasses;
    }

    // Portions

    protected Portions portions;

    protected void loadPortions() throws ParserException, DeclinedFormDoesNotExistException, I18nException, LexerException, VerbFormDoesNotExistException {
        portions = new Portions();
        Iterable<Document> documents = collection_portions.find();
        portions.loadPortions(documents);
    }

    public Portions getPortions() {
        return portions;
    }

    /* ----- objects that also modify the database ----- */

    // Wordbase

    protected Wordbase wordbase;

    protected void loadWordbase() {
        wordbase = new DbWordbase(this);
    }

    public Wordbase getWordbase() {
        return wordbase;
    }
}