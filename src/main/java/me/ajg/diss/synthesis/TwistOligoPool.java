package me.ajg.diss.synthesis;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * A class for storing prices for oligonucleotide sizes and quantities
 */

public class TwistOligoPool  {
    
    int minimumOligos = 2;
    int maximumOligos = 696000;
    int minimumOligoSize = 20;
    int maximumOligoSize = 300;
    public static double errorRate = 0.0005;
    public static double oligoQuantity = 2e-16; //represent moles of each oligo
    
    private final TreeMap<Integer, TreeMap<Integer, Double>> prices = new TreeMap<>();
    
    /**
     * Uses the insert method to create the price map
     */
    public TwistOligoPool(){
        initialisePrices();
    }
    
    /**
     * Inserts price data into the price map
     * @param upTo maximum oligo quantity for this tier
     * @param one 20-120 nucleotide cost
     * @param two 121-150
     * @param three 151,200
     * @param four 201-250
     * @param five 251-300
     */
    private void insert(int upTo, double one, double two, double three, double four, double five){
            TreeMap<Integer, Double> inner = new TreeMap<>();
            inner.put(120, one);
            inner.put(150, two);
            inner.put(200, three);
            inner.put(250, four);
            inner.put(300, five);
            prices.put(upTo, inner);
    }
    
    public Map<Integer, Map<Integer, Double>> getPrices(){
        return Collections.unmodifiableMap(prices);
    }
    
    private void initialisePrices(){
        insert(100,400,466,520,689,1030);
        insert(500,800,933,1040,1397,2060);
        insert(1000,1200,1400,1560,2068,3090);
        insert(2000, 1600,1867,2080,2757,4121);
        insert(6000,2400,2800,3120,4136,6181);
        insert(12000,3120,3744,4056,5145,7684);
        insert(18000,4056,4867,5273,6694,10004);
        insert(24000,5273, 6327,6855,8702,13006);
        insert(30000,6855,8225,8912,11315,16910);
        insert(36000,7037,8444,9149,11615,17359);
        insert(42000,7741,9289,10064,12775,19093);
        insert(48000,8515,10218,11091,14051,21000);
        insert(54000,9367,11240,12177,15456,23100);
        insert(60000,10304,12364,13395,17003,25411);
        insert(72000, 10819,12928,14065, 17854,26684);
        insert(84000,11197,13437,14557,18477,27614);
        insert(96000,11502,13802,14952,18981,28367);
        insert(120000,11629,13956,15119,19190,28680);
        insert(150000, 13284,15942,17270, 21919, 32758);
        insert(180000,14613,17536,18997,24111,36035);
        insert(210000,15479,18575,20123,25541,38172);
        insert(240000,17027, 20423,22135,28095,41989);
        insert(300000, 21000,25500,27000,33000,49320);
        insert(360000,25200,30600,32400,39600,59184);
        insert(420000,29400,35700,37800,46200,69048);
        insert(480000,33600,40800,43200,52800,78912);
        insert(600000,41160,49980,52920,64680,96667);
        insert(696000,48720,59160,62640,76560,114422);
    }
    
    public double getSpecificPrice(int oligoSize, int oligoQuantity){
        if (oligoSize < minimumOligoSize || oligoSize > maximumOligoSize){
            System.out.println("Oligo Size out of range");
            return -1;
        }
        if (oligoQuantity < minimumOligos || oligoQuantity > maximumOligos){
            System.out.println("Oligo quanitity out of range");
            return -1;
        }
        
        int quantityTier = prices.ceilingKey(oligoQuantity);
        int sizeTier = prices.get(quantityTier).ceilingKey(oligoSize);
        return prices.get(quantityTier).get(sizeTier);
        
    }
    
}
