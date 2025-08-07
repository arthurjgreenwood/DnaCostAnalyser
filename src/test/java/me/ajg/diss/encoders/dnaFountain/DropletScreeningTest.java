package me.ajg.diss.encoders.dnaFountain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DropletScreeningTest {
    
    DropletScreening screener = new DropletScreening(0.1, 5);
    
    @Test
    void ensureValidOligosAccepted(){
      assertTrue(screener.screen("AAACCCGGGTTT"));
    }
    
    @Test
    void ensureInvalidOligosRejected(){
        assertFalse(screener.screen("GGGCCCGGGCCCAAA"));
        assertFalse(screener.screen("AAAAAAA"));
        assertFalse(screener.screen("TTTTTTT"));
        assertFalse(screener.screen("CCCCCCC"));
        assertFalse(screener.screen("GGGGGGG"));
    }
}
