package me.ajg.diss.ui.upgrade;

import me.ajg.diss.encoders.ChurchEncoder;
import me.ajg.diss.encoders.dnaFountain.DNAFountainEncoder;
import me.ajg.diss.encoders.goldman.GoldmanEncoder;
import me.ajg.diss.fileManagement.FileUtil;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GoButton extends JButton {
    
    private final Config config;
    
    public GoButton(Config config) {
        this.setText("Go");
        this.setFocusable(false);
        this.config = config;
        
        this.addActionListener(e -> {
        
            if (this.config.getChosenSynthesis() == null){
                JOptionPane.showMessageDialog(null, "No synthesis selected", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (this.config.getChosenSequencer() == null){
                JOptionPane.showMessageDialog(null, "No sequencer selected", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (this.config.getFiles().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No files selected", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (this.config.getOutputDir() == null) {
                JOptionPane.showMessageDialog(null, "No output directory selected", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (this.config.getChosenEncoders() == null || this.config.getChosenEncoders().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No encoders selected", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            int goldmanFileCounter = 1; // Numbering the Goldman files if it exceeds 9 files
            List<OutputObject> outputObjects = new ArrayList<>();
            
            for (int i = 0; i < this.config.getFiles().size(); i++) {
                String filename = this.config.getFiles().get(i).getName();
                
                for (String encoder: this.config.getChosenEncoders()) {
                    if (encoder.equals("Goldman") && i % 9 == 0) {
                        List<String> filePaths = new ArrayList<>();
                        List<String> fileNames = new ArrayList<>();
                        long fileSize = 0;
                        for (int j = i; j < (i + 9) && j < this.config.getFiles().size(); j++) {
                            filePaths.add(this.config.getFiles().get(j).getPath());
                            fileNames.add(this.config.getFiles().get(j).getName());
                            fileSize+= this.config.getFiles().get(j).length();
                        }
                        List<String> oligos = GoldmanEncoder.encode(filePaths);
                        File outputName = FileUtil.encoderOutput(new File(this.config.getOutputDir(), "batch_" + goldmanFileCounter++).getPath(), "Goldman", oligos);
                        outputObjects.add(new OutputObject(this.config, outputName.getName(), fileNames.toString(), "Goldman", oligos, fileSize));
                        
                    }
                    if (encoder.equals("Church")) {
                        List<String> oligos = ChurchEncoder.encode(this.config.getFiles().get(i).getPath(), 10);
                        File outputName = FileUtil.encoderOutput(new File(this.config.getOutputDir(), filename).getPath(), "Church", oligos);
                        long fileSize = this.config.getFiles().get(i).length();
                        outputObjects.add(new OutputObject(this.config, outputName.getName(), filename, "Church", oligos, fileSize));
                    }
                    if (encoder.equals("DNA_Fountain")) {
                        List<String> oligos = DNAFountainEncoder.encode(this.config.getFiles().get(i).getPath(), 42, 0.03, 32, 0.01, 6);
                        File outputName = FileUtil.encoderOutput(new File(this.config.getOutputDir(), filename).getPath(), "DNA_Fountain", oligos);
                        long fileSize = this.config.getFiles().get(i).length();
                        outputObjects.add(new OutputObject(this.config, outputName.getName(), filename, "DNA_Fountain", oligos, fileSize));
                    }
                }
            }
            OutputObject.printStats(outputObjects);
        });
    }
}