package me.ajg.diss.encoders;

import me.ajg.diss.fileManagement.FileUtil;
import me.ajg.diss.huffman.Huffman;

import java.security.cert.PKIXBuilderParameters;
import java.time.chrono.MinguoDate;
import java.util.*;

public class GoldmanEncoder {
    
    /*
    Key bugs discovered:
        huffman code checking against binary string instead of denary representation of byte
        missing break statement was adding and error base after every C
        String builders were appending spaces and 0s to the wrong string, causing error bases
        DNA complement tags not working:
        Error bases in 1.7
        ID tags were variable lengths
     */
    
    /**
     * Encodes binary data using the algorithm presented in Goldman 2013b
     * Divided up into sections and using method names equivalent to the document to make it simple to reference
     *
     * @param bytesList, a list of files converted to byte arrays, max size of 9
     */
    public static void encode(List<byte[]> bytesList) {
        int IDcounter = 0;
        for (byte[] b : bytesList) {
            //1.1
            byte[] s0 = FileUtil.gzipCompression(b);
            System.out.println("s0 = " + Arrays.toString(s0));
            //1.2
            String s1 = huffmanTernaryEncoding(s0);
            System.out.println("Huffman ternary encoding = " + s1);
            //1.3
            String s2 = base3Length(s1);
            String s3 = padToMultipleOf25(s1, s2);
            String s4 = s1 + s3 + s2;
            //1.4
            String s5 = tritsToDNA(s4, 'A'); //Uses row A when trit String is empty
            int ID = IDcounter++;
            //1.5
            List<String> s5Fragmented = fragment(s5);
            //1.6
            List<String> s5Complemented = reverseComplementOdds(s5Fragmented);
            //1.7 and 1.8
            List<String> s5Indexed = indexDnaCode(s5Complemented, ID);
            //1.9
            List<String> finalOligos = addEndTags(s5Indexed);
            
            
        }
        
    }
    
    /**
     * Uses a pre-built Huffman code to convert binary into ternary numbers of 5-6 digits in length called trits
     *
     * @param bytes the byte[] to convert
     * @return a String of trits
     */
    public static String huffmanTernaryEncoding(byte[] bytes) {
        Map<String, String> huffmanCodeList = Objects.requireNonNull(Huffman.getHuffmanCodeList());
        StringBuilder output = new StringBuilder();
        
        for (byte b : bytes) {
            String decimalStr = Integer.toString(b & 0xFF); // unsigned byte as decimal string
            output.append(huffmanCodeList.get(decimalStr));
        }
        
        return output.toString();
    }
    
    
    /**
     * Returns length of a given bit string padded with 0s to 20 trits
     * Gives a maximum length of
     * @param trits string to find the length of
     * @return String representing length of trits in ternary
     */
    public static String base3Length(String trits){
        return String.format("%20s", (Integer.toString(trits.length(), 3))).replace(' ', '0');
    }
    
    
    /**
     * Produces a string of 0s to be prefixed to the ternary code to make it a multiple of 25
     * @param s1 String 1
     * @param s2 String 2
     * @return A string of 0's which, when concatenated with s1 and s2, has length%25 = 0
     */
    public static String padToMultipleOf25(String s1, String s2){
        int totalLength = s1.length() + s2.length();
        int required0s = (25 - (totalLength % 25)) % 25;
        return "0".repeat(required0s);
    }
    
    public static String tritsToDNA(String trits, char initialRow){
        StringBuilder output = new StringBuilder();
        char previousBase = initialRow;
        
        for (char c: trits.toCharArray()){
            if (previousBase == 'A'){
                switch(c){
                    case '0':
                        output.append('C');
                        previousBase = 'C';
                        break;
                    case '1':
                        output.append('G');
                        previousBase = 'G';
                        break;
                    case '2':
                        output.append('T');
                        previousBase = 'T';
                        break;
                    default:
                        output.append('E');
                        
                }
            }
            else if (previousBase == 'C'){
                switch(c){
                    case '0':
                        output.append('G');
                        previousBase = 'G';
                        break;
                    case '1':
                        output.append('T');
                        previousBase = 'A';
                        break;
                    case '2':
                        output.append('A');
                        previousBase = 'A';
                        break;
                    default:
                        output.append('E');
                    
                }
            }
            else if (previousBase == 'G'){
                switch(c){
                    case '0':
                        output.append('T');
                        previousBase = 'T';
                        break;
                    case '1':
                        output.append('A');
                        previousBase = 'A';
                        break;
                    case '2':
                        output.append('C');
                        previousBase = 'C';
                        break;
                    default:
                        output.append('E');
                }
            }
            else {
                switch(c){
                    case '0':
                        output.append('A');
                        previousBase = 'A';
                        break;
                    case '1':
                        output.append('C');
                        previousBase = 'C';
                        break;
                    case '2':
                        output.append('G');
                        previousBase = 'G';
                        break;
                    default:
                        output.append('E');
                }
            }
        }
        
        return output.toString();
    }
    
