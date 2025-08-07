package me.ajg.diss.encoders.dnaFountain;

/**
 * Screens droplets for required composition
 */
public class DropletScreening {
    
    private final double acceptableGCDeviation;
    private final int maxHomopolymer;
    
    /**
     * Constructor for Screener
     * @param acceptableGCDeviation the maximum acceptable GC deviation from 50%
     * @param maxHomopolymer the maximum homopolymer length
     */
    
    public DropletScreening(double acceptableGCDeviation, int maxHomopolymer) {
        this.acceptableGCDeviation = acceptableGCDeviation;
        this.maxHomopolymer = maxHomopolymer;
    }
    
    /**
     * Screens an oligo for composition
     * @param candidateOligo the oligo to be screened
     * @return true if oligo passes screen
     */
    public boolean screen(String candidateOligo) {
        //Screen for homopolymer runs above maxHomopolymer count
        String pattern = String.format("A{%d,}|C{%d,}|G{%d,}|T{%d,}", maxHomopolymer + 1, maxHomopolymer + 1, maxHomopolymer + 1, maxHomopolymer + 1);
        if (candidateOligo.matches(".*(" + pattern + ").*"))
            return false;
        
        int totalGC = 0;
        for (char c : candidateOligo.toCharArray()) {
            if(c == 'G' || c == 'C')
                totalGC++;
        }
        float gcRatio = (float) totalGC / candidateOligo.length();
        return !(gcRatio > acceptableGCDeviation + .5) && !(gcRatio < acceptableGCDeviation - .5);
    }
    
    /**
     * A helper method for converting a droplet into DNA code
     * @param droplet the droplet to convert to DNA
     * @return String representation of droplet as DNA code
     */
    public String convertToDNA(byte[] droplet){
        
        StringBuilder outputDroplet = new StringBuilder();
        for (byte b : droplet) {
            String binary = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            for (int k = 0; k < binary.length(); k+=2) {
                switch(binary.substring(k,k+2)) {
                    case "00":
                        outputDroplet.append('A');
                        break;
                    case "01":
                        outputDroplet.append('C');
                        break;
                    case "10":
                        outputDroplet.append('G');
                        break;
                    case "11":
                        outputDroplet.append('T');
                        break;
                    default:
                        outputDroplet.append('E');
                        
                }
            }
        }
        return outputDroplet.toString();
    }
    
    
}
