package com.restermans;

/**
 * The {@code BooksDataBase} class represents a data base holding users {@code Record}s
 *
 *@author  Vakhid Betrakhmadov
 */
public class UsersDataBase extends DataBaseAbstract {

    /**
     * @see DataBaseAbstract#DataBaseAbstract(int)
     * If the file did not exits adds 3 default entries:
     * Name,Surname,Password,Status,BooksBorrowed
     * vakhid,betrakhmadov,mans1994,staff,none
     * root,root,root,staff,none
     */
    public UsersDataBase()throws Exception {
        super(5);
        array.add(new Record("root,root,root,staff,none"));
        array.add(new Record("vakhid,betrakhmadov,mans1994,staff,none"));
        array.add(new Record("sult,tukaev,sult1993,user,none"));
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
