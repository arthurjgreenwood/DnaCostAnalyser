package me.ajg.diss.ui;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JFrame {
    
    Config config;
    
    public HomePage() {
        config = new Config();
        this.setTitle("DNA Cost Analyser");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLayout(new FlowLayout());
        
        
        
        //Add Components to the main frame
        this.add(new FileSelector(this.config));
        this.add(new EncoderChooser(this.config));
        this.add(new SequencerChooser(this.config));
        
        this.add(new SynthesisChooser(this.config));
        this.add(new GoButton(this.config));
        
        this.getContentPane().setBackground(Color.DARK_GRAY);
        this.setVisible(true);
        
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            System.out.println("Look and feel not supported");
        }
        
    }
    
}
