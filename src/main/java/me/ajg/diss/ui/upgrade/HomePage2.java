package me.ajg.diss.ui.upgrade;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;

public class HomePage2 extends JFrame {
    ToolBar toolBar;
    FileSelectionPanel fileSelectionPanel;
    MainPanel mainPanel;
    Config config;
    
    public HomePage2() {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            System.out.println("Look and feel not supported");
        }
        config = new Config();
        
        this.toolBar = new ToolBar(this.config);
        this.fileSelectionPanel = new FileSelectionPanel(this.config);
        this.mainPanel = new MainPanel(this.config);
        
        
        
        this.setTitle("Home Page");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLayout(new BorderLayout(5, 5));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.getContentPane().setBackground(Color.DARK_GRAY);
        
        this.add(toolBar, BorderLayout.NORTH);
        this.add(fileSelectionPanel, BorderLayout.EAST);
        this.add(mainPanel, BorderLayout.WEST);
        
        
    }
}
