package jupiterpi.vocabulum.core.sessions.selection;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.portions.Portion;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;

import java.util.ArrayList;
import java.util.List;

/**
 * 37 -> Portion 37
 * <p>
 * 'A' -> Portion A (for non-number names)
 * <p>
 * 37+38 -> Portion 37 and portion 38 are joined
 * <p>
 * 37_1,2 -> Blocks 1 (index 0) and 2 (index 1) of portion 37 are joined
 * <p>
 * 37+et -> Portion 37 and "et"
 * <p>
 * 37-et -> Portion 37 without "et"
 * <p>
 * 37_1,2-agere -> Blocks 1 and 2 of portion 37 without "agere"
 */
public class PortionBasedVocabularySelection implements StringifiableVocabularySelection {
    private List<Part> parts;
    static class Part {
        boolean subtract;
        VocabularySelection selection;

        public Part(boolean subtract, VocabularySelection selection) {
            this.subtract = subtract;
            this.selection = selection;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Part part = (Part) o;
            return subtract == part.subtract && VocabularySelections.equal(selection, part.selection);
        }
    }

    public PortionBasedVocabularySelection(List<Part> parts) {
        this.parts = parts;
    }

    /* from string */

    private PortionBasedVocabularySelection() {}
    public static PortionBasedVocabularySelection fromString(String str) {
        return new PortionBasedVocabularySelectionParser(str) {
            @Override
            protected Vocabulary retrieveVocabulary(String base_form) {
                return Database.get().getPortions().getVocabularyInPortion(base_form);
            }

            @Override
            protected Portion retrievePortion(String name) {
                return Database.get().getPortions().getPortion(name);
            }
        }.getPortionBasedVocabularySelection();
    }

    /* from generalized VocabularySelection */

    public static PortionBasedVocabularySelection fromVocabularySelection(VocabularySelection vocabularySelection) {
        List<Part> parts = new ArrayList<>();
        for (Vocabulary vocabulary : vocabularySelection.getVocabularies()) {
            parts.add(new Part(false, new SingleVocabulary(vocabulary)));
        }
        return new PortionBasedVocabularySelection(parts);
    }

    /* VocabularySelection */

    @Override
    public List<Vocabulary> getVocabularies() {
        List<Vocabulary> vocabularies = new ArrayList<>();
        for (Part part : parts) {
            if (part.subtract) {
                vocabularies.removeAll(part.selection.getVocabularies());
            } else {
                vocabularies.addAll(part.selection.getVocabularies());
            }
        }
        return vocabularies;
    }

    /* toString */

    @Override
    public String getString() {
        String str = "";
        for (int i = 0; i < parts.size(); i++) {
            Part part = parts.get(i);
            if (i > 0 || part.subtract) str += part.subtract ? PortionBasedVocabularySelectionParser.SUBTRACT_TOKEN : PortionBasedVocabularySelectionParser.JOIN_TOKEN;
            str += VocabularySelections.getPortionBasedString(part.selection);
        }
        return str;
    }

    public String toString() {
        return getString();
    }
}