    public static List<String> fragment(String dnaCode){
        if (dnaCode.length()%25 != 0){
            System.out.println("Inputs cannot be fragmented: not a multiple of 25");
            return null;
        }
        List<String> output = new ArrayList<>();
        for (int i = 0; i<= dnaCode.length()-100; i+=25){
            output.add(dnaCode.substring(i, i+100));
        }
        return output;
    }
    
    public static List<String> reverseComplementOdds(List<String> dnaFragments){
        List<String> output = new ArrayList<>();
        for (int i = 0; i< dnaFragments.size(); i++){
            if (i%2 == 1){
                StringBuilder sb = new StringBuilder();
                for (char c: dnaFragments.get(i).toCharArray()){
                    switch (c){
                        case 'A':
                            sb.append('T');
                            break;
                        case 'T':
                            sb.append('A');
                            break;
                        case 'C':
                            sb.append('G');
                            break;
                        case 'G':
                            sb.append('C');
                            break;
                        default:
                            sb.append('E');
                    }
                }
                output.add(sb.toString());
            }
        }
        return output;
    }
    
    public static List<String> indexDnaCode(List<String> s5Complemented, int ID){
        List<String> outputString = new ArrayList<>();
        
        for (int i = 0; i<s5Complemented.size(); i++){
            String i3 = String.format("%12s", (Integer.toString(i,3))).replace(' ', '0');
            int P = ID;
            for(int j = 1; j<i3.length(); j+=2){
                P += i3.charAt(i);
            }
            //Mod 3 to act as parity trit
            P %=P;
            //1.8
            String IX = String.format("%2s", (Integer.toString(ID,3))).replace(' ', '0') + i3 + P;
            //encodes index string with starting base as current fragments last base
            String s = s5Complemented.get(i) +
                    tritsToDNA(IX, s5Complemented.get(i).charAt(s5Complemented.get(i).length() - 1));
            outputString.add(s);
        }
        return outputString;
    }
    
    public static List<String> addEndTags(List<String> s5Indexed){
        Random random = new Random();
        List<String> output = new ArrayList<>();
        for (int i = 0; i< s5Indexed.size(); i++) {
            StringBuilder sb = new StringBuilder();
            String code = s5Indexed.get(i);
            if (i % 2 == 1) {
                switch (code.charAt(0)) {
                    case 'C':
                        sb.append('G');
                        break;
                    case 'G':
                        sb.append('C');
                        break;
                    default:
                        sb.append(random.nextBoolean() ? 'G' : 'C');
                }
                sb.append(code);
                switch (code.charAt(code.length() - 1)) {
                    case 'A':
                        sb.append('T');
                        break;
                    case 'T':
                        sb.append('A');
                        break;
                    default:
                        sb.append(random.nextBoolean() ? 'T' : 'A');
                }
                output.add(sb.toString());
            } else {
                switch (code.charAt(0)) {
                    case 'T':
                        sb.append('A');
                        break;
                    case 'A':
                        sb.append('T');
                        break;
                    default:
                        sb.append(random.nextBoolean() ? 'T' : 'A');
                }
                sb.append(code);
                switch (code.charAt(code.length() - 1)) {
                    case 'G':
                        sb.append('C');
                        break;
                    case 'C':
                        sb.append('G');
                        break;
                    default:
                        sb.append(random.nextBoolean() ? 'G' : 'C');
                }
                output.add(sb.toString());
            }
        }
        return output;
    }
}
