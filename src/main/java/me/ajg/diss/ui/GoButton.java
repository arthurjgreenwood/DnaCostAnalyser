package me.ajg.diss.ui;

import me.ajg.diss.Outputs;

import javax.swing.*;


public class GoButton extends JButton {
    
    public GoButton(Config config) {
        this.setText("Go");
        this.addActionListener(e -> {
            new Outputs(config);
        });
        
        
        
        
    }
}
