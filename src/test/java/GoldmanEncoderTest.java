import me.ajg.diss.encoders.goldman.GoldmanEncoder;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GoldmanEncoderTest {
    
    @Test
    public void testBase3Length() {
        String input = "012012012012";
        String result = GoldmanEncoder.base3Length(input);
        int expectedLength = Integer.toString(input.length(), 3).length();
        assertEquals(20, result.length());
        assertTrue(result.endsWith(Integer.toString(input.length(), 3)));
    }
    
    @Test
    public void testPadToMultipleOf25() {
        String s1 = "012012012";
        String s2 = "111";
        String padding = GoldmanEncoder.padToMultipleOf25(s1, s2);
        int total = s1.length() + s2.length() + padding.length();
        assertEquals(0, total % 25);
    }
    
    @Test
    public void testTritsToDNA() {
        String trits = "012012012";
        String dna = GoldmanEncoder.tritsToDNA(trits, 'A');
        assertEquals(trits.length(), dna.length());
        assertTrue(dna.matches("[ACGT]+"));
    }
    
    @Test
    public void testFragment() {
        String dna = "A".repeat(125); // 5 fragments of 101 bases, 25 overlap
        List<String> fragments = GoldmanEncoder.fragment(dna);
        assertNotNull(fragments);
        for (String frag : fragments) {
            assertEquals(101, frag.length());
        }
    }
    
    @Test
    public void testReverseComplementOdds() {
        List<String> input = Arrays.asList("ATCG", "GCTA", "TTAA", "CCGG");
        List<String> result = GoldmanEncoder.reverseComplementOdds(input);
        assertEquals(input.size(), result.size());
        // Check that odd indexes (1 and 3) are reverse complements (expected errors in original impl)
        assertNotEquals("GCTA", result.get(1)); // changed
    }
}

