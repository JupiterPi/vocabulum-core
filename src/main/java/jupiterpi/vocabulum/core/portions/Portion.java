package jupiterpi.vocabulum.core.portions;

import jupiterpi.tools.util.AppendingList;
import jupiterpi.vocabulum.core.Main;
import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.i18n.I18nException;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Portion {
    private String name;
    private List<List<Vocabulary>> vocabularyBlocks = new ArrayList<>();

    private Portion() {}
    public static Portion readFromDocument(Document document) throws ParserException, DeclinedFormDoesNotExistException, I18nException, LexerException, VerbFormDoesNotExistException {
        Portion portion = new Portion();

        String name = document.getString("name");
        portion.name = name;

        I18n i18n = Main.i18nManager.get(document.getString("i18n"));
        List<List<String>> vocabularyBlocks = (List<List<String>>) document.get("vocabularies");
        for (List<String> vocabularies : vocabularyBlocks) {
            List<Vocabulary> vocabularyBlock = new ArrayList<>();
            for (String vocabulary : vocabularies) {
                vocabularyBlock.add(Vocabulary.fromString(vocabulary, i18n, name));
            }
            portion.vocabularyBlocks.add(vocabularyBlock);
        }

        return portion;
    }

    public String getName() {
        return name;
    }

    public List<List<Vocabulary>> getVocabularyBlocks() {
        return vocabularyBlocks;
    }

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
        AppendingList list = new AppendingList();
        list.addAll(blockStrs);
        String blocksStr = list.render(" // ");
        return "Portion{name=" + name + ",vocabularies=[" + blocksStr + "]}";
    }
}