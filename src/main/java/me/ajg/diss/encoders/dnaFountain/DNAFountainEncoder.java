package me.ajg.diss.encoders.dnaFountain;

import me.ajg.diss.fileManagement.FileUtil;

import java.util.List;

/**
 * The DNA fountain encoder
 */

public class DNAFountainEncoder {
    
    /**
     * Converts a file to a List of encoded DNA bases
     * @param filePath the file to be encoded
     * @param INPUT_SEED the initial seed for the random number generator
     * @param redundancy the percentage of bytes to be generated above the required value
     * @param fragmentLength length of each fragment in bytes
     * @param maxGcDeviation the maximum acceptable GC deviation from 50%
     * @param maxHomopolymer the maximum homopolymer length
     * @return List of encoded oligos
     */
    public static List<String> encode(String filePath, int INPUT_SEED, double redundancy, int fragmentLength, double maxGcDeviation, int maxHomopolymer){
        //Compress to gzip
        byte[] gzipCompressed = FileUtil.gzipCompression(FileUtil.fileToByteArray(filePath));
        //fragment the data
        List<byte[]> fragmentedData = FileUtil.fragment(gzipCompressed, fragmentLength);
        //Generate DNA droplets
        LubyTransform lb = new LubyTransform(fragmentedData, INPUT_SEED, redundancy, fragmentLength, maxGcDeviation, maxHomopolymer);
        return lb.getDroplets();
    }
    
 
    
    
}
