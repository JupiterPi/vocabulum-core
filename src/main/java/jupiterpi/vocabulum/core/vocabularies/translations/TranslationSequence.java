package jupiterpi.vocabulum.core.vocabularies.translations;

import java.util.ArrayList;
import java.util.Arrays;

public class TranslationSequence extends ArrayList<VocabularyTranslation> {
    public TranslationSequence(VocabularyTranslation... translations) {
        super(Arrays.asList(translations));
    }
}