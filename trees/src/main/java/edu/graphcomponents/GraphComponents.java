package edu.graphcomponents;

import edu.dsu.DSU;

public class GraphComponents {
    /**
     * Cuenta componentes conexas de un grafo no dirigido.
     * 
     * @param n     número de vértices (0..n-1)
     * @param edges lista de aristas [u, v]
     * @return número de componentes conexas
     */
    public static int countComponents(int n, int[][] edges) {
        DSU dsu = new DSU(n);
        for (int[] e : edges)
            dsu.union(e[0], e[1]);
        return dsu.components();
    }

    /**
     * Verifica si agregar la arista (u,v) crea un ciclo.
     * Útil para el algoritmo de Kruskal.
     */
    public static boolean createsCycle(DSU dsu, int u, int v) {
        if (dsu.connected(u, v))
            return true;
        dsu.union(u, v);
        return false;
    }
}