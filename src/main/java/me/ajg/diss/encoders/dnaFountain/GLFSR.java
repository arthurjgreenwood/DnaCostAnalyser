
package me.ajg.diss.encoders.dnaFountain;

public class GLFSR {
    
    private int state;
    private final int POLY = 0b10100011000000000000000000000000;
    
    public GLFSR (int seed){
        state = seed;
    }
    
    public int next(){
        int lsb = state & 0b1; // check output bit
        state >>>= 1; //unsigned right shift the whole state
        if (lsb == 1){
            state ^= POLY;
        }
        return state;
    }
}
