package com.restermans;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        final int SIZE = 20;
        final int RANGE = 50;

        AVLTree<Integer> myTree = new AVLTree<>();
        Random randomNumberGenerator = new Random();

        for(int i = 0; i < SIZE; ++i){
            int next = randomNumberGenerator.nextInt(RANGE)+1;
            System.out.println("Inserting: " + next);
            myTree.add(next);
            System.out.println("My tree: \n" + myTree);
        }

        for(int i = 0; i < SIZE; ++i){
            int next = randomNumberGenerator.nextInt(RANGE)+1;
            System.out.println("Removing " + next + " from the myTree: " + myTree.remove(next));
            System.out.println("My tree: \n" + myTree);
        }
    }
}
