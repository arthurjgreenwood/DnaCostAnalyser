package me.ajg.diss;

import me.ajg.diss.encoders.ChurchEncoder;
import me.ajg.diss.encoders.GoldmanEncoder;
import me.ajg.diss.fileManagement.FileUtil;
import me.ajg.diss.huffman.Huffman;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static me.ajg.diss.encoders.GoldmanEncoder.*;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Example input
        String input = "It is unbelievable how many bugs i am currently finding, please help me i cant do it asdahsdhasdiaufhaidf";
        byte[] inputBytes = input.getBytes();
        List<byte[]> inputList = new ArrayList<>();
        inputList.add(inputBytes);
        
        int IDcounter = 0;
        for (byte[] b: inputList) {
            System.out.println("Original input: " + input);
            
            // 1.1 - Gzip Compression
            byte[] s0 = FileUtil.gzipCompression(b);
            System.out.println("1.1 Gzip compressed: " + Arrays.toString(s0));
            
            // 1.2 - Huffman ternary encoding
            String s1 = huffmanTernaryEncoding(s0);
            System.out.println("1.2 Huffman encoded (ternary): " + s1);
            
            // 1.3 - Padding to multiple of 25
            String s2 = base3Length(s1);
            System.out.println("1.3 Length of s1 in base 3 (20 trits): " + s2);
            
            String s3 = padToMultipleOf25(s1, s2);
            System.out.println("1.3 Padding to reach multiple of 25: " + s3);
            
            String s4 = s1 + s3 + s2;
            System.out.println("1.3 Padded ternary string: " + s4);
            
            // 1.4 - Trits to DNA
            String s5 = tritsToDNA(s4, 'A');
            System.out.println("1.4 Trits to DNA: " + s5);
            
            int ID = IDcounter++;
            
            // 1.5 - Fragment into 100-mers
            List<String> s5Fragmented = fragment(s5);
            System.out.println("1.5 Fragmented DNA (100-mers):");
            if (s5Fragmented != null) {
                for (String frag : s5Fragmented) {
                    System.out.println(frag);
                }
            }
            
            // 1.6 - Reverse complement odd-indexed fragments
            List<String> s5Complemented = reverseComplementOdds(s5Fragmented);
            System.out.println("1.6 Reverse complemented odds:");
            for (String frag : s5Complemented) {
                System.out.println(frag);
            }
            
            // 1.7 and 1.8 - Add index
            List<String> s5Indexed = indexDnaCode(s5Complemented, ID);
            System.out.println("1.7-1.8 Indexed DNA:");
            for (String frag : s5Indexed) {
                System.out.println(frag);
            }
            
            // 1.9 - Add end tags
            List<String> finalOligos = addEndTags(s5Indexed);
            System.out.println("1.9 Final DNA oligos with tags:");
            for (String frag : finalOligos) {
                System.out.println(frag);
            }
        }
    }
    
}