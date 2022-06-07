package jupiterpi.vocabulum.core.vocabularies.conjugated;

import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbForm;
import org.bson.Document;

public abstract class Verb extends Vocabulary {
    public abstract String makeForm(VerbForm form);

    @Override
    public Kind getKind() {
        return Kind.VERB;
    }

    @Override
    public Document generateWordbaseEntry() {
        return null;
        //TODO implement
    }
}