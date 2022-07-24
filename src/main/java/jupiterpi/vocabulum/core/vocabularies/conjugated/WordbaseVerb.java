package jupiterpi.vocabulum.core.vocabularies.conjugated;

import jupiterpi.vocabulum.core.vocabularies.conjugated.form.*;
import jupiterpi.vocabulum.core.vocabularies.translations.VocabularyTranslation;
import org.bson.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordbaseVerb extends Verb {
    private String baseForm;
    private Map<VerbForm, String> forms;

    public WordbaseVerb(String baseForm, Document forms, List<VocabularyTranslation> translations, String portion) {
        super(translations, portion);
        this.baseForm = baseForm;

        this.forms = new HashMap<>();
        for (Voice voice : Voice.values()) {
            Document voiceDocument = (Document) forms.get(voice.toString().toLowerCase());
            for (Tense tense : Tense.values()) {
                Document tenseDocument = (Document) voiceDocument.get(tense.toString().toLowerCase());
                for (Mode mode : Mode.values()) {
                    Document modeDocument = (Document) tenseDocument.get(mode.toString().toLowerCase());
                    for (CNumber number : CNumber.values()) {
                        Document numberDocument = (Document) modeDocument.get(number.toString().toLowerCase());
                        for (Person person : Person.values()) {
                            VerbForm form = new VerbForm(new ConjugatedForm(person, number), mode, tense, voice);
                            this.forms.put(form, numberDocument.getString(person.toString().toLowerCase()));
                        }
                    }
                }
            }
        }
    }

    public static WordbaseVerb readFromDocument(Document document) {
        return new WordbaseVerb(
                document.getString("base_form"),
                (Document) document.get("forms"),
                readTranslations(document),
                document.getString("portion"));
    }

    @Override
    public String getBaseForm() {
        return baseForm;
    }

    @Override
    public String makeForm(VerbForm form) {
        return forms.get(form);
    }
}