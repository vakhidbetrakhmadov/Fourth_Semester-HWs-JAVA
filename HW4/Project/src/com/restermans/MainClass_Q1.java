package com.restermans;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MainClass_Q1 {

    public static void main(String[] args) {
        try {

            FileWriter fileWriter = new FileWriter(" testResult_1.csv");
            Scanner scanner = new Scanner(new File("test.csv"));

            StackA<Integer> stackA1 = new StackA<>();
            StackA<Double> stackA2 = new StackA<>();
            StackA<Character> stackA3 = new StackA<>();
            StackA<String> stackA4 = new StackA<>();

            StackB<Integer> stackB1 = new StackB<>();
            StackB<Double> stackB2 = new StackB<>();
            StackB<Character> stackB3 = new StackB<>();
            StackB<String> stackB4 = new StackB<>();


            StackC<Integer> stackC1 = new StackC<>();
            StackC<Double> stackC2 = new StackC<>();
            StackC<Character> stackC3 = new StackC<>();
            StackC<String> stackC4 = new StackC<>();

            StackD<Integer> stackD1 = new StackD<>();
            StackD<Double> stackD2 = new StackD<>();
            StackD<Character> stackD3 = new StackD<>();
            StackD<String> stackD4 = new StackD<>();


            String[][] line = new String[4][];
            String nextLine = "";
            for(int i = 0; scanner.hasNext() && i < 4; ++i)
            {
                nextLine = scanner.nextLine();
                nextLine.trim();
                line[i] = nextLine.split(",");
            }

            for(int i = 0; i < line.length; ++i)
            {
                for(int j = 0; j < line[i].length; ++j)
                {
                    if(i == 0 )
                    {
                        stackA1.push(Integer.parseInt(line[i][j]));
                        stackB1.push(Integer.parseInt(line[i][j]));
                        stackC1.push(Integer.parseInt(line[i][j]));
                        stackD1.push(Integer.parseInt(line[i][j]));
                    }
                    else if(i == 1)
                    {
                        stackA2.push(Double.parseDouble(line[i][j]));
                        stackB2.push(Double.parseDouble(line[i][j]));
                        stackC2.push(Double.parseDouble(line[i][j]));
                        stackD2.push(Double.parseDouble(line[i][j]));
                    }
                    else if(i == 2)
                    {
                        stackA3.push(line[i][j].charAt(0));
                        stackB3.push(line[i][j].charAt(0));
                        stackC3.push(line[i][j].charAt(0));
                        stackD3.push(line[i][j].charAt(0));
                    }
                    else
                    {
                        stackA4.push(line[i][j]);
                        stackB4.push(line[i][j]);
                        stackC4.push(line[i][j]);
                        stackD4.push(line[i][j]);
                    }
                }
            }

            printStack(stackA1,fileWriter);
            printStack(stackA2,fileWriter);
            printStack(stackA3,fileWriter);
            printStack(stackA4,fileWriter);
            printStack(stackB1,fileWriter);
            printStack(stackB2,fileWriter);
            printStack(stackB3,fileWriter);
            printStack(stackB4,fileWriter);
            printStack(stackC1,fileWriter);
            printStack(stackC2,fileWriter);
            printStack(stackC3,fileWriter);
            printStack(stackC4,fileWriter);
            printStack(stackD1,fileWriter);
            printStack(stackD2,fileWriter);
            printStack(stackD3,fileWriter);
            printStack(stackD4,fileWriter);

            scanner.close();
            fileWriter.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    public static  <E> void printStack(StackInterface<E> stack,FileWriter fileWriter) throws IOException
    {
        fileWriter.write(stack.size() + ",");
        while(!stack.isEmpty())
        {
            fileWriter.write(stack.pop()+(stack.isEmpty() ? "\n" : ","));
        }

        fileWriter.flush();
    }

}