package me.ajg.diss.encoders;

import me.ajg.diss.fileManagement.FileUtil;

import java.util.*;

public class ChurchEncoder {
    
    public static List<String> encode(String filePath){
        byte[] fileBytes = FileUtil.fileToByteArray(filePath);
        List<byte[]> fragmentedBytes = FileUtil.fragment(fileBytes, 12);
        Random random = new Random(); //For randomly selecting base to 
        List<String> outputCode = new ArrayList<>();
        for (int i = 0; i < fragmentedBytes.size(); i++) {
            StringBuilder payload = new StringBuilder();
            for (byte b : fragmentedBytes.get(i)) {
                String binaryRep = String.format("%8s",Integer.toBinaryString(b)).replace(" ", "0");
                payload.append(binaryRep);
            }
            StringBuilder output = new StringBuilder();
            output.append(String.format("%19s",Integer.toBinaryString(i+1)).replace(" ", "0"));
            output.append(payload);
            
            StringBuilder fragment = new StringBuilder();
            for (int j = 0; j < fragment.length(); j++) {
                char c = fragment.charAt(j);
                if (j < 3){
                    if (c == '0') {
                        fragment.append(random.nextBoolean() ? 'A' : 'C');
                    }
                    else
                        fragment.append(random.nextBoolean() ? 'T' : 'G');
                    continue;
                }
                
                String last3 = fragment.substring(j-3);
                boolean homopolymer = (last3.charAt(0) == last3.charAt(1) && last3.charAt(1) == last3.charAt(2));
                if (c == '0' && !homopolymer)
                    fragment.append(random.nextBoolean() ? 'A' : 'C');
                else if (c == '1' && !homopolymer)
                    fragment.append(random.nextBoolean() ? 'T' : 'G');
                else if (c == '0'){
                    if (last3.equals("AAA"))
                        fragment.append('C');
                    else fragment.append('A');
                }
                else{
                    if (last3.equals("TTT"))
                        fragment.append('G');
                    else fragment.append('T');
                }
            }
            outputCode.add(fragment.toString());
        }
        return outputCode;
    }
}
