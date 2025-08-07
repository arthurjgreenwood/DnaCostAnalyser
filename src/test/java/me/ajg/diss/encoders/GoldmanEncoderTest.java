package me.ajg.diss.encoders;

import me.ajg.diss.encoders.goldman.GoldmanEncoder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class GoldmanEncoderTest {
    
    List<String> files = new ArrayList<>();
    List<String> encoded = new ArrayList<>();
    
    //Testing full encoder
    public GoldmanEncoderTest() {
        files.add("src/test/resources/test.png");
        encoded = GoldmanEncoder.encode(files);
    }
    
    @Test
    public void checkOligoLength(){
        for (String encoded : encoded){
            assertEquals(117, encoded.length(), "Oligo length should be 117");
        }
    }
    
    @Test
    public void checkNoHomopolymers(){
        for (String s : encoded){
            for (int i = 1; i < s.length(); i++){
                assertTrue(s.charAt(i) != s.charAt(i - 1));
            }
        }
    }
    
    //Testing subunits
    @Test
    public void testHuffmanTernaryEncoding(){
        byte[] input = new byte[]{(byte) 170, 127};
        assertEquals("2212222121", GoldmanEncoder.huffmanTernaryEncoding(input));
    }
    
    @Test
    public void testBase3Length(){
        String output = GoldmanEncoder.base3Length("20120110");
        assertEquals("00000000000000000022", output);
        assertEquals(20, output.length());
    }
    
    @Test
    public void testPadToMultipleOf25(){
        String string1 = "210102012012012102102102102102102";
        String string2 = "12201201210210102010201220202";
        String zeros = GoldmanEncoder.padToMultipleOf25(string1, string2);
        assertEquals(0, (string1.length() + string2.length() + zeros.length())%25);
    }
    
    @Test
    public void testTritsToDNA(){
        assertEquals("CGTACGTA", GoldmanEncoder.tritsToDNA("00000000", 'A'));
        assertEquals("CACACACA", GoldmanEncoder.tritsToDNA("02020202", 'A'));
    }
    
    @Test
    public void testFragment(){
        assertNull(GoldmanEncoder.fragment("AT")); //returns null for non multiples of 25
        
        String dnaCode = "CTAGACCGAGTAGATTAGCACTAGA" +
                "CTAGACCGAGTAGATTAGCACTAGA" +
                "CTAGACCGAGTAGATTAGCACTAGA" +
                "CTAGACCGAGTAGATTAGCACTAGA" +
                
                "CTAGACCGAGTAGATTAGCACTAGA" +
                "CTAGACCGAGTAGATTAGCACTAGA" +
                "CTAGACCGAGTAGATTAGCACTAGA" +
                "CTAGACCGAGTAGATTAGCACTAGA" +
                
                "CTAGACCGAGTAGATTAGCACTAGA" +
                "CTAGACCGAGTAGATTAGCACTAGA" +
                "CTAGACCGAGTAGATTAGCACTAGA" +
                "CTAGACCGAGTAGATTAGCACTAGA";
        
        
        assertEquals(9, Objects.requireNonNull(GoldmanEncoder.fragment(dnaCode)).size());
    }
    
    @Test
    public void testReverseComplementOdds(){
        List<String> dnaFragments = new ArrayList<>();
        dnaFragments.add("CTAG");
        dnaFragments.add("CTAG");
        dnaFragments.add("CTAG");
        List<String> reverseComplementedOdds = GoldmanEncoder.reverseComplementOdds(dnaFragments);
        assertEquals("CTAG", reverseComplementedOdds.get(0));
        assertEquals("GATC", reverseComplementedOdds.get(1));
        assertEquals("CTAG", reverseComplementedOdds.get(2));
    }
    
    @Test
    public void testAddEndTags() {
        List<String> input = Arrays.asList("ATCG", "GCAT", "TACC", "CGTA");
        List<String> output = GoldmanEncoder.addEndTags(input);
        assertEquals(input.size(), output.size(), "Output list size should match input");
        
        for (int i = 0; i < input.size(); i++) {
            String original = input.get(i);
            String transformed = output.get(i);
            assertEquals(original.length() + 2, transformed.length());
            String middle = transformed.substring(1, transformed.length() - 1);
            
            assertEquals(original, middle);
            
            char firstChar = original.charAt(0);
            char lastChar = original.charAt(original.length() - 1);
            if (i%2 == 0) {
                assertTrue(firstChar == 'A' || firstChar == 'T');
                assertTrue(lastChar == 'G' || lastChar == 'C');
            }
            else {
                assertTrue(firstChar == 'C' || firstChar == 'G');
                assertTrue(lastChar == 'A' || lastChar == 'T');
            }
            
        }
    }
}

