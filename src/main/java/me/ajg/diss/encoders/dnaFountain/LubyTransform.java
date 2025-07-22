package me.ajg.diss.encoders.dnaFountain;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LubyTransform {
    
    //Constants
    double DELTA = 0.001;
    double CONST = 0.025;
    
    private final GLFSR glfsr;
    private final Soliton soliton;
    private final List<byte[]> fragments;
    private final int fragmentLength;
    private final double maxGcDeviation;
    private final int maxHomopolymer;
    
    private final int requiredOligos;
    //FragmentLength in bytes
    public LubyTransform(List<byte[]> fragments, int INPUT_SEED, double redundancy, int fragmentLength, double maxGcDeviation, int maxHomopolymer){
        this.fragments = fragments;
        glfsr = new GLFSR(INPUT_SEED);
        soliton = new Soliton(fragments.size(), DELTA, CONST);
        this.fragmentLength = fragmentLength;
        this.maxGcDeviation = maxGcDeviation;
        this.maxHomopolymer = maxHomopolymer;
        
        requiredOligos = (int) Math.ceil( fragments.size() * (soliton.getZ() + redundancy)); // Represents the required number of droplets
        System.out.println(requiredOligos + " required Oligos");
    }
    
    public List<String> getDroplets(){
        
        List<String> outputDroplets = new ArrayList<>();
        Random random = new Random();
        DropletScreening screener = new DropletScreening(maxGcDeviation, maxHomopolymer);
        
        while (outputDroplets.size() < requiredOligos){
            int seed = glfsr.next();
            random.setSeed(seed); //This will change for every droplet
            int fragmentsToCombine = soliton.getIndex(random.nextDouble());
            random.setSeed(seed); //replace the seed
            
            //List to check for repeated candidates
            List<byte[]> xorCandidates = new ArrayList<>();
            
            while (xorCandidates.size() < fragmentsToCombine){
                byte[] fragment = fragments.get(random.nextInt(fragments.size()));
                if (!xorCandidates.contains(fragment))
                    xorCandidates.add(fragment);
            }
            
            //XOR fragments together into droplet
            
            byte[] droplet = new byte[fragmentLength];
            for (int i = 0; i <xorCandidates.getFirst().length; i++){
                droplet[i] = xorCandidates.getFirst()[i]; //initialise droplet with first byte[]
            }
            
            //Pad with zeros for final byte[]
            if (droplet.length != fragmentLength){
                for (int i = xorCandidates.getFirst().length; i < fragmentLength; i++){
                    droplet[i] = 0;
                }
            }
            
            for (int i = 1; i < xorCandidates.size(); i++){
                for (int j = 0; j <xorCandidates.get(i).length; j++){
                    droplet[j] ^= xorCandidates.get(i)[j]; //xor each candiate byte with the existing array
                }
            }
            
            byte[] seedAsBytes = ByteBuffer.allocate(4).putInt(seed).array();
            byte[] seededDroplet = new byte[seedAsBytes.length + droplet.length];
            System.arraycopy(seedAsBytes, 0, seededDroplet, 0, seedAsBytes.length);
            System.arraycopy(droplet, 0, seededDroplet, seedAsBytes.length, droplet.length);
            
            //TODO add RS code or parity check here
            
            //Only adds if the droplet passes the screening stage
            String dnaString = screener.convertToDNA(seededDroplet);
            if (screener.screen(dnaString))
                outputDroplets.add(dnaString);
        }
        
        return outputDroplets;
    }
}
