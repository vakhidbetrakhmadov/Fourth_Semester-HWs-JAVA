package com.restermans;

/**
 * The {@code LibraryStaff} class represents library staff as a user of the Library Management System
 * It contains such operations as adding or removing books and users
 * All methods return {@code String} message indicating result of the request to the underlying database
 *
 * @author  Vakhid Betrakhmadov
 */
public class LibraryStaff extends SystemUser{

    /**
     * @see SystemUser#SystemUser(String, String, String)
     */
    public LibraryStaff(String name,String surname,String password) {
        super(name,surname,password);
    }

    /**
     * Adds a book to the a database
     *
     * @param booksDataBase
     *        The books data base
     *
     * @param bookName
     *        The name of the book to be added
     *
     * @param author
     *        The author of the book to be added
     *
     * @param number
     *         The number of the books to be added
     *
     * @return result message
     *
     * @throws Exception if could not create a {@code Record} using books data
     */
    public String addBook(DataBase booksDataBase,String bookName,String author,int number)throws Exception {
        if(booksDataBase.numberOfFields() < 3)
            throw new Exception("Too few fields in dataBase");

        if(booksDataBase.findRecord(new Record(bookName,author,"none"))!=null)
            return "Could not add the book, this book is already in the database";

        if(booksDataBase.addRecord(new Record(bookName,author,Integer.toString(number))))
            return "Book was successfully added to the database";
        else
            return "Could not add the book to the database,please check correctness of the entered fields\n"+
                    "Book and author names can not contain any digits or commas,number of books must be "+
                    "positive integer";
    }

    /**
     * Removes a book from the a database
     *
     * @param booksDataBase
     *        The books data base
     *
     * @param bookName
     *        The name of the book to be removed
     *
     * @param author
     *        The author of the book to be removed
     *
     * @return result message
     *
     * @throws Exception if could not create a {@code Record} using books data
     */
    public String removeBook(DataBase booksDataBase,String bookName,String author) throws Exception {
        if(booksDataBase.numberOfFields() < 3)
            throw new Exception("Too few fields in dataBase");

        if(booksDataBase.removeRecord(new Record(bookName,author,"none")))
            return "Book was successfully removed from the database";
        else
            return "Could not remove the book,the book was not found in the database";
    }

    /**
     * Adds a user to a database
     *
     * @param usersDataBase
     *        The users database
     *
     * @param name
     *        The name of the user to be added
     *
     * @param surname
     *        The surname of the user to be added
     *
     * @param password
     *        The password of the user to be added
     *
     * @param status
     *        The status of the user to be added
     *
     * @param booksBorrowed
     *        The list of books user has borrowed
     *
     * @return result message
     *
     * @throws Exception if could not create a {@code Record} using books data
     */
    public String addUser(DataBase usersDataBase,String name,String surname,String password,
                           String status, String booksBorrowed)throws Exception {
        if(usersDataBase.numberOfFields() < 5)
            throw new Exception("Too few fields in dataBase");

        if(usersDataBase.findRecord(new Record(name,surname,"none","none","none"))!=null)
            return "Could not add the user,this user is already in the database";;

        if(usersDataBase.addRecord(new Record(name,surname,password,status,booksBorrowed)))
            return "User was successfully added to the database";
        else
            return "Could not add the user to the database,please check correctness of the entered fields\n"+
                    "Name,surname and books borrowed by the user can not contain any digits or commas,"+
                    " password can not contain any commas";

    }

    /**
     * Removes a user from a database
     *
     * @param usersDataBase
     *        The users database
     *
     * @param name
     *        The name of the user to be removed
     *
     * @param surname
     *        The surname of the user to be removed
     *
     * @return result message
     *
     * @throws Exception if could not create a {@code Record} using books data
     */
    public String removeUser(DataBase usersDataBase,String name,String surname)throws Exception {
        if(usersDataBase.numberOfFields() < 5)
            throw new Exception("Too few fields in dataBase");

        if(usersDataBase.removeRecord(new Record(name,surname,"none","none","none")))
            return "User was successfully removed from the database";
        else
            return "Could not remove the user,the user is not in the database";
    }
}
