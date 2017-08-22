package com.restermans;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

public class Main_Q2 {

    public static void main(String[] args) {
        try {
            FamilyTree familyTree = readFamilyTree("family.txt");
            System.out.println(familyTree.toString());
            Iterator<String> iterator = familyTree.iterator();
            while (iterator.hasNext())
                iterator.next();
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


