package jupiterpi.vocabulum.core.vocabularies.translations.parts;

import jupiterpi.vocabulum.core.db.Database;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TranslationPartContainer extends TranslationPart {
    private boolean optional;
    private List<TranslationPart> parts;

    public TranslationPartContainer(boolean optional, List<TranslationPart> parts) {
        this.optional = optional;
        this.parts = parts;
    }

    public TranslationPartContainer(TranslationPart... parts) {
        this.optional = false;
        this.parts = List.of(parts);
    }

    public List<TranslationPart> getParts() {
        return parts;
    }

    /* parser */

    public static TranslationPartContainer fromString(boolean optional, String string) {
        List<TranslationPart> parts = new ArrayList<>();
        for (String partString : string.split(" ")) {
            TranslationPart part;
            if (partString.startsWith("(") && partString.endsWith(")")) {
                partString = partString.substring(1, partString.length()-1);
                part = TranslationPartContainer.fromString(true, partString);
            } else if (partString.equals("...")) {
                part = new DotsPart();
            } else if (Database.get().getTranslationsDocument().getList("articles", String.class).contains(partString)) {
                part = new ArticlePart(partString);
            } else {
                part = new PlainTextPart(partString);
            }
            parts.add(part);
        }
        return new TranslationPartContainer(optional, parts);
    }

    /* extends TranslationPart */

    @Override
    public String getBasicString() {
        List<String> partStrings = new ArrayList<>();
        for (TranslationPart part : parts) {
            partStrings.add(part.getBasicString());
        }
        String basicString = String.join(" ", partStrings);
        if (optional) {
            return "(" + basicString + ")";
        } else {
            return basicString;
        }
    }

    @Override
    public String getRegex() {
        List<String> regexes = new ArrayList<>();
        for (TranslationPart part : parts) {
            regexes.add(part.getRegex());
        }
        String partsRegex = String.join(" ", regexes);
        if (optional) {
            return "(\\(" + partsRegex + "\\)|" + partsRegex + ")?";
        } else {
            return partsRegex;
        }
    }

    /* equals */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TranslationPartContainer container = (TranslationPartContainer) o;
        return optional == container.optional && Objects.equals(parts, container.parts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(optional, parts);
    }
}
