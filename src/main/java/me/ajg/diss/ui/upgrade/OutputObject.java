package me.ajg.diss.ui.upgrade;

import me.ajg.diss.sequencers.SequencingStats;
import me.ajg.diss.synthesis.SynthesisStats;
import me.ajg.diss.synthesis.TwistOligoPool;
import org.jfree.chart.JFreeChart;

import java.util.ArrayList;
import java.util.List;

public class OutputObject {

    //Name of encoded file
    //The synthesis stats for that file
    //The Sequencing stats for that file
    //Graph of sequencing depth
    //The required sequencing depth
    //Total price for individual run and for grouped run e.g. price / maximum multiplex OR price / percentage of total run
    //A comparison (if any) between each encoders price
    
    private final Config config;
    private String filename;
    private SynthesisStats synthesisStats;
    private SequencingStats coverage;
    
    public OutputObject(Config config, String filename, String encodingAlgorithmUsed, List<String> oligos) {
        this.config = config;
        this.filename = filename;
        this.synthesisStats = new SynthesisStats(new ArrayList<>(List.of(filename)), encodingAlgorithmUsed, oligos,
                TwistOligoPool.oligoQuantity, TwistOligoPool.errorRate);
        this.coverage = new SequencingStats(this.config.getChosenSequencer(), oligos.size(), oligos.getFirst().length());
    }
    
     public static void printStats(List<OutputObject> outputObjects) {
     
     }
}
