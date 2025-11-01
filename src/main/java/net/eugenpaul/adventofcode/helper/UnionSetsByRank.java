package net.eugenpaul.adventofcode.helper;

public class UnionSetsByRank {
    private int[] root;
    private int[] rank;
    private int components; // Number of distinct components in the graph.

    // Constructor
    public UnionSetsByRank(int n) {
        rank = new int[n];
        root = new int[n];
        components = n;
        for (int i = 0; i < n; i++) {
            // Initially, all elements are in their own set.
            root[i] = i;
        }
    }

    public int getComponents() {
        return components;
    }

    // Returns representative of x's set
    public int find(int i) {
        while (root[i] != i) {
            i = root[i];
        }
        return i;
    }

    // Unites the set that includes x and the set that includes x
    public void union(int x, int y) {
        // Find representatives of two sets
        int xRoot = find(x);
        int yRoot = find(y);

        // Elements are in the same set, no need to unite anything.
        if (xRoot == yRoot) {
            return;
        }

        if (rank[xRoot] > rank[yRoot]) {
            root[yRoot] = xRoot;
        } else if (rank[xRoot] < rank[yRoot]) {
            root[xRoot] = yRoot;
        } else {
            root[yRoot] = xRoot;
            rank[xRoot] += 1;
        }
        components -= 1;
    }
}