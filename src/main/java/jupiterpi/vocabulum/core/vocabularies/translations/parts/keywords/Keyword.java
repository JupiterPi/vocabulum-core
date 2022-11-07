package jupiterpi.vocabulum.core.vocabularies.translations.parts.keywords;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Keyword {
    private String primaryKeyword;
    private List<String> secondaryKeywords;
    private boolean optional;

    public Keyword(String primaryKeyword, List<String> secondaryKeywords, boolean optional) {
        this.primaryKeyword = primaryKeyword;
        this.secondaryKeywords = secondaryKeywords;
        this.optional = optional;
    }

    public static List<Keyword> fromDocuments(List<Document> keywordDocuments) {
        List<Keyword> keywords = new ArrayList<>();
        for (Document document : keywordDocuments) {
            String primaryKeyword = document.getString("primaryKeyword");
            List<String> secondaryKeywords = document.getList("secondaryKeywords", String.class);
            boolean optional = document.getBoolean("optional");
            keywords.add(new Keyword(primaryKeyword, secondaryKeywords, optional));
        }
        return keywords;
    }

    /* getters */

    public String getPrimaryKeyword() {
        return primaryKeyword;
    }

    public List<String> getSecondaryKeywords() {
        return secondaryKeywords;
    }

    public List<String> getAllKeywords() {
        ArrayList<String> keywords = new ArrayList<>();
        keywords.add(getPrimaryKeyword());
        keywords.addAll(getSecondaryKeywords());
        return keywords;
    }

    public boolean isOptional() {
        return optional;
    }

    /* accessors */

    public boolean matches(String str) {
        return getAllKeywords().contains(str);
    }
}