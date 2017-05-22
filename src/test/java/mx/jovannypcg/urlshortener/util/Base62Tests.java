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


}
