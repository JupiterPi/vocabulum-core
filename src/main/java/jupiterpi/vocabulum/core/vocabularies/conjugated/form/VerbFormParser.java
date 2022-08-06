package jupiterpi.vocabulum.core.vocabularies.conjugated.form;

import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.interpreter.tokens.Token;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;

public class VerbFormParser {
    private VerbForm verbForm;

    public VerbFormParser(TokenSequence tokens) throws ParserException {
        this.verbForm = parseVerbForm(tokens);
    }

    public VerbForm getVerbForm() {
        return verbForm;
    }

    /* parser */

    private VerbForm parseVerbForm(TokenSequence tokens) throws ParserException {

        // Kind.INFINITIVE
        if (tokens.size() == 2 && tokens.fitsStartsWith(TokenSequence.fromTypes(
                Token.Type.INFINITIVE_FLAG,
                Token.Type.TENSE
        ))) {
            InfinitiveTense tense = tokens.getI18n().infinitiveTenseFromSymbol(tokens.get(1).getContent());
            return new VerbForm(tense);
        }

        // Kind.NOUN_LIKE
        if (tokens.size() >= 3 && tokens.fitsStartsWith(TokenSequence.fromTypes(
                Token.Type.NOUN_LIKE_FORM,
                Token.Type.CASUS,
                Token.Type.NUMBER
        )) && tokens.size() <= 4) {
            NounLikeForm nounLikeForm = tokens.getI18n().nounLikeFormFromSymbol(tokens.get(0).getContent());
            DeclinedForm declinedForm = DeclinedForm.fromTokens(tokens.subsequence(1));
            declinedForm.normalizeGender();
            return new VerbForm(nounLikeForm, declinedForm);
        }

        // Kind.BASIC
        try {
            int voiceIndex = tokens.indexOf(new Token(Token.Type.VOICE));
            Voice voice = VerbForm.DEFAULT_VOICE;
            if (voiceIndex >= 0) {
                voice = tokens.getI18n().voiceFromSymbol(tokens.get(voiceIndex).getContent());
                tokens.remove(voiceIndex);
            }

            int tenseIndex = tokens.indexOf(new Token(Token.Type.TENSE));
            Tense tense = VerbForm.DEFAULT_TENSE;
            if (tenseIndex >= 0) {
                tense = tokens.getI18n().tenseFromSymbol(tokens.get(tenseIndex).getContent());
                tokens.remove(tenseIndex);
            }

            int modeIndex = tokens.indexOf(new Token(Token.Type.MODE));
            Mode mode = VerbForm.DEFAULT_MODE;
            if (modeIndex >= 0) {
                mode = tokens.getI18n().modeFromSymbol(tokens.get(modeIndex).getContent());
                tokens.remove(modeIndex);
            }

            ConjugatedForm conjugatedForm = ConjugatedForm.fromTokens(tokens);

            return new VerbForm(conjugatedForm, mode, tense, voice);
        } catch (ParserException e) {
            throw new ParserException("Could not parse verb form: " + tokens);
        }
    }
}