package jupiterpi.vocabulum.core.vocabularies.conjugated.schemas;

public class FormInfo {
    public enum Root {
        PRESENT, PERFECT
    }

    private Root root;
    private String suffix;

    public FormInfo(Root root, String suffix) {
        this.root = root;
        this.suffix = suffix;
    }

    public Root getRoot() {
        return root;
    }

    public String getSuffix() {
        return suffix;
    }
}