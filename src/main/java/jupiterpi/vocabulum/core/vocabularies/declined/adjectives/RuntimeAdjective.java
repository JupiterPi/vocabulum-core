package jupiterpi.vocabulum.core.vocabularies.declined.adjectives;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import jupiterpi.vocabulum.core.vocabularies.declined.schemas.DeclensionSchema;
import jupiterpi.vocabulum.core.vocabularies.translations.VocabularyTranslation;
import org.bson.Document;

import java.util.List;

public class RuntimeAdjective extends Adjective {
    private static final DeclensionSchema masculineDeclensionSchema = Database.get().getDeclensionClasses().o_Declension;
    private static final DeclensionSchema feminineDeclensionSchema = Database.get().getDeclensionClasses().a_Declension;
    private static final DeclensionSchema neuterDeclensionSchema = Database.get().getDeclensionClasses().o_Declension;
    private static final DeclensionSchema consonantalDeclensionSchema = Database.get().getDeclensionClasses().cons_adjectives_Declension;

    private static final Document adjectivesData = Database.get().getAdjectivesDocument();

    private String nom_sg_masc;
    private String nom_sg_fem;
    private String nom_sg_neut;
    private Kind kind;
    public enum Kind {
        AO, CONS
    }
    private String root;

    private RuntimeAdjective(List<VocabularyTranslation> translations, String portion, AdjectiveDefinitionType definitionType) {
        super(translations, portion, definitionType);
    }
    public static RuntimeAdjective fromBaseForms(String nom_sg_masc, String nom_sg_fem, String nom_sg_neut, List<VocabularyTranslation> translations, String portion) throws DeclinedFormDoesNotExistException {
        RuntimeAdjective adjective = new RuntimeAdjective(translations, portion, AdjectiveDefinitionType.FROM_BASE_FORMS);
        adjective.nom_sg_masc = nom_sg_masc;
        adjective.nom_sg_fem = nom_sg_fem;
        adjective.nom_sg_neut = nom_sg_neut;

        if (
                /*nom_sg_masc.endsWith(masculineDeclensionSchema.getSuffix(DeclinedForm.get("Nom. Sg. m.")))      // incorrect, only depends on f. and n. ending
                &&*/ nom_sg_fem.endsWith(feminineDeclensionSchema.getSuffix(DeclinedForm.get("Nom. Sg. f.")))
                && nom_sg_neut.endsWith(neuterDeclensionSchema.getSuffix(DeclinedForm.get("Nom. Sg. n.")))
        ) {
            adjective.kind = Kind.AO;
            adjective.root = nom_sg_fem.substring(0, nom_sg_fem.length() - feminineDeclensionSchema.getSuffix(DeclinedForm.get("Nom. Sg. f.")).length());
        } else {
            adjective.kind = Kind.CONS;
            adjective.root = "";
            for (int i = 0; i < nom_sg_fem.length() && i < nom_sg_neut.length(); i++) {
                String fem_c = nom_sg_fem.split("")[i];
                String neut_c = nom_sg_neut.split("")[i];
                if (fem_c.equals(neut_c)) adjective.root += fem_c;
                else break;
            }
        }

        return adjective;
    }
    public static RuntimeAdjective fromGenitive(String nom_sg, String gen_sg, List<VocabularyTranslation> translations, String portion) throws DeclinedFormDoesNotExistException, ParserException {
        RuntimeAdjective adjective = new RuntimeAdjective(translations, portion, AdjectiveDefinitionType.FROM_GENITIVE);
        adjective.nom_sg_masc = nom_sg;
        adjective.nom_sg_fem = nom_sg;
        adjective.nom_sg_neut = nom_sg;

        String gen_sg_suffix = consonantalDeclensionSchema.getSuffix(DeclinedForm.get("Gen. Sg. m."));
        if (gen_sg.endsWith(gen_sg_suffix)) {
            adjective.root = gen_sg.substring(0, gen_sg.length() - gen_sg_suffix.length());
        } else {
            throw new ParserException("Could not determine root from Gen. Sg. form: " + gen_sg);
        }

        adjective.kind = Kind.CONS;
        return adjective;
    }

