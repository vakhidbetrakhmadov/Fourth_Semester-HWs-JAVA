package com.restermans;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MainClass_Q3 {

    public static void main(String[] argv)
    {
        try {
            FileWriter fileWriter = new FileWriter(" testResult_3.csv");
            Scanner scanner = new Scanner(new File("test.csv"));

            PriorityQueueA<Integer> priorityQueueA1 = new PriorityQueueA<>();
            PriorityQueueA<Double> priorityQueueA2 = new PriorityQueueA<>();
            PriorityQueueA<Character> priorityQueueA3 = new PriorityQueueA<>();
            PriorityQueueA<String> priorityQueueA4 = new PriorityQueueA<>();

            PriorityQueueB<Integer> priorityQueueB1 = new PriorityQueueB<>();
            PriorityQueueB<Double> priorityQueueB2 = new PriorityQueueB<>();
            PriorityQueueB<Character> priorityQueueB3 = new PriorityQueueB<>();
            PriorityQueueB<String> priorityQueueB4 = new PriorityQueueB<>();


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
                        priorityQueueA1.insert(Integer.parseInt(line[i][j]));
                        priorityQueueB1.insert(Integer.parseInt(line[i][j]));
                    }
                    else if(i == 1)
                    {
                        priorityQueueA2.insert(Double.parseDouble(line[i][j]));
                        priorityQueueB2.insert(Double.parseDouble(line[i][j]));
                    }
                    else if(i == 2)
                    {
                        priorityQueueA3.insert(line[i][j].charAt(0));
                        priorityQueueB3.insert(line[i][j].charAt(0));
                    }
                    else
                    {
                        priorityQueueA4.insert(line[i][j]);
                        priorityQueueB4.insert(line[i][j]);
                    }
                }
            }

            printQueue(priorityQueueA1,fileWriter);
            printQueue(priorityQueueA2,fileWriter);
            printQueue(priorityQueueA3,fileWriter);
            printQueue(priorityQueueA4,fileWriter);
            printQueue(priorityQueueB1,fileWriter);
            printQueue(priorityQueueB2,fileWriter);
            printQueue(priorityQueueB3,fileWriter);
            printQueue(priorityQueueB4,fileWriter);

            scanner.close();
            fileWriter.close();
        }
        catch (Exception ex )
        {
            System.out.println(ex);
        }
    }

    public static  <E extends Comparable> void printQueue(PriorityQueueA<E> queue, FileWriter fileWriter) throws IOException
    {
        fileWriter.write(queue.size() + ",");
        while(!queue.isEmpty())
        {
            fileWriter.write(queue.deleteMin()+(queue.isEmpty() ? "\n" : ","));
        }

        fileWriter.flush();
    }

    public static  <E extends Comparable> void printQueue(PriorityQueueB<E> queue, FileWriter fileWriter) throws IOException
    {
        fileWriter.write(queue.size() + ",");
        while(!queue.isEmpty())
        {
            fileWriter.write(queue.deleteMin()+(queue.isEmpty() ? "\n" : ","));
        }

        fileWriter.flush();
    }
}
