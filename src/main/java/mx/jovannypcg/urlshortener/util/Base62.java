package mx.jovannypcg.urlshortener.util;

public class Base62 {
    private static final char[] ALLOWED_ALPHABET = (
            "0123456789"+
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz").toCharArray();

    private static final int BASE = ALLOWED_ALPHABET.length;

    public static String encode(int base10) {
        StringBuilder result = new StringBuilder();

        if (base10 < 0) {
            throw new IllegalArgumentException("base10 must not be negative");
        }

        while(base10 > 0) {
            char characterFromAlphabet = ALLOWED_ALPHABET[(base10 % BASE)];
            result.insert(0, characterFromAlphabet);

            base10 /= 62;
        }

        return result.toString();
    }
}
