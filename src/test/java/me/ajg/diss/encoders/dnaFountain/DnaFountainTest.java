package me.ajg.diss.encoders.dnaFountain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DnaFountainTest {
    
    List<String> outputOligos = new ArrayList<>();
    
    public DnaFountainTest() {
        outputOligos = DNAFountainEncoder.encode("src/test/resources/test.png", 42, 0.03, 32, 0.01, 6);
        
        
    }
    
    @Test
    public void testOligoLength(){
        for (String s : outputOligos) {
            assertEquals(136, s.length());
        }
    }
}
