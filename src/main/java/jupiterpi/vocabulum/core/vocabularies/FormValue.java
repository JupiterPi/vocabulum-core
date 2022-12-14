package jupiterpi.vocabulum.core.vocabularies;

public class FormValue {
    private String primary;
    private String secondary;

    /* constructors */

    // doesn't exist
    public FormValue(String primary, String secondary) {
        this.primary = primary;
        this.secondary = secondary;
    }

    // only primary
    public FormValue(String primary) {
        this.primary = primary;
        this.secondary = null;
    }

    // has secondary
    public FormValue() {
        this.primary = null;
        this.secondary = null;
    }

    /* getters */

    public String getPrimary() {
        return primary;
    }

    public String getSecondary() {
        return secondary;
    }

    /* getters: check for type */

    public boolean exists() {
        return primary != null;
    }

    public boolean hasSecondary() {
        return secondary != null;
    }

    /* string representation */

    @Override
    public String toString() {
        if (!exists()) return "-";
        if (!hasSecondary()) return primary;
        return primary + "/" + secondary;
    }

    public static FormValue fromString(String str) {
        if (str.equals("-")) return new FormValue();
        if (str.contains("/")) {
            String[] parts = str.split("/");
            return new FormValue(parts[0], parts[1]);
        }
        return new FormValue(str);
    }
}