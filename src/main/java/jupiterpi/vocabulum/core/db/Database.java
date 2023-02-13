package jupiterpi.vocabulum.core.db;

import jupiterpi.vocabulum.core.db.classes.ConjugationClasses;
import jupiterpi.vocabulum.core.db.classes.DeclensionClasses;
import jupiterpi.vocabulum.core.db.lectures.Lectures;
import jupiterpi.vocabulum.core.db.portions.Dictionary;
import jupiterpi.vocabulum.core.db.portions.Portions;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.util.TextFile;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import org.bson.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Loads and hosts all relevant data from the database or resource files.
 * Call <code>load()</code> to load all data, then access it using <code>getPortions()</code> etc.
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

    /**
     * Loads all data.
     */
    public void load() throws LoadingDataException, ParserException, DeclinedFormDoesNotExistException, LexerException, VerbFormDoesNotExistException {
        loadDocuments();

        loadDeclensionClasses();
        loadConjugationClasses();
        loadPortionsAndDictionary();
        loadLectures();
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

    public Document getAdjectivesDocument() {
        return adjectivesDocument;
    }

    public Document getVerbsDocument() {
        return verbsDocument;
    }

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
        this.portions = new Portions();

        Map<String, List<List<String>>> portions = new HashMap<>();
        for (Document document : TextFile.readJsonResourceFile("portions.json").getList("portions", Document.class)) {
            String name = document.getString("name");
            String file = TextFile.readResourceFile("portions/" + document.getString("file")).getFile();
            List<String> blocksStr = List.of(file.split("\n\n"));
            List<List<String>> vocabularyStrBlocks = blocksStr.stream()
                    .map(blockStr -> List.of(blockStr.split("\n")))
                    .toList();
            portions.put(name, vocabularyStrBlocks);
        }
        this.portions.loadPortions(portions);

        dictionary = new Dictionary(this.portions);
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
        this.lectures = new Lectures();

        Map<String, List<String>> lectures = new HashMap<>();
        for (Document document : TextFile.readJsonResourceFile("lectures.json").getList("lectures", Document.class)) {
            String name = document.getString("name");
            List<String> lines = TextFile.readResourceFile("lectures/" + document.getString("file")).getLines();
            lectures.put(name, lines);
        }
        this.lectures.loadLectures(lectures);
    }

    public Lectures getLectures() {
        return lectures;
    }
}