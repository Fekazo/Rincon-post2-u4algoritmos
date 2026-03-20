package edu.trees;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class AVLTreeTest {

    // ─────────────────────────────────────────────────────────────────────────
    // TEST 1: Insertar 10 000 enteros aleatorios sin StackOverflowError
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("Insertar 10 000 enteros aleatorios sin StackOverflowError")
    void testInsertNoStackOverflow() {
        AVLTree<Integer> tree = new AVLTree<>();
        Random rng = new Random(42);

        assertDoesNotThrow(() -> {
            for (int i = 0; i < 10_000; i++)
                tree.insert(rng.nextInt());
        }, "Se lanzó StackOverflowError al insertar 10 000 elementos");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TEST 2: Altura resultante ≤ 1.44 * log2(10001) ≈ 20 niveles
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("Altura del árbol ≤ 1.44 * log2(10001) ≈ 20 niveles")
    void testHeightBound() {
        AVLTree<Integer> tree = new AVLTree<>();
        Random rng = new Random(42);

        for (int i = 0; i < 10_000; i++)
            tree.insert(rng.nextInt());

        int actualHeight = tree.height();
        int maxHeight = (int) Math.ceil(1.44 * (Math.log(10_001) / Math.log(2)));

        System.out.printf("Altura real: %d  |  Límite teórico (1.44·log₂(10001)): %d%n",
                actualHeight, maxHeight);

        assertTrue(actualHeight <= maxHeight,
            String.format("Altura %d supera el límite teórico %d", actualHeight, maxHeight));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TEST 3: contains devuelve true para todos los insertados
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("contains devuelve true para todos los elementos insertados")
    void testContainsTrueForInserted() {
        AVLTree<Integer> tree = new AVLTree<>();
        int[] elements = new int[10_000];
        Random rng = new Random(42);

        for (int i = 0; i < 10_000; i++) {
            elements[i] = rng.nextInt();
            tree.insert(elements[i]);
        }

        for (int e : elements) {
            assertTrue(tree.contains(e),
                "contains devolvió false para elemento insertado: " + e);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TEST 4: contains devuelve false para elementos NO insertados
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("contains devuelve false para elementos no insertados")
    void testContainsFalseForNotInserted() {
        AVLTree<Integer> tree = new AVLTree<>();

        // Insertar solo números pares
        for (int i = 0; i < 10_000; i++)
            tree.insert(i * 2);

        // Verificar que los impares NO están
        for (int i = 0; i < 10_000; i++) {
            assertFalse(tree.contains(i * 2 + 1),
                "contains devolvió true para elemento no insertado: " + (i * 2 + 1));
        }
    }
}