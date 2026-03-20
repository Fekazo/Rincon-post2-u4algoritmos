package edu.dsu;

public class DSU {
    private final int[] parent;
    private final int[] size;
    private int components;

    public DSU(int n) {
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
        components = n;
    }

    /** Find con path splitting (cada nodo apunta al abuelo). */
    public int find(int x) {
        while (parent[x] != x) {
            int next = parent[x];
            parent[x] = parent[next]; // path splitting
            x = next;
        }
        return x;
    }

    /** Union by size. Retorna true si los conjuntos eran distintos. */
    public boolean union(int x, int y) {
        int rx = find(x), ry = find(y);
        if (rx == ry)
            return false;
        if (size[rx] < size[ry]) {
            int t = rx;
            rx = ry;
            ry = t;
        }
        parent[ry] = rx;
        size[rx] += size[ry];
        components--;
        return true;
    }

    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }

    public int components() {
        return components;
    }
}