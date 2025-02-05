import java.util.*;

class TP5 {

    public static int[][] createAdjacencyMatrix() {
        int n = 10;
        int[][] adjMatrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(adjMatrix[i], Integer.MAX_VALUE);
        }

        int[][] edges = {
            {0, 1, 4}, {0, 2, 1},
            {1, 5, 3},
            {2, 3, 8}, {2, 5, 7},
            {3, 7, 5},
            {4, 8, 2}, {4, 7, 2},
            {5, 4, 1}, {5, 7, 1},
            {6, 8, 4}, {6, 9, 4},
            {7, 6, 7}, {7, 9, 7}, {7, 8, 6},
            {8, 9, 1}
        };

        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], w = edge[2];
            adjMatrix[u][v] = w;
            adjMatrix[v][u] = w;
        }

        return adjMatrix;
    }

    public static void showAdjacencyMatrix(int[][] adjMatrix) {
        System.out.println("Adjacency Matrix:");
        for (int[] row : adjMatrix) {
            for (int val : row) {
                if (val == Integer.MAX_VALUE) {
                    System.out.printf("%3s ", "∞");
                } else {
                    System.out.printf("%3d ", val);
                }
            }
            System.out.println();
        }
    }

    public static class Node implements Comparable<Node> {
        int vertex, distance;

        Node(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.distance, other.distance);
        }
    }

    public static List<Integer> dijkstra(int[][] adjMatrix, int start, int end) {
        int n = adjMatrix.length;
        int[] distances = new int[n];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;

        int[] prevNodes = new int[n];
        Arrays.fill(prevNodes, -1);

        PriorityQueue<Node> minHeap = new PriorityQueue<>();
        minHeap.add(new Node(start, 0));

        while (!minHeap.isEmpty()) {
            Node current = minHeap.poll();
            int currentDistance = current.distance, currentNode = current.vertex;

            if (currentDistance > distances[currentNode]) continue;

            for (int neighbor = 0; neighbor < n; neighbor++) {
                if (adjMatrix[currentNode][neighbor] != Integer.MAX_VALUE) {
                    int newDistance = currentDistance + adjMatrix[currentNode][neighbor];
                    if (newDistance < distances[neighbor]) {
                        distances[neighbor] = newDistance;
                        prevNodes[neighbor] = currentNode;
                        minHeap.add(new Node(neighbor, newDistance));
                    }
                }
            }
        }

        List<Integer> path = new ArrayList<>();
        for (int node = end; node != -1; node = prevNodes[node]) {
            path.add(node);
        }
        Collections.reverse(path);
        return path.isEmpty() || distances[end] == Integer.MAX_VALUE ? null : path;
    }

    public static void main(String[] args) {
        int[][] adjMatrix = createAdjacencyMatrix();
        showAdjacencyMatrix(adjMatrix);

        Scanner scanner = new Scanner(System.in);
        String[] nodes = {"A", "B", "C", "D", "E", "F", "G", "H", "L", "M"};

        System.out.print("\nEnter source node (A-M): ");
        String startNode = scanner.next().toUpperCase();
        System.out.print("Enter target node (A-M): ");
        String endNode = scanner.next().toUpperCase();

        int startIndex = Arrays.asList(nodes).indexOf(startNode);
        int endIndex = Arrays.asList(nodes).indexOf(endNode);

        if (startIndex == -1 || endIndex == -1) {
            System.out.println("Invalid input! Please enter valid node names.");
            return;
        }

        List<Integer> shortestPath = dijkstra(adjMatrix, startIndex, endIndex);
        if (shortestPath == null) {
            System.out.println("\nNo path found between " + startNode + " and " + endNode);
        } else {
            System.out.println("\nShortest Path: " + shortestPath.stream()
                    .map(i -> nodes[i])
                    .reduce((a, b) -> a + " -> " + b).orElse(""));
            System.out.println("Weighted Sum of Shortest Path: " + adjMatrix[startIndex][endIndex]);
        }

        scanner.close();
    }
}
