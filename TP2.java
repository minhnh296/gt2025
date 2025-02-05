import java.util.*;

class TP2 {
    
    public static int[][] graphToMatrix(int[][] edges, int nNodes) {
        int[][] matrix = new int[nNodes][nNodes];
        for (int[] edge : edges) {
            matrix[edge[0] - 1][edge[1] - 1] = 1;
        }
        return matrix;
    }
    
    public static void printMatrix(int[][] matrix) {
        System.out.println("Adjacency matrix:");
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
    
    public static Map<Integer, List<Integer>> createAdjList(int[][] matrix) {
        Map<Integer, List<Integer>> adjList = new HashMap<>();
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            adjList.put(i + 1, new ArrayList<>());
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 1) {
                    adjList.get(i + 1).add(j + 1);
                }
            }
        }
        return adjList;
    }
    
    public static int weaklyConnectedComponents(int[][] matrix) {
        Map<Integer, List<Integer>> adjList = createAdjList(matrix);
        Map<Integer, List<Integer>> undirAdjList = new HashMap<>();
        
        for (int u : adjList.keySet()) {
            undirAdjList.putIfAbsent(u, new ArrayList<>());
            for (int v : adjList.get(u)) {
                undirAdjList.putIfAbsent(v, new ArrayList<>());
                undirAdjList.get(u).add(v);
                undirAdjList.get(v).add(u);
            }
        }
        
        Set<Integer> visited = new HashSet<>();
        int weaklyCount = 0;
        
        for (int node : adjList.keySet()) {
            if (!visited.contains(node)) {
                weaklyCount++;
                bfs(node, undirAdjList, visited);
            }
        }
        
        return weaklyCount;
    }
    
    private static void bfs(int node, Map<Integer, List<Integer>> adjList, Set<Integer> visited) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(node);
        visited.add(node);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int neighbor : adjList.getOrDefault(current, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
    }
    
    public static int stronglyConnectedComponents(int[][] matrix) {
        Map<Integer, List<Integer>> adjList = createAdjList(matrix);
        Stack<Integer> finishStack = new Stack<>();
        Set<Integer> visited = new HashSet<>();
        
        for (int node : adjList.keySet()) {
            if (!visited.contains(node)) {
                dfs(node, adjList, visited, finishStack);
            }
        }
        
        Map<Integer, List<Integer>> reversedAdjList = new HashMap<>();
        for (int u : adjList.keySet()) {
            for (int v : adjList.get(u)) {
                reversedAdjList.putIfAbsent(v, new ArrayList<>());
                reversedAdjList.get(v).add(u);
            }
        }
        
        visited.clear();
        int stronglyConnectedCount = 0;
        
        while (!finishStack.isEmpty()) {
            int node = finishStack.pop();
            if (!visited.contains(node)) {
                stronglyConnectedCount++;
                dfsReversed(node, reversedAdjList, visited);
            }
        }
        
        return stronglyConnectedCount;
    }
    
    private static void dfs(int node, Map<Integer, List<Integer>> adjList, Set<Integer> visited, Stack<Integer> stack) {
        visited.add(node);
        for (int neighbor : adjList.getOrDefault(node, new ArrayList<>())) {
            if (!visited.contains(neighbor)) {
                dfs(neighbor, adjList, visited, stack);
            }
        }
        stack.push(node);
    }
    
    private static void dfsReversed(int node, Map<Integer, List<Integer>> adjList, Set<Integer> visited) {
        Stack<Integer> stack = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            int current = stack.pop();
            if (!visited.contains(current)) {
                visited.add(current);
                for (int neighbor : adjList.getOrDefault(current, new ArrayList<>())) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }
    }
    
    public static void main(String[] args) {
        int[][] edges = {
            {1, 2}, {1, 4}, {2, 3}, {2, 6},
            {6, 3}, {6, 4}, {5, 4}, {7, 6},
            {7, 3}, {7, 5}, {7, 8}, {8, 9}, {5, 9}
        };
        int nNodes = 9;

        int[][] G = graphToMatrix(edges, nNodes);
        
        printMatrix(G);
        
        int weaklyConnected = weaklyConnectedComponents(G);
        int stronglyConnected = stronglyConnectedComponents(G);
        
        System.out.println("\nWeak Connected Component's number: " + weaklyConnected);
        System.out.println("Strong Connected Component's number: " + stronglyConnected);
    }
}
