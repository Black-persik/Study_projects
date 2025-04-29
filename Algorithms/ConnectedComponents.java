// Ivan Vasilev 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class ConnectedComponents{
    public static void main(String[] arg){
        Graph<Integer, Integer> graph = new Graph<>();
        Scanner sc = new Scanner(System.in);
        int numberOfVertices = sc.nextInt();
        int numberOfEdges = sc.nextInt();
        for (int i = 1; i <= numberOfVertices; i++) {
            graph.addVertex(i);
        }
        for (int i = 0; i < numberOfEdges; i++) {
            int v1 = sc.nextInt();
            int v2 = sc.nextInt();
            Graph<Integer, Integer>.Vertex V1 = graph.vertices.get(v1);
            Graph<Integer, Integer>.Vertex V2 = graph.vertices.get(v2);
            //Graph<Integer, Integer>.Vertex V1 = graph.vertices.stream().filter(vertex -> vertex.value.equals(v1)).findFirst().orElse(null);
            //Graph<Integer, Integer>.Vertex V2 = graph.vertices.stream().filter(vertex -> vertex.value.equals(v2)).findFirst().orElse(null);
            graph.addEdge(V1, V2);
        }
        System.out.println(graph.IvanVasilev_mst_kruskal());
    }
}

class Graph<V, E>{
    class Vertex{
        V value;
        Vertex root;
        int rank;
        ArrayList<Edge> adjacent;
        public Vertex(V value){
            this.rank = 0;
            this.value = value;
            this.root = this;
            this.adjacent = new ArrayList<>();
        }
    }
    class Edge{
        Vertex from;
        Vertex to;

        public Edge(Vertex from, Vertex to){
            this.from = from;
            this.to = to;
        }
    }
    HashMap<V, Vertex> vertices;
    ArrayList<Edge> edges;
    public Graph(){
        this.vertices = new HashMap<>();
        this.edges = new ArrayList<Edge>();
    }
    Vertex addVertex(V value){
        Vertex v = new Vertex(value);
        this.vertices.put(value, v);
        return v;
    }
    Edge addEdge(Vertex from, Vertex to){
        Edge e = new Edge(from, to);
        from.adjacent.add(e);
        to.adjacent.add(e);
        this.edges.add(e);
        return e;
    }
    boolean isAdjacent(Vertex from, Vertex to){
        for(Edge e : from.adjacent){
            if (e.from.equals(to)){
                return true;
            }
        }
        return false;
    }
    void removeEdge(Edge e){
        (e.from).adjacent.remove(e);
        (e.to).adjacent.remove(e);
        this.edges.remove(e);
    }
    void removeVertex(Vertex v){
        for (Edge e : v.adjacent){
            e.from.adjacent.remove(e);
            e.to.adjacent.remove(e);
            this.edges.remove(e);
        }
        this.vertices.remove(v);
    }

    Vertex find_vertexSet(Vertex v){
        if (v != v.root){
            v.root = find_vertexSet(v.root);
        }
        return v.root;
    }
    void union(Vertex v1, Vertex v2){
        Vertex root1 = find_vertexSet(v1);
        Vertex root2 = find_vertexSet(v2);
        if (root1.rank > root2.rank){
            root2.root = root1;
        } else {
            root1.root = root2;
            if (root1.rank == root2.rank){
                root2.rank++;
            }
        }
    }
    int IvanVasilev_mst_kruskal(){
        for (Edge e : edges) {
            union(e.from, e.to);
        }
        HashSet<Vertex> setVertex = new HashSet<>();
        for (Vertex v : vertices.values()) {
            setVertex.add(find_vertexSet(v));
        }
        return setVertex.size();
    }


}

