package jupiterpi.vocabulum.core.vocabularies;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.LoadingDataException;
import jupiterpi.vocabulum.core.db.portions.Dictionary;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.*;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;

import java.util.ArrayList;
import java.util.List;

public class VocabularyFormComparisonTests {
    public static void main(String[] args) throws LoadingDataException, ParserException, DeclinedFormDoesNotExistException, LexerException, VerbFormDoesNotExistException {
        Database.get().load();

        String query = "exsp";
        List<Dictionary.IdentificationResult> identificationResults = Database.get().getDictionary().identifyWord(query, true);
        for (VocabularyForm form : identificationResults.get(0).getForms()) {
            System.out.println(form);
        }
    }

    public static void main1(String[] args) throws LoadingDataException, ParserException, DeclinedFormDoesNotExistException, LexerException, VerbFormDoesNotExistException {
        Database.get().load();

        // --- Verbs ---
        List<VerbForm> forms = new ArrayList<>();

        // Kind.IMPERATIVE
        for (CNumber number : CNumber.values()) {
            forms.add(new VerbForm(number));
        }

        // Kind.INFINITIVE
        for (InfinitiveTense infinitiveTense : InfinitiveTense.values()) {
            for (Voice voice : Voice.values()) {
                forms.add(new VerbForm(infinitiveTense, voice));
            }
        }

        // Kind.BASIC
        for (Voice voice : Voice.values()) {
            for (Tense tense : Tense.values()) {
                for (Mode mode : Mode.values()) {
                    for (CNumber number : CNumber.values()) {
                        for (Person person : Person.values()) {
                            forms.add(new VerbForm(new ConjugatedForm(person, number), mode, tense, voice));
                        }
                    }
                }
            }
        }

        // Kind.NOUN_LIKE
        for (NounLikeForm nounLikeForm : NounLikeForm.values()) {
            for (Gender gender : Gender.values()) {
                for (NNumber number : NNumber.values()) {
                    for (Casus casus : Casus.values()) {
                        forms.add(new VerbForm(nounLikeForm, new DeclinedForm(casus, number, gender)));
                    }
                }
            }
        }

        System.out.println("--- ORIGINAL FORMS ---");
        for (VerbForm form : forms) {
            System.out.println(form.toString());
        }

        System.out.println();
        System.out.println();

        System.out.println("--- SORTED FORMS ---");
        forms.sort(VerbForm.comparator());
        for (VerbForm form : forms) {
            System.out.println(form.toString());
        }
    }
}