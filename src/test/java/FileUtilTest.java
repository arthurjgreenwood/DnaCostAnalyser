import me.ajg.diss.fileManagement.FileUtil;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.function.ToDoubleBiFunction;
import java.util.zip.GZIPInputStream;

import static org.junit.jupiter.api.Assertions.*;

//TODO AI generated test class DO NOT SUBMIT


public class FileUtilTest {
    
    @Test
    public void testGzipCompressionOutputNotNull() {
        byte[] input = "This is a test string.".getBytes();
        byte[] compressed = FileUtil.gzipCompression(input);
        
        assertNotNull(compressed, "Compressed output should not be null");
        assertTrue(compressed.length > 0, "Compressed output should not be empty");
    }
    
    @Test
    public void testGzipCompressionActuallyCompresses() {
        byte[] input = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".getBytes();
        byte[] compressed = FileUtil.gzipCompression(input);
        
        assertTrue(compressed.length < input.length, "Compressed data should be smaller than input for repetitive strings");
    }
    
}

