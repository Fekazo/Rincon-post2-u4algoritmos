package edu.graphcomponents;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GraphComponentsTest {

    // ─────────────────────────────────────────────────────────────────────────
    // TEST 1 (a): Grafo sin aristas — n componentes
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("(a) Grafo sin aristas: n vértices = n componentes")
    void testNoEdges() {
        int n = 10;
        int[][] edges = new int[0][0]; // sin aristas

        int result = GraphComponents.countComponents(n, edges);

        System.out.printf("Grafo sin aristas: %d vértices → %d componentes%n", n, result);

        assertEquals(n, result,
            "Un grafo sin aristas debe tener exactamente n componentes");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TEST 2 (b): Grafo completo — 1 componente
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("(b) Grafo completo: todos conectados = 1 componente")
    void testCompleteGraph() {
        int n = 10;
        // Conectar todos los vértices en cadena: 0-1, 1-2, ..., 8-9
        int[][] edges = new int[n - 1][2];
        for (int i = 0; i < n - 1; i++) {
            edges[i][0] = i;
            edges[i][1] = i + 1;
        }

        int result = GraphComponents.countComponents(n, edges);

        System.out.printf("Grafo completo (cadena): %d vértices → %d componentes%n", n, result);

        assertEquals(1, result,
            "Un grafo completamente conectado debe tener exactamente 1 componente");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TEST 3 (c): Grafo de 10 vértices con aristas conocidas — verificable manualmente
    //
    // Estructura:
    //   Componente 1: 0-1, 1-2, 2-3        → {0,1,2,3}
    //   Componente 2: 4-5, 5-6             → {4,5,6}
    //   Componente 3: 7-8                  → {7,8}
    //   Componente 4: 9 (aislado)          → {9}
    //   Total esperado: 4 componentes
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("(c) Grafo 10 vértices con aristas conocidas = 4 componentes")
    void testKnownGraph() {
        int n = 10;
        int[][] edges = {
            {0, 1}, {1, 2}, {2, 3},  // componente {0,1,2,3}
            {4, 5}, {5, 6},          // componente {4,5,6}
            {7, 8}                   // componente {7,8}
                                     // 9 queda aislado
        };

        int result = GraphComponents.countComponents(n, edges);

        System.out.printf("Grafo conocido: %d vértices → %d componentes%n", n, result);
        System.out.println("  Componente 1: {0,1,2,3}");
        System.out.println("  Componente 2: {4,5,6}");
        System.out.println("  Componente 3: {7,8}");
        System.out.println("  Componente 4: {9} (aislado)");

        assertEquals(4, result,
            "El grafo conocido debe tener exactamente 4 componentes");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TEST 4: createsCycle detecta ciclos correctamente
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("createsCycle detecta ciclos correctamente")
    void testCreatesCycle() {
        edu.dsu.DSU dsu = new edu.dsu.DSU(4);

        // Conectar 0-1 y 1-2 (no hay ciclo aún)
        assertFalse(GraphComponents.createsCycle(dsu, 0, 1), "0-1 no debe crear ciclo");
        assertFalse(GraphComponents.createsCycle(dsu, 1, 2), "1-2 no debe crear ciclo");

        // Conectar 0-2 (crea ciclo: 0→1→2→0)
        assertTrue(GraphComponents.createsCycle(dsu, 0, 2), "0-2 debe crear ciclo");
    }
}