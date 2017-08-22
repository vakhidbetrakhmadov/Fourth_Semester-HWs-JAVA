package com.airlinesim;

import java.util.*;
import com.binaryheap.*;

/**
 * Class to simulate a queue of passengers.
 *
 */

public class PassengerQueue {

    // Data Fields
    /**
     * The queue of passengers.
     */
    private Queue<Passenger> theQueue;

    /**
     * The number of passengers served.
     */
    private int numServed;

    /**
     * The total time passengers were waiting.
     */
    private int totalWait;

    /**
     * The name of this queue.
     */
    private String queueName;

    /**
     * The average arrival rate.
     */
    private double freqFlyerArrivalRate; // ------------- MODIFIED -----------//

    private double regPassengerArrivalRate; // ------------- MODIFIED -----------//

    private double freqFlyerPriorityRate; // ------------- MODIFIED -----------//

    private double regPassengerPriorityRate; // ------------- MODIFIED -----------//

    // Constructor

    /**
     * Construct a PassengerQueue with the given name.
     *
     * @param queueName The name of this queue
     */
    public PassengerQueue(String queueName) {
        numServed = 0;
        totalWait = 0;
        this.queueName = queueName;
        theQueue = new BinaryHeap<Passenger>(new Comparator<Passenger>() {
            @Override
            public int compare(Passenger o1, Passenger o2) {
                return o1.getPriority().compareTo(o2.getPriority());
            }
        });
    }

    /**
     * Return the number of passengers served
     *
     * @return The number of passengers served
     */
    public int getNumServed() {
        return numServed;
    }

    /**
     * Return the total wait time
     *
     * @return The total wait time
     */
    public int getTotalWait() {
        return totalWait;
    }

    /**
     * Return the queue name
     *
     * @return - The queu name
     */
    public String getQueueName() {
        return queueName;
    }

    /**
     * Set the arrival rate
     *
     * @param freqFlyerArrivalRate the value to set
     * @param regPassengerArrivalRate the value to set
     */
    public void setArrivalRate(double freqFlyerArrivalRate,double regPassengerArrivalRate) {
        this.freqFlyerArrivalRate = freqFlyerArrivalRate;
        this.regPassengerArrivalRate = regPassengerArrivalRate;
    }

    public void setPriorityRate(double freqFlyerPriorityRate,double regPassengerPriorityRate) {
        this.freqFlyerPriorityRate = freqFlyerPriorityRate;
        this.regPassengerPriorityRate = regPassengerPriorityRate;
    }

    /**
     * Determine if the passenger queue is empty
     *
     * @return true if the passenger queue is empty
     */
    public boolean isEmpty() {
        return theQueue.isEmpty();
    }

    /**
     * Determine the size of the passenger queue
     *
     * @return the size of the passenger queue
     */
    public int size() {
        return theQueue.size();
    }

    /**
     * Check if a new arrival has occurred.
     *
     * @param clock   The current simulated time
     * @param showAll Flag to indicate that detailed
     *                data should be output
     */
    public void checkNewArrival(int clock, boolean showAll) {
        if (Math.random() < freqFlyerArrivalRate) {
            theQueue.add(new Passenger(clock, Passenger.Status.FREQUENT_FLYER,theQueue.size()/freqFlyerPriorityRate));
            if (showAll) {
                System.out.println("Time is "
                        + clock + ": "
                        + Passenger.Status.FREQUENT_FLYER.toString()
                        + " arrival, new queue size is "
                        + theQueue.size());
            }
        }
        if(Math.random() < regPassengerArrivalRate)
        {
            theQueue.add(new Passenger(clock, Passenger.Status.REGULAR_PASSENGER,theQueue.size()/regPassengerPriorityRate));
            if (showAll) {
                System.out.println("Time is "
                        + clock + ": "
                        + Passenger.Status.REGULAR_PASSENGER.toString()
                        + " arrival, new queue size is "
                        + theQueue.size());
            }
        }
    }

    /**
     * Update statistics.
     * pre: The queue is not empty.
     *
     * @param clock   The current simulated time
     * @param showAll Flag to indicate whether to show detail
     * @return Time passenger is done being served
     */
    public int update(int clock, boolean showAll) {
        Passenger nextPassenger = theQueue.remove();
        int timeStamp = nextPassenger.getArrivalTime();
        int wait = clock - timeStamp;
        totalWait += wait;
        numServed++;
        if (showAll) {
            System.out.println("Time is " + clock
                    + ": Serving "
                    + nextPassenger.getStatus().toString()
                    + " with time stamp "
                    + timeStamp);
        }
        return clock + nextPassenger.getProcessingTime();
    }

}
