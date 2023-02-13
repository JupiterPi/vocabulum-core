package jupiterpi.vocabulum.core.db.portions;

import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.sessions.selection.VocabularySelection;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection ("portion") of vocabularies.
 * The vocabularies are divided into blocks (similar to the textbook).
 */
public class Portion implements VocabularySelection {
    private String name;
    private List<List<Vocabulary>> vocabularyBlocks = new ArrayList<>();

    public Portion(String name, List<List<Vocabulary>> vocabularyBlocks) {
        this.name = name;
        this.vocabularyBlocks = vocabularyBlocks;
    }

    private Portion() {}
    public static Portion readFromVocabularyStrBlocks(String name, List<List<String>> vocabularyStrBlocks) throws ParserException, DeclinedFormDoesNotExistException, LexerException, VerbFormDoesNotExistException {
        Portion portion = new Portion();
        portion.name = name;

        List<List<Vocabulary>> vocabularyBlocks = new ArrayList<>();
        for (List<String> block : vocabularyStrBlocks) {
            List<Vocabulary> vocabularies = new ArrayList<>();
            for (String vocabularyStr : block) {
                vocabularies.add(Vocabulary.fromString(vocabularyStr, name));
            }
            vocabularyBlocks.add(vocabularies);
        }
        portion.vocabularyBlocks = vocabularyBlocks;

        return portion;
    }

    /**
     * @return the name of the portion (e. g. "1")
     */
    public String getName() {
        return name;
    }

    /**
     * @return the vocabulary blocks
     */
    public List<List<Vocabulary>> getVocabularyBlocks() {
        return vocabularyBlocks;
    }

    /**
     * @return the vocabularies from all blocks
     */
    public List<Vocabulary> getVocabularies() {
        List<Vocabulary> vocabularies = new ArrayList<>();
        for (List<Vocabulary> block : vocabularyBlocks) {
            vocabularies.addAll(block);
        }
        return vocabularies;
    }

    @Override
    public String toString() {
        List<String> blockStrs = new ArrayList<>();
        for (List<Vocabulary> block : getVocabularyBlocks()) {
            List<String> vocabularies = new ArrayList<>();
            block.forEach((vocabulary -> vocabularies.add(vocabulary.toString())));
            blockStrs.add(String.join(",", vocabularies));
        }
        String blocksStr = String.join(" // ", blockStrs);
        return "Portion{name=" + name + ",vocabularies=[" + blocksStr + "]}";
    }
}