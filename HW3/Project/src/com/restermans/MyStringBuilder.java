package com.restermans;

import java.util.Iterator;

/**
 * This is a simple copy of StringBuilder java class. This implementation uses MyList.
 *
 * <p>The principal operations on a {@code MyStringBuilder} are the
 * {@code append} methods, which are overloaded so as to accept data of any type.
 *
 * <p>This implementation also has 3 toString methods with different efficiencies (toString1,toString2,toString3)
 *  which convert all appended elements into one concatenated string.
 *
 *  @author  Vakhid Betrakhmadov
 */
public class MyStringBuilder {

    /**
     * Value to hold objects appended
     */
    private MyList<Object> myList = null;

    /**
     * Constructs a string builder with no characters in it.
     */
    public MyStringBuilder()
    {
        myList = new SingleLinkedList<>();
    }

    //----------------------------------------------------------------------------------------------------------------//
    public String toString1()
    {
        String toReturn = "";
        for(int i = 0; i < myList.size(); ++i)
            toReturn+=myList.get(i).toString();

        return toReturn;
    }

    public String toString2()
    {
        String toReturn = "";
        Iterator<Object> iterator = myList.iterator();
        while(iterator.hasNext())
            toReturn+=iterator.next().toString();

        return toReturn;
    }

    public String toString3()
    {
        return myList.toString();
    }
    //----------------------------------------------------------------------------------------------------------------//

    /**
     * Appends the specified {@code Object} to this sequence.
     * <p>
     * @param obj object to be appended
     * @return a reference to this object.
     */
    public MyStringBuilder append(Object obj)
    {
        myList.add(obj);
        return this;
    }

    /**
     * Appends the specified {@code String} to this sequence.
     * <p>
     * @param str string to be appended
     * @return a reference to this object.
     */
    public MyStringBuilder append(String str)
    {
        myList.add(str);
        return this;
    }

    /**
     * Appends the specified {@code StringBuffer} to this sequence.
     * <p>
     * @param sb stringBuffer to be appended
     * @return a reference to this object.
     */
    public MyStringBuilder append(StringBuffer sb)
    {
        myList.add(sb);
        return this;
    }

    /**
     * Appends the specified {@code CharSequence} to this sequence.
     * <p>
     * @param s CharSequence to be appended
     * @return a reference to this object.
     */
    public MyStringBuilder append(CharSequence s)
    {
        myList.add(s);
        return this;
    }

    /**
     * Appends the specified {@code char[]} to this sequence.
     * <p>
     * @param str char[] to be appended
     * @return a reference to this object.
     */
    public MyStringBuilder append(char[] str)
    {
        for(int i = 0; i < str.length; ++i)
            myList.add(str[i]);

        return this;
    }

    /**
     * Appends the specified {@code boolean} to this sequence.
     * <p>
     * @param b boolean to be appended
     * @return a reference to this object.
     */
    public MyStringBuilder append(boolean b)
    {
        myList.add(b);
        return this;
    }

    /**
     * Appends the specified {@code char} to this sequence.
     * <p>
     * @param c char to be appended
     * @return a reference to this object.
     */
    public MyStringBuilder append(char c)
    {
        myList.add(c);
        return this;
    }

    /**
     * Appends the specified {@code int} to this sequence.
     * <p>
     * @param i int to be appended
     * @return a reference to this object.
     */
    public MyStringBuilder append(int i)
    {
        myList.add(i);
        return this;
    }

    /**
     * Appends the specified {@code long} to this sequence.
     * <p>
     * @param l long to be appended
     * @return a reference to this object.
     */
    public MyStringBuilder append(long l)
    {
        myList.add(l);
        return this;
    }

    /**
     * Appends the specified {@code float} to this sequence.
     * <p>
     * @param f float to be appended
     * @return a reference to this object.
     */
    public MyStringBuilder append(float f)
    {
        myList.add(f);
        return this;
    }

    /**
     * Appends the specified {@code double} to this sequence.
     * <p>
     * @param d double to be appended
     * @return a reference to this object.
     */
    public MyStringBuilder append(double d)
    {
        myList.add(d);
        return this;
    }

    /**
     * Appends a subsequence of the specified CharSequence to this sequence.
     * Characters of the argument s, starting at index start, are appended, in order, to the contents of this sequence
     * up to the (exclusive) index end. The length of this sequence is increased by the value of end - start.
     * @param s the sequence to append.
     * @param start the starting index of the subsequence to be appended
     * @param end the end index of the subsequence to be appended.
     * @return a reference to this object.
     */
    public MyStringBuilder append(CharSequence s,int start,int end)
    {
        myList.add(s.subSequence(start,end));
        return this;
    }

    /**
     * Appends the string representation of a subarray of the char array argument to this sequence.
     * Characters of the char array str, starting at index offset, are appended, in order, to the contents of this
     * sequence. The length of this sequence increases by the value of len.
     *
     * @param str the characters to be appended.
     * @param offset the index of the first char to append.
     * @param len the number of chars to append.
     * @return  a reference to this object.
     * @throws IndexOutOfBoundsException if offset < 0 or len < 0 or offset+len > str.length
     */
    public MyStringBuilder append(char[] str,int offset,int len)
    {
        if(offset < 0 || len < 0 || offset+len > str.length)
            throw new IndexOutOfBoundsException();

        for(int i = offset; i < offset+len ; ++i)
            myList.add(str[i]);

        return this;
    }
}
