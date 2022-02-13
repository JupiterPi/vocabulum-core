package jupiterpi.vocabulum.core.vocabularies.declinated.nouns;

import jupiterpi.vocabulum.core.vocabularies.declinated.form.DeclinedForm;

public class NounForm {
    private DeclinedForm declinedForm;

    public NounForm(DeclinedForm declinedForm) {
        this.declinedForm = declinedForm;
    }

    public DeclinedForm getDeclinedForm() {
        return declinedForm;
    }

    @Override
    public String toString() {
        return "Noun{form=" + declinedForm.toString() + "}";
    }
}