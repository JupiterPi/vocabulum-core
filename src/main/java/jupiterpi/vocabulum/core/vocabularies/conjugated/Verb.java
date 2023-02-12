package jupiterpi.vocabulum.core.vocabularies.conjugated;

import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.*;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;

import java.util.ArrayList;
import java.util.List;

public abstract class Verb extends Vocabulary {
    protected Verb(TranslationSequence translations, String portion) {
        super(translations, portion);
    }

    public abstract String makeForm(VerbForm form) throws VerbFormDoesNotExistException, DeclinedFormDoesNotExistException;

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

    public abstract String getBaseForm();

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

    public abstract String getConjugationSchema();

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