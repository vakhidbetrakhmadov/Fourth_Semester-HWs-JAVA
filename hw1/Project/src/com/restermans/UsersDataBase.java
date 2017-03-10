package com.restermans;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * The {@code BooksDataBase} class represents a data base holding users {@code Record}s
 *
 *@author  Vakhid Betrakhmadov
 */
public class UsersDataBase extends DataBaseAbstract {

    /**
     * @see DataBaseAbstract#DataBaseAbstract(String, int)
     * If the file did not exits adds 3 default entries:
     * Name,Surname,Password,Status,BooksBorrowed
     * vakhid,betrakhmadov,mans1994,staff,none
     * root,root,root,staff,none
     */
    public UsersDataBase(String fileName)throws Exception {
        super(fileName,5);

        if(file.length() == 0)
        {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(new Record("Name,Surname,Password,Status,BooksBorrowed").toString() + "\n"
                    + new Record("vakhid,betrakhmadov,mans1994,staff,none").toString()+"\n"
                    + new Record("root,root,root,staff,none").toString());
            bufferedWriter.flush();
            bufferedWriter.close();
        }
    }

    /**
     * @see DataBaseAbstract#recordIsLegit(Record)
     */
    protected boolean recordIsLegit(Record record)throws Exception
    {
        return !(record.numberOfFields()!= NUMBER_OF_FIELDS ||
                !(record.getField(4).equals("staff") || record.getField(4).equals("user")) ||
                record.getField(1).equals("none") || record.getField(2).equals("none") ||
                record.getField(3).equals("none") || !record.getField(1).matches("[^\\d,]*") ||
                !record.getField(2).matches("[^\\d,]*")|| !record.getField(3).matches("[^,]*")||
                !(record.getField(5).matches("([^\\d,]*-[^\\d,]*/)+") || record.getField(5).equals("none")));
    }
}
