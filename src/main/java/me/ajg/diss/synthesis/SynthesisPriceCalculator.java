package me.ajg.diss.synthesis;

import java.util.List;

public class SynthesisPriceCalculator {
    
    private String tech;
    private List<String> outputDNA;
    
    
    public SynthesisPriceCalculator(List<String> outputDNA, String tech) {
        this.tech = tech;
        this.outputDNA = outputDNA;
    }
    
    public double getPrice(){
        int numberOfOligos = outputDNA.size();
        int oligoSize = outputDNA.getFirst().length();
        TwistOligoPool oligoPool = new TwistOligoPool();
        if (tech.equals("twist")){
            return oligoPool.getSpecificPrice(oligoSize, numberOfOligos);
        }
        return -1;
    }
}
