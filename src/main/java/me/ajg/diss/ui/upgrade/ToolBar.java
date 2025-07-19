package me.ajg.diss.ui.upgrade;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ToolBar extends JMenuBar {
    
    private final Config config;
    
    public ToolBar(Config config) {
        this.config = config;
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu helpMenu = new JMenu("Help");
        fileMenu.setForeground(Color.WHITE);
        editMenu.setForeground(Color.WHITE);
        helpMenu.setForeground(Color.WHITE);
        this.setBackground(new Color(40,40,40));
        this.setOpaque(true);
        
        JMenuItem selectOutputDir = getJMenuItem();
        fileMenu.add(selectOutputDir);
        
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.add(fileMenu);
        this.add(editMenu);
        this.add(helpMenu);
    }
    
    private JMenuItem getJMenuItem() {
        JMenuItem selectOutputDir = new JMenuItem("Select output directory");
        selectOutputDir.setForeground(Color.WHITE);
        selectOutputDir.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setDialogTitle("Select output directory");
            
            int returnVal = chooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File outputDir = chooser.getSelectedFile();
                if (outputDir != null && outputDir.exists() && outputDir.isDirectory()) {
                    this.config.setOutputDir(outputDir);
                }
            }
        });
        return selectOutputDir;
    }
}
