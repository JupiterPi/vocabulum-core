package jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form;

public class Genders {
    public static Gender fromSymbol(String symbol) {
        return switch (symbol) {
            case "m" -> Gender.MASC;
            case "f" -> Gender.FEM;
            case "n" -> Gender.NEUT;
            default -> null;
        };
    }
}
