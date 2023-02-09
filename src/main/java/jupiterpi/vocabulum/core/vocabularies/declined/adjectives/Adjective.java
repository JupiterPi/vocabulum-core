package jupiterpi.vocabulum.core.vocabularies.declined.adjectives;

import jupiterpi.vocabulum.core.i18n.Symbols;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public abstract class Adjective extends Vocabulary {
    protected Adjective(TranslationSequence translations, String portion, AdjectiveDefinitionType definitionType) {
        super(translations, portion);
        this.definitionType = definitionType;
    }

    public String makeFormOrDash(AdjectiveForm form) {
        try {
            return makeForm(form);
        } catch (DeclinedFormDoesNotExistException e) {
            return "-";
        }
    }

    public abstract String makeForm(AdjectiveForm form) throws DeclinedFormDoesNotExistException;

    @Override
    public Vocabulary.Kind getKind() {
        return Vocabulary.Kind.ADJECTIVE;
    }

    @Override
    public Document generateWordbaseEntrySpecificPart() {
        Document formsDocument = new Document();

        Document adjectiveFormsDocument = new Document();
        for (ComparativeForm comparativeForm : ComparativeForm.values()) {
            Document comparativeFormDocument = new Document();
            for (Gender gender : Gender.values()) {
                Document genderDocument = new Document();
                for (NNumber number : NNumber.values()) {
                    Document numberDocument = new Document();
                    for (Casus casus : Casus.values()) {
                        AdjectiveForm form = new AdjectiveForm(new DeclinedForm(casus, number, gender), comparativeForm);
                        numberDocument.put(casus.toString().toLowerCase(), makeFormOrDash(form));
                    }
                    genderDocument.put(number.toString().toLowerCase(), numberDocument);
                }
                comparativeFormDocument.put(gender.toString().toLowerCase(), genderDocument);
            }
            adjectiveFormsDocument.put(comparativeForm.toString().toLowerCase(), comparativeFormDocument);
        }

        Document adverbFormsDocument = new Document();
        for (ComparativeForm comparativeForm : ComparativeForm.values()) {
            AdjectiveForm adverbForm = new AdjectiveForm(true, comparativeForm);
            adverbFormsDocument.put(comparativeForm.toString().toLowerCase(), makeFormOrDash(adverbForm));
        }

        formsDocument.put("adjectives", adjectiveFormsDocument);
        formsDocument.put("adverbs", adverbFormsDocument);

        Document document = new Document();
        document.put("forms", formsDocument);
        document.put("definition_type", definitionType.toString().toLowerCase());
        return document;
    }

    @Override
    protected List<String> getAllFormsToString() {
        List<String> forms = new ArrayList<>();
        for (Casus casus : Casus.values()) {
            for (NNumber number : NNumber.values()) {
                for (Gender gender : Gender.values()) {
                    for (ComparativeForm comparativeForm : ComparativeForm.values()) {
                        AdjectiveForm form = new AdjectiveForm(new DeclinedForm(casus, number, gender), comparativeForm);
                        try {
                            forms.add(makeForm(form));
                        } catch (Exception ignored) {}
                    }
                }
            }
        }
        for (ComparativeForm comparativeForm : ComparativeForm.values()) {
            AdjectiveForm form = new AdjectiveForm(true, comparativeForm);
            try {
                forms.add(makeForm(form));
            } catch (Exception ignored) {}
        }
        return forms;
    }

    public List<AdjectiveForm> identifyForm(String word, boolean partialSearch) {
        List<AdjectiveForm> forms = new ArrayList<>();
        for (ComparativeForm comparativeForm : ComparativeForm.values()) {
            for (Gender gender : Gender.values()) {
                for (NNumber number : NNumber.values()) {
                    for (Casus casus : Casus.values()) {
                        AdjectiveForm form = new AdjectiveForm(new DeclinedForm(casus, number, gender), comparativeForm);
                        try {
                            if (partialSearch) {
                                if (makeForm(form).contains(word)) forms.add(form);
                            } else {
                                if (makeForm(form).equalsIgnoreCase(word)) forms.add(form);
                            }
                        } catch (DeclinedFormDoesNotExistException ignored) {}
                    }
                }
            }

            AdjectiveForm adverbForm = new AdjectiveForm(true, comparativeForm);
            try {
                if (partialSearch) {
                    if (makeForm(adverbForm).contains(word)) forms.add(adverbForm);
                } else {
                    if (makeForm(adverbForm).equalsIgnoreCase(word)) forms.add(adverbForm);
                }
            } catch (DeclinedFormDoesNotExistException ignored) {}
        }
        forms.sort(AdjectiveForm.comparator());
        return forms;
    }

    // definition

    protected AdjectiveDefinitionType definitionType;

    public enum AdjectiveDefinitionType {
        FROM_BASE_FORMS, FROM_GENITIVE
    }

    @Override
    public String getDefinition() {
        if (this.definitionType == AdjectiveDefinitionType.FROM_BASE_FORMS) {
            String nom_sg_masc = "-";
            String nom_sg_fem = "-";
            String nom_sg_neut = "-";
            try {
                nom_sg_masc = makeForm(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC), ComparativeForm.POSITIVE));
                nom_sg_fem = makeForm(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.FEM), ComparativeForm.POSITIVE));
                nom_sg_neut = makeForm(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.NEUT), ComparativeForm.POSITIVE));
            } catch (DeclinedFormDoesNotExistException ignored) {}
            return nom_sg_masc + ", " + nom_sg_fem + ", " + nom_sg_neut;
        } else {
            String nom_sg_masc = "-";
            String gen_sg_masc = "-";
            try {
                nom_sg_masc = makeForm(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC), ComparativeForm.POSITIVE));
                gen_sg_masc = makeForm(new AdjectiveForm(new DeclinedForm(Casus.GEN, NNumber.SG, Gender.MASC), ComparativeForm.POSITIVE));
            } catch (DeclinedFormDoesNotExistException ignored) {}
            return nom_sg_masc + ", " + Symbols.get().getCasusSymbol(Casus.GEN) + ". " + gen_sg_masc;
        }
    }
}