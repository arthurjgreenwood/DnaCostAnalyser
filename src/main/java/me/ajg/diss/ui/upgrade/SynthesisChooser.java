package me.ajg.diss.ui.upgrade;

import javax.swing.*;

public class SynthesisChooser extends JPanel {
    
    Config config;
    
    public SynthesisChooser(Config config) {
        this.config = config;
        
        this.setBorder(BorderFactory.createTitledBorder("Choose an OligoPool Synthesis Provider"));
        
        ButtonGroup bg = new ButtonGroup();
        JRadioButton twist = new JRadioButton("Twist Bioscience");
        twist.addActionListener(e -> {
            this.config.setChosenSynthesis("twist");
        });
        bg.add(twist);
        
        this.add(twist);
        
    }
}
