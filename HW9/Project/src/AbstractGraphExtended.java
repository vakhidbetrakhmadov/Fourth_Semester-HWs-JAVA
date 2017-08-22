import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public abstract class AbstractGraphExtended extends AbstractGraph {

    private boolean identified[];
    private final static int UNCOLORED = 0;
    private final static int BLACK = 1;
    private final static int RED = 2;

    // Constructor
    /**
     * Construct a graph with the specified number of vertices
     * and the directed flag. If the directed flag is true,
     * this is a directed graph.
     * @param numV The number of vertices
     * @param directed The directed flag
     */
    public AbstractGraphExtended(int numV, boolean directed) {
        super(numV,directed);
        identified = new boolean[getNumV()];
    }

    public abstract void insert(Edge edge);
    public abstract boolean isEdge(int source, int dest);
    public abstract Edge getEdge(int source, int dest);
    public abstract Iterator<Edge> edgeIterator(int source);

    /**
     * Inserts random number of random edges to the graph
     * @param edgeLimit The upper limit for the number of edges to be inserted
     * @return number of edges inserted
     */
    public int addRandomEdgesToGraph(int edgeLimit) {
        int source, dest, edgesInserted = 0;
        Random random = new Random();
        int edgesToInsert = random.nextInt(edgeLimit);

        for(int i = 0; i < edgesToInsert; ++i){
            source = random.nextInt(getNumV());
            dest = random.nextInt(getNumV());
            if(!isEdge(source,dest)){
                insert(new Edge(source,dest));
                ++edgesInserted;
            }
        }

        return edgesInserted;
    }

    /**
     * Performs breadth first search on the graph
     * @param start The start vertex of the breadth first search
     * @return array which contains the predecessor of each vertex in the breadth-first search tree
     */
    public int[] breadthFirstSearch (int start){
        Queue <Integer> theQueue = new LinkedList <Integer> ();

        int[] parent = new int[getNumV()];
        for (int i = 0; i < getNumV(); i++) {
            parent[i] = -1;
        }

        boolean[] identified = new boolean[getNumV()];
        identified[start] = true;
        this.identified[start] = true; // for getConnectedComponentUndirectedGraph

        theQueue.offer(start);
        while (!theQueue.isEmpty()) {

            int current = theQueue.remove();
            Iterator < Edge > itr = edgeIterator(current);
            while (itr.hasNext()) {
                Edge edge = itr.next();
                int neighbor = edge.getDest();

                if (!identified[neighbor]) {

                    identified[neighbor] = true;
                    this.identified[neighbor] = true; // for getConnectedComponentUndirectedGraph
                    theQueue.offer(neighbor);

                    parent[neighbor] = current;
                }
            }

        }
        return parent;
    }

    /**
     * Finds all connected components of the given graph
     * @return array of the connected graphs
     * @throws UnsupportedOperationException if graph is directed
     */
    public Graph [] getConnectedComponentUndirectedGraph (){
        if(isDirected())
            throw new UnsupportedOperationException();

        int parent[] = null;
        List<Graph> connectedComponents = new ArrayList<>();
        for(int i = 0; i < identified.length; ++i)
            identified[i] = false;

        for(int i = 0; i < getNumV(); ++i) {
            if(!identified[i]){
                parent = breadthFirstSearch(i);
                parent[i] = i;
                connectedComponents.add(createGraphFromVertexes(parent));
            }
        }

        Graph[] toReturn = new Graph[connectedComponents.size()];
        for (int i = 0; i < connectedComponents.size(); ++i)
            toReturn[i] = connectedComponents.get(i);

        return toReturn;
    }

    /**
     * Checks if the graph is bipartite
     * @return true if the graph is bipartite, false otherwise
     * @throws UnsupportedOperationException if graph is directed
     */
    public boolean isBipartiteUndirectedGraph (){

        Graph[] connectedComponents = getConnectedComponentUndirectedGraph();
        for (int i = 0; i < connectedComponents.length; ++i){
            int[] vertexColors = new int[connectedComponents[i].getNumV()];
            if(!((AbstractGraphExtended)connectedComponents[i]).isBipartiteUndirectedGraph(vertexColors,0, RED))
                return false;
        }

        return true;
    }

    /**
     * Writes the graph to the output file
     * @param fileName The name of the output file
     */
    public void writeGraphToFile (String fileName){
        FileWriter fileWriter = null;
        try {
            File file = new File(fileName);
            fileWriter = new FileWriter(file);
            StringBuilder sb = new StringBuilder();
            Set<Edge> edgesSet = new LinkedHashSet<>();

            for (int i = 0; i < getNumV(); ++i){
                for(Iterator<Edge> it = edgeIterator(i); it.hasNext();) {
                    Edge nextEdge = it.next();
                    if(!edgesSet.contains(invertEdge(nextEdge))) {
                        edgesSet.add(nextEdge);
                    }
                }
            }

            sb.append(String.format("%d\n",getNumV()));
            for(Edge nextEdge : edgesSet){
                sb.append(String.format("%d %d",nextEdge.getSource(),nextEdge.getDest()));
                if(nextEdge.getWeight() == 1.0)
                    sb.append("\n");
                else
                    sb.append(String.format(" %f\n",nextEdge.getWeight()));
            }

            fileWriter.write(sb.toString());
            fileWriter.flush();
            fileWriter.close();

        }catch (IOException ex){
            ex.printStackTrace();
            System.exit(1);
        }
    }

    // ----------------------------------------- Helper methods ------------------------------------------------------//

    private boolean isBipartiteUndirectedGraph(int[] vertexColors, int current, int color){
        vertexColors[current] = color;
        for(Iterator<Edge> iter = edgeIterator(current);iter.hasNext();) {
            int neighbor = iter.next().getDest();
            if(vertexColors[neighbor] == UNCOLORED){
                if(hasSameColorNeighbors(vertexColors,neighbor, color == RED ? BLACK : RED))
                    return false;
                else
                    return isBipartiteUndirectedGraph(vertexColors,neighbor,color == RED ? BLACK : RED);
            }
        }

        return true;
    }

    private boolean hasSameColorNeighbors(int[] vertexColors, int current, int color){
        for(Iterator<Edge> iter = edgeIterator(current); iter.hasNext();) {
            int neighbor = iter.next().getDest();
            if(vertexColors[neighbor] == color)
                return true;
        }
        return false;
    }

    private Graph createGraphFromVertexes(int parent[]) {
        int vertexCount = 0;
        StringBuilder sb = new StringBuilder();
        Set<Edge> edgesSet = new LinkedHashSet<>();
        Map<Integer,Integer> map = new HashMap<>();
        Graph connectedComponent = null;

        for(int i = 0; i < parent.length; ++i){
            if(parent[i] != -1){
                map.put(i,vertexCount);
                for(Iterator<Edge> it = edgeIterator(i); it.hasNext();) {
                    Edge nextEdge = it.next();
                    if(!edgesSet.contains(invertEdge(nextEdge))) {
                        edgesSet.add(nextEdge);
                    }
                }
                ++vertexCount;
            }
        }

        edgesSet = remapEdges(edgesSet,map);

        sb.append(String.format("%d\n",vertexCount));
        for(Edge nextEdge : edgesSet){
            sb.append(String.format("%d %d",nextEdge.getSource(),nextEdge.getDest()));
            if(nextEdge.getWeight() == 1.0)
                sb.append("\n");
            else
                sb.append(String.format(" %f\n",nextEdge.getWeight()));
        }

        try {
            if(this instanceof ListGraph)
                connectedComponent = createGraph(new Scanner(sb.toString()),false,"List");
            else
                connectedComponent = createGraph(new Scanner(sb.toString()),false,"Matrix");
        }catch (IOException ex){
            ex.printStackTrace();
            System.exit(1);
        }

        return connectedComponent;
    }

    private Set<Edge> remapEdges(Set<Edge> edgesSet, Map<Integer,Integer> map){
        Set<Edge> remapedEdgesSet = new HashSet<>();
        for(Edge nextEdge : edgesSet){
            remapedEdgesSet.add(new Edge(map.get(nextEdge.getSource()),map.get(nextEdge.getDest()),nextEdge.getWeight()));
        }

        return remapedEdgesSet;
    }

    private Edge invertEdge(Edge edge){
        return new Edge(edge.getDest(),edge.getSource(),edge.getWeight());
    }
}
