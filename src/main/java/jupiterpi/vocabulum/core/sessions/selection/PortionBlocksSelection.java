package jupiterpi.vocabulum.core.sessions.selection;

import jupiterpi.vocabulum.core.db.portions.Portion;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;

import java.util.ArrayList;
import java.util.List;

public class PortionBlocksSelection implements StringifiableVocabularySelection {
    private Portion portion;
    private List<Integer> blocks;

    public PortionBlocksSelection(Portion portion, List<Integer> blocks) {
        this.portion = portion;
        this.blocks = blocks;
    }

    @Override
    public List<Vocabulary> getVocabularies() {
        List<List<Vocabulary>> vocabularyBlocks = portion.getVocabularyBlocks();
        List<Vocabulary> vocabularies = new ArrayList<>();
        for (int block : blocks) {
            vocabularies.addAll(vocabularyBlocks.get(block));
        }
        return vocabularies;
    }

    @Override
    public String getString() {
        String portionName = PortionBasedVocabularySelectionParser.NON_NUMBER_PORTION_NAME_TOKEN + portion.getName() + PortionBasedVocabularySelectionParser.NON_NUMBER_PORTION_NAME_TOKEN;
        try {
            Integer.parseInt(portion.getName());
            portionName = portion.getName();
        } catch (NumberFormatException ignored) {}
        if (blocks.size() < portion.getVocabularyBlocks().size()) {
            List<String> blockStrs = new ArrayList<>();
            for (Integer block : blocks) {
                blockStrs.add(Integer.toString(block + 1));
            }
            return portionName + PortionBasedVocabularySelectionParser.BLOCKS_TOKEN + String.join(PortionBasedVocabularySelectionParser.BLOCKS_SEPARATOR_TOKEN, blockStrs);
        } else {
            return portionName;
        }
    }
}