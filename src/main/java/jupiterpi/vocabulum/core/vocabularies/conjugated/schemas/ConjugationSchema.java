package jupiterpi.vocabulum.core.vocabularies.conjugated.schemas;

public abstract class ConjugationSchema {
    protected String name;

    protected ConjugationSchema(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    //TODO implement
}
