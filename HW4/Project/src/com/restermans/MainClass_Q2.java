package com.restermans;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class MainClass_Q2 {

    public static void main(String[] argv)
    {
        try {

            FileWriter fileWriter = new FileWriter(" testResult_2.csv");
            Scanner scanner = new Scanner(new File("test.csv"));

            MyQueue<Integer> myQueue1 = new MyQueue<>();
            MyQueue<Double> myQueue2 = new MyQueue<>();
            MyQueue<Character> myQueue3 = new MyQueue<>();
            MyQueue<String> myQueue4 = new MyQueue<>();

            Queue<Integer> queue1 = new ArrayDeque<>();
            Queue<Double> queue2 = new ArrayDeque<>();
            Queue<Character> queue3 = new ArrayDeque<>();
            Queue<String> queue4 = new ArrayDeque<>();


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
                        myQueue1.offer(Integer.parseInt(line[i][j]));
                        queue1.offer(Integer.parseInt(line[i][j]));
                    }
                    else if(i == 1)
                    {
                        myQueue2.offer(Double.parseDouble(line[i][j]));
                        queue2.offer(Double.parseDouble(line[i][j]));
                    }
                    else if(i == 2)
                    {
                        myQueue3.offer(line[i][j].charAt(0));
                        queue3.offer(line[i][j].charAt(0));

                    }
                    else
                    {
                        myQueue4.offer(line[i][j]);
                        queue4.offer(line[i][j]);
                    }
                }
            }

            myQueue1.reverse();
            myQueue2.reverse();
            myQueue3.reverse();
            myQueue4.reverse();
            queue1 = MyQueue.reverseQueue(queue1);
            queue2 = MyQueue.reverseQueue(queue2);
            queue3 = MyQueue.reverseQueue(queue3);
            queue4 = MyQueue.reverseQueue(queue4);


            printQueue(myQueue1,fileWriter);
            printQueue(myQueue2,fileWriter);
            printQueue(myQueue3,fileWriter);
            printQueue(myQueue4,fileWriter);
            printQueue(queue1,fileWriter);
            printQueue(queue2,fileWriter);
            printQueue(queue3,fileWriter);
            printQueue(queue4,fileWriter);

            scanner.close();
            fileWriter.close();
        }
        catch (Exception ex )
        {
            System.out.println(ex);
        }
    }

    public static  <E> void printQueue(Queue<E> queue,FileWriter fileWriter) throws IOException
    {
        fileWriter.write(queue.size() + ",");
        while(!queue.isEmpty())
        {
            fileWriter.write(queue.poll()+(queue.isEmpty() ? "\n" : ","));
        }

        fileWriter.flush();
    }
}
