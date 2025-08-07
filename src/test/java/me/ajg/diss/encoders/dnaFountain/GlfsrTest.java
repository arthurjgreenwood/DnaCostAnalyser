package me.ajg.diss.encoders.dnaFountain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;

public class GlfsrTest {
    GLFSR glfsr = new GLFSR(42);
    @Test
    void verifyAllOutputsAreUnique(){
        HashSet<Integer> outputs = new HashSet<>();
        for (int i = 0; i < 1000000; i++) {
            int next = glfsr.next();
            assertFalse(outputs.contains(next), "Output " + next + " is not unique");
            outputs.add(next);
        }
    }
}
