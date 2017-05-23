package mx.jovannypcg.urlshortener.util;

public class Base62 {
    private static final String ALLOWED_ALPHABET =
            "0123456789"+
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz";

    private static final int BASE = ALLOWED_ALPHABET.length();

    public static String encode(int base10) {
        StringBuilder result = new StringBuilder();

        if (base10 < 0) {
            throw new IllegalArgumentException("base10 must not be negative");
        }

        while(base10 > 0) {
            char characterFromAlphabet = ALLOWED_ALPHABET.charAt((base10 % BASE));
            result.insert(0, characterFromAlphabet);

            base10 /= 62;
        }

        return result.toString();
    }

    public static int decode(String base62) {
        int result = 0;
        int count = 1;

        if (!containsValidCharacters(base62)) {
            throw new IllegalArgumentException("Invalid character found in string: " + base62);
        }

        String reversedBase62 = new StringBuilder(base62).reverse().toString();

        for (char character : reversedBase62.toCharArray()) {
            result += ALLOWED_ALPHABET.indexOf(character) * count;
            count *= 62;
        }

        return result;
    }


    public static boolean containsValidCharacters(String str) {
        for (char character : str.toCharArray()) {
            if (!ALLOWED_ALPHABET.contains(String.valueOf(character))) {
                return false;
            }
        }

        return true;
    }
}
