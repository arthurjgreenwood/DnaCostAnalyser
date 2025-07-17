package me.ajg.diss.ui;

import javax.swing.*;
import java.awt.*;

public class ToolBar extends JMenuBar {
    public ToolBar() {
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu helpMenu = new JMenu("Help");
        fileMenu.setForeground(Color.WHITE);
        editMenu.setForeground(Color.WHITE);
        helpMenu.setForeground(Color.WHITE);
        this.setBackground(new Color(40,40,40));
        this.setOpaque(true);
        
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.add(fileMenu);
        this.add(editMenu);
        this.add(helpMenu);
    }
}
