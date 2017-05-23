package mx.jovannypcg.urlshortener.util;

/**
 * Provides useful methods to convert base10 integer to a base62 string and backwards.
 *
 * @author  Jovanny Pablo Cruz Gomez
 *          Software Engineer
 *          jovannypcg@yahoo.com
 */
public class Base62 {
    /** Set of valid characters that allows to convert base10 to base62 and backwards. */
    private static final String ALLOWED_ALPHABET =
            "0123456789"+
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz";

    /** Size of <code>ALLOWS_ALPHABET</code> (62) */
    private static final int BASE = ALLOWED_ALPHABET.length();

    /**
     * Converts the number sent as argument to a base62 string.
     *
     * @param base10 Number to be converted.
     * @return Base62 string.
     */
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

    /**
     * Converts a base62 string to a base10 number.
     *
     * @param base62 String to be decoded.
     * @return Base10 number.
     */
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

    /**
     * Verifies that the given string contains just characters from
     * <code>ALLOWED_ALPHABET</code>.
     *
     * @param str String to analyze.
     * @return  true if string contains characters from ALLOWED_ALPHABET.
     *          false otherwise.
     */
    public static boolean containsValidCharacters(String str) {
        for (char character : str.toCharArray()) {
            if (!ALLOWED_ALPHABET.contains(String.valueOf(character))) {
                return false;
            }
        }

        return true;
    }
}
