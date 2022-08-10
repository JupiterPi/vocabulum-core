package jupiterpi.vocabulum.core.vocabularies.declined.adjectives;

import jupiterpi.vocabulum.core.i18n.I18n;
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

    public abstract String makeForm(AdjectiveForm form) throws DeclinedFormDoesNotExistException;

    @Override
    public Vocabulary.Kind getKind() {
        return Vocabulary.Kind.ADJECTIVE;
    }

    @Override
    public Document generateWordbaseEntrySpecificPart() {
        Document formsDocument = new Document();
        for (ComparativeForm comparativeForm : ComparativeForm.values()) {
            Document comparativeFormDocument = new Document();
            for (Gender gender : Gender.values()) {
                Document genderDocument = new Document();
                for (NNumber number : NNumber.values()) {
                    Document numberDocument = new Document();
                    for (Casus casus : Casus.values()) {
                        AdjectiveForm form = new AdjectiveForm(new DeclinedForm(casus, number, gender), comparativeForm);
                        String generatedForm;
                        try {
                            generatedForm = makeForm(form);
                        } catch (DeclinedFormDoesNotExistException e) {
                            generatedForm = "-";
                        }
                        numberDocument.put(casus.toString().toLowerCase(), generatedForm);
                    }
                    genderDocument.put(number.toString().toLowerCase(), numberDocument);
                }
                comparativeFormDocument.put(gender.toString().toLowerCase(), genderDocument);
            }
            formsDocument.put(comparativeForm.toString().toLowerCase(), comparativeFormDocument);
        }

        Document document = new Document();
        document.put("forms", formsDocument);
        document.put("definition_type", definitionType.toString().toLowerCase());
        return document;
    }

    public List<AdjectiveForm> identifyForm(String word) {
        List<AdjectiveForm> forms = new ArrayList<>();
        for (ComparativeForm comparativeForm : ComparativeForm.values()) {
            for (Gender gender : Gender.values()) {
                for (NNumber number : NNumber.values()) {
                    for (Casus casus : Casus.values()) {
                        AdjectiveForm form = new AdjectiveForm(new DeclinedForm(casus, number, gender), comparativeForm);
                        try {
                            if (makeForm(form).equalsIgnoreCase(word)) forms.add(form);
                        } catch (DeclinedFormDoesNotExistException ignored) {}
                    }
                }
            }

            AdjectiveForm adverbForm = new AdjectiveForm(true, comparativeForm);
            try {
                if (makeForm(adverbForm).equalsIgnoreCase(word)) forms.add(adverbForm);
            } catch (DeclinedFormDoesNotExistException ignored) {}
        }
        return forms;
    }

    // definition

    protected AdjectiveDefinitionType definitionType;

    public enum AdjectiveDefinitionType {
        FROM_BASE_FORMS, FROM_GENITIVE
    }

    @Override
    public String getDefinition(I18n i18n) {
        if (this.definitionType == AdjectiveDefinitionType.FROM_BASE_FORMS) {
            String nom_sg_masc = "-";
            String nom_sg_fem = "-";
            String nom_sg_neut = "-";
            try {
                nom_sg_masc = makeForm(new AdjectiveForm(DeclinedForm.get("Nom. Sg. m."), ComparativeForm.POSITIVE));
                nom_sg_fem = makeForm(new AdjectiveForm(DeclinedForm.get("Nom. Sg. f."), ComparativeForm.POSITIVE));
                nom_sg_neut = makeForm(new AdjectiveForm(DeclinedForm.get("Nom. Sg. n."), ComparativeForm.POSITIVE));
            } catch (DeclinedFormDoesNotExistException ignored) {}
            return nom_sg_masc + ", " + nom_sg_fem + ", " + nom_sg_neut;
        } else {
            String nom_sg_masc = "-";
            String gen_sg_masc = "-";
            try {
                nom_sg_masc = makeForm(new AdjectiveForm(DeclinedForm.get("Nom. Sg. m."), ComparativeForm.POSITIVE));
                gen_sg_masc = makeForm(new AdjectiveForm(DeclinedForm.get("Gen. Sg. m."), ComparativeForm.POSITIVE));
            } catch (DeclinedFormDoesNotExistException ignored) {}
            return nom_sg_masc + ", " + i18n.getCasusSymbol(Casus.GEN) + ". " + gen_sg_masc;
        }
    }
}