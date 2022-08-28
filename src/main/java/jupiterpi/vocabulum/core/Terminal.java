package jupiterpi.vocabulum.core;

import jupiterpi.tools.ui.ConsoleInterface;
import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.ta.TAResult;
import jupiterpi.vocabulum.core.ta.TranslationAssistance;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.conjugated.Verb;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbForm;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.Adjective;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.AdjectiveForm;
import jupiterpi.vocabulum.core.vocabularies.declined.nouns.Noun;
import jupiterpi.vocabulum.core.vocabularies.declined.nouns.NounForm;
import jupiterpi.vocabulum.core.vocabularies.translations.VocabularyTranslation;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Terminal extends ConsoleInterface {
    public void run(I18n i18n) {
        out("----- Vocabulum Terminal -----");

        Document texts = (Document) i18n.getTexts().get("terminal");
        Document promptTexts = (Document) texts.get("prompt");
        Document translationAssistanceTexts = (Document) texts.get("translation_assistance");
        Document germanTranslationMatchingTexts = (Document) texts.get("german_translation_matching");

        final String NOUN = texts.getString("noun");
        final String ADJECTIVE = texts.getString("adjective");
        final String VERB = texts.getString("verb");
        final String ERROR = texts.getString("error");

        out("");
        out(texts.getString("help-text"));

        String modeInput = in("p/t/g: ");
        if (modeInput.equals("p")) {

            out("");
            out("--- " + promptTexts.getString("title") + " ---");
            out(promptTexts.getString("help-text"));
            out("");

            while (true) {
                try {
                    String wordInput = in("> ");
                    if (wordInput.equals("")) break;
                    Vocabulary vocabulary = Vocabulary.fromString(wordInput, i18n, "terminal");
                    if (vocabulary.getKind() == Vocabulary.Kind.NOUN) {
                        Noun noun = (Noun) vocabulary;
                        while (true) {
                            try {
                                String formInput = in("[" + NOUN + "] " + noun.getBaseForm() + " > ");
                                if (formInput.equals("")) break;
                                NounForm form = NounForm.fromString(formInput, i18n);
                                out(noun.makeForm(form));
                            } catch (Exception e) {
                                out(ERROR + ": " + e.getClass().getSimpleName() + " \"" + e.getMessage() + "\"");
                            }
                        }
                    } else if (vocabulary.getKind() == Vocabulary.Kind.ADJECTIVE) {
                        Adjective adjective = (Adjective) vocabulary;
                        while (true) {
                            try {
                                String formInput = in("[" + ADJECTIVE + "] " + adjective.getBaseForm() + " > ");
                                if (formInput.equals("")) break;
                                AdjectiveForm form = AdjectiveForm.fromString(formInput, i18n);
                                out(adjective.makeForm(form));
                            } catch (Exception e) {
                                out(ERROR + ": " + e.getClass().getSimpleName() + " \"" + e.getMessage() + "\"");
                            }
                        }
                    } else if (vocabulary.getKind() == Vocabulary.Kind.VERB) {
                        Verb verb = (Verb) vocabulary;
                        while (true) {
                            try {
                                String formInput = in("[" + VERB + "] " + verb.getBaseForm() + " > ");
                                if (formInput.equals("")) break;
                                VerbForm form = VerbForm.fromString(formInput, i18n);
                                out(verb.makeForm(form));
                            } catch (Exception e) {
                                e.printStackTrace();
                                out(ERROR + ": " + e.getClass().getSimpleName() + " \"" + e.getMessage() + "\"");
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    out(ERROR + ": " + e.getClass().getSimpleName() + " \"" + e.getMessage() + "\"");
                }
            }

        } else if (modeInput.equals("t")) {

            out("");
            out("--- " + translationAssistanceTexts.getString("title") + " ---");
            out(translationAssistanceTexts.getString("help-text"));
            out("");

            while (true) {
                try {
                    String sentence = in("> ");
                    if (sentence.equals("")) break;
                    TAResult result = new TranslationAssistance(sentence).getResult();

                    int maxLines = 0;
                    for (TAResult.TAResultItem item : result.getItems()) {
                        if (item.getLines(i18n).size() > maxLines) maxLines = item.getLines(i18n).size();
                    }

                    List<String> outputLines = new ArrayList<>();
                    for (int i = 0; i < (maxLines + 2); i++) {
                        outputLines.add("");
                    }

                    for (TAResult.TAResultItem item : result.getItems()) {
                        int maxLineLength = 0;
                        if (item.getItem().length() > maxLineLength) maxLineLength = item.getItem().length();
                        for (String line : item.getLines(i18n)) {
                            if (line.length() > maxLineLength) maxLineLength = line.length();
                        }

                        String itemLine = item.getItem();
                        while (itemLine.length() < maxLineLength) {
                            itemLine += " ";
                        }
                        outputLines.set(0, outputLines.get(0) + "   " + itemLine);

                        int linesCount = item.getLines(i18n).size();
                        for (int i = 0; i < linesCount; i++) {
                            String lineLine = item.getLines(i18n).get(i);
                            while (lineLine.length() < maxLineLength) {
                                lineLine += " ";
                            }
                            int index = 2 + i;
                            outputLines.set(index, outputLines.get(index) + "   " + lineLine);
                        }
                        String fillLine = "";
                        for (int i = 0; i < maxLineLength; i++) {
                            fillLine += " ";
                        }
                        for (int i = 0; i < (maxLines - linesCount); i++) {
                            int index = 2 + linesCount + i;
                            outputLines.set(index, outputLines.get(index) + fillLine + "   ");
                        }
                    }

                    out("");
                    for (String outputLine : outputLines) {
                        out(outputLine);
                    }
                    out("");
                } catch (Exception e) {
                    out(ERROR + ": " + e.getClass().getSimpleName() + " \"" + e.getMessage() + "\"");
                }
            }

        } else if (modeInput.equals("g")) {

            out("");
            out("--- " + germanTranslationMatchingTexts.getString("title") + " ---");
            out(germanTranslationMatchingTexts.getString("help-text"));
            out("");

            while (true) {
                try {
                    String targetTranslationInput = in("> ");
                    if (targetTranslationInput.equals("")) break;
                    VocabularyTranslation targetTranslation = VocabularyTranslation.fromString(targetTranslationInput);

                    while (true) {
                        try {
                            String input = in("  > ");
                            if (input.isEmpty()) break;
                            out(targetTranslation.isValid(input)
                                    ? "    ✅"
                                    : "    ❌"
                            );
                        } catch (Exception e) {
                            out(ERROR + ": " + e.getClass().getSimpleName() + " \"" + e.getMessage() + "\"");
                        }
                    }
                } catch (Exception e) {
                    out(ERROR + ": " + e.getClass().getSimpleName() + " \"" + e.getMessage() + "\"");
                }
            }

        } else {
            out(String.format(texts.getString("unknown-mode-err"), modeInput));
        }

        out(texts.getString("done"));
    }
}