package jupiterpi.vocabulum.core.vocabularies.declined.form;

import jupiterpi.vocabulum.core.i18n.I18n;

public class DeclinedFormAspects {
    public static Casus casusFromString(String str, I18n i18n) {
        for (Casus casus : Casus.values()) {
            if (i18n.getString(casus).equals(str)) return casus;
        }
        new Exception("casus: " + str).printStackTrace();
        return null;
    }

    public static Number numberFromString(String str, I18n i18n) {
        for (Number number : Number.values()) {
            if (i18n.getString(number).equals(str)) return number;
        }
        new Exception("number: " + str).printStackTrace();
        return null;
    }

    public static Gender genderFromString(String str, I18n i18n) {
        for (Gender gender : Gender.values()) {
            if (i18n.getString(gender).equals(str)) return gender;
        }
        new Exception("gender: " + str).printStackTrace();
        return null;
    }
}