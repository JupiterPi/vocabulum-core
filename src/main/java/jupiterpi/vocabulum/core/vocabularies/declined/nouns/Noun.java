package jupiterpi.vocabulum.core.vocabularies.declined.nouns;

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

public abstract class Noun extends Vocabulary {
    protected Noun(TranslationSequence translations, String portion) {
        super(translations, portion);
    }

    protected abstract Gender getGender();

    public abstract String makeForm(NounForm form) throws DeclinedFormDoesNotExistException;

    @Override
    public String getDefinition(I18n i18n) {
        String gen_sg = "-";
        try {
            gen_sg = makeForm(new NounForm(DeclinedForm.get("Gen. Sg.")));
        } catch (DeclinedFormDoesNotExistException ignored) {}
        return getBaseForm() + ", " + gen_sg + " " + i18n.getGenderSymbol(getGender()) + ".";
    }

    @Override
    public Kind getKind() {
        return Kind.NOUN;
    }

    @Override
    public Document generateWordbaseEntrySpecificPart() {
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

        Document document = new Document();
        document.put("forms", formsDocument);
        document.put("gender", getGender().toString().toLowerCase());
        return document;
    }

    public List<NounForm> identifyForm(String word) {
        List<NounForm> forms = new ArrayList<>();
        for (Gender gender : Gender.values()) {
            for (NNumber number : NNumber.values()) {
                for (Casus casus : Casus.values()) {
                    NounForm form = new NounForm(new DeclinedForm(casus, number, gender));
                    try {
                        if (makeForm(form).equalsIgnoreCase(word)) forms.add(form);
                    } catch (DeclinedFormDoesNotExistException ignored) {}
                }
            }
        }
        return forms;
    }
}