    @Override
    public String makeForm(AdjectiveForm form) throws DeclinedFormDoesNotExistException {
        // adverbs
        if (form.isAdverb()) {
            Document adverbsData = (Document) adjectivesData.get("adverbs");
            Document suffixData = (Document) (kind == Kind.AO ? adverbsData.get("ao_suffix") : adverbsData.get("cons_suffix"));

            if (kind == Kind.CONS) {
                List<Document> exceptions = (List<Document>) adverbsData.get("cons_exceptions");
                for (Document exceptionData : exceptions) {
                    Document requiredSuffixData = (Document) exceptionData.get("required_suffix");
                    String requiredSuffixForm = requiredSuffixData.getString("form");
                    String requiredSuffix = requiredSuffixData.getString("suffix");
                    if (switch (requiredSuffixForm) {
                        case "nom_sg_masc" -> nom_sg_masc.endsWith(requiredSuffix);
                        case "root" -> root.endsWith(requiredSuffix);
                        default -> false;
                    }) {
                        suffixData = (Document) exceptionData.get("suffix");
                    }
                }
            }

            String result = root + switch (form.getComparativeForm()) {
                case POSITIVE -> suffixData.getString("positive");
                case COMPARATIVE -> suffixData.getString("comparative");
                case SUPERLATIVE -> suffixData.getString("superlative");
            };
            if (form.getComparativeForm() == ComparativeForm.SUPERLATIVE) {
                String extra = suffixData.getString("extra");
                if (extra != null && extra.equals("superlative_with_nom_sg_masc")) {
                    result = nom_sg_masc + suffixData.getString("superlative");
                }
            }
            return result;

        // adjectives
        } else {
            // positive
            if (form.getComparativeForm() == ComparativeForm.POSITIVE) {

                DeclinedForm declinedForm = form.getDeclinedForm();
                declinedForm.normalizeGender();
                if (declinedForm.fits(DeclinedForm.get("Nom. Sg."))) {
                    return switch (declinedForm.getGender()) {
                        case MASC -> nom_sg_masc;
                        case FEM -> nom_sg_fem;
                        case NEUT -> nom_sg_neut;
                    };
                }
                if (kind == Kind.AO) {
                    return root + switch (declinedForm.getGender()) {
                        case MASC -> masculineDeclensionSchema.getSuffix(declinedForm);
                        case FEM -> feminineDeclensionSchema.getSuffix(declinedForm);
                        case NEUT -> neuterDeclensionSchema.getSuffix(declinedForm);
                    };
                } else {
                    return root + consonantalDeclensionSchema.getSuffix(declinedForm);
                }

            } else {
                Document comparativeFormsData = (Document) adjectivesData.get("comparative_forms");

                // comparative
                if (form.getComparativeForm() == ComparativeForm.COMPARATIVE) {

                    Document comparativeData = (Document) comparativeFormsData.get("comparative");

                    DeclinedForm declinedForm = form.getDeclinedForm();
                    declinedForm.normalizeGender();

                    String comparativeSign = comparativeData.getString("comparative_sign");
                    if (declinedForm.equals(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.NEUT))) {
                        comparativeSign = comparativeData.getString("comparative_sign_nom_sg_n");
                    }

                    return root + comparativeSign + consonantalDeclensionSchema.getSuffix(declinedForm);

                // superlative
                } else {

                    Document superlativeData = (Document) comparativeFormsData.get("superlative");

                    DeclinedForm declinedForm = form.getDeclinedForm();
                    declinedForm.normalizeGender();

                    String superlativeSign = superlativeData.getString("superlative_sign");
                    Document exceptionData = (Document) superlativeData.get("exception");
                    if (nom_sg_masc.endsWith(exceptionData.getString("nom_sg_masc_suffix"))) {
                        superlativeSign = exceptionData.getString("superlative_sign");
                    }

                    return root + superlativeSign + switch (declinedForm.getGender()) {
                        case MASC -> masculineDeclensionSchema.getSuffix(declinedForm);
                        case FEM -> feminineDeclensionSchema.getSuffix(declinedForm);
                        case NEUT -> neuterDeclensionSchema.getSuffix(declinedForm);
                    };

                }
            }
        }
    }

    @Override
    public String getBaseForm() {
        return nom_sg_masc;
    }
}