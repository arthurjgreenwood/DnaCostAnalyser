package me.ajg.diss;

import me.ajg.diss.encoders.Soliton;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main (String[] args){
        Soliton soliton = new Soliton(100, 0.05, 0.1);
        soliton.setSolitonDistribution();
    }
    
}