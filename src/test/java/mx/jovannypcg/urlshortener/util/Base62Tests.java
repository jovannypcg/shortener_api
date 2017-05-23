package mx.jovannypcg.urlshortener.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Base62Tests {
    @Test
    public void base62ShouldEncode15Correctly() {
        int base10ToBeConverted = 15;
        String expectedEncode = "F";

        Assert.assertEquals(Base62.encode(base10ToBeConverted),
                expectedEncode);
    }

    @Test
    public void base62ShouldEncode170Correctly() {
        int base10ToBeConverted = 170;
        String expectedEncode = "2k";

        Assert.assertEquals(expectedEncode,
                Base62.encode(base10ToBeConverted));
    }

    @Test
    public void base62ShouldEncode2015Correctly() {
        int base10ToBeConverted = 2015;
        String expectedEncode = "WV";

        Assert.assertEquals(expectedEncode,
                Base62.encode(base10ToBeConverted));
    }

    @Test
    public void base62ShouldEncode123551Correctly() {
        int base10ToBeConverted = 123551;
        String expectedEncode = "W8l";

        Assert.assertEquals(expectedEncode,
                Base62.encode(base10ToBeConverted));
    }

    @Test
    public void base62ShouldEncode1923951236Correctly() {
        int base10ToBeConverted = 1923951236;
        String expectedEncode = "26ChbY";

        Assert.assertEquals(expectedEncode,
                Base62.encode(base10ToBeConverted));
    }

    @Test
    public void containsValidCharactersShouldBeTrue() {
        String base62ToTest = "26ChbY";

        Assert.assertTrue(Base62.containsValidCharacters(base62ToTest));
    }

    @Test
    public void containsValidCharactersShouldBeFalse() {
        String base62ToTest = "26ChbY[";

        Assert.assertFalse(Base62.containsValidCharacters(base62ToTest));
    }

    @Test(expected = IllegalArgumentException.class)
    public void decodeShouldThrowIllegalArgumentException() {
        String invalidBase62String = "26ChbY[";
        Base62.decode(invalidBase62String);
    }

    @Test
    public void decodeShouldRunF() {
        String base62ToBeDecoded = "F";
        int expectedResult = 15;

        Assert.assertEquals(Base62.decode(base62ToBeDecoded),
                expectedResult);
    }

    @Test
    public void decodeShouldRun2k() {
        String base62ToBeDecoded = "2k";
        int expectedResult = 170;

        Assert.assertEquals(Base62.decode(base62ToBeDecoded),
                expectedResult);
    }

    @Test
    public void decodeShouldRunWV() {
        String base62ToBeDecoded = "WV";
        int expectedResult = 2015;

        Assert.assertEquals(Base62.decode(base62ToBeDecoded),
                expectedResult);
    }

    @Test
    public void decodeShouldRunW8l() {
        String base62ToBeDecoded = "W8l";
        int expectedResult = 123551;

        Assert.assertEquals(Base62.decode(base62ToBeDecoded),
                expectedResult);
    }

    @Test
    public void decodeShouldRun26ChbY() {
        String base62ToBeDecoded = "26ChbY";
        int expectedResult = 1923951236;

        Assert.assertEquals(Base62.decode(base62ToBeDecoded),
                expectedResult);
    }
}
