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

public class MockDatabase extends Database {
    public static void inject() {
        Database.inject(new MockDatabase());
    }

    // load classes

    public void reload() throws LoadingDataException, ParserException, DeclinedFormDoesNotExistException, LexerException, VerbFormDoesNotExistException {
        load();
    }

    public void injectDeclensionClasses(DeclensionClasses declensionClasses) {
        this.declensionClasses = declensionClasses;
    }

    public void injectConjugationClasses(ConjugationClasses conjugationClasses) {
        this.conjugationClasses = conjugationClasses;
    }

    public void injectPortions(Portions portions) {
        this.portions = portions;
    }

    public void injectDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public void injectLectures(Lectures lectures) {
        this.lectures = lectures;
    }
}