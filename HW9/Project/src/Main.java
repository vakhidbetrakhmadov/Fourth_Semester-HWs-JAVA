import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try {
            ListGraph listGraph1 = (ListGraph) AbstractGraph.createGraph(new Scanner(new File("input1.txt")),false,"List");
            ListGraph listGraph2 = (ListGraph) AbstractGraph.createGraph(new Scanner(new File("input2.txt")),false,"List");
            MatrixGraph matrixGraph1 = (MatrixGraph) AbstractGraph.createGraph(new Scanner(new File("input1.txt")),false,"Matrix");
            MatrixGraph matrixGraph2 = (MatrixGraph) AbstractGraph.createGraph(new Scanner(new File("input2.txt")),false,"Matrix");

            System.out.println("\nTesting listGraph with input1.txt:");
            testAbstractGraphExtended(listGraph1,"listGraphInput1");
            System.out.println("\nTesting listGraph with input2.txt:");
            testAbstractGraphExtended(listGraph2,"listGraphInput2");
            System.out.println("\nTesting matrixGraph with input1.txt:");
            testAbstractGraphExtended(matrixGraph1,"matrixGraphInput1");
            System.out.println("\nTesting matrixGraph with input2.txt:");
            testAbstractGraphExtended(matrixGraph2,"matrixGraphInput2");

        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public static void testAbstractGraphExtended(AbstractGraphExtended graph, String outputFileNamePrefix){

        System.out.println("isBipartiteUndirectedGraph: " + graph.isBipartiteUndirectedGraph());

        Random random = new Random();
        int vertex = random.nextInt(graph.getNumV());
        System.out.printf("breadthFirstSearch(%d):\n",vertex);
        int parent[] = graph.breadthFirstSearch(vertex);
        for (int next: parent)
            System.out.print(next + " ");

        Graph[] subGraphs = graph.getConnectedComponentUndirectedGraph();
        for (int i = 0; i < subGraphs.length; ++i){
            ((AbstractGraphExtended)subGraphs[i]).writeGraphToFile(String.format("%sConnectedComponent%d.txt",outputFileNamePrefix,i));
        }

        System.out.println("\nEdges were added: " + graph.addRandomEdgesToGraph(15));

        System.out.println("isBipartiteUndirectedGraph: " + graph.isBipartiteUndirectedGraph());

        graph.writeGraphToFile(String.format("%sAfterEdgesAdded.txt",outputFileNamePrefix));


    }


}
