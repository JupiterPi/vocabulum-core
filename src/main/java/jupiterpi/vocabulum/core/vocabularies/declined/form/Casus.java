package jupiterpi.vocabulum.core.vocabularies.declined.form;

public enum Casus {
    NOM, // Nominative
    GEN, // Genitive
    DAT, // Dative
    ACC, // Accusative
    ABL, // Ablative
    VOC; // Vocative

    public static Casus[] standardCasus() {
        return new Casus[]{NOM, GEN, DAT, ACC, ABL};
    }
}