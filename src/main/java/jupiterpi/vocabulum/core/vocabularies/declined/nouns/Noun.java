package jupiterpi.vocabulum.core.vocabularies.declined.nouns;

import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;

public abstract class Noun extends Vocabulary {
    public abstract String makeForm(NounForm form) throws DeclinedFormDoesNotExistException;

    @Override
    public Kind getKind() {
        return Kind.NOUN;
    }
}