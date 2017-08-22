package com.airlinesim;

import java.util.*;

/**
 * A class to represent a passenger.
 *
 *
 */

public class Passenger {

    public enum Status{
        REGULAR_PASSENGER,FREQUENT_FLYER //---------------- MODIFIED -------------------//
    }

    // Data Fields
    /**
     * The ID number for this passenger.
     */
    private int passengerId;

    /**
     * The time needed to process this passenger.
     */
    private int processingTime;

    /**
     * The time this passenger arrives.
     */
    private int arrivalTime;


    private Double priority; //---------------- MODIFIED -------------------//

    private Status status; //---------------- MODIFIED -------------------//

    /**
     * The maximum time to process a passenger.
     */
    private static int maxProcessingTime;

    /**
     * The sequence number for passengers.
     */
    private static int idNum = 0;


    /**
     * Create a new passenger.
     *
     * @param arrivalTime The time this passenger arrives
     */
    public Passenger(int arrivalTime,Status status,Double priority) {
        this.arrivalTime = arrivalTime;
        this.status = status;  //---------------- MODIFIED -------------------//
        this.priority = priority; //---------------- MODIFIED -------------------//
        processingTime = 1 + (new Random()).nextInt(maxProcessingTime);
        passengerId = idNum++;
    }

    public Double getPriority()
    {
        return priority;
    }  //---------------- MODIFIED -------------------//

    public Status getStatus()
    {
        return status;
    } //---------------- MODIFIED -------------------//
    /**
     * Get the arrival time.
     *
     * @return The arrival time
     */
    public int getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Get the processing time.
     *
     * @return The processing time
     */
    public int getProcessingTime() {
        return processingTime;
    }

    /**
     * Get the passenger ID.
     *
     * @return The passenger ID
     */
    public int getId() {
        return passengerId;
    }

    /**
     * Set the maximum processing time
     *
     * @param maxProcessingTime The new value
     */
    public static void setMaxProcessingTime(int maxProcessingTime) {
        Passenger.maxProcessingTime = maxProcessingTime;
    }
}
