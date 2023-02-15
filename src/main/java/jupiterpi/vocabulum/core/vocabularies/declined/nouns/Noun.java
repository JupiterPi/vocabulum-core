package jupiterpi.vocabulum.core.vocabularies.declined.nouns;

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

import java.util.ArrayList;
import java.util.List;

public class Noun extends Vocabulary {
    private DeclensionSchema declensionSchema;
    private String nom_sg;
    private String root;
    private Gender gender;

    public Noun(DeclensionSchema declensionSchema, String nom_sg, String root, Gender gender, TranslationSequence translations, String portion) {
        super(translations, portion);
        this.declensionSchema = declensionSchema;
        this.nom_sg = nom_sg;
        this.root = root;
        this.gender = gender;
    }

    private Noun(TranslationSequence translations, String portion) {
        super(translations, portion);
    }
    public static Noun fromGenitive(String nom_sg, String gen_sg, Gender gender, TranslationSequence translations, String portion) throws DeclinedFormDoesNotExistException, ParserException {
        Noun noun = new Noun(translations, portion);
        noun.nom_sg = nom_sg;
        noun.gender = gender;
        for (DeclensionSchema declensionSchema : Database.get().getDeclensionClasses().getAllForNouns()) {
            String suffix = declensionSchema.getSuffix(new DeclinedForm(Casus.GEN, NNumber.SG, gender));
            if (gen_sg.endsWith(suffix)) {
                noun.declensionSchema = declensionSchema;
                noun.root = gen_sg.substring(0, gen_sg.length()-suffix.length());
                break;
            }
        }
        if (noun.declensionSchema == null) {
            throw new ParserException("Could not determine declension schema for Gen. Sg. form: " + gen_sg);
        }
        return noun;
    }

    @Override
    public String getBaseForm() {
        return nom_sg;
    }

    protected Gender getGender() {
        return gender;
    }

    public String makeFormOrDash(NounForm form) {
        try {
            return makeForm(form);
        } catch (DeclinedFormDoesNotExistException e) {
            return "-";
        }
    }

    public String makeForm(NounForm form) throws DeclinedFormDoesNotExistException {
        DeclinedForm declinedForm = form.getDeclinedForm();
        if (declinedForm.hasGender() && declinedForm.getGender() != this.gender) {
            throw DeclinedFormDoesNotExistException.forWord(declinedForm, nom_sg);
        }
        declinedForm.normalizeGender(gender);
        if (declinedForm.isCasus(Casus.NOM) && declinedForm.isNumber(NNumber.SG)) {
            return nom_sg;
        }
        return root + declensionSchema.getSuffix(declinedForm);
    }

    @Override
    public String getDefinition() {
        String gen_sg = "-";
        try {
            gen_sg = makeForm(new NounForm(new DeclinedForm(Casus.GEN, NNumber.SG)));
        } catch (DeclinedFormDoesNotExistException ignored) {}
        return getBaseForm() + ", " + gen_sg + " " + Symbols.get().getGenderSymbol(getGender()) + ".";
    }

    @Override
    public Kind getKind() {
        return Kind.NOUN;
    }

    @Override
    public List<String> getAllFormsToString() {
        List<String> forms = new ArrayList<>();
        for (Gender gender : Gender.values()) {
            for (NNumber number : NNumber.values()) {
                for (Casus casus : Casus.values()) {
                    NounForm form = new NounForm(new DeclinedForm(casus, number, gender));
                    try {
                        forms.add(makeForm(form));
                    } catch (Exception ignored) {}
                }
            }
        }
        return forms;
    }

    public String getDeclensionSchema() {
        return declensionSchema.getName();
    }

    public List<NounForm> identifyForm(String word, boolean partialSearch) {
        List<NounForm> forms = new ArrayList<>();
        for (Gender gender : Gender.values()) {
            for (NNumber number : NNumber.values()) {
                for (Casus casus : Casus.values()) {
                    NounForm form = new NounForm(new DeclinedForm(casus, number, gender));
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
        forms.sort(NounForm.comparator());
        return forms;
    }
}