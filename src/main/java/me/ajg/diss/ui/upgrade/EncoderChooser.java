package me.ajg.diss.ui.upgrade;

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
             if (church.isSelected())
                this.config.addChosenEncoder("Church");
             else
                 this.config.removeChosenEncoder("Church");
         });
         
         JCheckBox goldman = new JCheckBox();
         goldman.setFocusable(false);
         goldman.setText("Goldman");
         goldman.addActionListener(e -> {
             if (goldman.isSelected())
                this.config.addChosenEncoder("Goldman");
             else
                 this.config.removeChosenEncoder("Goldman");
         });
         
         JCheckBox dnaFountain = new JCheckBox();
         dnaFountain.setFocusable(false);
         dnaFountain.setText("DNA Fountain");
         dnaFountain.addActionListener(e -> {
             if (dnaFountain.isSelected())
                this.config.addChosenEncoder("DNA_Fountain");
             else
                 this.config.removeChosenEncoder("DNA_Fountain");
         });
         
         this.add(church);
         this.add(goldman);
         this.add(dnaFountain);
     }
}
