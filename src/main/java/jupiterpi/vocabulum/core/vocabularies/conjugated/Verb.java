package jupiterpi.vocabulum.core.vocabularies.conjugated;

import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public abstract class Verb extends Vocabulary {
    protected Verb(List<String> translations) {
        super(translations);
    }

    public abstract String makeForm(VerbForm form) throws VerbFormDoesNotExistException;

    @Override
    public Kind getKind() {
        return Kind.VERB;
    }

    public abstract String getBaseForm();

    @Override
    public Document generateWordbaseEntry() {
        Document document = new Document();
        document.put("kind", "verb");
        document.put("base_form", getBaseForm());

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

        document.put("forms", formsDocument);
        document.put("translations", translations);
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