package jupiterpi.vocabulum.core.vocabularies.translations.parts.container;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.vocabularies.translations.parts.ArticlePart;
import jupiterpi.vocabulum.core.vocabularies.translations.parts.DotsPart;
import jupiterpi.vocabulum.core.vocabularies.translations.parts.PlainTextPart;
import jupiterpi.vocabulum.core.vocabularies.translations.parts.TranslationPart;
import jupiterpi.vocabulum.core.vocabularies.translations.parts.keywords.Keyword;
import jupiterpi.vocabulum.core.vocabularies.translations.parts.keywords.KeywordPart;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class TranslationPartContainerParser {
    private TranslationPartContainer translationPartContainer;

    public TranslationPartContainerParser(boolean optional, String str) {
        this.translationPartContainer = parseTranslationPartContainer(optional, str);
    }

    public TranslationPartContainer getTranslationPartContainer() {
        return translationPartContainer;
    }

    /* parser */

    private List<TranslationPart> parts = new ArrayList<>();

    private TranslationPartContainer parseTranslationPartContainer(boolean optional, String string) {
        parseString(string);
        handleSpecialMeaningIndicators();
        return new TranslationPartContainer(optional, parts);
    }

    /* parse string */

    private final List<Keyword> keywords = Keyword.fromDocuments((List<Document>) Database.get().getTranslationsDocument().get("keywords"));

    private String buffer = "";
    private boolean inParens = false;

    private void parseString(String string) {
        for (String c : string.split("")) {
            if (inParens) {
                if (c.equals(")")) {
                    parts.add(TranslationPartContainer.fromString(true, buffer));
                    buffer = "";
                    flushBuffer();
                    inParens = false;
                } else {
                    buffer += c;
                }
                continue;
            }

            if (c.equals("(")) {
                flushBuffer();
                inParens = true;
                continue;
            }

            if (c.isBlank()) {
                flushBuffer();
                continue;
            }

            buffer += c;
        }
        flushBuffer();
    }

    private void flushBuffer() {
        if (buffer.equals("")) return;

        TranslationPart part = null;
        if (buffer.equals("...")) {
            part = new DotsPart();
        } else if (Database.get().getTranslationsDocument().getList("articles", String.class).contains(buffer)) {
            part = new ArticlePart(buffer);
        } else {
            for (Keyword keyword : keywords) {
                if (keyword.matches(buffer)) {
                    part = new KeywordPart(keyword);
                    break;
                }
            }
        }
        if (part == null) {
            part = new PlainTextPart(buffer);
        }
        parts.add(part);

        buffer = "";
    }

    /* special meaning indicators */

    private void handleSpecialMeaningIndicators() {
        List<TranslationPart> newParts = new ArrayList<>();
        for (int i = 0; i < parts.size();) {
            if (i <= parts.size()-2) { // 2 parts
                if (
                        List.of("Sg.", "Pl.").contains(parts.get(i).getBasicString())
                                && parts.get(i + 1).getBasicString().equals("auch")) { // Sg./Pl. auch
                    newParts.add(new TranslationPartContainer(true, List.of(parts.get(i), parts.get(i + 1))));
                    i += 2;
                    continue;
                }
            }

            // 1 part
            if (parts.get(i).getBasicString().equals("Subst.")) { // Subst.
                newParts.add(new TranslationPartContainer(true, List.of(parts.get(i))));
                i += 1; continue;
            }

            newParts.add(parts.get(i));
            i++;
        }
        parts = newParts;
    }
}