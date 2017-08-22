package com;


import com.airlinesim.*;

public class Main_Q1 {

    public static void main(String[] args)
    {
        AirlineCheckinSim airlineCheckinSim = new AirlineCheckinSim(3,100,true,
                0.2,0.8,0.8,0.3);

        airlineCheckinSim.runSimulation();
        airlineCheckinSim.showStats();
    }
}
