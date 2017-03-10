
/**
 * The {@code DataBase} interface represents a general database and gives skeleton structure of
 * methods every database must implement
 *
 *@author  Vakhid Betrakhmadov
 */
public interface DataBase {

    /** Adds a {@code Record} to the database
     *
     *  @param record
     *          The {@code Record} to be added
     *
     *  @return true on success and false otherwise
     *
     *  @throws Exception if problems with opening or appending the file
     *
     */
    boolean addRecord(Record record)throws Exception;

    /** Removes a {@code Record} from the database
     *
     *  @param record
     *          The {@code Record} to be removed from database
     *
     *  @return true on success and false otherwise
     *
     *  @throws Exception if problems with opening or appending the file
     *
     */
    boolean removeRecord(Record record)throws Exception;

    /** Finds a {@code Record} in the database
     *
     *  @param record
     *          The {@code Record} to be found
     *
     *  @return {@code Record} on success and null otherwise
     *
     *  @throws Exception if problems with opening or appending the file
     *
     */
    Record findRecord(Record record)throws  Exception;

    /** Replaces an old {@code Record} with a new {@code Record}
     *
     *  @param oldRecord
     *          The {@code Record} to be edited
     *
     *  @param newRecord
     *          The {@code Record} to edit with
     *
     *  @return true on success and false otherwise
     *
     *  @throws Exception if problems with opening or appending the file
     *
     */
    boolean editRecord(Record oldRecord,Record newRecord)throws Exception;


    int numberOfFields();
}
