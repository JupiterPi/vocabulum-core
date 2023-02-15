package jupiterpi.vocabulum.core.vocabularies.declined.adjectives;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.symbols.Symbols;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import jupiterpi.vocabulum.core.vocabularies.declined.schemas.DeclensionSchema;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Adjective extends Vocabulary {
    public static final DeclensionSchema masculineDeclensionSchema = Database.get().getDeclensionClasses().o_Declension();
    public static final DeclensionSchema feminineDeclensionSchema = Database.get().getDeclensionClasses().a_Declension();
    public static final DeclensionSchema neuterDeclensionSchema = Database.get().getDeclensionClasses().o_Declension();
    public static final DeclensionSchema consonantalDeclensionSchema = Database.get().getDeclensionClasses().cons_adjectives_Declension();

    private static final Document adjectivesData = Database.get().getAdjectivesDocument();

    private String nom_sg_masc;
    private String nom_sg_fem;
    private String nom_sg_neut;
    private Kind kind;
    public enum Kind {
        AO, CONS
    }
    private String root;
    public Adjective(String nom_sg_masc, String nom_sg_fem, String nom_sg_neut, Kind kind, String root, TranslationSequence translations, String portion, AdjectiveDefinitionType definitionType) {
        super(translations, portion);
        this.definitionType = definitionType;
        
        this.nom_sg_masc = nom_sg_masc;
        this.nom_sg_fem = nom_sg_fem;
        this.nom_sg_neut = nom_sg_neut;
        this.kind = kind;
        this.root = root;
    }

    private Adjective(TranslationSequence translations, String portion, AdjectiveDefinitionType definitionType) {
        super(translations, portion);
        this.definitionType = definitionType;
    }
    public static Adjective fromBaseForms(String nom_sg_masc, String nom_sg_fem, String nom_sg_neut, TranslationSequence translations, String portion) throws DeclinedFormDoesNotExistException {
        Adjective adjective = new Adjective(translations, portion, AdjectiveDefinitionType.FROM_BASE_FORMS);
        adjective.nom_sg_masc = nom_sg_masc;
        adjective.nom_sg_fem = nom_sg_fem;
        adjective.nom_sg_neut = nom_sg_neut;

        if (
                /*nom_sg_masc.endsWith(masculineDeclensionSchema.getSuffix(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC)))      // incorrect, only depends on f. and n. ending
                &&*/ nom_sg_fem.endsWith(feminineDeclensionSchema.getSuffix(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.FEM)))
                && nom_sg_neut.endsWith(neuterDeclensionSchema.getSuffix(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.NEUT)))
        ) {
            adjective.kind = Kind.AO;
            adjective.root = nom_sg_fem.substring(0, nom_sg_fem.length() - feminineDeclensionSchema.getSuffix(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.FEM)).length());
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
    public static Adjective fromGenitive(String nom_sg, String gen_sg, TranslationSequence translations, String portion) throws DeclinedFormDoesNotExistException, ParserException {
        Adjective adjective = new Adjective(translations, portion, AdjectiveDefinitionType.FROM_GENITIVE);
        adjective.nom_sg_masc = nom_sg;
        adjective.nom_sg_fem = nom_sg;
        adjective.nom_sg_neut = nom_sg;

        String gen_sg_suffix = consonantalDeclensionSchema.getSuffix(new DeclinedForm(Casus.GEN, NNumber.SG, Gender.MASC));
        if (gen_sg.endsWith(gen_sg_suffix)) {
            adjective.root = gen_sg.substring(0, gen_sg.length() - gen_sg_suffix.length());
        } else {
            throw new ParserException("Could not determine root from Gen. Sg. form: " + gen_sg);
        }

        adjective.kind = Kind.CONS;
        return adjective;
    }

    public String makeFormOrDash(AdjectiveForm form) {
        try {
            return makeForm(form);
        } catch (DeclinedFormDoesNotExistException e) {
            return "-";
        }
    }

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
                if (declinedForm.fits(new DeclinedForm(Casus.NOM, NNumber.SG))) {
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

                    //TODO fix error e. g. "pulchrrimus"

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

    @Override
    public Vocabulary.Kind getKind() {
        return Vocabulary.Kind.ADJECTIVE;
    }

    @Override
    public List<String> getAllFormsToString() {
        List<String> forms = new ArrayList<>();
        for (Casus casus : Casus.values()) {
            for (NNumber number : NNumber.values()) {
                for (Gender gender : Gender.values()) {
                    for (ComparativeForm comparativeForm : ComparativeForm.values()) {
                        AdjectiveForm form = new AdjectiveForm(new DeclinedForm(casus, number, gender), comparativeForm);
                        try {
                            forms.add(makeForm(form));
                        } catch (Exception ignored) {}
                    }
                }
            }
        }
        for (ComparativeForm comparativeForm : ComparativeForm.values()) {
            AdjectiveForm form = new AdjectiveForm(true, comparativeForm);
            try {
                forms.add(makeForm(form));
            } catch (Exception ignored) {}
        }
        return forms;
    }

    public List<AdjectiveForm> identifyForm(String word, boolean partialSearch) {
        List<AdjectiveForm> forms = new ArrayList<>();
        for (ComparativeForm comparativeForm : ComparativeForm.values()) {
            for (Gender gender : Gender.values()) {
                for (NNumber number : NNumber.values()) {
                    for (Casus casus : Casus.values()) {
                        AdjectiveForm form = new AdjectiveForm(new DeclinedForm(casus, number, gender), comparativeForm);
                        try {
                            if (partialSearch) {
                                if (makeForm(form).contains(word)) forms.add(form);
                            } else {
                                if (makeForm(form).equalsIgnoreCase(word)) forms.add(form);
                            }
                        } catch (DeclinedFormDoesNotExistException ignored) {}
                    }
                }
            }

            AdjectiveForm adverbForm = new AdjectiveForm(true, comparativeForm);
            try {
                if (partialSearch) {
                    if (makeForm(adverbForm).contains(word)) forms.add(adverbForm);
                } else {
                    if (makeForm(adverbForm).equalsIgnoreCase(word)) forms.add(adverbForm);
                }
            } catch (DeclinedFormDoesNotExistException ignored) {}
        }
        forms.sort(AdjectiveForm.comparator());
        return forms;
    }

    // definition

    protected AdjectiveDefinitionType definitionType;

    public enum AdjectiveDefinitionType {
        FROM_BASE_FORMS, FROM_GENITIVE
    }

    @Override
    public String getDefinition() {
        if (this.definitionType == AdjectiveDefinitionType.FROM_BASE_FORMS) {
            String nom_sg_masc = "-";
            String nom_sg_fem = "-";
            String nom_sg_neut = "-";
            try {
                nom_sg_masc = makeForm(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC), ComparativeForm.POSITIVE));
                nom_sg_fem = makeForm(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.FEM), ComparativeForm.POSITIVE));
                nom_sg_neut = makeForm(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.NEUT), ComparativeForm.POSITIVE));
            } catch (DeclinedFormDoesNotExistException ignored) {}
            return nom_sg_masc + ", " + nom_sg_fem + ", " + nom_sg_neut;
        } else {
            String nom_sg_masc = "-";
            String gen_sg_masc = "-";
            try {
                nom_sg_masc = makeForm(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC), ComparativeForm.POSITIVE));
                gen_sg_masc = makeForm(new AdjectiveForm(new DeclinedForm(Casus.GEN, NNumber.SG, Gender.MASC), ComparativeForm.POSITIVE));
            } catch (DeclinedFormDoesNotExistException ignored) {}
            return nom_sg_masc + ", " + Symbols.get().getCasusSymbol(Casus.GEN) + ". " + gen_sg_masc;
        }
    }
}