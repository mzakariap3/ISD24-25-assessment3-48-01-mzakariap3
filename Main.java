
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Graph graphlangkahdita = new Graph();
        graphlangkahdita.initializeGraph();

        System.out.println("A. Langkah yang Berdekatan");
        System.out.println("Langkah Awal: preheat oven");
        List<String> bfsOrder = graphlangkahdita.bfsTraversal("preheat oven");
        System.out.println("Urutan langkkah Dita (BFS):");
        System.out.println(String.join(" -> ", bfsOrder) + "\n");

        System.out.println("B. Langkah yang semakin jauh");
        List<String> distanceOrder = graphlangkahdita.distanceBasedTraversal("preheat oven");
        System.out.println("Urutan langkah Dita (DFS):");
        System.out.println(String.join(" -> ", distanceOrder));
    }
}