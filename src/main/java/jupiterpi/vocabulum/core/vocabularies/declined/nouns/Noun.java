package jupiterpi.vocabulum.core.vocabularies.declined.nouns;

import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Number;
import org.bson.Document;

public abstract class Noun extends Vocabulary {
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
            for (Number number : Number.values()) {
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
        return document;
    }
}