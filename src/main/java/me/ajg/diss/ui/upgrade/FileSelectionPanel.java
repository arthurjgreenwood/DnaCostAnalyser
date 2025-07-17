package me.ajg.diss.ui.upgrade;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileSelectionPanel extends JPanel {
    
    private final DefaultListModel<String> listModel;
    private final JList<String> list;
    private final List<File> files;
    
    public FileSelectionPanel() {
        files = new ArrayList<>();
        setLayout(new BorderLayout());
        this.listModel = new DefaultListModel<>();
        this.list = new JList<>(listModel);
        
        add(new JScrollPane(list), BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Files");
        JButton removeButton = new JButton("Remove Selected");
        
        addButton.setFocusPainted(false);
        removeButton.setFocusPainted(false);
        
        addButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setMultiSelectionEnabled(true);
            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                for (File f : chooser.getSelectedFiles()) {
                    String filename = f.getName();
                    if (!listModel.contains(filename)) {
                        listModel.addElement(filename);
                        files.add(f);
                    }
                }
            }
        });
        
        removeButton.addActionListener(e -> {
            for (String selected : list.getSelectedValuesList()) {
                listModel.removeElement(selected);
                files.removeIf(f -> f.getName().equals(selected));
            }
        });
        
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public List<File> getSelectedFiles() {
        return Collections.unmodifiableList(files);
    }
}
