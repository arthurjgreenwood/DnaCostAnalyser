package me.ajg.diss.sequencers;

import java.util.TreeMap;

public class SequencingStats {
    
    int numberOfOligos;
    int oligoLength;
    Sequencer sequencer;
    int maximumDepth; //Represents the maximum number possible depth given a flow cells maximum output
    TreeMap<Integer, Double> depthMap;
    
    public SequencingStats(Sequencer sequencer, int numberOfOligos, int oligoLength) {
        this.sequencer = sequencer;
        this.numberOfOligos = numberOfOligos;
        this.oligoLength = oligoLength;
        this.maximumDepth = (int) Math.floor((this.sequencer.getYieldPerFlowCellInGb()*10e9)/(double) oligoLength);
        calculateDepthErrorRates();
    }
    
    public void calculateDepthErrorRates(){
        
        double errorRate = sequencer.calculateErrorRate();
        
        TreeMap<Integer, Double> outputMap = new TreeMap<>(); //Represents Depth to sequencing percentage relationship
        
        for (int depth = 0; depth <= 100; depth++) {
            double probPerfectRead = Math.pow(1-errorRate, oligoLength);
            double probSuccess = 1 - Math.pow(1-probPerfectRead, depth);
            double probMissed = Math.exp(-depth); //Probability an oligo escapes sequencing
            double probTotalSuccess = (1 - probMissed)*probSuccess;
        
            outputMap.put(depth, probTotalSuccess);
        }
        this.depthMap = outputMap;
    }
    
    public int getCoverageForSpecifiedSuccessRate(double requiredSuccessRate){
        for (var entry : depthMap.entrySet()) {
            if (entry.getValue()>= requiredSuccessRate) {
                return entry.getKey();
            }
        }
        return 0;
    }
    
    public double calculateSequencingCost(){
        double totalCost = 0;
        totalCost+= sequencer.getFlowCellPrice();
        totalCost+= sequencer.getReagentPrice();
        totalCost+= sequencer.getLibraryPrepPrice();
        totalCost+= sequencer.getIndexingPrice();
        return totalCost;
    }
}
