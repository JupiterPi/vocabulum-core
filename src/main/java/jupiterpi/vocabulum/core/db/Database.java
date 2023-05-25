package jupiterpi.vocabulum.core.db;

import jupiterpi.vocabulum.core.db.classes.ConjugationClasses;
import jupiterpi.vocabulum.core.db.classes.DeclensionClasses;
import jupiterpi.vocabulum.core.db.lectures.Lectures;
import jupiterpi.vocabulum.core.db.portions.Dictionary;
import jupiterpi.vocabulum.core.db.portions.Portions;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;

import java.util.List;
import java.util.Map;

/**
 * Loads and hosts all objects that host data from resource files.
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
        load(Resources.get().getPortions(), Resources.get().getLectures());
    }
    public void load(Map<String, List<List<String>>> portions, Map<String, List<String>> lectures) throws LoadingDataException, ParserException, DeclinedFormDoesNotExistException, LexerException, VerbFormDoesNotExistException {
        loadDeclensionClasses();
        loadConjugationClasses();
        loadPortionsAndDictionary(portions);
        loadLectures(lectures);
    }

    /* ----- objects that read the database ----- */

    // DeclensionClasses

    protected DeclensionClasses declensionClasses;

    protected void loadDeclensionClasses() throws LoadingDataException {
        declensionClasses = new DeclensionClasses();
        declensionClasses.loadDeclensionSchemas(Resources.get().getDeclensionClasses());
    }

    public DeclensionClasses getDeclensionClasses() {
        return declensionClasses;
    }

    // ConjugationClasses

    protected ConjugationClasses conjugationClasses;

    protected void loadConjugationClasses() throws LoadingDataException {
        conjugationClasses = new ConjugationClasses();
        conjugationClasses.loadConjugationSchemas(Resources.get().getConjugationClasses());
    }

    public ConjugationClasses getConjugationClasses() {
        return conjugationClasses;
    }

    // Portions & Dictionary

    protected Portions portions;
    protected Dictionary dictionary;

    protected void loadPortionsAndDictionary(Map<String, List<List<String>>> portions) throws ParserException, DeclinedFormDoesNotExistException, LexerException, VerbFormDoesNotExistException {
        this.portions = new Portions();
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

    protected void loadLectures(Map<String, List<String>> lectures) {
        this.lectures = new Lectures();
        this.lectures.loadLectures(lectures);
    }

    public Lectures getLectures() {
        return lectures;
    }
}