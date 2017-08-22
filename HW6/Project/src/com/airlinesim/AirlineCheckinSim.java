package com.airlinesim;

/**
 * Simulate the check-in process of an airline.
 *
 */

public class AirlineCheckinSim {

    // Data Fields


    private PassengerQueue passengerQueue = new PassengerQueue("Passengers queue");

    /**
     * Total simulated time.
     */
    private int totalTime;

    /**
     * If set true, print additional output.
     */
    private boolean showAll;

    /**
     * Simulated clock.
     */
    private int clock = 0;

    /**
     * Time that the agent will be done with the current passenger.
     */
    private int timeDone;


    public AirlineCheckinSim(int maxProcessingTime,int totalTime,boolean showAll,
                             double frequentFlyerArrivalRate, double regularPassengerArrivalRate,
                             double frequentFlyerPriority, double regularPassengerPriority )
    {
        Passenger.setMaxProcessingTime(maxProcessingTime);
        this.totalTime = totalTime;
        this.showAll = showAll;
        passengerQueue.setArrivalRate(frequentFlyerArrivalRate,regularPassengerArrivalRate);
        passengerQueue.setPriorityRate(frequentFlyerPriority,regularPassengerPriority);
    }

    public void runSimulation() {
        for (clock = 0; clock < totalTime; clock++) {
            passengerQueue.checkNewArrival(clock, showAll);
            if (clock >= timeDone) {
                startServe();
            }
        }
    }

    public void startServe() {
        if (!passengerQueue.isEmpty()){
            timeDone = passengerQueue.update(clock, showAll);
        } else if (showAll) {
            System.out.println("Time is " + clock
                    + " server is idle");
        }
    }

    /**
     * Method to show the statistics.
     */
    public void showStats() {
        System.out.println
                ("\nThe number of  passengers served was "
                        + passengerQueue.getNumServed());
        double averageWaitingTime =
                (double) passengerQueue.getTotalWait()
                        / (double) passengerQueue.getNumServed();
        System.out.println(" with an average waiting time of "
                + averageWaitingTime);
        System.out.println("Passengers in queue: "
                + passengerQueue.size());
    }

}
