package jupiterpi.vocabulum.core.vocabularies.declined.schemas;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.LoadingDataException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class SimpleDeclensionSchema extends DeclensionSchema {
    private Map<DeclinedForm, String> suffixes;
    private boolean isGenderDependant;

    public SimpleDeclensionSchema(String name, String displayName, Map<DeclinedForm, String> suffixes, boolean isGenderDependant) {
        super(name, displayName);
        this.suffixes = suffixes;
        this.isGenderDependant = isGenderDependant;
    }

    protected SimpleDeclensionSchema(String name, String displayName, boolean isGenderDependant) {
        super(name, displayName);
        this.suffixes = new HashMap<>();
        this.isGenderDependant = isGenderDependant;
    }
    public static SimpleDeclensionSchema readFromDocument(Document document) throws LoadingDataException, DeclinedFormDoesNotExistException {
        String name = document.getString("name");
        String displayName = document.getString("displayName");
        boolean isGenderDependant = document.getString("schema").equals("gender_dependant");
        SimpleDeclensionSchema schema = new SimpleDeclensionSchema(name, displayName, isGenderDependant);

        DeclensionSchema parent = null;
        if (document.containsKey("parent")) {
            String parentName = document.getString("parent");
            Document parentDocument = Database.get().getDeclensionClasses().getRaw(parentName);
            parent = SimpleDeclensionSchema.readFromDocument(parentDocument);
        }

        for (Gender gender : Gender.values()) {
            Document genderDocument = isGenderDependant
                    ? (Document) document.get(gender.toString().toLowerCase())
                    : document;
            for (NNumber number : NNumber.values()) {
                Document numberDocument = (Document) genderDocument.get(number.toString().toLowerCase());
                for (Casus casus : Casus.values()) {
                    String suffix = numberDocument.getString(casus.toString().toLowerCase());
                    DeclinedForm form = new DeclinedForm(casus, number, gender);
                    if (suffix.equals(".")) {
                        if (parent != null) {
                            suffix = parent.getSuffixRaw(form);
                        } else {
                            throw new LoadingDataException("Could not create SimpleDeclensionSchema " + name + ": encountered '.' raw suffix, but no parent set.");
                        }
                    }
                    schema.suffixes.put(form, suffix);
                }
            }
        }

        return schema;
    }

    @Override
    protected String getSuffixRaw(DeclinedForm form) throws DeclinedFormDoesNotExistException {
        if (!form.hasGender()) {
            if (isGenderDependant) throw DeclinedFormDoesNotExistException.forDeclensionSchema(form, this);
            else form.normalizeGender();
        }
        return suffixes.get(form);
    }

}