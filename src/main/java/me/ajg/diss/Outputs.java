package me.ajg.diss;

import me.ajg.diss.encoders.ChurchEncoder;
import me.ajg.diss.encoders.dnaFountain.DNAFountainEncoder;
import me.ajg.diss.encoders.goldman.GoldmanEncoder;
import me.ajg.diss.synthesis.SynthesisPriceCalculator;
import me.ajg.diss.ui.Config;

import java.util.ArrayList;
import java.util.List;

public class Outputs {

    private final Config config;
    private List<String> outputDNA;
    
    public Outputs(Config config){
        this.config = config;
        runEncoder();
        System.out.println("Output Fragments: " + outputDNA);
        System.out.println("Expected Synthesis Cost: " + getTotalPrice());
        System.out.println("Number of oligos: " + outputDNA.size());
        
    }
    
    public void runEncoder(){
        switch (config.getChosenEncoder()) {
            case "church" -> outputDNA = ChurchEncoder.encode(config.getFilePath(), 10); //10 bytes by default
            case "goldman" -> {
                ArrayList<String> files = new ArrayList<>();
                files.add(config.getFilePath());
                outputDNA = GoldmanEncoder.encode(files);
            }
            case "dnaFountain" -> outputDNA = DNAFountainEncoder.encode(config.getFilePath(), 42, 0, 32, 0.05f, 5);
        }
    }
    
    
    
    public double getTotalPrice(){
        double price = 0;
        
        SynthesisPriceCalculator synthesisPriceCalculator = new SynthesisPriceCalculator(outputDNA, config.getChosenSynthesis());
        price+= synthesisPriceCalculator.getPrice();
        
        return price;
    }
    
}
