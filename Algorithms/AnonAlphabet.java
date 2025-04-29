import java.util.*;
public class AnonAlphabet {
    public static void main(String[] args) {
        Graph<Character, Integer> graph = new Graph<>();
        Graph<Character, Integer> graphDop = new Graph<>();
        Scanner scanner = new Scanner(System.in);
        int numberOfWords = scanner.nextInt();
        Set<Character> setOfChars = new HashSet<>();
        String[] words = new String[numberOfWords];
        for (int i = 0; i < numberOfWords; i++) {
            words[i] = scanner.next();
            for (int j = 0; j < words[i].length(); j++) {
                setOfChars.add(words[i].charAt(j));
            }
        }
        for (Character c : setOfChars) {
            graph.addVertex(c);
        }

        for (int i = 0; i < numberOfWords - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            if (word1.length() == word2.length()) {
                for (int j = 0; j < word1.length(); j++) {
                    if (word1.charAt(j) != word2.charAt(j)) {
                        int finalJ = j;
                        Graph<Character, Integer>.Vertex v1 = graph.vertices.stream().filter(vertex -> vertex.value.equals(word1.charAt(finalJ))).findFirst().orElse(null);
                        Graph<Character, Integer>.Vertex v2 = graph.vertices.stream().filter(vertex -> vertex.value.equals(word2.charAt(finalJ))).findFirst().orElse(null);
                        graph.addEdge(v2, v1);
                        break;
                    }
                }
            }
            else{
                for(int k = 0; k < Math.min(word1.length(), word2.length()); k++){
                    if (word1.charAt(k) != word2.charAt(k)) {
                        int finalK = k;
                        Graph<Character, Integer>.Vertex v1 = graph.vertices.stream().filter(vertex -> vertex.value.equals(word1.charAt(finalK))).findFirst().orElse(null);
                        Graph<Character, Integer>.Vertex v2 = graph.vertices.stream().filter(vertex -> vertex.value.equals(word2.charAt(finalK))).findFirst().orElse(null);
                        graph.addEdge(v2, v1);
                    }
                }
            }

        }
        graph.topoSort(graph);
        boolean flag = true;
        for (int i = 0; i < numberOfWords - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            if (word1.length() > word2.length()) {
                if (word1.contains(word2)) {
                    System.out.println("Doh");
                    flag = false;
                    break;

                }
            }
        }
        if (flag) {
            if (!graph.hasCycle){
                for (Character c : graph.answer) {
                    System.out.print(c);
                }
            }
            else {
                System.out.println("Doh");
            }
        }

    }

}

class Graph<V, E>{
    class Vertex{
        V value;
        Color color;
        int d;
        int finish;
        Vertex papa;
        ArrayList<Edge> adjacent;
        public Vertex(V value){
            this.value = value;
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
    Vertex addVertex(V value){
        Vertex v = new Vertex(value);
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
     Stack<V> answer = new Stack<>();
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
        answer.push(v.value);
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
