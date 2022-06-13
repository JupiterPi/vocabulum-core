package jupiterpi.vocabulum.core;

import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.i18n.I18nException;
import jupiterpi.vocabulum.core.i18n.I18nManager;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.portions.Portion;
import jupiterpi.vocabulum.core.portions.PortionManager;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.conjugated.RuntimeVerb;
import jupiterpi.vocabulum.core.vocabularies.conjugated.Verb;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbForm;
import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.ConjugationClasses;
import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.ConjugationSchema;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.db.LoadingDataException;
import jupiterpi.vocabulum.core.vocabularies.declined.schemas.DeclensionClasses;
import jupiterpi.vocabulum.core.wordbase.WordbaseManager;

import java.util.Map;

public class Main {
    public static I18nManager i18nManager = new I18nManager();
    public static I18n i18n = i18nManager.de;
    public static PortionManager portionManager;
    public static WordbaseManager wordbaseManager;

    public static void main(String[] args) throws LoadingDataException, ParserException, DeclinedFormDoesNotExistException, I18nException, LexerException {
        System.out.println("----- Vocabulum Core -----");

        DeclensionClasses.loadDeclensionSchemas();
        ConjugationClasses.loadConjugationSchemas();
        portionManager = new PortionManager();
        wordbaseManager = new WordbaseManager();

        Map<String, Portion> portions = portionManager.getPortions();
        for (String key : portions.keySet()) {
            Portion portion = portions.get(key);
            System.out.println(portion);
        }
        for (Vocabulary vocabulary : portions.get("01-1").getVocabularies()) {
            System.out.println(vocabulary);
            //wordbaseManager.saveVocabulary(vocabulary);
        }

        //Verb sampleVerb = new RuntimeVerb(ConjugationClasses.a_Conjugation, "vocare", "voc", "vocav");
        /*Verb sampleVerb = (Verb) wordbaseManager.loadVocabulary("vocare");
        System.out.println(sampleVerb);
        System.out.println(sampleVerb.makeForm(VerbForm.fromString("1. Pers. Sg. Präs. Ind.", i18n)));
        System.out.println(sampleVerb.makeForm(VerbForm.fromString("1. Pers. Sg. Perf. Ind.", i18n)));
        System.out.println(sampleVerb.makeForm(VerbForm.fromString("1. Pers. Pl. Plusq. Konj.", i18n)));*/

        Portion sentence1Portion = portionManager.getPortion("x-01");
        for (Vocabulary vocabulary : sentence1Portion.getVocabularies()) {
            System.out.println(vocabulary);
            //wordbaseManager.saveVocabulary(vocabulary);
        }

        /*System.out.println(wordbaseManager.identifyWord("exspectat"));
        System.out.println(wordbaseManager.identifyWord("exspectant"));
        System.out.println(wordbaseManager.identifyWord("stat"));
        System.out.println(wordbaseManager.identifyWord("stant"));*/

        Terminal terminal = new Terminal();
        terminal.run();
    }
}
