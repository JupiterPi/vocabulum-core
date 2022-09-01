package jupiterpi.vocabulum.core.vocabularies.translations.parts.container;

import jupiterpi.vocabulum.core.vocabularies.translations.parts.TranslationPart;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        return new TranslationPartContainerParser(optional, string).getTranslationPartContainer();
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
        String regex = String.join(" *", regexes);
        regex = " *" + regex + " *";
        if (optional) {
            return "(\\(" + regex + "\\)|" + regex + ")?";
        } else {
            return regex;
        }
    }

    @Override
    public String getNonNullRegex() {
        List<String> regexes = new ArrayList<>();
        for (TranslationPart part : parts) {
            regexes.add(part.getNonNullRegex());
        }
        return String.join(" *", regexes);
    }

    //TODO account for edge cases
    public List<InputMatchedPart> matchValidInput(String input) {
        List<InputMatchedPart> inputMatchedParts = new ArrayList<>();
        String remainingInput = input;
        for (TranslationPart part : getParts()) {
            boolean isContainer = part instanceof TranslationPartContainer;
            Matcher matcher = Pattern.compile((isContainer ? part.getRegex() : part.getNonNullRegex())).matcher(remainingInput);
            if (matcher.find()) {
                String inputPart = remainingInput.substring(matcher.start(), matcher.end());
                remainingInput = remainingInput.substring(0, matcher.start()) + remainingInput.substring(matcher.end());
                if (isContainer) {
                    TranslationPartContainer container = (TranslationPartContainer) part;
                    inputMatchedParts.add(new InputMatchedPart("("));
                    inputMatchedParts.addAll(container.matchValidInput(inputPart));
                    inputMatchedParts.add(new InputMatchedPart(")"));
                } else {
                    inputMatchedParts.add(new InputMatchedPart(part, true, inputPart));
                }
            } else {
                inputMatchedParts.add(new InputMatchedPart(part, false, ""));
            }
            inputMatchedParts.add(new InputMatchedPart(" "));
        }
        inputMatchedParts.remove(inputMatchedParts.size()-1);
        return inputMatchedParts;
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
