package me.ajg.diss.ui.upgrade;

import me.ajg.diss.encoders.ChurchEncoder;
import me.ajg.diss.encoders.goldman.GoldmanEncoder;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GoButton extends JButton {
    
    private final Config config;
    
    public GoButton(Config config, FileSelectionPanel fileSelectionPanel) {
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
            } else if (this.config.getChosenEncoders() == null) {
                JOptionPane.showMessageDialog(null, "No encoders selected", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            for (String encoder : this.config.getChosenEncoders()) {
                if (encoder.equals("Goldman")) {
                    //Goldman can handle 9 files simultaneously
                    //TODO output the files appropriately
                }
            }
            
            for (File file : this.config.getFiles()) {
                for (String encoder : this.config.getChosenEncoders()){
                    List<String> output;
                    if (encoder.equals("Church")){
                        output = new ArrayList<>(ChurchEncoder.encode(file.getAbsolutePath(), 12)); //Default 12 bytes
                    }
                    else if (encoder.equals("Goldman")){
                       
                        List
                        for (int i = 0; i<9; i++){
                        
                        }
                        output = new ArrayList<>(GoldmanEncoder.encode())
                    }
                    
                }
            }
            
        });
    }
}