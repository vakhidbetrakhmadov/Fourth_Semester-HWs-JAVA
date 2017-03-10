import java.util.Scanner;

/**
 * The {@code LibraryManagementSystem} class is a completely functioning library management system
 * It is a composition of {@code BooksDataBase} and {@code UsersDataBase},and {@code SystemUsers}
 * Users can borrow or return books, staff can manage users and books
 *
 *  @author  Vakhid Betrakhmadov
 */
public class LibraryManagementSystem {

    /**The value to store {@code BooksDataBase}*/
    private BooksDataBase booksDataBase = null;
    /**The value to store {@code UsersDataBase}*/
    private UsersDataBase usersDataBase = null;
    /**The value to store {@code LibraryUser}*/
    private LibraryUser libraryUser = null;
    /**The value to store {@code LibraryStaff}*/
    private LibraryStaff libraryStaff = null;

    /**
     * Initializes the {@code LibraryManagementSystem} databases
     * If databases are not found creates new databases with default settings
     * If error occurs prints message to the screen and exits
     */
    public  LibraryManagementSystem()
    {
        try {
            booksDataBase = new BooksDataBase("books");
            usersDataBase = new UsersDataBase("users");
        }
        catch (Exception ex)
        {
            System.out.println();
        }
    }

    /**
     * Start library management system, which interacts with user prompting him through console
     * If incorrect data is entered user is asked to enter data again
     * Exits if user tries to login as staff, ot the other way around
     */
    public void start()
    {
        try {
            System.out.println("Welcome to the library management system !\n"+
                                "----------------------------------------\n");
            System.out.println("Select your status: Enter '1' for User, '2' for Staff or Q(q) to exit");

            Scanner scanner = new Scanner(System.in);
            String input = null;
            MenuStage menuStage = MenuStage.SELECT_STATUS;
            UserSubMenuStage userSubMenuStage = UserSubMenuStage.LOG_IN;
            StaffSubMenuStage staffSubMenuStage = StaffSubMenuStage.LOG_IN;
            boolean done = false;

            while(!done)
            {
                input = scanner.nextLine();
                input =input.trim().toLowerCase();
                System.out.println();

                if(input.equalsIgnoreCase("q"))
                    menuStage = MenuStage.EXIT;

                switch (menuStage)
                {
                    case SELECT_STATUS:
                    {
                        if(!input.equals("1") && !input.equals("2"))
                            System.out.println("Wrong input. Enter 1 for User, 2 for Staff or Q(q) to exit");
                        else
                        {
                            System.out.println("Enter your name,surname and password all delimited by space (Name Surname Password),"
                                    + " or Q(q) to exit");
                            if(input.equals("1"))
                                menuStage = MenuStage.STATUS_USER;
                            else
                                menuStage = MenuStage.STATUS_STAFF;
                        }
                    }break;
                    case STATUS_USER:
                    {
                        switch (userSubMenuStage)
                        {
                            case LOG_IN:
                            {
                                String[] loginData = input.split("\\s+");
                                if(loginData.length != 3)
                                    System.out.println("Wrong input,try again!\n"+"Enter your name,surname and"+
                                            " password all delimited by space (Name Surname Password), or Q(q) to exit");
                                else
                                {
                                    libraryUser = new LibraryUser(loginData[0],loginData[1],loginData[2]);
                                    if(libraryUser.logIn(usersDataBase))
                                    {
                                        System.out.println(libraryUser.getName() + " " + libraryUser.getSurname() +
                                                ", you have successfully logged in as " + libraryUser.getStatus()+".");
                                        System.out.println("Select operation. Enter '1' to BORROW a book, '2' to"+
                                                " RETURN the book or Q(q) to exit");
                                        userSubMenuStage = UserSubMenuStage.SELECT_OPERATION;
                                    }
                                    else
                                        System.out.println("LogIn error,check your name and password and try again"+
                                                " or enter Q(q) to exit");
                                }
                            }break;
                            case SELECT_OPERATION:
                            {
                                if(!input.equals("1") && !input.equals("2"))
                                    System.out.println("Wrong input. Enter '1' to BORROW a book, '2' to"+
                                            " RETURN the book or Q(q) to exit");
                                else
                                {
                                    System.out.println("Enter book's name and book's author in the following format: "+
                                            "Of humans bondage - W. Somerset Maugham, or Q(q) to exit");
                                    if(input.equals("1"))
                                        userSubMenuStage = UserSubMenuStage.BORROW_BOOK;
                                    else
                                        userSubMenuStage = UserSubMenuStage.RETURN_BOOK;
                                }
                            }break;
                            default : //BORROW_BOOK or RETURN_BOOK
                            {
                                String[] bookData = input.split("\\s*-\\s*");
                                if(bookData.length!=2)
                                    System.out.println("Wrong input,try again!\nEnter book's name and book's author in"+
                                            " the following format: Of humans bondage - W. Somerset Maugham,"+
                                            " or Q(q) to exit");
                                else
                                {
                                    if(userSubMenuStage.equals(UserSubMenuStage.BORROW_BOOK))
                                        System.out.println(libraryUser.borrowBook(booksDataBase,usersDataBase,bookData[0],bookData[1]) + "\n");
                                    else if(userSubMenuStage.equals(UserSubMenuStage.RETURN_BOOK))
                                        System.out.println(libraryUser.returnBook(booksDataBase,usersDataBase,bookData[0],bookData[1]) + "\n");

                                }

                                System.out.println("Select operation. Enter '1' to BORROW a book, '2' to"+
                                        " RETURN the book or Q(q) to exit");
                                userSubMenuStage = UserSubMenuStage.SELECT_OPERATION;
                            }break;
                        }
                    }break;
                    case STATUS_STAFF:
                    {
                        switch (staffSubMenuStage)
                        {
                            case LOG_IN:
                            {
                                String[] loginData = input.split("\\s+");
                                if(loginData.length != 3)
                                    System.out.println("Wrong input,try again!\n"+"Enter your name,surname and"+
                                            " password all delimited by space (Name Surname Password), or Q(q) to exit");
                                else
                                {
                                    libraryStaff = new LibraryStaff(loginData[0],loginData[1],loginData[2]);
                                    if(libraryStaff.logIn(usersDataBase))
                                    {
                                        if(!libraryStaff.getStatus().equals("staff"))
                                        {
                                            System.out.println(libraryStaff.getName()+" "+libraryStaff.getSurname()+" is user,"+
                                                    " please enter system again");
                                            done = true;
                                        }
                                        else
                                        {
                                            System.out.println(libraryStaff.getName() + " " + libraryStaff.getSurname() +
                                                    ", you have successfully logged in as " + libraryStaff.getStatus()+".");
                                            System.out.println("Select operation. Enter '1' to ADD a BOOK, '2' to"+
                                                    " REMOVE a BOOK, '3' to ADD a USER, '4' to REMOVE a USER Q(q) to exit");
                                            staffSubMenuStage = StaffSubMenuStage.SELECT_OPERATION;
                                        }
                                    }
                                    else
                                        System.out.println("LogIn error,check your name and password and try again"+
                                                " or enter Q(q) to exit");
                                }
                            }break;
                            case SELECT_OPERATION:
                            {
                                if(input.equals("1"))
                                {
                                    System.out.println("Enter book's name, author and number of books in the following format: "+
                                            "Of humans bondage - W. Somerset Maugham - 3), or Q(q) to exit");
                                    staffSubMenuStage = StaffSubMenuStage.ADD_BOOK;
                                }
                                else if (input.equals("2"))
                                {
                                    System.out.println("Enter book's name and book's author in the following format: "+
                                            "Of humans bondage - W. Somerset Maugham, or Q(q) to exit");
                                    staffSubMenuStage = StaffSubMenuStage.REMOVE_BOOK;
                                }
                                else if(input.equals("3"))
                                {
                                    System.out.println("Enter user's name,surname,password,status and books borrowed in "+
                                    "the following format : Mansur Tukaev mans1994 staff  bookName - author/bookName - author/\nor "
                                    + " Sult Tukaev sult1993 user none");
                                    staffSubMenuStage = StaffSubMenuStage.ADD_USER;
                                }
                                else if(input.equals("4"))
                                {
                                    System.out.println("Enter user's name and surname in the following format: " +
                                            "Mansur Tukaev");
                                    staffSubMenuStage = StaffSubMenuStage.REMOVE_USER;
                                }
                                else
                                    System.out.println("Wrong input. Enter '1' to ADD a BOOK, '2' to"+
                                            " REMOVE a BOOK, '3' to ADD a USER, '4' to REMOVE a USER Q(q) to exit");

                            }break;
                            case ADD_BOOK:
                            {
                                String[] bookData = input.split("\\s*-\\s*");
                                if(bookData.length!=3 || !bookData[2].matches("\\d"))
                                    System.out.println("Wrong input,try again!\nEnter book's name, author and number of "
                                            +"books in the following format: Of humans bondage - W. Somerset Maugham - 3"
                                            +", or Q(q) to exit");
                                else
                                    System.out.println(libraryStaff.addBook(booksDataBase,bookData[0],bookData[1],
                                            Integer.parseInt(bookData[2])) + "\n");

                                System.out.println("Select operation. Enter '1' to ADD a BOOK, '2' to"+
                                        " REMOVE a BOOK, '3' to ADD a USER, '4' to REMOVE a USER Q(q) to exit");
                                staffSubMenuStage = StaffSubMenuStage.SELECT_OPERATION;
                            }break;
                            case REMOVE_BOOK:
                            {
                                String[] bookData = input.split("\\s*-\\s*");
                                if(bookData.length!=2)
                                    System.out.println("Wrong input,try again!\nEnter book's name and book's author in "+
                                            "the following format: Of humans bondage - W. Somerset Maugham, or Q(q) to exit");
                                else
                                    System.out.println(libraryStaff.removeBook(booksDataBase,bookData[0],bookData[1])+"\n");

                                System.out.println("Select operation. Enter '1' to ADD a BOOK, '2' to"+
                                        " REMOVE a BOOK, '3' to ADD a USER, '4' to REMOVE a USER Q(q) to exit");
                                staffSubMenuStage = StaffSubMenuStage.SELECT_OPERATION;
                            }break;
                            case ADD_USER:
                            {
                                String[] userData = input.split("\\s+");
                                if(userData.length!=5)
                                    System.out.println("Wrong input,try again!\nEnter user's name,surname,password,status and books borrowed in "+
                                            "the following format : Mansur Tukaev mans1994 staff  bookName - author/bookName - author/\nor "
                                            + " Sult Tukaev sult1993 user none");
                                else
                                    System.out.println(libraryStaff.addUser(usersDataBase,userData[0],userData[1],userData[2],
                                            userData[3],userData[4])+"\n");

                                System.out.println("Select operation. Enter '1' to ADD a BOOK, '2' to"+
                                        " REMOVE a BOOK, '3' to ADD a USER, '4' to REMOVE a USER Q(q) to exit");
                                staffSubMenuStage = StaffSubMenuStage.SELECT_OPERATION;
                            }break;
                            case REMOVE_USER:
                            {
                                String[] userData = input.split("\\s+");
                                if(userData.length!=2)
                                    System.out.println("Wrong input,try again!\nEnter user's name and surname in the following format: " +
                                            "Mansur Tukaev");
                                else
                                    System.out.println(libraryStaff.removeUser(usersDataBase,userData[0],userData[1])+"\n");

                                System.out.println("Select operation. Enter '1' to ADD a BOOK, '2' to"+
                                        " REMOVE a BOOK, '3' to ADD a USER, '4' to REMOVE a USER Q(q) to exit");
                                staffSubMenuStage = StaffSubMenuStage.SELECT_OPERATION;

                            }break;
                            default: {/*do nothing*/}break;
                        }
                    }break;
                    case EXIT:
                    {
                        done = true;
                    }break;
                    default: {/*do nothing*/}break;
                }
            }

            System.out.println("Goodbye !");

           // booksDataBase.closeDataBase();
           // usersDataBase.closeDataBase();
        }
        catch (Exception ex )
        {
            System.out.println(ex);
        }
    }
}
