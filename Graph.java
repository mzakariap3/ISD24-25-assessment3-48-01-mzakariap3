import java.util.*;

public class Graph {
    private Map<String, Integer> langkahtoindex;
    private List<String> indextolangkah;
    private List<List<Integer>> adj;
    private Set<String> langkahdita;

    public Graph() {
        langkahtoindex = new HashMap<>();
        indextolangkah = new ArrayList<>();
        adj = new ArrayList<>();
        langkahdita = new HashSet<>();
    }

    public void initializeGraph() {
        String[] ditArrayLangkah = { "preheat oven", "bake bread", "serve bread", "eat", "set plate", "add some pickles", "preheat pan", 
                                    "add krabby patty", "serve patty", "add tartar sauce", "pour sauce over patty" };
        for (String langkah : ditArrayLangkah) {
            this.langkahdita.add(langkah);
        }
        String[] langkahArraydita = { "preheat oven", "bake bread", "serve bread", "eat", "set plate", "add some pickles", "preheat pan", 
                                    "add krabby patty", "serve patty", "add tartar sauce", "pour sauce over patty" };
        for (int i = 0; i < langkahArraydita.length; i++) {
            langkahtoindex.put(langkahArraydita[i], i);
            indextolangkah.add(langkahArraydita[i]);
        }
        adj = new ArrayList<>(langkahArraydita.length);
        for (int i = 0; i < langkahArraydita.length; i++) {
            adj.add(new ArrayList<>());
        }
        addEdge("preheat oven", "bake bread");
        addEdge("preheat oven", "preheat pan");
        addEdge("bake bread", "serve bread");
        addEdge("serve bread", "eat");
        addEdge("preheat pan", "set plate");
        addEdge("set plate", "serve bread");
        addEdge("set plate", "add some pickles");
        addEdge("add some pickles", "eat");
        addEdge("set plate", "serve patty");
        addEdge("serve patty", "pour sauce over patty");
        addEdge("pour sauce over patty", "eat");
        addEdge("preheat pan", "add krabby patty");
        addEdge("add krabby patty", "serve patty");
        addEdge("add krabby patty", "add tartar sauce");
        addEdge("add tartar sauce", "pour sauce over patty");
    }

    private void addEdge(String langkah1, String langkah2) {
        if (!langkahtoindex.containsKey(langkah1) || !langkahtoindex.containsKey(langkah2)) {
            System.err.println("Error: Salah satu atau kedua kota ('" + langkah1 + "', '" + langkah2
                    + "') tidak ada dalam daftar graf.");
            return;
        }
        int u = langkahtoindex.get(langkah1);
        int v = langkahtoindex.get(langkah2);
        adj.get(u).add(v);
        adj.get(v).add(u);
    }

    public List<String> bfsTraversal(String startlangkahName) {
        if (!langkahtoindex.containsKey(startlangkahName)) {
            System.err.println("Error: Kota awal '" + startlangkahName + "' tidak ditemukan dalam graf.");
            return new ArrayList<>();
        }
        int startIndex = langkahtoindex.get(startlangkahName);
        boolean[] visited = new boolean[adj.size()];
        Queue<Integer> queue = new LinkedList<>();
        List<String> visitedOrder = new ArrayList<>();
        queue.add(startIndex);
        visited[startIndex] = true;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            String currentlangkah = indextolangkah.get(u);
            if (langkahdita.contains(currentlangkah)) {
                visitedOrder.add(currentlangkah);
            }
            for (int v : adj.get(u)) {
                if (!visited[v]) {
                    visited[v] = true;
                    queue.add(v);
                }
            }
        }
        return visitedOrder;
    }

    public List<String> distanceBasedTraversal(String startlangkahName) {
        if (!langkahtoindex.containsKey(startlangkahName)) {
            System.err.println("Error: langkah awal '" + startlangkahName + "' tidak ditemukan dalam graf.");
            return new ArrayList<>();
        }
        int startIndex = langkahtoindex.get(startlangkahName);
        int[] distance = new int[adj.size()];
        Arrays.fill(distance, -1);
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startIndex);
        distance[startIndex] = 0;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v : adj.get(u)) {
                if (distance[v] == -1) {
                    distance[v] = distance[u] + 1;
                    queue.add(v);
                }
            }
        }
        List<Map.Entry<String, Integer>> citiesWithDistances = new ArrayList<>();
        for (String langkahName : langkahdita) {
            int langkahIdx = langkahtoindex.get(langkahName);
            if (distance[langkahIdx] != -1) {
                citiesWithDistances.add(new AbstractMap.SimpleEntry<>(langkahName, distance[langkahIdx]));
            }
        }
        citiesWithDistances.sort((entry1, entry2) -> {
            int distCompare = entry1.getValue().compareTo(entry2.getValue());
            if (distCompare == 0) {
                return entry1.getKey().compareTo(entry2.getKey());
            }
            return distCompare;
        });
        List<String> orderlangkah = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : citiesWithDistances) {
            orderlangkah.add(entry.getKey());
        }
        return orderlangkah;
    }
}