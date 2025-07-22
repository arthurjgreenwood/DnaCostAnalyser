package me.ajg.diss.sequencers;

public class PacificBioscience extends Sequencer {
        
        public PacificBioscience (String name, int qScore, double confidenceInQScore, double minimumInputMassNanoGrams,
                         double flowCellPrice, double reagentPrice, double libraryPrepPrice, double indexingPrice,
                         int maxMultiplex, double yieldPerFlowCellInGb){
            super("PacificBioscience", name, qScore, confidenceInQScore, minimumInputMassNanoGrams, flowCellPrice, reagentPrice,
                    libraryPrepPrice, indexingPrice, maxMultiplex, yieldPerFlowCellInGb);
        }
    
}
