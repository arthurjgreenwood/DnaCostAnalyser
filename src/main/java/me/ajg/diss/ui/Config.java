package me.ajg.diss.ui;

/**
 * A utility class to store data related to user selection in the program
 */
public class Config {
    
    private String filePath;
    private String chosenEncoder;
    private String chosenSequencer;
    private String chosenSynthesis;
    private String tech;
    
    public String getFilePath() {
        return filePath;
    }
    public String getChosenEncoder() {
        return chosenEncoder;
    }
    public String getChosenSequencer() {
        return chosenSequencer;
    }
    public String getChosenSynthesis() {
        return chosenSynthesis;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public void setChosenEncoder(String chosenEncoder) {
        this.chosenEncoder = chosenEncoder;
    }
    public void setChosenSequencer(String chosenSequencer) {
        this.chosenSequencer = chosenSequencer;
    }
    public void setChosenSynthesis(String chosenSynthesis) {
        this.chosenSynthesis = chosenSynthesis;
    }
    
}
