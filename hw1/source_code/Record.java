
import java.util.StringTokenizer;

/**
 * The {@code Record} class represents a general n-fields wide database record
 *<p>
 *     The {@code Record} implemets Comparable
 *     All fields of the record are of type {@code String}
 *     A {@code Record} contains mutators and accessors to all fields,method to get number of
 *     fields of a {@code Record},it overrides toString(),compareTo() and equals()
 *</p>
 *
 *@author  Vakhid Betrakhmadov
 */
public class Record implements Comparable<Record>{

    /** The value is used for fields storage*/
    private String[] arrayOfFields = null;

    /** Initializes a newly created {@code Record} with number of fields specified by the parameter
     *  All fields are set to "none" by default
     *
     *  @param numberOfFields
     *          Number if fields for a {@code Record} to be initialized to
     *
     *  @throws Exception if numberOfFields <=0
     *
     * */
    public Record(int numberOfFields)throws Exception {
        if(numberOfFields <=0)
            throw new Exception("Number of fields must be positive (Record)");

        arrayOfFields = new String[numberOfFields];
        for(int i = 0; i < arrayOfFields.length; ++i)
            arrayOfFields[i] = "none";
    }

    /** Initializes a newly created {@code Record} with field given
     *  If field is wished to be skipped it must be set to "none"
     *
     *  @param fields
     *          Fields for a {@code Record} to be initialized with
     *
     *  @throws Exception if fields.legth == 0
     *
     */
    public Record(String ... fields)throws Exception {
        if(fields.length == 0)
            throw new Exception("Fields must be specified (number or \"Field1\",\"...\") (Record)");

        arrayOfFields = new String[fields.length];
        for(int i = 0; i < arrayOfFields.length; ++i)
            arrayOfFields[i]=fields[i].trim();
    }

    /** Initializes a newly created {@code Record} with field given in a record
     *  If field is wished to be skipped it must be set to "none"
     *
     *  @param record
     *          A record in the following form "field1,none,fiedl3" for a  {@code Record} to be initialized with
     *
     *  @throws Exception if no fields are given
     *
     */
    public Record(String record)throws Exception {
        StringTokenizer stringTokenizer = new StringTokenizer(record,",");
        int numberOfTokens = stringTokenizer.countTokens();
        if(numberOfTokens == 0)
            throw new Exception("Fields must be specified (\"Field,...\") (Record)");

        arrayOfFields = new String[numberOfTokens];
        for( int i = 0; stringTokenizer.hasMoreTokens(); ++i) {
            arrayOfFields[i] = stringTokenizer.nextToken().trim();
        }
    }

    /** Returns filed specified by the parameter
     *
     *  @param fieldNumber
     *          Number of the field to be returned
     *
     *  @return The field specified by the parameter
     *
     *  @throws Exception if fieldNumber < 0 or fieldNumber > numberOfFields()
     *
     */
    public String getField(int fieldNumber)throws Exception {
        int index = fieldNumber-1;
        if(index < 0 || index >= arrayOfFields.length)
            throw new Exception("Field number " + fieldNumber + " does not exist (getField)");

        return arrayOfFields[index];
    }

    /** Sets field specified by fieldNumber to the value of field
     *
     *  @param fieldNumber
     *          Number of the field to be set
     *
     *  @param field
     *          A value for the field to be set to
     *
     *  @throws Exception if fieldNumber < 0 or fieldNumber > numberOfFields()
     */
    public void setField(int fieldNumber, String field) throws Exception{
        int index = fieldNumber-1;
        if(index < 0 || index >=arrayOfFields.length)
            throw new Exception("Field number " + fieldNumber + " does not exist (setField)");

        arrayOfFields[index] = field.trim();
    }

    /** Sets all fields at once
     *
     *  @param fields
     *          New fields
     *
     *  @throws Exception if fields.length != numberOfFields()
     */
    public void setFields(String ... fields) throws Exception{
        if(fields.length != arrayOfFields.length)
            throw new Exception("Fields number mismatch (setFields)");

        for(int i = 0; i < arrayOfFields.length; ++i) {
            if(fields[i]!="")
                arrayOfFields[i]=fields[i].trim();
        }
    }

    /** Sets all fields at once
     *
     *  @param record
     *          A record in the following form "field1,none,fiedl3" for a  {@code Record} to be initialized with
     *
     *  @throws Exception if number of fields != numberOfFields()
     */
    public void setFields(String record)throws Exception {
        StringTokenizer stringTokenizer = new StringTokenizer(record,",");
        int numberOfTokens = stringTokenizer.countTokens();
        if(numberOfTokens != arrayOfFields.length)
            throw new Exception("Fields number mismatch (setFields)");

        for( int i = 0; stringTokenizer.hasMoreTokens(); ++i) {
            arrayOfFields[i] = stringTokenizer.nextToken().trim();
        }
    }

    /** Returns number of fields of the {@code Record}
     *
     *  @return number of fields of the {@code Record}
     */
    public int numberOfFields() {
        return arrayOfFields.length;
    }

    /**
     * Retuns record in the following format "field1,field2,..."
     *
     * @return record in the following format "field1,field2,...
     */
    @Override
    public String toString() {
        String stringToReturn = "";
        for(int i = 0; i < arrayOfFields.length; ++i) {
            stringToReturn+=arrayOfFields[i] + (i < arrayOfFields.length-1 ? "," : "");
        }

        return stringToReturn;
    }

    /**
     * Compares two {@code Record}s
     *
     * @param record
     *          A {@code Record} to be compared with
     *
     * @return true if equal false otherwise
     */
    public boolean equals(Record record)
    {
        return this.toString().equals(record.toString());
    }


    /**
     * Compares two {@code Record}s
     *
     * @param otherRecord
     *          A {@code Record} to be compared with
     *
     * @return  -1 if this.numberOfFields < otherRecord.numberOfFields
     *          or and 1 otherwise
     *          -1 if the first non-equal field is less then otherRecord's,1 otherwise
     *          and 0 if all the fields are equal
     *          -2 if either of the {@code Record}s is default(that is all fields are "none")
     */
    public int compareTo(Record otherRecord)
    {
        if(this.arrayOfFields.length != otherRecord.arrayOfFields.length)
        {
            return (this.arrayOfFields.length < otherRecord.arrayOfFields.length ? -1 : 1);
        }

        try
        {
            if(this.equals(new Record(this.numberOfFields())) || otherRecord.equals(new Record(this.numberOfFields())))
            {
                return -2;
            }
        }
        catch (Exception ex)
        {
            return -2;
        }

        for(int i = 0; i < this.arrayOfFields.length; ++i)
        {
            if(this.arrayOfFields[i].compareTo(otherRecord.arrayOfFields[i])!=0 && !this.arrayOfFields[i].equals("none")
                    && !otherRecord.arrayOfFields[i].equals("none"))
            {
                return this.arrayOfFields[i].compareTo(otherRecord.arrayOfFields[i]);
            }
        }

        return 0;
    }
}
