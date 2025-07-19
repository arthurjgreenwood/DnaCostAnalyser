package me.ajg.diss.ui.upgrade;

import me.ajg.diss.sequencers.Sequencer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class to store data related to user selection in the program
 */
public class Config {
    
    private ArrayList<File> files;
    private ArrayList<String> chosenEncoders;
    private Sequencer chosenSequencer;
    private String chosenSynthesis;
    private File outputDir;
    
    public Config() {
        files = new ArrayList<>();
        chosenEncoders = new ArrayList<>();
    }
    
    
    public void addChosenEncoder(String chosenEncoder) {
        chosenEncoders.add(chosenEncoder);
    }
    public void removeChosenEncoder(String chosenEncoder) {
        chosenEncoders.remove(chosenEncoder);
    }
    public void setFiles(ArrayList<File> files) {
        this.files = files;
    }
    public void setChosenSequencer(Sequencer chosenSequencer) {
        this.chosenSequencer = chosenSequencer;
    }
    public void setChosenSynthesis(String chosenSynthesis) {
        this.chosenSynthesis = chosenSynthesis;
    }
    public void setOutputDir(File outputDir) {
        this.outputDir = outputDir;
    }
    
    public ArrayList<String> getChosenEncoders() {
        return chosenEncoders;
    }
    public Sequencer getChosenSequencer() {
        return chosenSequencer;
    }
    public String getChosenSynthesis() {
        return chosenSynthesis;
    }
    public ArrayList<File> getFiles() {
        return files;
    }
    public File getOutputDir() {
        return outputDir;
    }
    
    
}
