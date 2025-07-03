package me.ajg.diss.huffman;

import jdk.jshell.spi.ExecutionControl;

import java.io.*;
import java.util.*;

public class Huffman {
    
    /**
     * Utility class for converting the huffman codes given in Goldman et al (2013) to a map of bytes to trits
     * @return Hashmap associating byte values with trits
     */
    
    public static Map<String, String>getHuffmanCodeList(){
        Map<String, String> output = new HashMap<>();
        
        try (InputStream in = Huffman.class.getResourceAsStream("/me/ajg/diss/huffman/View_huff3.cd.new.correct")){
            if (in == null){
                System.out.println("Huffman view not found");
                return null;
            }
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            
            for (int i = 0; i<=255; i++) {
                try{
                    String line = Objects.requireNonNull(reader).readLine();
                    String[] parts = line.split("\\s+");
                    output.put((parts[0]), (parts[3]));
                }
                catch (IOException e){
                    System.out.println("Error occurred reading huffman file");
                }
            }
            System.out.println("Huffman code list: " + output);
            return output;
            
        } catch (IOException e) {
            System.out.println("Error occurred reading huffman file");
        }
        return null;
    }
}

