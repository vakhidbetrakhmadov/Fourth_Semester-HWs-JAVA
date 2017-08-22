package com;
import com.hafmantree.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main_Q2 {

    public static void main(String[] args) {
        HuffmanTree huffmanTree = new HuffmanTree();
        HuffmanTree.HuffData[] freqArray = readFile("freq.txt");

        huffmanTree.buildTree(freqArray);
        huffmanTree.printToScreen();

        System.out.println(huffmanTree.encodeUsingMap("abc"));
        System.out.println(huffmanTree.encodeRecursively("abc"));

        System.out.println( huffmanTree.decode(huffmanTree.encodeUsingMap("some")));
        System.out.println( huffmanTree.decode(huffmanTree.encodeRecursively("some")));
        
    }

    private static HuffmanTree.HuffData[] readFile(String filename)
    {
        List<HuffmanTree.HuffData> freqList = new ArrayList<>();
        try {
            /*-------------- Read file -------------------*/
            Scanner fileScanner = new Scanner(new File(filename));
            while(fileScanner.hasNext()) {
                String nextLine = fileScanner.nextLine();
                String[] column = nextLine.split(" ");
                freqList.add(new HuffmanTree.HuffData(Double.parseDouble(column[1]), column[0].charAt(0)));
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }

        return freqList.toArray(new HuffmanTree.HuffData[freqList.size()]);
    }
}
