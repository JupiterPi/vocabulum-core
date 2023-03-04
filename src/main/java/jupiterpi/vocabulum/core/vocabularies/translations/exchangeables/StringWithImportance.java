package jupiterpi.vocabulum.core.vocabularies.translations.exchangeables;

public record StringWithImportance(
        String string,
        boolean important
) {
    public static StringWithImportance fromString(String string) {
        boolean important = string.startsWith("*");
        if (important) {
            string = string.substring(1, string.length()-1);
        }
        return new StringWithImportance(string, important);
    }

    public String toString() {
        String string = this.string;
        if (important) string = "*" + string + "*";
        return string;
    }
}
