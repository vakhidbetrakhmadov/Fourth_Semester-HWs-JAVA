/**
 * The {@code LibraryUser} class represents library user as a user of the Library Management System
 * It contains such operations as borrowing or returning books
 * All methods return {@code String} message indicating result of the request to the underlying database
 *
 * @author  Vakhid Betrakhmadov
 */
public class LibraryUser extends SystemUser {

    /**
     * @see SystemUser#SystemUser(String, String, String)
     */
    public LibraryUser(String name,String surname,String password) {
        super(name,surname,password);
    }

    /**
     * Borrows a book from the library
     * If the book is in the library and copies are left,fixes book after user in a users database and reduces
     * number of copies left in a books database
     *
     * @param booksDataBase
     *        The books database
     *
     * @param usersDataBase
     *        The users database
     *
     * @param bookName
     *        The name of the book wished to borrowed
     *
     * @param author
     *        The author of the book wished to be borrowed
     *
     * @return result message
     *
     * @throws Exception if could not create a {@code Record} using books data or if booksDataBase.numberOfFields() < 3 or usersDataBase.numberOfFields() < 5
     */
    public String borrowBook(DataBase booksDataBase,DataBase usersDataBase, String bookName,String author)throws Exception {
        if(booksDataBase.numberOfFields() < 3 || usersDataBase.numberOfFields() < 5)
            throw new Exception("Too few fields in dataBase");
        if(status.equals("none"))
            return "Please LogIn";

        Record book = booksDataBase.findRecord(new Record(bookName,author,"none"));
        if(book == null)
            return "The book was not found in the library database";

        if(Integer.parseInt(book.getField(3)) == 0)
            return "No books are left for the moment to borrow,please try again later";

        if(booksDataBase.editRecord(book,new Record(bookName,author,Integer.toString((Integer.parseInt(book.getField(3))-1)))))
        {
            String message = "The book is successfully borrowed,please ask the staff for help.";
            booksBorrowed = (booksBorrowed.equals("none") ? "" : booksBorrowed)+bookName+"-"+author+"/";
            if(!usersDataBase.editRecord(new Record(name,surname,password,"none","none"),
                    new Record(name,surname,password,status,booksBorrowed)))
                message+=" Some complications with users database happened,please contact the library staff";

            return message;
        }
        else
            return "Some complications with books database happened,please contact the library staff";
    }

    /**
     * Returns a book to the library
     * If the book is fixed after the user,removes the book from the list of the books borrowed by the user in a users
     * database,and increments number of copies of the book in a books database
     *
     * @param booksDataBase
     *        The books database
     *
     * @param usersDataBase
     *        The users database
     *
     * @param bookName
     *        The name of the book wished to returned
     *
     * @param author
     *        The author of the book wished to be returned
     *
     * @return result message
     *
     * @throws Exception if could not create a {@code Record} using books data or if booksDataBase.numberOfFields() < 3 or usersDataBase.numberOfFields() < 5
     */
    public String returnBook(DataBase booksDataBase,DataBase usersDataBase,String bookName,String author)throws Exception {
        if(booksDataBase.numberOfFields() < 3 || usersDataBase.numberOfFields() < 5)
            throw new Exception("Too few fields in dataBase");
        if(status.equals("none"))
            return "Please LogIn";

        if(isThisBookDownInUsersName(bookName,author)!=null) {
            Record book = booksDataBase.findRecord(new Record(bookName,author,"none"));
            String message = "The book is successfully returned.";
            if(book != null){
                if(!booksDataBase.editRecord(book,new Record(bookName,author,Integer.toString((Integer.parseInt(book.getField(3))+1)))))
                    message+=" Some complications with database happened,please contact the library staff";
            }

            if(!usersDataBase.editRecord(new Record(name,surname,password,"none","none"),
                    new Record(name,surname,password,status,booksBorrowed)))
                message+=" Some complications with database happened,please contact the library staff";

            return message;
        }
        else {
            return "This book is not down in your name in this library,try some other library";
        }
    }

    private String isThisBookDownInUsersName(String bookName,String author)throws Exception {

        String book = bookName+"-"+author+"/";
         if(booksBorrowed.contains(book))
         {
             booksBorrowed = booksBorrowed.substring(0,booksBorrowed.lastIndexOf(book))
                     + booksBorrowed.substring(booksBorrowed.lastIndexOf(book) + book.length(),booksBorrowed.length());

             if(booksBorrowed.length()==0)
                 booksBorrowed = "none";

             return booksBorrowed;
         }
         else
             return null;
    }
}
