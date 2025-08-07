package me.ajg.diss.encoders.goldman;

import java.io.*;
import java.util.*;

public class Huffman {
    
    /**
     * Utility class for converting the huffman codes given in Goldman et al (2013) to a map of bytes to trits
     * @return Hashmap associating byte values with trits
     */
    
    public static Map<String, String>getHuffmanCodeList(){
        Map<String, String> output = new HashMap<>();
        
        try (InputStream in = Huffman.class.getResourceAsStream("/me/ajg/diss/huffman/huffmanCode")){
            if (in == null){
                System.out.println("Huffman view not found");
                return null;
            }
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            
            for (int i = 0; i<=255; i++) {
                try{
                    String line = Objects.requireNonNull(reader).readLine();
                    String[] parts = line.split(" ");
                    output.put((parts[0]), (parts[1]));
                }
                catch (IOException e){
                    System.out.println("Error occurred reading huffman file");
                }
            }
            return output;
            
        } catch (IOException e) {
            System.out.println("Error occurred reading huffman file");
        }
        return null;
    }
}

