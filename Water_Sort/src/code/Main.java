package code;

public class Main {
    public static void main(String[] args) {
        String simpleTest = "3;2;r,b;e,e;b,r"; // Initial state: Bottle 0: r,b | Bottle 1: e,e | Bottle 2: b,r

        // Test UCS
        System.out.println("Testing Uniform Cost Search (UCS):");
        String resultUCS = WaterSortSearch.solve(simpleTest, "UC", true);
        System.out.println("UCS Result: " + resultUCS);

        // Test BFS
        System.out.println("\nTesting Breadth-First Search (BFS):");
        String resultBFS = WaterSortSearch.solve(simpleTest, "BF", true);
        System.out.println("BFS Result: " + resultBFS);

        // Test DFS
        System.out.println("\nTesting Depth-First Search (DFS):");
        String resultDFS = WaterSortSearch.solve(simpleTest, "DF", true);
        System.out.println("DFS Result: " + resultDFS);
    }
}
