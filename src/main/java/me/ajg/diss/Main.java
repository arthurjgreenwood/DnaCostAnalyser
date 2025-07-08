package me.ajg.diss;

import me.ajg.diss.encoders.dnaFountain.DNAFountainEncoder;
import me.ajg.diss.encoders.dnaFountain.Soliton;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main (String[] args){
        System.out.println(DNAFountainEncoder.encode("C:\\Users\\Arthur\\Documents\\Dissertation stuff\\DnaCostAnalyser\\src\\main\\java\\me\\ajg\\diss\\encoders\\dnaFountain\\aaj2038s1.mp4",
                42, 0.05, 32, 0.05f, 4));
    }
    
}