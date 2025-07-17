package me.ajg.diss.sequencers;

public class Illumina extends Sequencer{
    
    public Illumina (String name, int qScore, double confidenceInQScore, double minimumInputMassNanoGrams,
                     double flowCellPrice, double reagentPrice, double libraryPrepPrice, double indexingPrice,
                     int maxMultiplex, double yieldPerFlowCellInGb){
        super("Illumina", name, qScore, confidenceInQScore, minimumInputMassNanoGrams, flowCellPrice, reagentPrice,
                libraryPrepPrice, indexingPrice, maxMultiplex, yieldPerFlowCellInGb);
    }
}
