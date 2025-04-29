//Ivan Vasilev
import java.util.*;
public class GarbageCollector {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numberOfSlots = sc.nextInt();
        int numberOfRoots = sc.nextInt();
        ArrayList<Integer> slots = new ArrayList<>();
        Graph<Integer, Integer> graph = new Graph<>();
        Graph<Integer, Integer> graphDop = new Graph<>();
        for (int i = 0; i < numberOfSlots; i++) {
            slots.add(sc.nextInt());
        }
        ArrayList<Integer> roots = new ArrayList<>();
        for (int i = 0; i < numberOfRoots; i++) {
            roots.add(sc.nextInt());
        }
        for (int i = 0; i < numberOfRoots; i++) {
            graph.addVertex(slots.get(roots.get(i)), roots.get(i)); // reference and index
            graphDop.addVertex(slots.get(roots.get(i)), roots.get(i));
        }
        for (Graph<Integer, Integer>.Vertex v : graph.vertices){
            if (v.value > 0){
                int reference = v.value;
                while (reference == 0){
                    int value = slots.get(reference); // ref = -3 ... slots.get(ref) =
                    Graph<Integer, Integer>.Vertex v1 = graphDop.addVertex(value, reference);
                    for (Graph<Integer, Integer>.Vertex vertexFromRootsMaybe : graphDop.vertices){
                        if (vertexFromRootsMaybe.index == value){
                            graphDop.addEdge(vertexFromRootsMaybe, v1);
                        } // тут я хотел сделать ребра для вершин
                    }

                    reference = value;
                }
            }
        }
        graph.IvanVasilev_dfs(graphDop);
        for(Graph<Integer, Integer>.Vertex v : graphDop.answer){
            slots.set(v.index, -1233);
        }
        StringBuilder mAnswer = new StringBuilder();
        for (int i = 0; i < numberOfSlots; i++) {
            if (slots.get(i) == -1233){
                mAnswer.append('0');
            } else {
                mAnswer.append('1');
            }
        }
        int AmountOf1 = 0;
        boolean flag2 = true;
        int dopAcum = 0;
        for (int i = 0; i < mAnswer.length(); i++){
            if (mAnswer.charAt(i) == '1' && flag2){
                AmountOf1++;
                flag2 = false;
            }
            if (mAnswer.charAt(i) == '1'){
                dopAcum++;
            }
            if ((mAnswer.charAt(i) == '0' && dopAcum > 0) || (i == mAnswer.length() - 1) && dopAcum > 0){
                dopAcum = 0;
                flag2 = true;
            }
        }
        if (mAnswer.length() == 0){
            System.exit(0);
        }




        for (int i = 0; i < numberOfRoots; i++) {
            int ref = roots.get(i);
            while (ref >= 0){
                int value = slots.get(ref);
                slots.set(ref, -1233);
                ref = value;
            }
        }
        int count = 0;
        boolean found = true;
        for (int i = 0; i < numberOfSlots; i++) {
            if (slots.get(i) != -1233 && found){
                found = false;
                count++;
            }
            if (slots.get(i) == -1233  || i == slots.size() - 1){
                found = true;

            }
        }
        System.out.println(count);
        if (count == 0){
            System.exit(0);
        }

        boolean flag = true;
        int acumSum = 0;
        for (int i = 0; i < numberOfSlots; i++) {
            if (slots.get(i) != -1233 && flag){
                System.out.print(i);
                flag = false;
            }
            if (slots.get(i) != -1233) {
                acumSum++;
            }
            if ((slots.get(i) == -1233 && acumSum > 0) || (i == slots.size() - 1 && acumSum > 0) ) {
                System.out.println(" " + acumSum);
                acumSum = 0;
                flag = true;
            }
        }








    }

}

class Graph<V, E>{
    class Vertex{
        V value;
        V index;
        Color color;
        int d;
        int finish;
        Vertex papa;
        ArrayList<Edge> adjacent;
        public Vertex(V value, V index){
            this.value = value;
            this.index = index;
            this.adjacent = new ArrayList<Edge>();
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
    ArrayList<Vertex> vertices;
    ArrayList<Edge> edges;
    public Graph(){
        this.vertices = new ArrayList<Vertex>();
        this.edges = new ArrayList<Edge>();
    }
    Vertex addVertex(V value, V index){
        Vertex v = new Vertex(value, index);
        this.vertices.add(v);
        return v;
    }
    Edge addEdge(Vertex from, Vertex to){
        Edge e = new Edge(from, to);
        from.adjacent.add(e);
        //to.adjacent.add(e);
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
    int time = 0;
    Graph<V, E> IvanVasilev_dfs(Graph<V, E> graph){
        for (Vertex v : graph.vertices){
            v.color = Color.WHITE;
            v.papa = null;
        }
        time = 0;
        for(Vertex v : graph.vertices){
            if (v.color == Color.WHITE){
                IvanVasilev_dfs_Recursive(graph, v);
            }
        }
        return graph;
    }
    Stack<Vertex> answer = new Stack<>();
    boolean hasCycle = false;
    void IvanVasilev_dfs_Recursive(Graph<V, E> graph, Vertex v){
        time += 1;
        v.d = time;
        v.color = Color.GRAY;
        for (Edge edge : v.adjacent){
            if (edge.to.color == Color.WHITE){
                edge.to.papa = v;
                IvanVasilev_dfs_Recursive(graph, edge.to);
            } else if(edge.to.color == Color.GRAY){
                hasCycle = true;
            }
        }
        v.color = Color.BLACK;
        answer.push(v);
        time += 1;
        v.finish = time;
    }
    boolean topoSort(Graph<V, E> graph){
        Graph<V, E> dfsGraph = IvanVasilev_dfs(graph);
        if (hasCycle){
            return true;
        } else {
            return false;
        }
    }

}
enum Color {
    WHITE, GRAY, BLACK
}
