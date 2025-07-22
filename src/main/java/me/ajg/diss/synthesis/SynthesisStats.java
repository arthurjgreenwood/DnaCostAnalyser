package me.ajg.diss.synthesis;

import me.ajg.diss.ui.upgrade.Config;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class SynthesisStats {
    
    private static final double AVOGADRO = 6.022e23; //Avagadro's constant
    private static final double NUCLEOTIDE_WEIGHT = 318 ; //318 g/mol expected weight of a nucleotide
    
    List<String> fileNames;
    String encodingAlgorithmUsed;
    List<String> oligos;
    int numberOfOligos;
    double expectedMass;
    int expectedCopiesOfEachOligo;
    double molsPerOligo;
    double synthesisErrorRate;
    int oligoLength;
    
    
    /**
     * Constructor for Encoding Algorithm Output
     *
     * @param fileNames             the file(s) converted
     * @param encodingAlgorithmUsed the encoder used
     * @param oligos                the encoded data
     * @param molsPerOligo          the number of moles of each oligo produced by the synthesis
     */
    public SynthesisStats(List<String> fileNames, String encodingAlgorithmUsed, List<String> oligos, double molsPerOligo, double synthesisErrorRate) {
        
        this.fileNames = fileNames;
        this.encodingAlgorithmUsed = encodingAlgorithmUsed;
        this.oligos = oligos;
        numberOfOligos = oligos.size();
        this.molsPerOligo = molsPerOligo;
        oligoLength = oligos.getFirst().length(); //All oligos are equal length
        expectedMass = calcExpectedMass();
        expectedCopiesOfEachOligo = (int) Math.round(molsPerOligo * AVOGADRO);
        this.synthesisErrorRate = synthesisErrorRate;
    }
    
    private double calcExpectedMass() {
        double expectedMassPerOligoInGrams = NUCLEOTIDE_WEIGHT * (oligoLength * molsPerOligo);
        return expectedMassPerOligoInGrams * numberOfOligos;
    }
    
    public String getOutput() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(true);
        nf.setMaximumFractionDigits(5);
        TwistOligoPool twistOligoPool = new TwistOligoPool();
        
        return "File: " + fileNames.toString() +
                " (" + encodingAlgorithmUsed + " encoded)\n" +
                "Produced " + nf.format(expectedCopiesOfEachOligo) + " copies of each oligo\n" +
                "Expected mass: " + formatExpectedMass(expectedMass) + "\n" +
                "Synthesis error: " + nf.format(synthesisErrorRate) + "\n" +
                "Expected correct oligos: " + nf.format(expectedCopiesOfEachOligo-(expectedCopiesOfEachOligo*synthesisErrorRate*oligoLength)) + "\n" +
                "Synthesis cost: " + "$" + twistOligoPool.getSpecificPrice(oligoLength, numberOfOligos) + "\n";
    }
    
    
    
    /**
     * Formats the expected mass into SI unit representation
     * @return mass + units
     */
    public static String formatExpectedMass(double mass) {
        String unit = "g";
        if (mass < 1e-12){
            mass *= 1e15;
            unit = "fg";
        } else if (mass < 1e-9){
            mass *= 1e12;
            unit = "pg";
        } else if (mass < 1e-6){
            mass *= 1e9;
            unit = "ng";
        } else if (mass < 1e-3){
            mass *= 1e6;
            unit = "µg";
        } else if (mass < 1){
            mass *= 1e3;
            unit = "mg";
        }
        return String.format("%.3f %s", mass, unit);
    }
    
}
