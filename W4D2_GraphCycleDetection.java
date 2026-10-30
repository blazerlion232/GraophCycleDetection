//package week4;

import java.util.*;

public class W4D2_GraphCycleDetection {
    private Map<String, List<String>> adjacencyList = new HashMap<>();

    public void addVertex(String vertex) {
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
    }

    public void addEdge(String vertex1, String vertex2) {
        adjacencyList.get(vertex1).add(vertex2);
        adjacencyList.get(vertex2).add(vertex1);
    }

    public boolean hasCycle() {
        Set<String> visited = new HashSet<>();
        
        // Check all components of the graph (in case it's disconnected)
        for (String vertex : adjacencyList.keySet()) {
            if (!visited.contains(vertex)) {
                if (hasCycleRecursive(vertex, visited, null)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasCycleRecursive(String current, Set<String> visited, String parent) {

        visited.add(current);
        List<String> neighbours = adjacencyList.get(current);

        if(neighbours != null){
            for(String neighbour : neighbours){
                if(!visited.contains(neighbour)){
                    if(hasCycleRecursive(neighbour, visited, current)){
                        return true;
                    }
                }
                else if(!neighbour.equals(parent)){
                    return true;
                }
            }
        }
        

        return false; // Placeholder - replace with your implementation
    }

    // Helper method to display graph structure
    public void printGraph() {
        System.out.println("Graph structure:");
        for (String vertex : adjacencyList.keySet()) {
            List<String> neighbors = adjacencyList.get(vertex);
            if (neighbors.isEmpty()) {
                System.out.println(vertex + " has no neighbors");
            } else {
                System.out.println(vertex + "'s neighbors are: " + String.join(", ", neighbors));
            }
        }
        System.out.println();
    }

    // Main method to test cycle detection
    public static void main(String[] args) {
        System.out.println("=== Cycle Detection Testing ===\n");

        // Test Case 1: Graph with a cycle (triangle)
        System.out.println("Test Case 1: Cyclic Graph (Triangle)");
        W4D2_GraphCycleDetection cyclicGraph = new W4D2_GraphCycleDetection();
        String[] vertices1 = {"A", "B", "C"};
        for (String v : vertices1) cyclicGraph.addVertex(v);
        cyclicGraph.addEdge("A", "B");
        cyclicGraph.addEdge("B", "C");
        cyclicGraph.addEdge("C", "A"); // This edge creates a cycle: A-B-C-A
        
        cyclicGraph.printGraph();
        System.out.println("Expected: true (cycle exists)");
        System.out.println("Result: " + cyclicGraph.hasCycle());
        System.out.println();

        // Test Case 2: Acyclic graph (line)
        System.out.println("Test Case 2: Acyclic Graph (Line)");
        W4D2_GraphCycleDetection acyclicGraph = new W4D2_GraphCycleDetection();
        String[] vertices2 = {"A", "B", "C"};
        for (String v : vertices2) acyclicGraph.addVertex(v);
        acyclicGraph.addEdge("A", "B");
        acyclicGraph.addEdge("B", "C");
        // No edge from C back to A, so no cycle
        
        acyclicGraph.printGraph();
        System.out.println("Expected: false (no cycle)");
        System.out.println("Result: " + acyclicGraph.hasCycle());
        System.out.println();

        // Test Case 3: More complex graph with cycle
        System.out.println("Test Case 3: Complex Graph with Cycle");
        W4D2_GraphCycleDetection complexCyclic = new W4D2_GraphCycleDetection();
        String[] vertices3 = {"A", "B", "C", "D", "E"};
        for (String v : vertices3) complexCyclic.addVertex(v);
        complexCyclic.addEdge("A", "B");
        complexCyclic.addEdge("B", "C");
        complexCyclic.addEdge("C", "D");
        complexCyclic.addEdge("D", "E");
        complexCyclic.addEdge("E", "C"); // Creates cycle: C-D-E-C
        
        complexCyclic.printGraph();
        System.out.println("Expected: true (cycle exists: C-D-E-C)");
        System.out.println("Result: " + complexCyclic.hasCycle());
        System.out.println();

        // Test Case 4: Disconnected graph with no cycles
        System.out.println("Test Case 4: Disconnected Graph (No Cycles)");
        W4D2_GraphCycleDetection disconnected = new W4D2_GraphCycleDetection();
        String[] vertices4 = {"A", "B", "C", "D"};
        for (String v : vertices4) disconnected.addVertex(v);
        disconnected.addEdge("A", "B"); // Component 1: A-B
        disconnected.addEdge("C", "D"); // Component 2: C-D
        // Two separate components, no cycles
        
        disconnected.printGraph();
        System.out.println("Expected: false (no cycles in either component)");
        System.out.println("Result: " + disconnected.hasCycle());
        System.out.println();

        // Test Case 5: Single vertex (no cycle possible)
        System.out.println("Test Case 5: Single Vertex");
        W4D2_GraphCycleDetection singleVertex = new W4D2_GraphCycleDetection();
        singleVertex.addVertex("A");
        
        singleVertex.printGraph();
        System.out.println("Expected: false (single vertex cannot have cycle)");
        System.out.println("Result: " + singleVertex.hasCycle());
    }
}