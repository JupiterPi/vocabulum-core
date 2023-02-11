package jupiterpi.vocabulum.core.vocabularies.conjugated.form;

import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.interpreter.tokens.Token;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;
import jupiterpi.vocabulum.core.symbols.Symbols;
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

        // Kind.IMPERATIVE
        if (tokens.size() == 2 && tokens.fitsStartsWith(TokenSequence.fromTypes(
                Token.Type.IMPERATIVE_FLAG,
                Token.Type.NUMBER
        ))) {
            CNumber number = Symbols.get().cNumberFromSymbol(tokens.get(1).getContent());
            return new VerbForm(number);
        }

        // Kind.INFINITIVE
        if (tokens.size() == 3 && tokens.fitsStartsWith(TokenSequence.fromTypes(
                Token.Type.INFINITIVE_FLAG,
                Token.Type.TENSE,
                Token.Type.VOICE
        ))) {
            InfinitiveTense tense = Symbols.get().infinitiveTenseFromSymbol(tokens.get(1).getContent());
            Voice voice = Symbols.get().voiceFromSymbol(tokens.get(2).getContent());
            return new VerbForm(tense, voice);
        }

        // Kind.NOUN_LIKE
        if (tokens.size() >= 3 && tokens.fitsStartsWith(TokenSequence.fromTypes(
                Token.Type.NOUN_LIKE_FORM,
                Token.Type.CASUS,
                Token.Type.NUMBER
        )) && tokens.size() <= 4) {
            NounLikeForm nounLikeForm = Symbols.get().nounLikeFormFromSymbol(tokens.get(0).getContent());
            DeclinedForm declinedForm = DeclinedForm.fromTokens(tokens.subsequence(1));
            declinedForm.normalizeGender();
            return new VerbForm(nounLikeForm, declinedForm);
        }

        // Kind.BASIC
        try {
            int voiceIndex = tokens.indexOf(new Token(Token.Type.VOICE));
            Voice voice = VerbForm.DEFAULT_VOICE;
            if (voiceIndex >= 0) {
                voice = Symbols.get().voiceFromSymbol(tokens.get(voiceIndex).getContent());
                tokens.remove(voiceIndex);
            }

            int tenseIndex = tokens.indexOf(new Token(Token.Type.TENSE));
            Tense tense = VerbForm.DEFAULT_TENSE;
            if (tenseIndex >= 0) {
                tense = Symbols.get().tenseFromSymbol(tokens.get(tenseIndex).getContent());
                tokens.remove(tenseIndex);
            }

            int modeIndex = tokens.indexOf(new Token(Token.Type.MODE));
            Mode mode = VerbForm.DEFAULT_MODE;
            if (modeIndex >= 0) {
                mode = Symbols.get().modeFromSymbol(tokens.get(modeIndex).getContent());
                tokens.remove(modeIndex);
            }

            ConjugatedForm conjugatedForm = ConjugatedForm.fromTokens(tokens);

            return new VerbForm(conjugatedForm, mode, tense, voice);
        } catch (ParserException e) {
            throw new ParserException("Could not parse verb form: " + tokens);
        }
    }
}