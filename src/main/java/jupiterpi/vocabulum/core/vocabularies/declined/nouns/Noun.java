package jupiterpi.vocabulum.core.vocabularies.declined.nouns;

import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.VocabularyForm;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import org.bson.Document;

import java.util.List;

public abstract class Noun extends Vocabulary {
    protected Noun(List<String> translations) {
        super(translations);
    }

    protected abstract Gender getGender();

    public abstract String makeForm(NounForm form) throws DeclinedFormDoesNotExistException;

    @Override
    public Kind getKind() {
        return Kind.NOUN;
    }

    @Override
    public Document generateWordbaseEntry() {
        Document document = new Document();
        document.put("kind", "noun");
        document.put("base_form", getBaseForm());
        document.put("gender", getGender().toString().toLowerCase());

        Document formsDocument = new Document();
        for (Gender gender : Gender.values()) {
            Document genderDocument = new Document();
            for (NNumber number : NNumber.values()) {
                Document numberDocument = new Document();
                for (Casus casus : Casus.values()) {
                    NounForm form = new NounForm(new DeclinedForm(casus, number, gender));
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
            formsDocument.put(gender.toString().toLowerCase(), genderDocument);
        }

        document.put("forms", formsDocument);
        document.put("translations", translations);
        return document;
    }

    public NounForm identifyForm(String word) {
        for (Gender gender : Gender.values()) {
            for (NNumber number : NNumber.values()) {
                for (Casus casus : Casus.values()) {
                    NounForm form = new NounForm(new DeclinedForm(casus, number, gender));
                    try {
                        if (makeForm(form).equalsIgnoreCase(word)) return form;
                    } catch (DeclinedFormDoesNotExistException ignored) {}
                }
            }
        }
        return null;
    }
}