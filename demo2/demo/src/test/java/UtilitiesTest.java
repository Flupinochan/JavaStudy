import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.example.Utilities;

public class UtilitiesTest {
    private Utilities utilities;

    @BeforeEach
    public void setup(){
        this.utilities = new Utilities();
    }

    @Test
    public void everyNthChar() throws Exception{
        char[] output = this.utilities.everyNthChar(new char[]{'h', 'e', 'l', 'l', 'o'}, 2);
        assertArrayEquals(new char[]{'e', 'l',}, output);
        char[] output2 = this.utilities.everyNthChar(new char[]{'h', 'e', 'l', 'l', 'o'}, 8);
        assertArrayEquals(new char[]{'h', 'e', 'l', 'l', 'o'}, output2);
    }

    // inputには、CsvSourceの第1引数、expectedには、CsvSourceの第2引数が入る
    @ParameterizedTest
    @CsvSource({
        "AABCDDEFF, ABCDEF",
        "ABCCABDEEF, ABCABDEF",
        "A, A"
    })
    public void removePairs(String input, String expected) throws Exception{
        assertEquals(expected, utilities.removePairs(input));
    }

    public void removePairs() throws Exception{
        assertEquals("ABCDEF", this.utilities.removePairs("AABCDDEFF"));
        assertEquals("ABCABDEF", this.utilities.removePairs("ABCCABDEEF"));
        assertEquals("A", this.utilities.removePairs("A"));
        assertNull(this.utilities.removePairs(null));
    }

    @Test
    public void converter() throws Exception{
        assertEquals(40, this.utilities.converter(10, 5));
        assertThrows(ArithmeticException.class, () -> {
            this.utilities.converter(10, 0);
        });
    }

    @Test
    public void nullIfOddLength() throws Exception{
        assertNull(this.utilities.nullIfOddLength("odd"));
        assertNotNull(this.utilities.nullIfOddLength("even"));
        fail("Fail nullIfOddLength()");
    }

}
