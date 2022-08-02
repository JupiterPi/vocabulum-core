package jupiterpi.vocabulum.core.db.classes;

import jupiterpi.vocabulum.core.db.LoadingDataException;
import jupiterpi.vocabulum.core.vocabularies.declined.schemas.DeclensionSchema;
import org.bson.Document;

import java.util.List;

public interface DeclensionClasses {
    void loadDeclensionSchemas(Iterable<Document> documents) throws LoadingDataException;

    DeclensionSchema makeSchema(Document document) throws LoadingDataException;

    Document getRaw(String name);

    DeclensionSchema getSchema(String name);

    List<DeclensionSchema> getAll();

    // utility fields

    DeclensionSchema a_Declension();
    DeclensionSchema o_Declension();
    DeclensionSchema cons_Declension();
    DeclensionSchema e_Declension();
    DeclensionSchema u_Declension();
    DeclensionSchema cons_adjectives_Declension();
}
