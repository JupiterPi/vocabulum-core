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

    private final List<Keyword> keywords = Keyword.fromDocuments((List<Document>) Database.get().getTranslationsDocument().get("keywords"));

    private List<TranslationPart> parts = new ArrayList<>();

    private String buffer = "";
    private boolean inParens = false;

    private TranslationPartContainer parseTranslationPartContainer(boolean optional, String string) {
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

        return new TranslationPartContainer(optional, parts);
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
}