package jupiterpi.vocabulum.core.portions;

import jupiterpi.tools.util.AppendingList;
import jupiterpi.vocabulum.core.Main;
import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.i18n.I18nException;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Portion {
    private String name;
    private int lesson;
    private int part;
    private List<Vocabulary> vocabularies = new ArrayList<>();

    private Portion() {}
    public static Portion readFromDocument(Document document) throws ParserException, DeclinedFormDoesNotExistException, I18nException, LexerException {
        Portion portion = new Portion();

        String nameStr = document.getString("name");
        portion.name = nameStr;
        if (nameStr.matches("..-.")) {
            String[] nameStrParts = nameStr.split("-");
            portion.lesson = Integer.parseInt(nameStrParts[0]);
            portion.part = Integer.parseInt(nameStrParts[1]);
        }

        I18n i18n = Main.i18nManager.get(document.getString("i18n"));
        List<String> vocabularies = (List<String>) document.get("vocabularies");
        for (String vocabulary : vocabularies) {
            portion.vocabularies.add(Vocabulary.fromString(vocabulary, i18n));
        }

        return portion;
    }

    public String getName() {
        return name;
    }

    public int getLesson() {
        return lesson;
    }

    public int getPart() {
        return part;
    }

    public List<Vocabulary> getVocabularies() {
        return vocabularies;
    }

    @Override
    public String toString() {
        AppendingList vocabulariesStr = new AppendingList();
        for (Vocabulary vocabulary : vocabularies) {
            vocabulariesStr.add(vocabulary.toString());
        }
        return "Portion{name=" + name + ",vocabularies=[" + vocabulariesStr.render(",") + "]}";
    }
}