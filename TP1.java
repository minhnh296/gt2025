import java.util.*;

class TP1 {
    private int V;
    private LinkedList<Integer> adj[];

    TP1(int v) {
        V = v + 1;
        adj = new LinkedList[V];
        for (int i = 0; i < V; ++i)
            adj[i] = new LinkedList<>();
    }

    void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
    }
    Boolean isReachable(int s, int d) {
        LinkedList<Integer> queue = new LinkedList<>();
        boolean visited[] = new boolean[V];

        visited[s] = true;
        queue.add(s);

        while (queue.size() != 0) {
            s = queue.poll();

            for (int n : adj[s]) {
                if (n == d)
                    return true;
                if (!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }

        return false;
    }

    public static void main(String args[]) {
        TP1 _g = new TP1(7);
        _g.addEdge(1, 2);
        _g.addEdge(2, 5);
        _g.addEdge(3, 6);
        _g.addEdge(4, 6);
        _g.addEdge(4, 7);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter start node: ");
        int u = scanner.nextInt();
        System.out.print("Enter destination node: ");
        int v = scanner.nextInt();

        if (_g.isReachable(u, v))
            System.out.println("True");
        else
            System.out.println("false");

        scanner.close();
    }
}
