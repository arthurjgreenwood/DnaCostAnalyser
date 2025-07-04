package me.ajg.diss.encoders;

import me.ajg.diss.fileManagement.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DNAFountainEncoder {
    
    public static void encode(String filePath, int length){
        //Compress to gzip
        FileUtil.gzipCompression(FileUtil.fileToByteArray(filePath));
    }
    
 
    public static List<byte[]> fragment(byte[] bytes, int length){
        List<byte[]> fragmentedOutput = new ArrayList<>();
        for (int i = 0; i< bytes.length; i+=length){
            int size = Math.min(bytes.length - i, length);
            byte[] tmp = new byte[size];
            System.arraycopy(bytes, i, tmp, 0, size);
            fragmentedOutput.add(tmp);
        }
        return fragmentedOutput;
    }
    
}
