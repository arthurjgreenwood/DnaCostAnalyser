package me.ajg.diss.ui.upgrade;

import me.ajg.diss.sequencers.SequencingStats;
import me.ajg.diss.synthesis.SynthesisStats;
import me.ajg.diss.synthesis.TwistOligoPool;
import org.jfree.chart.JFreeChart;

import javax.lang.model.element.NestingKind;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OutputObject {

    //Name of encoded file
    //The synthesis stats for that file
    //The Sequencing stats for that file
    //Graph of sequencing depth
    //The required sequencing depth
    //Total price for individual run and for grouped run e.g. price / maximum multiplex OR price / percentage of total run
    //A comparison (if any) between each encoder's price
    
    private final Config config;
    private String filesEncoded;
    private String encodedFileName;
    private SynthesisStats synthesisStats;
    private SequencingStats coverage;
    private String encodingAlgorithmUsed;
    private long fileSize;
    private long encodingDuration;
    
    public OutputObject(Config config, String encodedFileName, String filesEncoded, String encodingAlgorithmUsed, List<String> oligos, long fileSize, long encodingDuration) {
        this.config = config;
        this.encodedFileName = encodedFileName;
        this.encodingAlgorithmUsed = encodingAlgorithmUsed;
        this.filesEncoded = filesEncoded;
        this.fileSize = fileSize;
        this.synthesisStats = new SynthesisStats(new ArrayList<>(List.of(filesEncoded)), encodingAlgorithmUsed, oligos,
                TwistOligoPool.oligoQuantity, TwistOligoPool.errorRate);
        this.coverage = new SequencingStats(this.config.getChosenSequencer(), oligos.size(), oligos.getFirst().length());
        this.encodingDuration = encodingDuration;
    }
    
     public static void printStats(List<OutputObject> outputObjects) {
        StringBuilder output = new StringBuilder();
        File outputDir  = outputObjects.getFirst().config.getOutputDir();
        for (OutputObject outputObject : outputObjects) {
            double requiredSuccessRate = switch (outputObject.encodingAlgorithmUsed){
                case "Goldman" ->.99;
                case "DNA_Fountain" -> .9;
                default -> .999; //Change these for researched values
            };
            
            output.append(outputObject.encodedFileName).append("\n");
            output.append(outputObject.synthesisStats.getOutput());
            output.append(String.format("Sequencing Cost (single run): $%.2f%n",
                    outputObject.coverage.calculateSequencingCost()));
            output.append(String.format("Sequencing Cost (multiplexed): $%.2f%n",
                    outputObject.coverage.calculateSequencingCost() /
                            outputObject.config.getChosenSequencer().getMaxMultiplex()));
            output.append("Minimum Coverage: ").append(outputObject.coverage.getCoverageForSpecifiedSuccessRate(requiredSuccessRate)).append("\n");
            output.append(String.format("Total Cost (single run): $%.2f%n",
                    outputObject.coverage.calculateSequencingCost()+outputObject.synthesisStats.getSynthesisCost()));
            output.append(String.format("Total Cost (multiplexed): $%.2f%n",
                    (outputObject.coverage.calculateSequencingCost() /
                            outputObject.config.getChosenSequencer().getMaxMultiplex())
                            +outputObject.synthesisStats.getSynthesisCost()));
            output.append(String.format("Efficiency: %.2fbits/nt", (outputObject.fileSize*8.0)/((long) outputObject.synthesisStats.getNumberOfOligos() *
                    outputObject.synthesisStats.getOligoLength()))).append("\n");
            output.append(String.format("Encoding time: %d ms%n", outputObject.encodingDuration)).append("\n\n");
        }
         FileWriter writer;
         try {
             writer = new FileWriter(new File(outputDir, "Statistics.txt"), true);
             writer.write(output + "\n");
             writer.close();
         } catch (IOException ex) {
             System.out.println("not found");
         }
     }
}
