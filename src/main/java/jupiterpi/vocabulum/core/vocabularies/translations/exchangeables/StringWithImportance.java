package jupiterpi.vocabulum.core.vocabularies.translations.exchangeables;

public class StringWithImportance {
    private String string;
    private boolean important;

    public StringWithImportance(String string, boolean important) {
        this.string = string;
        this.important = important;
    }

    /* from/to string */

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

    /* getters, setters */

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }
}
