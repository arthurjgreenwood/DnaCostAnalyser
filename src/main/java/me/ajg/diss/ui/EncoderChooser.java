package me.ajg.diss.ui;

import javax.swing.*;

public class EncoderChooser extends JPanel {
    
    private final Config config;
    
     public EncoderChooser(Config config) {
         this.config = config;
         this.setBorder(BorderFactory.createTitledBorder("Select any number of encoders: "));
         
         JCheckBox church = new JCheckBox();
         church.setFocusable(false);
         church.setText("Church");
         church.addActionListener(e -> {
             this.config.setChosenEncoder("church");
         });
         
         JCheckBox goldman = new JCheckBox();
         goldman.setFocusable(false);
         goldman.setText("Goldman");
         goldman.addActionListener(e -> {
             this.config.setChosenEncoder("goldman");
         });
         
         JCheckBox dnaFountain = new JCheckBox();
         dnaFountain.setFocusable(false);
         dnaFountain.setText("DNA Fountain");
         dnaFountain.addActionListener(e -> {
             this.config.setChosenEncoder("dnaFountain");
         });
         
         this.add(church);
         this.add(goldman);
         this.add(dnaFountain);
     }
}
