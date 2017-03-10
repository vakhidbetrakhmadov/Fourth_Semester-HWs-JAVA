import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * The {@code BooksDataBase} class represents a data base holding books {@code Record}s
 *
 *@author  Vakhid Betrakhmadov
 */
public class BooksDataBase extends DataBaseAbstract {

    /**
     * @see DataBaseAbstract#DataBaseAbstract(String, int)
     * If the file did not exits adds 4 default entries:
     * BookName,Author,Available
     * of human bondage,w. somerset maugham,3
     * the great gatsby,f. scott fitzgerald,5
     * nineteen eighty-four,george orwell,4
     */
    public BooksDataBase(String fileName)throws Exception {
        super(fileName,3);

        if(file.length() == 0)
        {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(new Record("BookName,Author,Available") + "\n"
                    + new Record("of human bondage,w. somerset maugham,3") + "\n"
                    + new Record("the great gatsby,f. scott fitzgerald,5") + "\n"
                    + new Record("nineteen eighty-four,george orwell,4"));
            bufferedWriter.flush();
            bufferedWriter.close();
        }
    }

    /**
     * @see DataBaseAbstract#recordIsLegit(Record)
     */
    protected boolean recordIsLegit(Record record)throws Exception
    {
        return !(record.numberOfFields()!= NUMBER_OF_FIELDS || !record.getField(1).matches("[^\\d,]*")
                || !record.getField(2).matches("[^\\d,]*") || !record.getField(3).matches("\\d+")
                || (Integer.parseInt(record.getField(3)) < 0) || record.getField(1).equals("none")
                || record.getField(2).equals("none") || record.getField(3).equals("none") );
    }
}
