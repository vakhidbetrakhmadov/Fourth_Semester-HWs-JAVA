package com.familytree;

/**
 * The class {@code FamilyTreeException} is a form of
 * {@code Throwable} that indicates conditions that a new family member can not be inserted
 **/
public class FamilyTreeException extends Exception {

    public FamilyTreeException() {
        super();
    }

    public FamilyTreeException(String message) {
        super(message);
    }
}
