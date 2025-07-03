package me.ajg.diss.fileManagement;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.GZIPOutputStream;

/**
 * A collection of static methods for manipulating files for DNA encoding.
 * This is to abstract the complicated file processes from the main logic of the DNA encoders.
 * @author Arthur Greenwood
 */
public class FileUtil {
    
    /**
     * Coverts a file from a specified path into a byte array
     * This implementation is based on the Converting a File to a Byte Array section of:
     * Vultur 2024
     *
     * @param filePath file for conversion
     * @return byte[] of file contents
     */
    public static byte[] fileToByteArray(String filePath){
            byte[] fileData = null;
            try {
                fileData = Files.readAllBytes(Paths.get(filePath));
            } catch (IOException e) {
                System.out.println("An error occurred reading file " + filePath);
            }
            return fileData;
    }
    
    /**
     * Returns the file extension
     * @param filePath file to find extension of
     * @return String of file extension
     */
    public static String getFileExtension(String filePath){
        return filePath.substring(filePath.lastIndexOf('.') + 1);
    }
    
    
    /**
     * Compresses a byte[] using the Gzip algorithm, required for Goldman Encoding
     *
     * @param bytes the byte[] to be compressed
     * @return compressed byte[]
     */
    public static byte[] gzipCompression(byte[] bytes){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        
        try(GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)){ //Try-with-resources will automatically call close()
            gzipOutputStream.write(bytes, 0, bytes.length); //send the whole byte array to the output stream
        } catch (IOException e) {
            System.out.println("An error occurred loading gzip");;
        }
        return byteArrayOutputStream.toByteArray();
    }
    
    /**
     * This is a utility method for converting a byte[] to String representation of the bytes
     * Because Java uses signed bytes, the maximum value is 127, this causes issues for comparison
     * This method is called whenever the bits need to be analysed / encoded
     * @param bytes byte[] to convert
     * @return a String[] of the bytes
     */
    public static String[] bytesToString(byte[] bytes){
        String[] strings = new String[bytes.length];
        for (int i = 0; i < bytes.length; i++){
            strings[i] = String.format("%8s", Integer.toBinaryString(bytes[i] & 0xFF)).replace(' ', '0');
        }
        return strings;
    }
}
