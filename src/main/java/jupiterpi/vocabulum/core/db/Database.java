package jupiterpi.vocabulum.core.db;

import com.mongodb.client.*;
import jupiterpi.vocabulum.core.db.classes.ConjugationClasses;
import jupiterpi.vocabulum.core.db.classes.DeclensionClasses;
import jupiterpi.vocabulum.core.db.lectures.Lectures;
import jupiterpi.vocabulum.core.db.portions.Dictionary;
import jupiterpi.vocabulum.core.db.portions.Portions;
import jupiterpi.vocabulum.core.db.users.DbUsers;
import jupiterpi.vocabulum.core.db.users.Users;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.util.TextFile;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import org.bson.Document;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Loads and hosts all relevant data from the database or resource files.
 * Call <code>connectAndLoad()</code> to load all data, then access it using <code>getPortions()</code> etc.
 * @see #connectAndLoad(String)
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

    public MongoCollection<Document> collection_portions;
    public MongoCollection<Document> collection_lectures;
    public MongoCollection<Document> collection_wordbase;
    public MongoCollection<Document> collection_users;

    public void connectAndLoad(String mongoConnectUrl) throws LoadingDataException, ParserException, DeclinedFormDoesNotExistException, LexerException, VerbFormDoesNotExistException {
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

        collection_portions = database.getCollection("portions");
        collection_lectures = database.getCollection("lectures");
        collection_wordbase = database.getCollection("wordbase");
        collection_users = database.getCollection("users");
    }

    protected void load() throws LoadingDataException, ParserException, DeclinedFormDoesNotExistException, LexerException, VerbFormDoesNotExistException {
        loadDocuments();

        loadDeclensionClasses();
        loadConjugationClasses();
        loadPortionsAndDictionary();
        loadLectures();

        loadUsers();
    }

    /* ----- provide access for non-DB classes ----- */

    private Document adjectivesDocument;
    private Document verbsDocument;
    private Document translationsDocument;

    private void loadDocuments() {
        adjectivesDocument = TextFile.readJsonResourceFile("adjectives.json");
        verbsDocument = TextFile.readJsonResourceFile("verbs.json");
        translationsDocument = TextFile.readJsonResourceFile("translations.json");
    }

    /**
     * @return The "adjectives" raw document on the database.
     */
    public Document getAdjectivesDocument() {
        return adjectivesDocument;
    }

    /**
     * @return The "verbs" raw document on the database.
     */
    public Document getVerbsDocument() {
        return verbsDocument;
    }

    /**
     * @return The "translations" raw document on the database.
     */
    public Document getTranslationsDocument() {
        return translationsDocument;
    }
    
    /* ----- objects that read the database ----- */

    // DeclensionClasses

    protected DeclensionClasses declensionClasses;

    protected void loadDeclensionClasses() throws LoadingDataException {
        declensionClasses = new DeclensionClasses();
        List<Document> documents = Stream.of(
                "a_decl.json",
                "cons_adjectives_decl.json",
                "cons_decl.json",
                "e_decl.json",
                "o_decl.json",
                "u_decl.json"
        )
                .map(declensionClassFile -> TextFile.readJsonResourceFile("declension_schemas/" + declensionClassFile))
                .collect(Collectors.toList());
        declensionClasses.loadDeclensionSchemas(documents);
    }

    public DeclensionClasses getDeclensionClasses() {
        return declensionClasses;
    }

    // ConjugationClasses

    protected ConjugationClasses conjugationClasses;

    protected void loadConjugationClasses() throws LoadingDataException {
        conjugationClasses = new ConjugationClasses();
        List<Document> documents = Stream.of(
                        "a_conj.json"
                )
                .map(conjugationClassFile -> TextFile.readJsonResourceFile("conjugation_schemas/" + conjugationClassFile))
                .collect(Collectors.toList());
        conjugationClasses.loadConjugationSchemas(documents);
    }

    public ConjugationClasses getConjugationClasses() {
        return conjugationClasses;
    }

    // Portions & Dictionary

    protected Portions portions;
    protected Dictionary dictionary;

    protected void loadPortionsAndDictionary() throws ParserException, DeclinedFormDoesNotExistException, LexerException, VerbFormDoesNotExistException {
        portions = new Portions();
        Iterable<Document> documents = collection_portions.find();
        portions.loadPortions(documents);

        dictionary = new Dictionary(portions);
    }

    public Portions getPortions() {
        return portions;
    }

    public Dictionary getDictionary() {
        return dictionary;
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

    // Users

    protected Users users;

    protected void loadUsers() {
        users = new DbUsers(this);
    }

    public Users getUsers() {
        return users;
    }
}