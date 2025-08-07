package me.ajg.diss.sequencers;

import me.ajg.diss.synthesis.TwistOligoPool;

import java.util.TreeMap;

/**
 * A class used to generate the output statistics for sequencers
 */
public class SequencingStats {
    
    int numberOfOligos;
    int oligoLength;
    Sequencer sequencer;
    int maximumDepth; //Represents the maximum number possible depth given a flow cells maximum output
    public TreeMap<Integer, Double> depthMap;
    
    /**
     * Constructor
     * @param sequencer The sequencer object to use
     * @param numberOfOligos the number of oligos
     * @param oligoLength the size of each oligo
     */
    
    public SequencingStats(Sequencer sequencer, int numberOfOligos, int oligoLength) {
        this.sequencer = sequencer;
        this.numberOfOligos = numberOfOligos;
        this.oligoLength = oligoLength;
        this.maximumDepth = (int) Math.floor((this.sequencer.getYieldPerFlowCellInGb()*10e9)/(double) oligoLength);
        calculateDepthErrorRates();
    }
    
    /**
     * Creates a Map associating a specific sequencing depth with the probability of successful oligo recovery
     */
    
    public void calculateDepthErrorRates(){
        
        double errorRate = sequencer.calculateErrorRate() + TwistOligoPool.errorRate ;
        TreeMap<Integer, Double> outputMap = new TreeMap<>(); //Represents Depth to sequencing percentage relationship
        
        for (int depth = 0; depth <= 200; depth++) {
            double probPerfectRead = Math.pow(1-errorRate, oligoLength);
            double probSuccess = 1 - Math.pow(1-probPerfectRead, depth);
            double probMissed = Math.exp(-depth); //Probability an oligo escapes sequencing
            double probTotalSuccess = (1 - probMissed)*probSuccess;
        
            outputMap.put(depth, probTotalSuccess);
        }
        this.depthMap = outputMap;
    }
    
    /**
     * Obtains the required coverage for a specified success rate from the generated depth map
     * @param requiredSuccessRate the desired success rate
     * @return the required sequencing coverage
     */
    public int getCoverageForSpecifiedSuccessRate(double requiredSuccessRate){
        for (var entry : depthMap.entrySet()) {
            if (entry.getValue()>= requiredSuccessRate) {
                System.out.println("Hello from depth map");
                System.out.println(depthMap);
                return entry.getKey();
            }
        }
        return 0;
    }
    
    /**
     * Calculates the total cost of sequencing
     * @return total cost
     */
    
    public double calculateSequencingCost(){
        double totalCost = 0;
        totalCost+= sequencer.getFlowCellPrice();
        totalCost+= sequencer.getReagentPrice();
        totalCost+= sequencer.getLibraryPrepPrice();
        totalCost+= sequencer.getIndexingPrice();
        return totalCost;
    }
}
