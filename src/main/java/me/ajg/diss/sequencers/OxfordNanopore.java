package me.ajg.diss.sequencers;

public class OxfordNanopore extends Sequencer {
        
        public OxfordNanopore (String name, int qScore, double confidenceInQScore, double minimumInputMassNanoGrams,
                         double flowCellPrice, double reagentPrice, double libraryPrepPrice, double indexingPrice,
                         int maxMultiplex, double yieldPerFlowCellInGb){
            super("OxfordNanopore", name, qScore, confidenceInQScore, minimumInputMassNanoGrams, flowCellPrice, reagentPrice,
                    libraryPrepPrice, indexingPrice, maxMultiplex, yieldPerFlowCellInGb);
        }
    
}
