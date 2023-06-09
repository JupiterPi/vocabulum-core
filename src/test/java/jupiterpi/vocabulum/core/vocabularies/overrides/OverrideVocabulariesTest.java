package jupiterpi.vocabulum.core.vocabularies.overrides;

import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.Verb;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.*;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OverrideVocabulariesTest {

    @Test
    void parseTemplateString() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method parseTemplateString = OverrideVocabularies.class.getDeclaredMethod("parseTemplateString", String.class);
        parseTemplateString.setAccessible(true);
        List<OverrideVocabularies.TemplatePart> template = (List<OverrideVocabularies.TemplatePart>) parseTemplateString.invoke(null, "\"(pr)\"+esse+\"(sf)\"");
        assertAll(
            () -> assertTrue(template.get(0) instanceof OverrideVocabularies.TextTemplatePart),
            () -> assertEquals("(pr)", ((OverrideVocabularies.TextTemplatePart) template.get(0)).getText()),
            () -> assertTrue(template.get(1) instanceof OverrideVocabularies.OverrideTemplatePart),
            () -> assertEquals("esse", ((OverrideVocabularies.OverrideTemplatePart) template.get(1)).getOverrideName()),
            () -> assertTrue(template.get(2) instanceof OverrideVocabularies.TextTemplatePart),
            () -> assertEquals("(sf)", ((OverrideVocabularies.TextTemplatePart) template.get(2)).getText())
        );
    }

    @Test
    @DisplayName("general")
    void general() throws ParserException {
        Verb v = (Verb) OverrideVocabularies.createOverrideVocabulary("test", "definition", "\"ab\"+esse", null, new TranslationSequence());
        assertEquals("abest", v.makeForm(new VerbForm(new ConjugatedForm(Person.THIRD, CNumber.SG), Mode.INDICATIVE, Tense.PRESENT, Voice.ACTIVE)).toString());
    }

}