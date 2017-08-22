package com.restermans;

/**
 * The {@code SystemUser} class represents a general user of the Library Management System
 * It holds such user data as name,surname,password,status and books borrowed by the user
 * It has accessors to the user' name,surname and status
 *
 *@author  Vakhid Betrakhmadov
 */
public abstract class SystemUser {
    /**The value is used to store user's name */
    protected String name = null;
    /**The value is used to store user's surname */
    protected String surname = null;
    /**The value is used to store user's password */
    protected String password = null;
    /**The value is used to store user's status */
    protected String status = null;
    /**The value is used to store user's booksBorrowed */
    protected String booksBorrowed = null;

    /**
     * Initializes new SystemUser
     * Sets name,surname and password to those given in arguments list
     * Sets status and booksBorrowed to "none" by default
     *
     * @param name
     *        Name of the user
     *
     * @param surname
     *        Surname of the user
     *
     * @param password
     *        Password of the user
     */
    public SystemUser(String name,String surname,String password) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.status = "none";
        this.booksBorrowed = "none";
    }

    /**
     * Returns user name
     * @return user name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns user surname
     * @return user surname
     */
    public String getSurname() {return  surname;}

    /**
     * Returns user status
     * @return user status
     */
    public String getStatus()
    {
        return status;
    }


    /**
     * Checks if the user is in the data base, if the user is found
     * sets user status ans books borrowed by the user fields
     *
     * @param dataBase
     *        database to search for the user
     *
     * @return true if the user was found,false otherwise
     *
     * @throws Exception if the data base passed to the method has less then 5 fields
     */
    public boolean logIn(DataBase dataBase)throws Exception {
        if(dataBase.numberOfFields() < 5)
            throw new Exception("Too few fields in dataBase");

        Record user = new Record(name,surname,password,"none","none");
        if((user = (dataBase.findRecord(user))) == null)
            return false;

        status = user.getField(4);
        booksBorrowed = user.getField(5);

        return true;
    }
}
