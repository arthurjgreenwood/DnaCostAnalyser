package me.ajg.diss.ui.upgrade;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    ToolBar toolBar;
    FileSelectionPanel fileSelectionPanel;
    MainPanel mainPanel;
    Config config;
    
    public MainFrame() {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            System.out.println("Look and feel not supported");
        }
        config = new Config();
        
        this.toolBar = new ToolBar(this.config);
        this.fileSelectionPanel = new FileSelectionPanel(this.config);
        this.mainPanel = new MainPanel(this.config);
        
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.add(this.toolBar, BorderLayout.NORTH);
        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        JLabel titleLabel = new JLabel();
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 35));
        titleLabel.setText("DNA Data Storage Cost Analyser");
        titlePanel.add(titleLabel);
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        
        
        this.setTitle("Home Page");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(650, 475);
        this.setLayout(new BorderLayout(5, 5));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.getContentPane().setBackground(Color.DARK_GRAY);
        
        this.add(headerPanel, BorderLayout.NORTH);
        this.add(fileSelectionPanel, BorderLayout.EAST);
        this.add(mainPanel, BorderLayout.WEST);
    }
}
