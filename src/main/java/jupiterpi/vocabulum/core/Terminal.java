package jupiterpi.vocabulum.core;

import jupiterpi.vocabulum.core.ta.TranslationAssistance;
import jupiterpi.vocabulum.core.ta.result.TAResult;
import jupiterpi.vocabulum.core.util.ConsoleInterface;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.conjugated.Verb;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbForm;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.Adjective;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.AdjectiveForm;
import jupiterpi.vocabulum.core.vocabularies.declined.nouns.Noun;
import jupiterpi.vocabulum.core.vocabularies.declined.nouns.NounForm;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;

import java.util.ArrayList;
import java.util.List;

public class Terminal extends ConsoleInterface {
    public void run() {
        out("----- Vocabulum Terminal -----");

        out("");
        out("Enter \"p\" for Prompting, \"t\" for Translation Assistance, \"g\" for German translation matching. ");

        String modeInput = in("p/t/g: ");
        if (modeInput.equals("p")) {

            out("");
            out("--- Prompting ---");
            out("Usage: Type a vocabulary after \">\" (e. g. \"amicus, amici m. -- friend\", \"laetus, laeta, laetum -- happy\" or \"felix, Gen. felicis -- lucky\"). Then after the indented \">\", type the form you want to generate (e. g. \"Nom. Sg.\" or \"Gen. Pl. Fem.\"). To go back at any time, press Enter without typing something on a prompt. ");
            out("");

            while (true) {
                try {
                    String wordInput = in("> ");
                    if (wordInput.equals("")) break;
                    Vocabulary vocabulary = Vocabulary.fromString(wordInput, "terminal");
                    if (vocabulary.getKind() == Vocabulary.Kind.NOUN) {
                        Noun noun = (Noun) vocabulary;
                        while (true) {
                            try {
                                String formInput = in("[Noun] " + noun.getBaseForm() + " > ");
                                if (formInput.equals("")) break;
                                NounForm form = NounForm.fromString(formInput);
                                out(noun.makeForm(form).toString());
                            } catch (Exception e) {
                                out("ERROR: " + e.getClass().getSimpleName() + " \"" + e.getMessage() + "\"");
                            }
                        }
                    } else if (vocabulary.getKind() == Vocabulary.Kind.ADJECTIVE) {
                        Adjective adjective = (Adjective) vocabulary;
                        while (true) {
                            try {
                                String formInput = in("[Adjective] " + adjective.getBaseForm() + " > ");
                                if (formInput.equals("")) break;
                                AdjectiveForm form = AdjectiveForm.fromString(formInput);
                                out(adjective.makeForm(form).toString());
                            } catch (Exception e) {
                                out("ERROR: " + e.getClass().getSimpleName() + " \"" + e.getMessage() + "\"");
                            }
                        }
                    } else if (vocabulary.getKind() == Vocabulary.Kind.VERB) {
                        Verb verb = (Verb) vocabulary;
                        while (true) {
                            try {
                                String formInput = in("[Verb] " + verb.getBaseForm() + " > ");
                                if (formInput.equals("")) break;
                                VerbForm form = VerbForm.fromString(formInput);
                                out(verb.makeForm(form).toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                                out("ERROR: " + e.getClass().getSimpleName() + " \"" + e.getMessage() + "\"");
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    out("ERROR: " + e.getClass().getSimpleName() + " \"" + e.getMessage() + "\"");
                }
            }

        } else if (modeInput.equals("t")) {

            out("");
            out("--- Translation Assistance ---");
            out("Usage: Type a sentence after \">\" (e. g. \"Asinus stat es exspectat.\") and press Enter. Translation Assistance will print you all available information required for translating it.");
            out("");

            while (true) {
                try {
                    String sentence = in("> ");
                    if (sentence.equals("")) break;
                    TAResult result = new TranslationAssistance(sentence).getResult();

                    int maxLines = 0;
                    for (TAResult.TAResultItem item : result.getItems()) {
                        if (item.getLines().size() > maxLines) maxLines = item.getLines().size();
                    }

                    List<String> outputLines = new ArrayList<>();
                    for (int i = 0; i < (maxLines + 2); i++) {
                        outputLines.add("");
                    }

                    for (TAResult.TAResultItem item : result.getItems()) {
                        int maxLineLength = 0;
                        if (item.getItem().length() > maxLineLength) maxLineLength = item.getItem().length();
                        for (String line : item.getLines()) {
                            if (line.length() > maxLineLength) maxLineLength = line.length();
                        }

                        String itemLine = item.getItem();
                        while (itemLine.length() < maxLineLength) {
                            itemLine += " ";
                        }
                        outputLines.set(0, outputLines.get(0) + "   " + itemLine);

                        int linesCount = item.getLines().size();
                        for (int i = 0; i < linesCount; i++) {
                            String lineLine = item.getLines().get(i);
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
                    out("ERROR: " + e.getClass().getSimpleName() + " \"" + e.getMessage() + "\"");
                }
            }

        } else if (modeInput.equals("g")) {

            out("");
            out("--- German translation matching ---");
            out("Usage: Type a translation sequence after \">\" (e. g. \"der Freund\" or \"aus, von (... her)\"). Then after the indented \">\", type an input for the translation. The German translation matcher will match your input to the target translation. ");
            out("");

            while (true) {
                try {
                    String targetTranslationInput = in("> ");
                    if (targetTranslationInput.equals("")) break;
                    TranslationSequence targetTranslation = TranslationSequence.fromString(targetTranslationInput);

                    while (true) {
                        try {
                            String input = in("  > ");
                            if (input.isEmpty()) break;
                            List<TranslationSequence.ValidatedTranslation> validation = targetTranslation.validateInput(input);
                            for (TranslationSequence.ValidatedTranslation translation : validation) {
                                out(
                                        "    " +
                                        translation.getVocabularyTranslation().getTranslation() + ": " +
                                        (translation.isValid() ? "✅" : "❌") + " " + translation.getInput()
                                );
                            }
                        } catch (Exception e) {
                            out("ERROR: " + e.getClass().getSimpleName() + " \"" + e.getMessage() + "\"");
                        }
                    }
                } catch (Exception e) {
                    out("ERROR: " + e.getClass().getSimpleName() + " \"" + e.getMessage() + "\"");
                }
            }

        } else {
            out("Unknown mode: " + modeInput);
        }

        out("Done.");
    }
}