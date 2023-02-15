package jupiterpi.vocabulum.core.vocabularies.conjugated;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.*;
import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.ConjugationSchema;
import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.forminfo.Pattern;
import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.forminfo.VerbInfo;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.Adjective;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.AdjectiveForm;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.ComparativeForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import jupiterpi.vocabulum.core.vocabularies.declined.schemas.DeclensionSchema;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Verb extends Vocabulary {
    private ConjugationSchema conjugationSchema;
    private String infinitive;
    private String presentRoot;
    private String perfectRoot;
    private String pppRoot;

    public Verb(ConjugationSchema conjugationSchema, String infinitive, String presentRoot, String perfectRoot, String pppRoot, TranslationSequence translations, String portion) {
        super(translations, portion);
        this.conjugationSchema = conjugationSchema;
        this.infinitive = infinitive;
        this.presentRoot = presentRoot;
        this.perfectRoot = perfectRoot;
        this.pppRoot = pppRoot;
    }

    public static Verb fromBaseForms(String infinitive, String first_sg_present, String first_sg_perfect, String ppp_nom_sg_neut, TranslationSequence translations, String portion) throws ParserException, VerbFormDoesNotExistException, DeclinedFormDoesNotExistException {
        ConjugationSchema conjugationSchema = Database.get().getConjugationClasses().a_Conjugation();

        /* roots */

        String presentRoot = null;
        String perfectRoot = "-";
        for (ConjugationSchema schema : Database.get().getConjugationClasses().getAll()) {
            Pattern pattern = schema.getPattern(new VerbForm(new ConjugatedForm(Person.FIRST, CNumber.SG), Mode.INDICATIVE, Tense.PRESENT, Voice.ACTIVE));
            if (first_sg_present.endsWith(pattern.getSuffix())) {
                presentRoot = first_sg_present.substring(0, first_sg_present.length() - pattern.getSuffix().length());
                if (!first_sg_perfect.equals("-")) {
                    perfectRoot = first_sg_perfect.substring(0, first_sg_perfect.length() - schema.getPattern(new VerbForm(new ConjugatedForm(Person.FIRST, CNumber.SG), Mode.INDICATIVE, Tense.PERFECT, Voice.ACTIVE)).getSuffix().length());
                }
            }
        }
        if (presentRoot == null) {
            throw new ParserException("Couldn't read present root from 1. Sg. Pres. form: " + first_sg_present);
        }

        /* ppp*/

        String pppRoot = "-";
        if (!ppp_nom_sg_neut.equals("-")) {
            String nom_sg_neut_suffix = Adjective.neuterDeclensionSchema.getSuffix(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.NEUT));
            pppRoot = ppp_nom_sg_neut.substring(0, ppp_nom_sg_neut.length() - nom_sg_neut_suffix.length());
        }

        return new Verb(conjugationSchema, infinitive, presentRoot, perfectRoot, pppRoot, translations, portion);
    }

    @Override
    public String getBaseForm() {
        return infinitive;
    }

    public String makeForm(VerbForm form) throws VerbFormDoesNotExistException, DeclinedFormDoesNotExistException {
        VerbInfo info = new VerbInfo(
                presentRoot, perfectRoot,
                makeNounLikeForm(new VerbForm(NounLikeForm.PPP, new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC))),
                makeNounLikeForm(new VerbForm(NounLikeForm.PPP, new DeclinedForm(Casus.NOM, NNumber.PL, Gender.MASC))),
                makeNounLikeForm(new VerbForm(NounLikeForm.PPA, new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC))),
                makeNounLikeForm(new VerbForm(NounLikeForm.PPA, new DeclinedForm(Casus.NOM, NNumber.PL, Gender.MASC))),
                makeNounLikeForm(new VerbForm(NounLikeForm.PFA, new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC))),
                makeNounLikeForm(new VerbForm(NounLikeForm.PFA, new DeclinedForm(Casus.NOM, NNumber.PL, Gender.MASC))),
                !perfectRoot.equals("-"), !pppRoot.equals("-")
        );
        return switch (form.getKind()) {
            case IMPERATIVE, INFINITIVE, BASIC -> makeImperativeOrInfinitiveOrBasicForm(form, info);
            case NOUN_LIKE -> makeNounLikeForm(form);
        };
    }

    private String makeImperativeOrInfinitiveOrBasicForm(VerbForm form, VerbInfo info) throws VerbFormDoesNotExistException {
        if (form.equals(new VerbForm(InfinitiveTense.PRESENT, Voice.ACTIVE))) {
            return infinitive;
        }
        Pattern pattern = conjugationSchema.getPattern(form);
        return pattern.make(info);
    }

    private String makeNounLikeForm(VerbForm form) throws DeclinedFormDoesNotExistException {
        VerbInfo info = new VerbInfo(
                presentRoot, perfectRoot,
                "", "", "", "", "", "",
                !perfectRoot.equals("-"), !pppRoot.equals("-")
        );

        String adjective_nom_sg_masc_suffix = Adjective.masculineDeclensionSchema.getSuffix(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC));
        String adjective_nom_sg_fem_suffix = Adjective.feminineDeclensionSchema.getSuffix(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.FEM));
        String adjective_nom_sg_neut_suffix = Adjective.neuterDeclensionSchema.getSuffix(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.NEUT));

        NounLikeForm nounLikeForm = form.getNounLikeForm();
        if (nounLikeForm == NounLikeForm.PPP) {
            Adjective ppp = new Adjective(
                    pppRoot + adjective_nom_sg_masc_suffix,
                    pppRoot + adjective_nom_sg_fem_suffix,
                    pppRoot + adjective_nom_sg_neut_suffix,
                    Adjective.Kind.AO, pppRoot, new TranslationSequence(), "ppp", Adjective.AdjectiveDefinitionType.FROM_BASE_FORMS);
            AdjectiveForm adjectiveForm = new AdjectiveForm(form.getNounLikeDeclinedForm(), ComparativeForm.POSITIVE);
            return ppp.makeForm(adjectiveForm);
        } else {
            Document verbsDocument = (Document) Database.get().getVerbsDocument().get("noun_like_form_suffixes");
            if (nounLikeForm == NounLikeForm.PPA) {
                Document ppaDocument = (Document) verbsDocument.get("ppa");
                String nom_sg_sign = ppaDocument.getString("nom_sg_sign");
                String other_sign = ppaDocument.getString("other_sign");

                Pattern pattern = conjugationSchema.getNounLikeFormRootPattern(NounLikeForm.PPA);
                String root = pattern.make(info);

                DeclinedForm declinedForm = form.getNounLikeDeclinedForm();
                if (declinedForm.fits(new DeclinedForm(Casus.NOM, NNumber.SG))) {
                    return root + nom_sg_sign;
                } else {
                    DeclensionSchema cons_adjectives = Database.get().getDeclensionClasses().cons_adjectives_Declension();
                    String suffix = cons_adjectives.getSuffix(declinedForm);
                    return root + other_sign + suffix;
                }
            } else if (nounLikeForm == NounLikeForm.PFA) {
                Document pfaDocument = (Document) verbsDocument.get("pfa");
                String normal_sign = pfaDocument.getString("sign");
                String alt_sign = pfaDocument.getString("alt_sign");
                String alt_sign_ppp_root_ending = pfaDocument.getString("alt_sign_ppp_root_ending");

                Pattern pattern = conjugationSchema.getNounLikeFormRootPattern(NounLikeForm.PFA);
                String root = pattern.make(info);

                String sign = normal_sign;
                if (pppRoot.endsWith(alt_sign_ppp_root_ending)) {
                    sign = alt_sign;
                }

                Adjective pfa = new Adjective(
                        root + sign + adjective_nom_sg_masc_suffix,
                        root + sign + adjective_nom_sg_fem_suffix,
                        root + sign + adjective_nom_sg_neut_suffix,
                        Adjective.Kind.AO, root + sign, new TranslationSequence(), "ppp", Adjective.AdjectiveDefinitionType.FROM_BASE_FORMS);
                return pfa.makeForm(new AdjectiveForm(form.getNounLikeDeclinedForm(), ComparativeForm.POSITIVE));
            } else if (nounLikeForm == NounLikeForm.GERUNDIUM) {
                Document gerundiumDocument = (Document) verbsDocument.get("gerundium");
                String sign = gerundiumDocument.getString("sign");

                DeclinedForm declinedForm = form.getNounLikeDeclinedForm();
                if (declinedForm.getCasus() == Casus.DAT || declinedForm.getNumber() == NNumber.PL) throw new DeclinedFormDoesNotExistException(declinedForm);
                if (declinedForm.hasGender() && declinedForm.getGender() != Gender.MASC) throw new DeclinedFormDoesNotExistException(declinedForm);
                declinedForm.normalizeGender();

                Pattern pattern = conjugationSchema.getNounLikeFormRootPattern(NounLikeForm.GERUNDIUM);
                String root = pattern.make(info);

                if (declinedForm.fits(new DeclinedForm(Casus.NOM, NNumber.SG))) {
                    return infinitive;
                } else {
                    DeclensionSchema o_decl = Database.get().getDeclensionClasses().o_Declension();
                    String suffix = o_decl.getSuffix(declinedForm);
                    return root + sign + suffix;
                }
            } else if (nounLikeForm == NounLikeForm.GERUNDIVUM) {
                Document gerundivumDocument = (Document) verbsDocument.get("gerundivum");
                String sign = gerundivumDocument.getString("sign");

                Pattern pattern = conjugationSchema.getNounLikeFormRootPattern(NounLikeForm.GERUNDIVUM);
                String root = pattern.make(info);

                Adjective pfa = new Adjective(
                        root + sign + adjective_nom_sg_masc_suffix,
                        root + sign + adjective_nom_sg_fem_suffix,
                        root + sign + adjective_nom_sg_neut_suffix,
                        Adjective.Kind.AO, root + sign, new TranslationSequence(), "ppp", Adjective.AdjectiveDefinitionType.FROM_BASE_FORMS);
                return pfa.makeForm(new AdjectiveForm(form.getNounLikeDeclinedForm(), ComparativeForm.POSITIVE));
            }
        }
        return null;
    }

    public String makeFormOrDash(VerbForm form) {
        try {
            return makeForm(form);
        } catch (VerbFormDoesNotExistException | DeclinedFormDoesNotExistException e) {
            return "-";
        }
    }

    @Override
    public Kind getKind() {
        return Kind.VERB;
    }

    @Override
    public String getDefinition() {
        String first_sg_pres = makeFormOrDash(new VerbForm(new ConjugatedForm(Person.FIRST, CNumber.SG), Mode.INDICATIVE, Tense.PRESENT, Voice.ACTIVE));
        String first_sg_perfect = makeFormOrDash(new VerbForm(new ConjugatedForm(Person.FIRST, CNumber.SG), Mode.INDICATIVE, Tense.PERFECT, Voice.ACTIVE));
        String ppp = makeFormOrDash(new VerbForm(NounLikeForm.PPP, new DeclinedForm(Casus.NOM, NNumber.SG, Gender.NEUT)));
        return getBaseForm() + ", " + first_sg_pres + ", " + first_sg_perfect + ", " + ppp;
    }

    @Override
    public List<String> getAllFormsToString() {
        List<String> forms = new ArrayList<>();

        // Kind.IMPERATIVE
        for (CNumber number : CNumber.values()) {
            VerbForm form = new VerbForm(number);
            try {
                forms.add(makeForm(form));
            } catch (Exception ignored) {}
        }

        // Kind.INFINITIVE
        for (InfinitiveTense infinitiveTense : InfinitiveTense.values()) {
            for (Voice voice : Voice.values()) {
                VerbForm form = new VerbForm(infinitiveTense, voice);
                try {
                    forms.add(makeForm(form));
                } catch (Exception ignored) {}
            }
        }

        // Kind.BASIC
        for (Voice voice : Voice.values()) {
            for (Tense tense : Tense.values()) {
                for (Mode mode : Mode.values()) {
                    for (CNumber number : CNumber.values()) {
                        for (Person person : Person.values()) {
                            VerbForm form = new VerbForm(new ConjugatedForm(person, number), mode, tense, voice);
                            try {
                                forms.add(makeForm(form));
                            } catch (Exception ignored) {}
                        }
                    }
                }
            }
        }

        // Kind.NOUN_LIKE
        for (NounLikeForm nounLikeForm : NounLikeForm.values()) {
            for (Gender gender : Gender.values()) {
                for (NNumber number : NNumber.values()) {
                    for (Casus casus : Casus.values()) {
                        VerbForm form = new VerbForm(nounLikeForm, new DeclinedForm(casus, number, gender));
                        try {
                            forms.add(makeForm(form));
                        } catch (Exception ignored) {}
                    }
                }
            }
        }

        return forms;
    }

    public String getConjugationSchema() {
        return conjugationSchema.getName();
    }

    public List<VerbForm> identifyForm(String word, boolean partialSearch) {
        List<VerbForm> forms = new ArrayList<>();

        // Kind.INFINITIVE
        for (InfinitiveTense infinitiveTense : InfinitiveTense.values()) {
            for (Voice voice : Voice.values()) {
                VerbForm form = new VerbForm(infinitiveTense, voice);
                try {
                    if (partialSearch) {
                        if (makeForm(form).contains(word)) forms.add(form);
                    } else {
                        if (makeForm(form).equalsIgnoreCase(word)) forms.add(form);
                    }
                } catch (VerbFormDoesNotExistException | DeclinedFormDoesNotExistException ignored) {}
            }
        }

        // Kind.BASIC
        for (Voice voice : Voice.values()) {
            for (Tense tense : Tense.values()) {
                for (Mode mode : Mode.values()) {
                    for (CNumber number : CNumber.values()) {
                        for (Person person : Person.values()) {
                            VerbForm form = new VerbForm(new ConjugatedForm(person, number), mode, tense, voice);
                            try {
                                if (partialSearch) {
                                    if (makeForm(form).contains(word)) forms.add(form);
                                } else {
                                    if (makeForm(form).equalsIgnoreCase(word)) forms.add(form);
                                }
                            } catch (VerbFormDoesNotExistException | DeclinedFormDoesNotExistException ignored) {}
                        }
                    }
                }
            }
        }

        // Kind.NOUN_LIKE
        for (NounLikeForm nounLikeForm : NounLikeForm.values()) {
            for (Gender gender : Gender.values()) {
                for (NNumber number : NNumber.values()) {
                    for (Casus casus : Casus.values()) {
                        VerbForm form = new VerbForm(nounLikeForm, new DeclinedForm(casus, number, gender));
                        try {
                            if (partialSearch) {
                                if (makeForm(form).contains(word)) forms.add(form);
                            } else {
                                if (makeForm(form).equalsIgnoreCase(word)) forms.add(form);
                            }
                        } catch (VerbFormDoesNotExistException | DeclinedFormDoesNotExistException ignored) {}
                    }
                }
            }
        }

        forms.sort(VerbForm.comparator());
        return forms;
    }
}