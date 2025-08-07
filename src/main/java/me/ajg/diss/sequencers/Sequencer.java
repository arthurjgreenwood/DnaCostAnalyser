package me.ajg.diss.sequencers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a Generic Sequencer
 */
public abstract class Sequencer {
    
    private String platform;
    private String name;
    private int qScore;
    private double confidenceInQScore;
    private double minimumInputMassNanoGrams;
    private double flowCellPrice;
    private double reagentPrice;
    private double libraryPrepPrice;
    private double indexingPrice;
    private int maxMultiplex;
    private double yieldPerFlowCellInGb;
    
    
    
    public Sequencer (){} //This is for Gson
    
    public Sequencer (String platform, String name, int qScore, double confidenceInQScore, double minimumInputMassNanoGrams,
                      double flowCellPrice, double reagentPrice, double libraryPrepPrice, double indexingPrice,
                      int maxMultiplex, double yieldPerFlowCellInGb) {
        this.name = name;
        this.platform = platform;
        this.qScore = qScore;
        this.confidenceInQScore = confidenceInQScore;
        this.minimumInputMassNanoGrams = minimumInputMassNanoGrams;
        this.flowCellPrice = flowCellPrice;
        this.reagentPrice = reagentPrice;
        this.libraryPrepPrice = libraryPrepPrice;
        this.indexingPrice = indexingPrice;
        this.maxMultiplex = maxMultiplex;
        this.yieldPerFlowCellInGb = yieldPerFlowCellInGb;
    }
    
    public double calculateErrorRate(){
        double baseError = Math.pow(10, (double) (-qScore) /10);
        double remainingError = Math.pow(10, (double) (-qScore+10)/10); //Assumes 10x lower Q score for unknowns
        return (baseError*(confidenceInQScore/100))+(remainingError*(1-(confidenceInQScore/100)));
    }
    
    public String getPlatform() {
        return platform;
    }
    public void setPlatform(String platform) {
        this.platform = platform;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setQScore(int qScore){
        this.qScore = qScore;
    }
    public int getQScore(){
        return qScore;
    }
    public void setConfidenceInQScore(double confidenceInQScore){
        this.confidenceInQScore = confidenceInQScore;
    }
    public double getConfidenceInQScore(){
        return confidenceInQScore;
    }
    public void setMinimumInputMassNanoGrams(double minimumInputMassNanoGrams){
        this.minimumInputMassNanoGrams = minimumInputMassNanoGrams;
    }
    public double getMinimumInputMassNanoGrams(){
        return minimumInputMassNanoGrams;
    }
    public void setFlowCellPrice(double flowCellPrice){
        this.flowCellPrice = flowCellPrice;
    }
    public double getFlowCellPrice(){
        return flowCellPrice;
    }
    public void setReagentPrice(double reagentPrice){
        this.reagentPrice = reagentPrice;
    }
    public double getReagentPrice(){
        return reagentPrice;
    }
    public void setLibraryPrepPrice(double libraryPrepPrice){
        this.libraryPrepPrice = libraryPrepPrice;
    }
    public double getLibraryPrepPrice(){
        return libraryPrepPrice;
    }
    public void setIndexingPrice(double indexingPrice){
        this.indexingPrice = indexingPrice;
    }
    public double getIndexingPrice(){
        return indexingPrice;
    }
    public void setMaxMultiplex(int maxMultiplex){
        this.maxMultiplex = maxMultiplex;
    }
    public int getMaxMultiplex(){
        return maxMultiplex;
    }
    public void setYieldPerFlowCellInGb(double yieldPerFlowCellInGb){
        this.yieldPerFlowCellInGb = yieldPerFlowCellInGb;
    }
    public double getYieldPerFlowCellInGb(){
        return yieldPerFlowCellInGb;
    }
    
    
    
}
