package me.ajg.diss.encoders;

import me.ajg.diss.fileManagement.FileUtil;

import java.util.Random;

public class ChurchEncoder {
    
    /**
     * Encodes a byte[] as DNA code using Church algorithm
     * 0s will become A or C
     * 1s will become T or G
     * @param bytes the byte array to convert
     * @return a String representation of the DNA code
     */
    public static String encode(byte[] bytes){
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        String[] bytesAsStringArray = FileUtil.bytesToString(bytes);
        for (String byteString : bytesAsStringArray) {
            for(char c : byteString.toCharArray()){
                if (c == '0')
                    stringBuilder.append(random.nextBoolean() ? 'A' : 'C');
                else if (c == '1')
                    stringBuilder.append(random.nextBoolean() ? 'T' : 'G');
            }
        }
        return stringBuilder.toString();
    }
    
    public static void split(String input){
    
    }
    
    
    public static byte[] decode(String dnaCode){
        StringBuilder binaryString = new StringBuilder();
        for (char c : dnaCode.toCharArray()) {
            if (c == 'A' || c == 'C')
                binaryString.append(0);
            else
                binaryString.append(1);
        }
        if (binaryString.length()%8 !=0){
            System.out.println("Binary output is not the correct length");
            return null;
        }
        byte[] outputBytes = new byte[binaryString.length()/8];
        for (int i = 0; i < outputBytes.length; i++) {
            String s = binaryString.substring(i*8, i*8+8);
            outputBytes[i] = (byte) Integer.parseInt(s, 2);
        }
        return outputBytes;
    }
    
}
