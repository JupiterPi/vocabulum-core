package jupiterpi.vocabulum.core.db;

import jupiterpi.vocabulum.core.db.classes.ConjugationClasses;
import jupiterpi.vocabulum.core.db.classes.DeclensionClasses;
import jupiterpi.vocabulum.core.db.lectures.Lectures;
import jupiterpi.vocabulum.core.db.portions.Dictionary;
import jupiterpi.vocabulum.core.db.portions.Portions;
import jupiterpi.vocabulum.core.db.users.Users;
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

    @Override
    protected void loadPortionsAndDictionary() throws ParserException, DeclinedFormDoesNotExistException, LexerException, VerbFormDoesNotExistException {
        this.portions = new Portions();
        this.portions.loadPortions(List.of(
                Document.parse("""
                        {
                           "name": "1",
                           "i18n": "de",
                           "vocabularies": [
                             [
                               "sol, solis m. -- *die Sonne*",
                               "silentium, silentii n. -- *die Ruhe*, *die Stille*, das Schweigen",
                               "villa, villae f. -- *das (Land)Haus*, die Villa",
                               "canis, canis n. -- *der Hund*",
                               "acer, acris, acre -- *heftig*, hart, scharf"
                             ],
                             [
                               "brevis, brevis, breve -- *kurz*",
                               "felix, Gen. felicis -- *glücklich*",
                               "clemens, Gen. clementis -- *sanft*, *zart*",
                               "celer, celeris, celere -- *schnell*",
                               "pulcher, pulchra, pulchrum -- *hübsch*, schön",
                               "vocare, voco, vocavi, vocatum -- *rufen*, *nennen*"
                             ]
                           ]
                         }"""),
                Document.parse("""
                        {
                           "name": "A",
                           "i18n": "de",
                           "vocabularies": [
                             [
                               "asinus, asini m. -- *der Esel*",
                               "stare, sto, stavi, statum -- *dastehen*, aufrecht stehen",
                               "et -- *und*",
                               "exspectare, exspecto, exspectavi, exspectatum -- *erwarten*, warten auf"
                             ]
                           ]
                         }
                        """),
                Document.parse("""
                        {
                           "name": "L1",
                           "i18n": "de",
                           "vocabularies": [
                             [
                               "clamare, clamo, clamavi, clamatum -- *rufen*, schreien"
                             ]
                           ]
                         }
                        """)/*,
                Document.parse("""
                        {
                          "name": "2",
                          "i18n": "de",
                          "vocabularies": [
                            [
                              "a/ab (m. Abl.) -- *von (... her)*, von ... weg, *seit* [9]",
                              "abducere, abduco, abduxi, abductum -- *wegführen*, (weg)bringen, verschleppen [6]",
                              "abesse, absum, afui [\\"ab\\"+esse] -- *abwesend sein*, fehlen, entfernt sein [20]",
                              "abire, abeo, abii, abitum -- *(weg)gehen* [20]",
                              "abstinentia, abstinentiae f. -- *die Enthaltsamkeit* [36]",
                              "accedere, accedo, accessi, accessum -- *herantreten*, hingehen",
                              "accendere, accendo, accendi, accensum -- *in Brand setzen*, entflammen, aufregen [29]",
                              "accidere, accidit, accidit, - -- *sich ereignen*, *zustoßen* [22]",
                              "accipere, accipio, accepi, acceptum --*annehmen*, empfangen, aufnehmen [20]",
                              "accurrere, accurro, accurri, accursum -- *herbeilaufen*, angelaufen kommen [5]",
                              "acer, acris, acre; Gen. acris -- *heftig*, hart, scharf [15]",
                              "acies, aciei f. -- *das (kampfbereite) Heer*, die Schlachtordnung [26]",
                              "ad (m. Akk.) -- *zu*, *zu ... hin*, *an*, *bei* [6]",
                              "addere, addo, addidi, additum -- *hinzufügen* [10]",
                              "adducere, adduco, adduxi, adductum -- *heranführen*, *veranlassen* [27]",
                              "adeo (Adv.) -- *so sehr* [30]"
                            ]
                          ]
                        }
                        """)*/
        ));

        dictionary = new Dictionary(portions);
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

    @Override
    protected void loadUsers() {
        users = new MockUsers();
    }

    public void reloadUsers() {
        loadUsers();
    }

    public void injectUsers(Users users) {
        this.users = users;
    }
}