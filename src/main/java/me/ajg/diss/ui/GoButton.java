package me.ajg.diss.ui;

import me.ajg.diss.encoders.ChurchEncoder;
import me.ajg.diss.encoders.GoldmanEncoder;
import me.ajg.diss.encoders.dnaFountain.DNAFountainEncoder;

import javax.swing.*;
import java.util.ArrayList;

public class GoButton extends JButton {
    
    Config config;
    
    public GoButton(Config config) {
        this.setText("Go");
        this.config = config;
        
        this.addActionListener(e -> {
            switch (config.getChosenEncoder()) {
                case "church" -> ChurchEncoder.encode(config.getFilePath());
                case "goldman" -> {
                    ArrayList<String> files = new ArrayList<>();
                    files.add(config.getFilePath());
                    GoldmanEncoder.encode(files);
                }
                case "dnaFountain" -> DNAFountainEncoder.encode(config.getFilePath(), 42, 0, 32, 0.05f, 5);
            }
        });
        
    }
}
