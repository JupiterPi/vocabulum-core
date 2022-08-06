package jupiterpi.vocabulum.core.vocabularies.conjugated;

import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.*;
import jupiterpi.vocabulum.core.vocabularies.translations.VocabularyTranslation;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public abstract class Verb extends Vocabulary {
    protected Verb(List<VocabularyTranslation> translations, String portion) {
        super(translations, portion);
    }

    public abstract String makeForm(VerbForm form) throws VerbFormDoesNotExistException;

    @Override
    public Kind getKind() {
        return Kind.VERB;
    }

    @Override
    public String getDefinition(I18n i18n) {
        String first_sg_pres = "-";
        String first_sg_perfect = "-";
        try {
            first_sg_pres = makeForm(new VerbForm(ConjugatedForm.get("1. Sg."), Mode.INDICATIVE, Tense.PRESENT, Voice.ACTIVE));
            first_sg_perfect = makeForm(new VerbForm(ConjugatedForm.get("1. Sg."), Mode.INDICATIVE, Tense.PERFECT, Voice.ACTIVE));
        } catch (VerbFormDoesNotExistException ignored) {}
        return getBaseForm() + ", " + first_sg_pres + ", " + first_sg_perfect;
        //TODO update for full form making
    }

    public abstract String getBaseForm();

    @Override
    public Document generateWordbaseEntrySpecificPart() {
        Document formsDocument = new Document();
        for (Voice voice : Voice.values()) {
            Document voiceDocument = new Document();
            for (Tense tense : Tense.values()) {
                Document tenseDocument = new Document();
                for (Mode mode : Mode.values()) {
                    Document modeDocument = new Document();
                    for (CNumber number : CNumber.values()) {
                        Document numberDocument = new Document();
                        for (Person person : Person.values()) {
                            try {
                                String form = makeForm(new VerbForm(new ConjugatedForm(person, number), mode, tense, voice));
                                numberDocument.put(person.toString().toLowerCase(), form);
                            } catch (VerbFormDoesNotExistException e) {
                                numberDocument.put(person.toString().toLowerCase(), "-");
                            }
                        }
                        modeDocument.put(number.toString().toLowerCase(), numberDocument);
                    }
                    tenseDocument.put(mode.toString().toLowerCase(), modeDocument);
                }
                voiceDocument.put(tense.toString().toLowerCase(), tenseDocument);
            }
            formsDocument.put(voice.toString().toLowerCase(), voiceDocument);
        }

        Document document = new Document();
        document.put("forms", formsDocument);
        return document;
    }

    public List<VerbForm> identifyForm(String word) {
        List<VerbForm> forms = new ArrayList<>();
        for (Voice voice : Voice.values()) {
            for (Tense tense : Tense.values()) {
                for (Mode mode : Mode.values()) {
                    for (CNumber number : CNumber.values()) {
                        for (Person person : Person.values()) {
                            VerbForm form = new VerbForm(new ConjugatedForm(person, number), mode, tense, voice);
                            try {
                                if (makeForm(form).equalsIgnoreCase(word)) forms.add(form);
                            } catch (VerbFormDoesNotExistException ignored) {}
                        }
                    }
                }
            }
        }
        return forms;
    }
}