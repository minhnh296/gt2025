import java.util.*;

class TP4 {

    public static int[][] createWeightedAdjacencyMatrix() {
        int n = 9;
        int[][] adjMatrix = new int[n][n];

        int[][] edges = {
            {1, 2, 4}, {1, 5, 1}, {1, 7, 2},
            {2, 3, 7}, {2, 6, 5},
            {3, 4, 1}, {3, 6, 8},
            {4, 6, 6}, {4, 7, 4}, {4, 8, 3},
            {5, 6, 9}, {5, 7, 10},
            {6, 9, 2},
            {7, 9, 8},
            {8, 9, 1},
            {9, 8, 7}
        };

        for (int[] edge : edges) {
            int u = edge[0] - 1;
            int v = edge[1] - 1;
            int w = edge[2];
            adjMatrix[u][v] = w;
            adjMatrix[v][u] = w;
        }

        return adjMatrix;
    }

    public static void printAdjacencyMatrix(int[][] adjMatrix) {
        for (int[] row : adjMatrix) {
            for (int val : row) {
                System.out.printf("%2d ", val);
            }
            System.out.println();
        }
    }

    public static void primMST(int[][] adjMatrix, int root) {
        int n = adjMatrix.length;
        boolean[] visited = new boolean[n];
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        minHeap.add(new int[]{0, root, -1});
        List<int[]> mstEdges = new ArrayList<>();
        int totalWeight = 0;

        while (!minHeap.isEmpty()) {
            int[] curr = minHeap.poll();
            int weight = curr[0], node = curr[1], parent = curr[2];

            if (visited[node]) continue;
            visited[node] = true;
            totalWeight += weight;

            if (parent != -1) {
                mstEdges.add(new int[]{parent + 1, node + 1, weight});
            }

            for (int neighbor = 0; neighbor < n; neighbor++) {
                if (!visited[neighbor] && adjMatrix[node][neighbor] > 0) {
                    minHeap.add(new int[]{adjMatrix[node][neighbor], neighbor, node});
                }
            }
        }

        System.out.println("Edges in MST (Prim's Algorithm): " + mstEdges);
        System.out.println("Total Weight of MST: " + totalWeight);
    }

    public static void kruskalMST(int[][] adjMatrix) {
        int n = adjMatrix.length;
        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (adjMatrix[i][j] > 0) {
                    edges.add(new int[]{adjMatrix[i][j], i, j});
                }
            }
        }

        edges.sort(Comparator.comparingInt(a -> a[0]));
        int[] parent = new int[n];
        int[] rank = new int[n];
        List<int[]> mstEdges = new ArrayList<>();
        int totalWeight = 0;

        for (int i = 0; i < n; i++) parent[i] = i;

        for (int[] edge : edges) {
            int weight = edge[0], u = edge[1], v = edge[2];

            if (find(parent, u) != find(parent, v)) {
                union(parent, rank, u, v);
                mstEdges.add(new int[]{u + 1, v + 1, weight});
                totalWeight += weight;
            }
        }

        System.out.println("Edges in MST (Kruskal's Algorithm): " + mstEdges);
        System.out.println("Total Weight of MST: " + totalWeight);
    }

    private static int find(int[] parent, int x) {
        if (parent[x] != x) {
            parent[x] = find(parent, parent[x]);
        }
        return parent[x];
    }

    private static void union(int[] parent, int[] rank, int x, int y) {
        int rootX = find(parent, x);
        int rootY = find(parent, y);
        if (rootX != rootY) {
            if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }

    public static void main(String[] args) {
        int[][] adjMatrix = createWeightedAdjacencyMatrix();
        printAdjacencyMatrix(adjMatrix);

        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter the root node (1-9): ");
        int rootNode = scanner.nextInt() - 1;

        System.out.println("\nPrim's Algorithm:");
        primMST(adjMatrix, rootNode);

        System.out.println("\nKruskal's Algorithm:");
        kruskalMST(adjMatrix);

        scanner.close();
    }
}
