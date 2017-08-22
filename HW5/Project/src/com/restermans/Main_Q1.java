package com.restermans;


import java.io.File;
import java.util.Iterator;
import java.util.Scanner;

public class Main_Q1 {

    public static void main(String[] argv) {
        CompleteBinaryTree<Integer> completeBinaryTree = new CompleteBinaryTree<>();
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();

        readBinaryTree(completeBinaryTree, "test.txt");
        readBinaryTree(binarySearchTree, "test.txt");

        System.out.println("Binary tree (complete):\n" + completeBinaryTree.toString());
        System.out.println("Traversing binary tree (complete) in pre-order");
        traverseBinaryTree(completeBinaryTree.iterator());

        System.out.println();

        System.out.println("Binary search tree:\n" + binarySearchTree.toString());
        System.out.println("Traversing binary search tree in pre-order");
        traverseBinaryTree(binarySearchTree.iterator());
        System.out.println("Traversing binary search tree in level-order");
        traverseBinaryTree(binarySearchTree.levelOrderIterator());
    }

    private static void readBinaryTree(BinaryTree<Integer> binaryTree, String fileName) {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNext())
                binaryTree.add(scanner.nextInt());

            scanner.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    private static <E> void traverseBinaryTree(Iterator<E> iterator) {
        while (iterator.hasNext())
            iterator.next();
        System.out.println();
    }
}
