package com.hafmantree;

import java.util.*;
import java.io.*;

/**
 * Class to represent and build a Huffman tree.
 *
 * @author Koffman and Wolfgang
 */

public class HuffmanTree
        implements Serializable {

    // Nested Classes

    /**
     * A datum in the Huffman tree.
     */
    public static class HuffData
            implements Serializable {
        // Data Fields
        /**
         * The weight or probability assigned to this HuffData.
         */
        private double weight;

        /**
         * The alphabet symbol if this is a leaf.
         */
        private Character symbol;

        public HuffData(double weight, Character symbol) {
            this.weight = weight;
            this.symbol = symbol;
        }
    }

    // Data Fields
    /**
     * A reference to the completed Huffman tree.
     */
    private BinaryTree<HuffData> huffTree;

    // ------------ Map to keep characters and their codes --------------//
    private Map<Character,String> huffMap = new HashMap<>();

    /**
     * A Comparator for Huffman trees; nested class.
     */
    private static class CompareHuffmanTrees
            implements Comparator<BinaryTree<HuffData>> {
        /**
         * Compare two objects.
         *
         * @param treeLeft  The left-hand object
         * @param treeRight The right-hand object
         * @return -1 if left less than right,
         * 0 if left equals right,
         * and +1 if left greater than right
         */
        public int compare(BinaryTree<HuffData> treeLeft,
                           BinaryTree<HuffData> treeRight) {
            double wLeft = treeLeft.getData().weight;
            double wRight = treeRight.getData().weight;
            return Double.compare(wLeft, wRight);
        }
    }

    /**
     * Builds the Huffman tree using the given alphabet and weights.
     * post:  huffTree contains a reference to the Huffman tree.
     *
     * @param symbols An array of HuffData objects
     */
    public void buildTree(HuffData[] symbols) {
        Queue<BinaryTree<HuffData>> theQueue
                = new PriorityQueue<BinaryTree<HuffData>>
                (symbols.length, new CompareHuffmanTrees());
        // Load the queue with the leaves.
        for (HuffData nextSymbol : symbols) {
            BinaryTree<HuffData> aBinaryTree =
                    new BinaryTree<HuffData>(nextSymbol, null, null);
            theQueue.offer(aBinaryTree);
        }

        // Build the tree.
        while (theQueue.size() > 1) {
            BinaryTree<HuffData> left = theQueue.poll();
            BinaryTree<HuffData> right = theQueue.poll();
            double wl = left.getData().weight;
            double wr = right.getData().weight;
            HuffData sum = new HuffData(wl + wr, null);
            BinaryTree<HuffData> newTree =
                    new BinaryTree<HuffData>(sum, left, right);
            theQueue.offer(newTree);
        }

        // The queue should now contain only one item.
        huffTree = theQueue.poll();

        //Build huffman map for encoding
        huffMap.clear();
        buildHuffMap("",huffTree);
    }

    //---------------- Building Huffman map from Huffman Tree --------------//
    private void buildHuffMap(String code,BinaryTree<HuffData> tree)
    {
        HuffData theData = tree.getData();
        if (theData.symbol != null) {
                huffMap.put(theData.symbol,code);
        } else {
            buildHuffMap(code + "0", tree.getLeftSubtree());
            buildHuffMap(code + "1", tree.getRightSubtree());
        }
    }

    /**
     * Outputs the resulting code.
     *
     * @param out  A PrintStream to write the output to
     * @param code The code up to this node
     * @param tree The current node in the tree
     */
    private void printCode(PrintStream out, String code,
                           BinaryTree<HuffData> tree) {
        HuffData theData = tree.getData();
        if (theData.symbol != null) {
            if (theData.symbol.equals(" ")) {
                out.println("space: " + code);
            } else {
                out.println(theData.symbol + ": " + code);
            }
        } else {
            printCode(out, code + "0", tree.getLeftSubtree());
            printCode(out, code + "1", tree.getRightSubtree());
        }
    }


    // ----------- Output the resulting code to the screen -------------//
    public void printToScreen()
    {
        printCode(System.out,"",huffTree);
    }

    /**
     * Method to decode a message that is input as a string of
     * digit characters '0' and '1'.
     *
     * @param codedMessage The input message as a String of
     *                     zeros and ones.
     * @return The decoded message as a String
     */
    public String decode(String codedMessage) {
        StringBuilder result = new StringBuilder();
        BinaryTree<HuffData> currentTree = huffTree;
        for (int i = 0; i < codedMessage.length(); i++) {
            if (codedMessage.charAt(i) == '1') {
                currentTree = currentTree.getRightSubtree();
            } else {
                currentTree = currentTree.getLeftSubtree();
            }
            if (currentTree.isLeaf()) {
                HuffData theData = currentTree.getData();
                result.append(theData.symbol);
                currentTree = huffTree;
            }
        }
        return result.toString();
    }


    // ----------------- Encoding in constant time using map ------------------------//
    public String encodeUsingMap(String message)
    {
        String result = "";
        for(int i = 0; i < message.length(); ++i)
        {
            result+=huffMap.get(message.charAt(i));
        }

        return result;
    }

    // ----------------- Encoding in Linear ? time using recursive traverse ------------------------//
    public String encodeRecursively(String message)
    {
        String result = "";
        for(int i = 0; i < message.length(); ++i)
        {
            result+=findCharacterCode(message.charAt(i),"",huffTree);
        }

        return result;
    }

    private String findCharacterCode(Character c,String code,BinaryTree<HuffData> tree)
    {
        String leftTreeSearchResult = "";
        String rightTreeSearchResult = "";

        if(tree.isLeaf())
            return (tree.getData().symbol.equals(c) ? code : "");

        leftTreeSearchResult = findCharacterCode(c,code + "0",tree.getLeftSubtree());
        if(leftTreeSearchResult.equals(""))
            rightTreeSearchResult = findCharacterCode(c,code + "1",tree.getRightSubtree());

        return (!leftTreeSearchResult.equals("") ? leftTreeSearchResult : rightTreeSearchResult);
    }
}
