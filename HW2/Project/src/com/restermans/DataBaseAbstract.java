package com.restermans;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * The {@code DataBaseAbstract} class is an intermediate class for other database classes
 *<p>
 *     The {@code DataBaseAbstract} class implements all the methods of DataBase interface,so that
 *     to ease the implementation of the subclasses
 *     It also declares am abstract function for validation of the database's {@code Record}
 *</p>
 *
 *@author  Vakhid Betrakhmadov
 */
public abstract class DataBaseAbstract implements DataBase{
    /** Associated file used as a database */
    protected LinkedList<Record> array = null;
    /** The value is used to store number of fields of the database*/
    protected final int  NUMBER_OF_FIELDS;

    /** Validates a {@code Record},checks if a [@code Record} meets database format
     *
     *  @param record
     *          The {@code Record} to be checked
     *
     *  @return true on success and false otherwise
     *
     *  @throws Exception if out of range field is tryied to be accessed
     *
     */
    protected abstract boolean recordIsLegit(Record record)throws Exception;

    /**
     * Opens a file specified by the first argument as a database,sets number of fields of the database to the second argument
     * Creates a file with the name specified by the first argument,if the file does not exist
     *
     * @param numberOfFields
     *        Nuber of fields in the database
     *
     * @throws Exception if numberOfFields <= 0 or the file does not exists and could not be created
     */
    public DataBaseAbstract(int numberOfFields) throws Exception{
        if(numberOfFields <= 0 )
            throw new Exception("Number of fields must be positive integer");

        array = new LinkedList<>();
        this.NUMBER_OF_FIELDS = numberOfFields;
    }

    /**
     * @see DataBase#numberOfFields()
     */
    public int numberOfFields()
    {
        return NUMBER_OF_FIELDS;
    }

    /**
     * @see DataBase#addRecord(Record)
     */
    public boolean addRecord(Record record) throws Exception{
        if(findRecord(record)!=null)
            return false;

        if(!recordIsLegit(record))
            return false;

        array.add(record);

        return true;
    }

    /**
     * @see DataBase#removeRecord(Record)
     */
    public boolean removeRecord(Record record)throws Exception {

        return array.remove(record);
    }

    /**
     * @see DataBase#findRecord(Record)
     */
    //to find a record, at least 2 first fields in 'record' argument object must be non-'none' fields
    public Record findRecord(Record record) throws Exception{

        if(record.numberOfFields()!= NUMBER_OF_FIELDS)
            return null;

        Record toReturn = null;
        Iterator<Record> iterator = array.iterator();
        while(iterator.hasNext())
        {
            if((toReturn = iterator.next()).equals(record))
                return  toReturn;
        }

        return null;
    }

    /**
     * @see DataBase#editRecord(Record, Record)
     */
    public boolean editRecord(Record oldRecord,Record newRecord)throws Exception {

        Record toEdit = null;
        ListIterator<Record> iterator = array.listIterator();
        while(iterator.hasNext())
        {
            if((toEdit = iterator.next()).equals(oldRecord))
            {
                iterator.set(newRecord);
                return true;
            }

        }

        return false;
    }
}
