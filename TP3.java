import java.util.*;

class TP3 {
    
    public static void printAdjacencyMatrix(Map<Integer, List<Integer>> adjacencyList) {
        int maxNode = Collections.max(adjacencyList.keySet());
        int[][] adjMatrix = new int[maxNode + 1][maxNode + 1];

        for (int key : adjacencyList.keySet()) {
            for (int child : adjacencyList.get(key)) {
                adjMatrix[key][child] = 1;
            }
        }

        System.out.println("Adjacency Matrix:");
        for (int i = 1; i <= maxNode; i++) {
            for (int j = 1; j <= maxNode; j++) {
                System.out.print(adjMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void inorderTraversal(int node, Map<Integer, List<Integer>> adjacencyList, boolean[] visited) {
        if (node == 0 || visited[node]) {
            return;
        }

        visited[node] = true;
        List<Integer> children = adjacencyList.getOrDefault(node, new ArrayList<>());

        if (!children.isEmpty()) {
            inorderTraversal(children.get(0), adjacencyList, visited);
        }

        System.out.print(node + " ");

        for (int i = 1; i < children.size(); i++) {
            inorderTraversal(children.get(i), adjacencyList, visited);
        }
    }

    public static void main(String[] args) {
        Map<Integer, List<Integer>> adjList = new HashMap<>();
        adjList.put(1, Arrays.asList(2, 3));
        adjList.put(2, Arrays.asList(5, 6));
        adjList.put(3, Arrays.asList(4));
        adjList.put(4, Arrays.asList(8));
        adjList.put(5, Arrays.asList(7));
        adjList.put(6, new ArrayList<>());
        adjList.put(7, new ArrayList<>());
        adjList.put(8, new ArrayList<>());

        printAdjacencyMatrix(adjList);

        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("\nEnter node: ");
            int startNode = scanner.nextInt();
            boolean[] visited = new boolean[adjList.size() + 1];

            System.out.println("InOrder Output:");
            inorderTraversal(startNode, adjList, visited);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter an integer.");
        } finally {
            scanner.close();
        }
    }
}
