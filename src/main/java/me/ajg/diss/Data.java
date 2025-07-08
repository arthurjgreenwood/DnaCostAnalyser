package me.ajg.diss;

import me.ajg.diss.encoders.ChurchEncoder;
import me.ajg.diss.fileManagement.FileUtil;

public class Data {
    
    byte[] churchBytes;
    String fileExtension;
    String dnaRepresentation;
    
    public Data(String filePath, String name){
        fileExtension = FileUtil.getFileExtension(filePath);
        churchBytes = FileUtil.fileToByteArray(filePath);
    }
   
    
}
