package jupiterpi.vocabulum.core.vocabularies.formresult;

import java.util.List;

public class FormResult {
    private boolean exists;
    private String primaryForm;
    private String secondaryForm;

    public FormResult(boolean exists, String primaryForm, String secondaryForm) {
        this.exists = exists;
        this.primaryForm = primaryForm;
        this.secondaryForm = secondaryForm;
    }

    public static FormResult doesNotExist() {
        return new FormResult(false, null, null);
    }

    public static FormResult withPrimaryForm(String primaryForm) {
        return new FormResult(true, primaryForm, null);
    }

    public static FormResult withSecondaryForm(String primaryForm, String secondaryForm) {
        return new FormResult(true, primaryForm, secondaryForm);
    }

    /* getters, accessors */

    public boolean exists() {
        return exists;
    }

    public String getPrimaryForm() {
        return primaryForm;
    }

    public String getSecondaryForm() {
        return secondaryForm;
    }

    public boolean hasSecondaryForm() {
        return secondaryForm != null;
    }

    public List<String> getAllForms() {
        if (hasSecondaryForm()) {
            return List.of(primaryForm, secondaryForm);
        } else {
            return List.of(primaryForm);
        }
    }

    /* string representation */

    public String getString() {
        if (!exists) return "-";
        String str = primaryForm;
        if (hasSecondaryForm()) {
            str += "/" + secondaryForm;
        }
        return str;
    }

    public static FormResult fromString(String str) {
        if (str.equals("-")) return doesNotExist();
        if (str.contains("/")) {
            String[] parts = str.split("/");
            return withSecondaryForm(parts[0], parts[1]);
        } else {
            return withPrimaryForm(str);
        }
    }
}