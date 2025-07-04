package me.ajg.diss.demos;

import me.ajg.diss.encoders.GLFSR;

public class GLFSRdemo {
    public static void demo() {
        GLFSR glfsr = new GLFSR(1923846);
        for (int i = 0; i<1000; i++){
            System.out.println( String.format("%32s", Integer.toBinaryString(glfsr.next())).replace(' ', '0') );
        }
    }
}
