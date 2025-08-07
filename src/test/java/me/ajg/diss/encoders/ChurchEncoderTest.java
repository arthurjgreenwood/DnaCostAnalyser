package me.ajg.diss.encoders;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChurchEncoderTest {
    
    String filePath = "src/test/resources/test.png";
    List<String> oligos = ChurchEncoder.encode(filePath, 12);
    
    @Test
    void ensureOutputIsNotEmpty(){
        assertFalse(oligos.isEmpty(), "Oligos should not be empty");
    }
    
    @Test
    void ensureOligosAreCorrectLength(){
        for (String oligo : oligos){
            assertEquals(115, oligo.length(), "Oligo length should be 115");
        }
    }
    
    @Test
    void ensureNoHomopolymersAbove3Nts(){
        for (String oligo : oligos){
            int count = 1;
            for (int i = 1; i < oligo.length(); i++){
                if (oligo.charAt(i) == oligo.charAt(i - 1)) {
                    count++;
                    assertTrue(count <= 3, "Oligo length should be 3 or less");
                }
                else{
                    count = 1;
                }
            }
        }
    }
}