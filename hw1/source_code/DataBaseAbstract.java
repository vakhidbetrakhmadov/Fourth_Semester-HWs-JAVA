import java.io.*;
import java.util.ArrayList;

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
    protected File file = null;
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
     * Creates a file with the name specified by the first argument,if the file does not exists
     *
     * @param fileName
     *        Name of the file to be used as a database
     *
     * @param numberOfFields
     *        Nuber of fields in the database
     *
     * @throws Exception if numberOfFields <= 0 or the file does not exists and could not be created
     */
    public DataBaseAbstract(String fileName,int numberOfFields) throws Exception{
        if(numberOfFields <= 0 )
            throw new Exception("Number of fields must be positive integer");

        file = new File(fileName);
        if(!file.exists())
            if(!file.createNewFile())
                throw new Exception("Error when creating file (UsersDataBase)");
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

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file,true));

        bufferedWriter.append("\n" + record.toString());
        bufferedWriter.flush();
        bufferedWriter.close();

        return true;
    }

    /**
     * @see DataBase#removeRecord(Record)
     */
    public boolean removeRecord(Record record)throws Exception {
        Record recordToRemove = findRecord(record);
        if(recordToRemove == null)
            return false;

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        String line = null;
        ArrayList<String> buffer = new ArrayList<>();
        while((line = bufferedReader.readLine())!=null)
            buffer.add(line);

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        boolean firstLine =true;
        for(String next : buffer)
        {
            if(!(new Record(next).equals(recordToRemove)))
            {
                bufferedWriter.write((firstLine ? "" : "\n") + next);
                bufferedWriter.flush();
                firstLine = false;
            }
        }

        bufferedWriter.close();
        bufferedReader.close();

        return true;
    }

    /**
     * @see DataBase#findRecord(Record)
     */
    //to find a record, at least 2 first fields in 'record' argument object must be non-'none' fields
    public Record findRecord(Record record) throws Exception{
        Record recordToReturn = null;
        String line = null;
        boolean found = false;

        if(record.numberOfFields()!= NUMBER_OF_FIELDS)
            return recordToReturn;

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        bufferedReader.readLine();//read the first line
        while((line = bufferedReader.readLine())!=null && !found)
        {
            Record tempRecord = new Record(line);
            if(record.compareTo(tempRecord)==0)
            {
                recordToReturn = tempRecord;
                found = true;
            }
        }

        bufferedReader.close();

        return recordToReturn;
    }

    /**
     * @see DataBase#editRecord(Record, Record)
     */
    public boolean editRecord(Record oldRecord,Record newRecord)throws Exception {

        Record recordToEdit = findRecord(oldRecord);
        if(recordToEdit == null)
            return false;

        if(!recordIsLegit(newRecord))
            return false;

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        String line = null;
        ArrayList<String> buffer = new ArrayList<>();
        while((line = bufferedReader.readLine())!=null)
            buffer.add(line);

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        boolean firstLine =true;
        for(String next : buffer)
        {
            if((new Record(next).equals(recordToEdit)))
            {
                bufferedWriter.write((firstLine ? "" : "\n") + newRecord.toString());
                firstLine = false;
            }
            else
            {
                bufferedWriter.write((firstLine ? "" : "\n") + next);
                firstLine = false;
            }

            bufferedWriter.flush();
        }

        bufferedWriter.close();
        bufferedReader.close();

        return true;
    }
}
