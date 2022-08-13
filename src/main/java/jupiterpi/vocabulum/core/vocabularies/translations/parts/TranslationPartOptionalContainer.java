package jupiterpi.vocabulum.core.vocabularies.translations.parts;

public class TranslationPartOptionalContainer extends TranslationPartContainer {
    /*public TranslationPartOptionalContainer(List<TranslationPart> parts) {
        super(parts);
    }

    public TranslationPartOptionalContainer(TranslationPart... parts) {
        super(parts);
    }

    @Override
    public String getBasicString() {
        return "(" + super.getBasicString() + ")";
    }

    @Override
    public Pattern getRegex() {
        String regex = super.getRegex().pattern();
        return Pattern.compile("(\\(" + regex + "\\)|" + regex + ")?");
    }*/

    /*@Override
    public boolean isCorrect(String string) {
        List<String> stringParts = List.of(string.split(" "));
        for (TranslationPart part : parts) {
            boolean correct = false;
            for (int i = 1; i < stringParts.size(); i++) {
                String partsString = String.join(" ", stringParts.subList(0, 1));
                if (part.isCorrect(partsString)) {
                    correct = true;
                    stringParts = stringParts.subList(i, stringParts.size());
                    break;
                }
            }
            if (!correct) return false;
        }
        return true;
    }*/
}
