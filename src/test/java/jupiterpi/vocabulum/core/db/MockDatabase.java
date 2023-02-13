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
import org.bson.Document;

import java.util.List;

public class MockDatabase extends Database {
    public static void inject() {
        Database.inject(new MockDatabase());
    }

    /////


    @Override
    protected void connect(String mongoConnectUrl) {
        // do nothing
    }

    // load classes

    public void reloadDeclensionClasses() throws LoadingDataException {
        loadDeclensionClasses();
    }

    public void injectDeclensionClasses(DeclensionClasses declensionClasses) {
        this.declensionClasses = declensionClasses;
    }

    public void reloadConjugationClasses() throws LoadingDataException {
        loadConjugationClasses();
    }

    public void injectConjugationClasses(ConjugationClasses conjugationClasses) {
        this.conjugationClasses = conjugationClasses;
    }

    public void reloadPortionsAndDictionary() throws ParserException, DeclinedFormDoesNotExistException, LexerException, VerbFormDoesNotExistException {
        loadPortionsAndDictionary();
    }

    public void injectPortions(Portions portions) {
        this.portions = portions;
    }

    public void injectDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    protected void loadLectures() {
        this.lectures = new Lectures();
        this.lectures.loadLectures(List.of(
                Document.parse("""
                        {
                          "name": "L1",
                          "text": "Sol ardet, silentium est; villa sub sole iacet.\\nEtiam canis tacet, asinus non iam clamat.\\nQuintus stat et exspectat.\\nUbi est Flavia?\\nCur amica non venit?\\nNon placet stare et exspectare,\\nnon placet esse sine amica,\\nnon placet villa sine amica,\\nnon placet sol,\\nnon placet silentium.\\nSubito canis latrat, etiam asinus clamat.\\nQuid est? Ecce! Quis venit?"
                        }
                        """)
        ));
    }

    public void reloadLectures() {
        loadLectures();
    }

    public void injectLectures(Lectures lectures) {
        this.lectures = lectures;
    }
}