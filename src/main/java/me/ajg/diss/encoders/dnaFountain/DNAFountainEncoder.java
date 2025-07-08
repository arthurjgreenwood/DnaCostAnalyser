package me.ajg.diss.encoders.dnaFountain;

import me.ajg.diss.fileManagement.FileUtil;
import org.w3c.dom.CDATASection;

import java.util.ArrayList;
import java.util.List;

public class DNAFountainEncoder {
    
    public static List<String> encode(String filePath, int INPUT_SEED, double redundancy, int fragmentLength, float maxGcDeviation, int maxHomopolymer){
        //Compress to gzip
        byte[] gzipCompressed = FileUtil.gzipCompression(FileUtil.fileToByteArray(filePath));
        //fragment the data
        List<byte[]> fragmentedData = FileUtil.fragment(gzipCompressed, fragmentLength);
        //Generate DNA droplets
        LubyTransform lb = new LubyTransform(fragmentedData, INPUT_SEED, redundancy, fragmentLength, maxGcDeviation, maxHomopolymer);
        return lb.getDroplets();
    }
    
 
    
    
}
