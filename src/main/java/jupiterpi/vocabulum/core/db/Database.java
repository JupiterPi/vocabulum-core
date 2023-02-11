package jupiterpi.vocabulum.core.db;

import com.mongodb.client.*;
import jupiterpi.vocabulum.core.db.classes.ConjugationClasses;
import jupiterpi.vocabulum.core.db.classes.DeclensionClasses;
import jupiterpi.vocabulum.core.db.lectures.Lectures;
import jupiterpi.vocabulum.core.db.portions.Portion;
import jupiterpi.vocabulum.core.db.portions.Portions;
import jupiterpi.vocabulum.core.db.users.DbUsers;
import jupiterpi.vocabulum.core.db.users.Users;
import jupiterpi.vocabulum.core.db.wordbase.DbWordbase;
import jupiterpi.vocabulum.core.db.wordbase.Wordbase;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import org.bson.Document;

/**
 * Loads and hosts all relevant data from the database.
 * Call <code>connectAndLoad()</code> and <code>prepareWordbase()</code> to load all data, then access it using <code>getPortions()</code> etc. or <code>getTranslationsDocument()</code>.
 * @see #connectAndLoad(String) connectAndLoad()
 * @see #prepareWordbase() prepareWordbase()
 */
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
    public MongoCollection<Document> collection_lectures;
    public MongoCollection<Document> collection_wordbase;
    public MongoCollection<Document> collection_users;

    public void connectAndLoad(String mongoConnectUrl) throws LoadingDataException, ParserException, DeclinedFormDoesNotExistException, LexerException, VerbFormDoesNotExistException, ReflectiveOperationException {
        connect(mongoConnectUrl);
        load();
    }

    /**
     * Establishes a connection to the MongoDB database and loads all data.
     * @param mongoConnectUrl the MongoDB connect url (e. g. "mongodb://localhost")
     */
    protected void connect(String mongoConnectUrl) {
        mongoClient = MongoClients.create(mongoConnectUrl);
        database = mongoClient.getDatabase("vocabulum_data");

        collection_declension_schemas = database.getCollection("declension_schemas");
        collection_conjugation_schemas = database.getCollection("conjugation_schemas");
        collection_other = database.getCollection("other");
        collection_i18ns = database.getCollection("i18ns");
        collection_portions = database.getCollection("portions");
        collection_lectures = database.getCollection("lectures");
        collection_wordbase = database.getCollection("wordbase");
        collection_users = database.getCollection("users");
    }

    protected void load() throws LoadingDataException, ParserException, DeclinedFormDoesNotExistException, LexerException, VerbFormDoesNotExistException {
        loadDeclensionClasses();
        loadConjugationClasses();
        loadPortions();
        loadLectures();

        loadWordbase();
        loadUsers();
    }

    /**
     * Loads all vocabularies from <code>Portions</code> into the <code>Wordbase</code>.
     */
    public void prepareWordbase() {
        wordbase.clearAll();
        for (Portion portion : portions.getPortions().values()) {
            for (Vocabulary vocabulary : portion.getVocabularies()) {
                wordbase.saveVocabulary(vocabulary);
            }
        }
    }

    /* ----- provide access for non-DB classes ----- */

    /**
     * @return The "adjectives" raw document on the database.
     */
    public Document getAdjectivesDocument() {
        return collection_other.find(new Document("id", "adjectives")).first();
    }

    /**
     * @return The "verbs" raw document on the database.
     */
    public Document getVerbsDocument() {
        return collection_other.find(new Document("id", "verbs")).first();
    }

    /**
     * @return The "translations" raw document on the database.
     */
    public Document getTranslationsDocument() {
        return collection_other.find(new Document("id", "translations")).first();
    }
    
    /* ----- objects that read the database ----- */

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

    protected void loadPortions() throws ParserException, DeclinedFormDoesNotExistException, LexerException, VerbFormDoesNotExistException {
        portions = new Portions();
        Iterable<Document> documents = collection_portions.find();
        portions.loadPortions(documents);
    }

    public Portions getPortions() {
        return portions;
    }

    // Lectures

    protected Lectures lectures;

    protected void loadLectures() {
        lectures = new Lectures();
        FindIterable<Document> documents = collection_lectures.find();
        lectures.loadLectures(documents);
    }

    public Lectures getLectures() {
        return lectures;
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

    // Users

    protected Users users;

    protected void loadUsers() {
        users = new DbUsers(this);
    }

    public Users getUsers() {
        return users;
    }
}