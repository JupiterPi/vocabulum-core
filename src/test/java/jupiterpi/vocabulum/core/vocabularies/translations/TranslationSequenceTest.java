package jupiterpi.vocabulum.core.vocabularies.translations;

import jupiterpi.vocabulum.core.vocabularies.translations.parts.ArticlePart;
import jupiterpi.vocabulum.core.vocabularies.translations.parts.PlainTextPart;
import jupiterpi.vocabulum.core.vocabularies.translations.parts.TranslationPartContainer;
import org.bson.Document;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TranslationSequenceTest {
    @Test
    void readFromDocument() {
        Document document = new Document("translations", List.of("*der Freund*", "der Kamerad"));
        TranslationSequence translations = TranslationSequence.readFromDocument(document);
        TranslationSequence e = new TranslationSequence(
                new VocabularyTranslation(true, new TranslationPartContainer(new ArticlePart("der"), new PlainTextPart("Freund"))),
                new VocabularyTranslation(false, new TranslationPartContainer(new ArticlePart("der"), new PlainTextPart("Kamerad")))
        );
        assertEquals(e, translations);
    }
}