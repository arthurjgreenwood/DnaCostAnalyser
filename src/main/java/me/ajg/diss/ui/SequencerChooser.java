package me.ajg.diss.ui;

import javax.swing.*;

public class SequencerChooser extends JPanel {
    
    private JRadioButton illumina;
    private JRadioButton pacBio;
    private JRadioButton nanopore;
    private Config config;

    private String selectedSequencingPlatform;
    
    private JComboBox<String> dropdown;
    
    public SequencerChooser(Config config) {
        this.config = config;
        this.setBorder(BorderFactory.createTitledBorder("Select a Sequencing Platform: "));
        
        illumina = new JRadioButton("Illumina");
        pacBio = new JRadioButton("Pacific Bioscience");
        nanopore = new JRadioButton("Oxford Nanopore");
        
        ButtonGroup group = new ButtonGroup();
        
        group.add(illumina);
        group.add(pacBio);
        group.add(nanopore);
        
        dropdown = new JComboBox<>();
        dropdown.setToolTipText("Choose a system");
        
        
        illumina.addActionListener(e -> {
            dropdown.removeAllItems();
            dropdown.addItem("MiSeq");
            dropdown.addItem("HiSeq");
        });
        
        pacBio.addActionListener(e -> {
            dropdown.removeAllItems();
            dropdown.addItem("Tech 1");
            dropdown.addItem("Tech 2");
        });
        
        nanopore.addActionListener(e -> {
            dropdown.removeAllItems();
            dropdown.addItem("MinION");
            dropdown.addItem("GridION");
            dropdown.addItem("PromethION");
        });
        
        
        this.add(illumina);
        this.add(pacBio);
        this.add(nanopore);
        this.add(dropdown);
    }
}
