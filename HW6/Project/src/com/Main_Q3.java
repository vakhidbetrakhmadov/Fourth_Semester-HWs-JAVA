package com;

import com.familytree.*;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

public class Main_Q3 {

    public static void main(String[] args) {
        try {
            FamilyTree familyTree = readFamilyTree("family.txt");
            System.out.println(familyTree.toString());

            // ---------- Pre-order iterator -----------//
            Iterator<String> iterator = familyTree.iterator();
            while (iterator.hasNext())
                iterator.next();

            // ---------- Level-order iterator -----------//
            Iterator<String> levelOrderIterator = familyTree.levelOrderIterator();
            while (iterator.hasNext())
                levelOrderIterator.next();
        }
        catch (FamilyTreeException familyTreeException) {
            System.out.println(familyTreeException);
        }
        catch (IOException exception) {
            System.out.println(exception);
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
    }

    private static FamilyTree readFamilyTree(String fileName) throws IOException,FamilyTreeException,Exception {
        FamilyTree familyTree = null;
            Scanner scanner = new Scanner(new File(fileName));

            if (!scanner.hasNext())
                throw new Exception("Empty file!");

            familyTree = new FamilyTree(scanner.nextLine().trim());

            String[] input;
            while (scanner.hasNext()) {
                input = scanner.nextLine().split(",");
                familyTree.add(input[0].trim(), input[1].trim(), input[2].trim());
            }

            scanner.close();

        return familyTree;
    }
}


