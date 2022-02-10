package jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form;

import java.util.Objects;

public class DeclinedForm {
    private Casus casus;
    private Number number;
    private Gender gender;

    public DeclinedForm(Casus casus, Number number, Gender gender) {
        this.casus = casus;
        this.number = number;
        this.gender = gender;
    }

    public Casus getCasus() {
        return casus;
    }

    public Number getNumber() {
        return number;
    }

    public Gender getGender() {
        return gender;
    }

    public boolean isCasus(Casus casus) {
        return this.casus == casus;
    }

    public boolean isNumber(Number number) {
        return this.number == number;
    }

    public boolean isGender(Gender gender) {
        return this.gender == gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeclinedForm that = (DeclinedForm) o;
        return casus == that.casus && number == that.number && gender == that.gender;
    }
}