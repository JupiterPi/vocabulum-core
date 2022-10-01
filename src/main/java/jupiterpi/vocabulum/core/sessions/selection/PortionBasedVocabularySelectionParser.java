package jupiterpi.vocabulum.core.sessions.selection;

import jupiterpi.vocabulum.core.db.portions.Portion;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;

import java.util.ArrayList;
import java.util.List;

public abstract class PortionBasedVocabularySelectionParser {
    private PortionBasedVocabularySelection portionBasedVocabularySelection;

    public PortionBasedVocabularySelectionParser(String str) {
        portionBasedVocabularySelection = new PortionBasedVocabularySelection(parseParts(str));
    }

    public PortionBasedVocabularySelection getPortionBasedVocabularySelection() {
        return portionBasedVocabularySelection;
    }

    /* parser */

    private List<PortionBasedVocabularySelection.Part> parts = new ArrayList<>();

    private String buffer = "";
    private boolean subtract;

    private List<PortionBasedVocabularySelection.Part> parseParts(String str) {
        for (String c : str.split("")) {
            if (c.equals("-") || c.equals("+")) {
                flushBuffer();
                subtract = c.equals("-");
                continue;
            }
            buffer += c;
        }
        flushBuffer();
        return parts;
    }

    private void flushBuffer() {
        if (buffer.isEmpty()) return;
        VocabularySelection selection;

        String[] parts = buffer.split("_");
        String basePart = parts[0];

        boolean isPortion = false;
        String portionName = null;
        if (basePart.contains("'")) {
            portionName = basePart.split("'")[1];
            isPortion = true;
        }
        try {
            int number = Integer.parseInt(basePart);
            portionName = Integer.toString(number);
            isPortion = true;
        } catch (NumberFormatException e) {}

        if (isPortion) {
            Portion portion = retrievePortion(portionName);
            List<Integer> blocks = new ArrayList<>();

            if (parts.length > 1) {
                String blocksPart = parts[1];
                for (String blockStr : blocksPart.split(",")) {
                    blocks.add(Integer.parseInt(blockStr) - 1);
                }
            } else {
                for (int i = 0; i < portion.getVocabularyBlocks().size(); i++) {
                    blocks.add(i);
                }
            }

            selection = new PortionBlocksSelection(portion, blocks);
        } else {
            Vocabulary vocabulary = retrieveVocabulary(basePart);
            selection = new SingleVocabulary(vocabulary);
        }

        this.parts.add(new PortionBasedVocabularySelection.Part(subtract, selection));
        buffer = "";
        subtract = false;
    }

    protected abstract Vocabulary retrieveVocabulary(String base_form);

    protected abstract Portion retrievePortion(String name);
}
