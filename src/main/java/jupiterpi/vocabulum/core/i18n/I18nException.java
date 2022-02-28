package jupiterpi.vocabulum.core.i18n;

public class I18nException extends Exception {
    public I18nException() {}

    public I18nException(String message) {
        super(message);
    }

    public static I18nException mismatch(I18n a, I18n b) {
        return new I18nException("Mismatching i18ns: " + a.getName() + " and " + b.getName());
    }
}