package jupiterpi.vocabulum.core.vocabularies.conjugated.schemas;

import jupiterpi.vocabulum.core.vocabularies.conjugated.form.*;
import jupiterpi.vocabulum.core.db.LoadingDataException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.forminfo.Pattern;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class SimpleConjugationSchema extends ConjugationSchema {
    public static SimpleConjugationSchema readFromDocument(Document document) throws LoadingDataException {
        String name = document.getString("name");
        SimpleConjugationSchema schema = new SimpleConjugationSchema(name);

        try {
            for (Voice voice : Voice.values()) {
                Document voiceDocument = (Document) document.get(voice.toString().toLowerCase());
                for (Tense tense : Tense.values()) {
                    Document tenseDocument = (Document) voiceDocument.get(tense.toString().toLowerCase());
                    for (Mode mode : Mode.values()) {
                        Document modeDocument = (Document) tenseDocument.get(mode.toString().toLowerCase());
                        for (CNumber number : CNumber.values()) {
                            Document numberDocument = (Document) modeDocument.get(number.toString().toLowerCase());
                            for (Person person : Person.values()) {
                                String pattern = numberDocument.getString(person.toString().toLowerCase());
                                VerbForm form = new VerbForm(new ConjugatedForm(person, number), mode, tense, voice);
                                schema.patterns.put(form, Pattern.fromString(pattern));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new LoadingDataException("Could not create SimpleConjugationSchema " + name + ": " + e.getClass().getSimpleName() + " \"" + e.getMessage() + "\"");
        }

        return schema;
    }

    private SimpleConjugationSchema(String name) {
        super(name);
        patterns = new HashMap<>();
    }

    private Map<VerbForm, Pattern> patterns;

    @Override
    public Pattern getPattern(VerbForm verbForm) throws VerbFormDoesNotExistException {
        Pattern formInfo = patterns.get(verbForm);
        if (!formInfo.exists()) throw VerbFormDoesNotExistException.forConjugationSchema(verbForm, this);
        return formInfo;
    }
}
