package me.ajg.diss.encoders;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LubyTransform {
    
    //Constants
    double DELTA = 0.001;
    double CONST = 0.025;
    
    private int INPUT_SEED;
    private GLFSR glfsr;
    private Soliton soliton;
    private List<Integer> fragments;
    
    private int requiredOligos;
    
    public LubyTransform(List<Integer> fragments, int INPUT_SEED, double redundancy){
        this.INPUT_SEED = INPUT_SEED;
        this.fragments = fragments;
        glfsr = new GLFSR(INPUT_SEED);
        soliton = new Soliton(fragments.size(), DELTA, CONST);
        
        requiredOligos = (int) Math.ceil( fragments.size() * (soliton.getZ() + redundancy)); // Represents the required number of droplets
    }
    
    public List<Integer> getDroplets(){
        
        List<Integer> outputDroplets = new ArrayList<>();
        Random random = new Random();
        
        for (int i = 0; i<requiredOligos; i++){
            int seed = glfsr.next();
            random.setSeed(seed); //This will change for every droplet
            int fragmentsToCombine = soliton.getIndex(random.nextDouble());
            
            //List to check for repeats
            List<Integer> xorCandidates = new ArrayList<>();
            
            while (xorCandidates.size() < fragmentsToCombine){
                int fragment = fragments.get(random.nextInt(fragments.size()))-1;
                if (!xorCandidates.contains(fragment))
                    xorCandidates.add(fragment);
            }
            
            //XOR fragments together into droplet
            
            int droplet = 0;
            for (int candidate : xorCandidates){
                droplet ^= candidate; //XOR together
            }
            outputDroplets.add(droplet);
        }
        return outputDroplets;
    }
}
