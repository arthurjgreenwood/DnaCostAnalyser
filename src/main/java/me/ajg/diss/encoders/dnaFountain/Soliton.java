package me.ajg.diss.encoders.dnaFountain;

public class Soliton{
    
    private int K;
    double[] rho;
    double[] tau;
    double[] mu;
    double delta;
    double c;
    double Z = 0; //Normalisation value
    
    public Soliton(int numberOfFragments, double delta, double c){
        K = numberOfFragments;
        rho = new double[K];
        tau = new double[K];
        mu = new double[K];
        this.delta = delta;
        this.c = c;
        initialiseSolitonDistribution();
    }
    
    public void initialiseSolitonDistribution(){
        for (int d = 1; d <=K; d++){
            double toSet;
            if(d == 1){
                toSet =  1.0/K;
            }
            else{
                toSet = 1.0/(d *(d -1));
            }
            rho[d -1] =toSet;
        }
        
        double R = c*Math.log(K/delta)*Math.sqrt(K);
        int spikeIndex = (int) Math.floor(K/R);
        
        for (int d = 1; d <=K; d++){
            double toSet;
            if (d < spikeIndex)
                toSet = R/(K* d);
            else if (d == spikeIndex){ //Add a spike for d = K/s
                toSet = R*Math.log(R/delta)/K;
            }
            else
                toSet = 0;
            tau[d -1] = toSet;
        }
        
        //Add tau and rho together to form the robust Soliton distribution
        // K*Z represents the minimum number of oligos that need to be synthesised for data recovery
        
        for (int i = 0; i< rho.length; i++){
            Z += (rho[i] + tau[i]);
        }
        for (int i = 0; i< rho.length; i++){
            mu[i] = (rho[i] + tau[i]) / Z;
        }
        
        double total = 0;
        for (double value : mu)
            total += value;
         // total Should be ≈ 1.0
    }
    
    public int getIndex(double random){
        double cumulativeProbability = 0;
        for (int i = 0; i< mu.length; i ++){
            cumulativeProbability += mu[i];
            if(cumulativeProbability >= random)
                return i+1;
        }
        System.out.println("There was an error initialising the Soliton distribution");
        return 0;
    }
    
    public double getZ(){
        return Z;
    }
    public double[] getMu(){
        return mu;
    }
    public double[] getRho(){
        return rho;
    }
    public double[] getTau(){
        return tau;
    }
}
