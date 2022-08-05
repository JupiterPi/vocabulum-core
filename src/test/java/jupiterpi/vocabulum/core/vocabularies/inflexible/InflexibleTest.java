package jupiterpi.vocabulum.core.vocabularies.inflexible;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import org.bson.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.print.Doc;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockDatabaseSetup.class)
class InflexibleTest {
    @Test
    @DisplayName("general test")
    void test() {
        Document document = Document.parse("""
                {
                  "kind": "inflexible",
                  "base_form": "et",
                  "portion": "test",
                  "translations": [
                    "*und*"
                  ]
                }
                """);
        Inflexible i = Inflexible.readFromDocument(document);
        assertAll(
                () -> assertEquals("et", i.getBaseForm()),
                () -> assertEquals("et", i.getDefinition(Database.get().getI18ns().internal())),
                () -> assertEquals(Vocabulary.Kind.INFLEXIBLE, i.getKind()),
                () -> assertEquals(new Document(), i.generateWordbaseEntrySpecificPart())
        );
    }
}