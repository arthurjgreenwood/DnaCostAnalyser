package me.ajg.diss.ui;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JFrame {
    
    public HomePage() {
        this.setTitle("DNA Cost Analyser");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            System.out.println("Look and feel not supported");
        }
        
        this.setBackground(Color.DARK_GRAY);
        this.setVisible(true);
        
    }
}
