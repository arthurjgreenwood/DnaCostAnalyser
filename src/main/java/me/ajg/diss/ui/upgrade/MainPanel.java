package me.ajg.diss.ui.upgrade;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    public MainPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(Color.DARK_GRAY);
        this.setOpaque(true);
        
        add(new SequencerSelector(), BorderLayout.WEST);
    }
}
