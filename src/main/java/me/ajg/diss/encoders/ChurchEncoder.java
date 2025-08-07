package me.ajg.diss.encoders;

import me.ajg.diss.fileManagement.FileUtil;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ChurchEncoder {
    
    public static List<String> encode(String filePath, int bytesPerFragment){
        byte[] fileBytes = FileUtil.fileToByteArray(filePath);
        List<byte[]> fragmentedBytes = FileUtil.fragment(fileBytes, 12);
        Random random = new Random(); //For randomly selecting base
        List<String> outputCode = new ArrayList<>();
        if (fragmentedBytes.size() > Math.pow(2,19)) {
            JOptionPane.showMessageDialog(null, filePath + " exceeds maximum size for Church encoding", "Error", JOptionPane.ERROR_MESSAGE);
            return outputCode;
        }
        
        for (int i = 0; i < fragmentedBytes.size(); i++) {
            StringBuilder payload = new StringBuilder();
            
            for (byte b : fragmentedBytes.get(i)) {
                String binaryRep = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
                payload.append(binaryRep);
            }
            
            StringBuilder output = new StringBuilder();
            output.append(String.format("%19s",Integer.toBinaryString(i & 0xFF)).replace(" ", "0"));
            output.append(payload);
            
            
            StringBuilder fragment = new StringBuilder();
            for (int j = 0; j < output.length(); j++) {
                char c = output.charAt(j);
                
                String last3 = fragment.length() >= 3
                        ? fragment.substring(fragment.length() - 3)
                        : "";
                
                boolean homopolymer = last3.length() == 3 &&
                        last3.charAt(0) == last3.charAt(1) &&
                        last3.charAt(1) == last3.charAt(2);
                
                if (c == '0') {
                    if (homopolymer) {
                        if (last3.equals("AAA"))
                            fragment.append('C');
                        else
                            fragment.append('A');
                    } else {
                        fragment.append(random.nextBoolean() ? 'A' : 'C');
                    }
                } else {
                    if (homopolymer) {
                        if (last3.equals("TTT"))
                            fragment.append('G');
                        else
                            fragment.append('T');
                    } else {
                        fragment.append(random.nextBoolean() ? 'T' : 'G');
                    }
                }
            }
            outputCode.add(fragment.toString());
        }
        return outputCode;
    }
}
