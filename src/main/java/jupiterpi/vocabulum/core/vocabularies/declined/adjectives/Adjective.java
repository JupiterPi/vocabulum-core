package jupiterpi.vocabulum.core.vocabularies.declined.adjectives;

import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import jupiterpi.vocabulum.core.vocabularies.declined.nouns.NounForm;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Adjective extends Vocabulary {
    protected Adjective(List<String> translations) {
        super(translations);
    }

    public abstract String makeForm(AdjectiveForm form) throws DeclinedFormDoesNotExistException;

    @Override
    public Vocabulary.Kind getKind() {
        return Vocabulary.Kind.ADJECTIVE;
    }

    @Override
    public Document generateWordbaseEntry() {
        Document document = new Document();
        document.put("kind", "adjective");
        document.put("base_form", getBaseForm());

        Document formsDocument = new Document();
        for (ComparativeForm comparativeForm : ComparativeForm.values()) {
            Document comparativeFormDocument = new Document();
            for (Gender gender : Gender.values()) {
                Document genderDocument = new Document();
                for (NNumber number : NNumber.values()) {
                    Document numberDocument = new Document();
                    for (Casus casus : Casus.values()) {
                        AdjectiveForm form = new AdjectiveForm(new DeclinedForm(casus, number, gender), ComparativeForm.POSITIVE);
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

        document.put("forms", formsDocument);
        document.put("translations", translations);
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
}