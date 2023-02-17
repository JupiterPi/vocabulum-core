package jupiterpi.vocabulum.core.db;

import jupiterpi.vocabulum.core.util.TextFile;
import org.bson.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Resources {
    private static Resources instance = null;
    public static Resources get() {
        if (instance == null) {
            instance = new Resources();
        }
        return instance;
    }
    protected static void inject(Resources instance) {
        Resources.instance = instance;
    }

    /////

    // single documents

    private Document adjectivesDocument;
    public Document getAdjectivesDocument() {
        if (adjectivesDocument == null) {
            adjectivesDocument = TextFile.readJsonResourceFile("adjectives.json");
        }
        return adjectivesDocument;
    }

    private Document verbsDocument;
    public Document getVerbsDocument() {
        if (verbsDocument == null) {
            verbsDocument = TextFile.readJsonResourceFile("verbs.json");
        }
        return verbsDocument;
    }

    private Document translationsDocument;
    public Document getTranslationsDocument() {
        if (translationsDocument == null) {
            translationsDocument = TextFile.readJsonResourceFile("translations.json");
        }
        return translationsDocument;
    }

    // declension classes

    private List<Document> declensionClasses;
    public List<Document> getDeclensionClasses() {
        if (declensionClasses == null) {
            List<String> declensionClassesFiles = List.of(
                    "a_decl.json",
                    "cons_adjectives_decl.json",
                    "cons_decl.json",
                    "e_decl.json",
                    "o_decl.json",
                    "u_decl.json"
            );
            declensionClasses = declensionClassesFiles.stream()
                    .map(declensionClassFile -> TextFile.readJsonResourceFile("declension_schemas/" + declensionClassFile))
                    .collect(Collectors.toList());
        }
        return declensionClasses;
    }

    // conjugation classes

    private List<Document> conjugationClasses;
    public List<Document> getConjugationClasses() {
        if (conjugationClasses == null) {
            List<String> conjugationClassesFiles = List.of(
                    "a_conj.json"
            );
            conjugationClasses = conjugationClassesFiles.stream()
                    .map(conjugationClassFile -> TextFile.readJsonResourceFile("conjugation_schemas/" + conjugationClassFile))
                    .collect(Collectors.toList());
        }
        return conjugationClasses;
    }

    // portions

    private Map<String, List<List<String>>> portions;
    public Map<String, List<List<String>>> getPortions() {
        if (portions == null) {
            portions = new HashMap<>();
            for (Document document : TextFile.readJsonResourceFile("portions.json").getList("portions", Document.class)) {
                String name = document.getString("name");
                String file = TextFile.readResourceFile("portions/" + document.getString("file")).getFile();
                List<String> blocksStr = List.of(file.split("\n\n"));
                List<List<String>> vocabularyStrBlocks = blocksStr.stream()
                        .map(blockStr -> List.of(blockStr.split("\n")))
                        .toList();
                portions.put(name, vocabularyStrBlocks);
            }
        }
        return portions;
    }

    // lectures

    private Map<String, List<String>> lectures;
    public Map<String, List<String>> getLectures() {
        if (lectures == null) {
            lectures = new HashMap<>();
            for (Document document : TextFile.readJsonResourceFile("lectures.json").getList("lectures", Document.class)) {
                String name = document.getString("name");
                List<String> lines = TextFile.readResourceFile("lectures/" + document.getString("file")).getLines();
                lectures.put(name, lines);
            }
        }
        return lectures;
    }
}