package code;

import java.util.Stack;

public class DFS extends WaterSortSearch {
    public DFS(State initialState) {
        super(initialState); // Call the constructor of the superclass
    }

    public SearchResult dfs() {
        Stack<Node> Nodestructure = new Stack<>(); // Initialize the nodestructure  as a Stack
        return search( initialState, Nodestructure); // Calls the search method with a LIFO stack
    }
}