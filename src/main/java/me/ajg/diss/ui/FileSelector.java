package me.ajg.diss.ui;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

public class FileSelector extends JPanel {
    
    private String fileName;
    private Icon icon;
    
    private final JLabel selectedFileLabel;
    
    private final Config config;
    
    public FileSelector(Config config) {
        this.config = config;
        JButton selectButton = new JButton("Select File");
        selectButton.setVisible(true);
        selectedFileLabel = new JLabel();
        
        selectButton.addActionListener(e ->{
            JFileChooser chooser = new JFileChooser();
            int response = chooser.showOpenDialog(null); //select file to open
            if (response == JFileChooser.APPROVE_OPTION) {
                this.config.setFilePath(chooser.getSelectedFile().getAbsolutePath());
                fileName = chooser.getSelectedFile().getName();
                icon = FileSystemView.getFileSystemView().getSystemIcon(chooser.getSelectedFile()); //Gets the file icon
                selectedFileLabel.setText(fileName);
                selectedFileLabel.setIcon(icon);
                selectedFileLabel.setVisible(true);
            }
        });
        add(selectButton);
        add(selectedFileLabel);
    }
    
  
    public String getFileName() {
        return fileName;
    }
    
}
