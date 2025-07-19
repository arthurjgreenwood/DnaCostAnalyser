package me.ajg.diss.ui.upgrade;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.ajg.diss.sequencers.Illumina;
import me.ajg.diss.sequencers.Sequencer;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class SequencerSelector extends JPanel {
    
    private JComboBox<String> instrumentComboBox;
    private String selectedPlatform;
    private Map<String, List<String>> platfomMap;
    private List<Sequencer> customSequencers;
    private Config config;
    
    public SequencerSelector(Config config) {
        platfomMap = new HashMap<>();
        customSequencers = new ArrayList<>();
        this.config = config;
        loadInstruments();
        
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createTitledBorder("Select a sequencer: "));
        //TODO Load data to platform map
        
        JRadioButton illumina = new JRadioButton("Illumina");
        JRadioButton pacBio = new JRadioButton("Pacific Bioscience");
        JRadioButton onp = new JRadioButton("Oxford Nanopore");
        
        ButtonGroup group = new ButtonGroup();
        group.add(illumina);
        group.add(pacBio);
        group.add(onp);
        
        illumina.addActionListener(e -> updatePlatform("Illumina"));
        pacBio.addActionListener(e -> updatePlatform("Pacific Bioscience"));
        onp.addActionListener(e -> updatePlatform("Oxford Nanopore"));
        
        JPanel platformPanel = new JPanel();
        platformPanel.add(illumina);
        platformPanel.add(pacBio);
        platformPanel.add(onp);
        
        instrumentComboBox = new JComboBox<>();
        
        JButton addInstrumentButton = new JButton("Add Instrument");
        addInstrumentButton.setFocusable(false);
        addInstrumentButton.addActionListener(e -> {
           addInstrument();
        });
        
        instrumentComboBox.addActionListener(e -> {
            String selectedInstrumentName = (String) instrumentComboBox.getSelectedItem();
            if (selectedInstrumentName != null) {
                for (Sequencer seq : customSequencers) {
                    if (seq.getName().equals(selectedInstrumentName)) {
                        config.setChosenSequencer(seq);
                        break;
                    }
                }
            }
        });
        
        add(addInstrumentButton, BorderLayout.SOUTH);
        add(platformPanel, BorderLayout.NORTH);
        add(instrumentComboBox, BorderLayout.CENTER);
    }
    
    private void updatePlatform(String newPlatform) {
        selectedPlatform = newPlatform;
        instrumentComboBox.removeAllItems();
        for (String instrument : platfomMap.getOrDefault(newPlatform, new ArrayList<>())){
            instrumentComboBox.addItem(instrument);
        }
    }
    
    private void addInstrument() {
        //modal prevents clicking on the ancestor frame
        
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add a new instrument for " + selectedPlatform, true);
        dialog.setLayout(new GridLayout(12,3));
        dialog.setSize(500,500);
        
        //Adding relavent text Fields
        JLabel nameLabel = new JLabel("Instrument name: ");
        JTextField nameField = new JTextField();
        
        JLabel phredQualityLabel = new JLabel("Phred quality: ");
        JTextField phredQualityField = new JTextField();
        
        JLabel qualityConfidenceLabel = new JLabel("Quality confidence: ");
        JTextField qualityConfidenceField = new JTextField();
        
        JLabel inputMassLabel = new JLabel("Minimum input mass: ");
        JTextField inputMassField = new JTextField();
        
        JLabel flowCellPriceLabel = new JLabel("Flow cell price: ");
        JTextField flowCellPriceField = new JTextField();
        
        JLabel reagentPriceLabel = new JLabel("Reagent price: ");
        JTextField reagentPriceField = new JTextField();
        
        JLabel libraryPrepPriceLabel = new JLabel("Library Prep price: ");
        JTextField libraryPrepPriceField = new JTextField();
        
        JLabel indexingPriceLabel = new JLabel("Indexing price: ");
        JTextField indexingPriceField = new JTextField();
        
        JLabel maxMultiplexLabel = new JLabel("Maximum multiplex: ");
        JTextField maxMultiplexField = new JTextField();
        
        JLabel yieldPerFlowCell = new JLabel("Yield per flow cell: ");
        JTextField yieldPerFlowCellField = new JTextField();
        
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        saveButton.addActionListener(e -> {
           if (nameField.getText().trim().isEmpty()){
               new JOptionPane("Please enter the instrument name", JOptionPane.WARNING_MESSAGE);
               return;
               //TODO maybe put some more option panes here
           }
           
           String name = nameField.getText().trim();
           int phredQuality = Integer.parseInt(phredQualityField.getText().trim().replaceAll(",", "")); //allow commas in numbers
           double qualityConfidence = Double.parseDouble(qualityConfidenceField.getText().trim());
           double inputMass = Double.parseDouble(inputMassField.getText().trim());
           double flowCellPrice = Double.parseDouble(flowCellPriceField.getText().trim().replaceAll(",", ""));
           double reagentPrice = Double.parseDouble(reagentPriceField.getText().trim().replaceAll(",", ""));
           double libraryPrepPrice = Double.parseDouble(libraryPrepPriceField.getText().trim().replaceAll(",", ""));
           double indexingPrice = Double.parseDouble(indexingPriceField.getText().trim().replaceAll(",", ""));
           int maxMultiplex = Integer.parseInt(maxMultiplexField.getText().trim());
           double yield = Double.parseDouble(yieldPerFlowCellField.getText().trim());
           
           Gson gson = new Gson();
           
           Sequencer sequencer = null;
           if (selectedPlatform.equals("Illumina")) {
               sequencer = new Illumina(name, phredQuality, qualityConfidence, inputMass, flowCellPrice, reagentPrice,
                       libraryPrepPrice, indexingPrice, maxMultiplex, yield);
               platfomMap.computeIfAbsent(selectedPlatform, _ -> new ArrayList<>()).add(name);
               updatePlatform(selectedPlatform);
           }
           
            if (sequencer != null) {
                
                String json = gson.toJson(sequencer);
                
                FileWriter writer;
                try {
                    writer = new FileWriter("src/main/resources/me/ajg/diss/huffman/sequencers", true);
                    writer.write(json + "\n");
                    writer.close();
                } catch (IOException ex) {
                    System.out.println("sequencers.txt not found");
                }
            }
           //TODO save the data to a datastructure
            dialog.dispose();
        });
        
        cancelButton.addActionListener(e -> {
            dialog.dispose();
        });
        
        
        dialog.add(nameLabel);
        dialog.add(nameField);
        dialog.add(new JLabel());
        
        dialog.add(phredQualityLabel);
        dialog.add(phredQualityField);
        dialog.add(new JLabel("Q score e.g. Q40"));
        
        dialog.add(qualityConfidenceLabel);
        dialog.add(qualityConfidenceField);
        dialog.add(new JLabel("Fraction of reads with this confidence"));
        
        dialog.add(inputMassLabel);
        dialog.add(inputMassField);
        dialog.add(new JLabel("in ng"));
        
        dialog.add(flowCellPriceLabel);
        dialog.add(flowCellPriceField);
        dialog.add(new JLabel("Flow cell or SMRT cell (PacBio) ($)"));
        
        dialog.add(reagentPriceLabel);
        dialog.add(reagentPriceField);
        dialog.add(new JLabel("per run ($)"));
        
        dialog.add(libraryPrepPriceLabel);
        dialog.add(libraryPrepPriceField);
        dialog.add(new JLabel("per run ($)"));
        
        dialog.add(indexingPriceLabel);
        dialog.add(indexingPriceField);
        dialog.add(new JLabel("per run ($)"));
        
        dialog.add(maxMultiplexLabel);
        dialog.add(maxMultiplexField);
        dialog.add(new JLabel());
        
        dialog.add(yieldPerFlowCell);
        dialog.add(yieldPerFlowCellField);
        dialog.add(new JLabel("in gb"));
        
        dialog.add(new JLabel());
        dialog.add(new JLabel());
        dialog.add(new JLabel()); //These act as spacers, im not sure how to lay them out properly yet
        
        dialog.add(saveButton);
        dialog.add(new JLabel());
        dialog.add(cancelButton);
        
        
        dialog.setVisible(true);
        dialog.setLocationRelativeTo(this);
    }
    
    private void loadInstruments(){
        File input = new File("src/main/resources/me/ajg/diss/huffman/sequencers");
        Gson gson = new Gson();
        Scanner scanner;
        try{
           scanner = new Scanner(input);
           while(scanner.hasNextLine()){
               String line = scanner.nextLine();
               if (line.isBlank()) continue;
               
               JsonObject object = JsonParser.parseString(line).getAsJsonObject();
               String platform = object.get("platform").getAsString();
               switch (platform){
                   case "Illumina" -> customSequencers.add(gson.fromJson(line, Illumina.class));
                   //TODO add the others
                   
               }
               
           }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (Sequencer sequencer : customSequencers){
            platfomMap.computeIfAbsent(sequencer.getPlatform(), _ -> new ArrayList<>()).add(sequencer.getName());
        }
    }
    
}
