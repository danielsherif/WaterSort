package code;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BFS extends WaterSortSearch {
    public BFS(State initialState) {
        super(initialState); // Call the constructor of the superclass
    }

    public SearchResult bfs() {
        Queue<Node> NodeStructure = new LinkedList<>(); // Initialize the nodestructure  as a Queue
        return search( initialState,NodeStructure); // Calls the search method with a FIFO queue
    }
}

