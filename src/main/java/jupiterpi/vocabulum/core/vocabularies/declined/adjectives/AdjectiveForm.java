package jupiterpi.vocabulum.core.vocabularies.declined.adjectives;

import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;

public class AdjectiveForm {
    private boolean adverb;
    private DeclinedForm declinedForm;
    private ComparativeForm comparativeForm;

    public AdjectiveForm(boolean adverb, DeclinedForm declinedForm, ComparativeForm comparativeForm) {
        this.adverb = adverb;
        this.declinedForm = declinedForm;
        this.comparativeForm = comparativeForm;
    }

    public AdjectiveForm(DeclinedForm declinedForm, ComparativeForm comparativeForm) {
        this.adverb = false;
        this.declinedForm = declinedForm;
        this.comparativeForm = comparativeForm;
    }

    public AdjectiveForm(boolean adverb, ComparativeForm comparativeForm) {
        this.adverb = adverb;
        this.declinedForm = null;
        this.comparativeForm = comparativeForm;
    }

    public boolean isAdverb() {
        return adverb;
    }

    public DeclinedForm getDeclinedForm() {
        return declinedForm;
    }

    public ComparativeForm getComparativeForm() {
        return comparativeForm;
    }

    @Override
    public String toString() {
        String adverbDeclinedFormStr = adverb ? "adverb" : "form=" + declinedForm.toString();
        return "{Noun" + adverbDeclinedFormStr + ",comparative=" + comparativeForm.toString().toLowerCase() + "}";
    }
}