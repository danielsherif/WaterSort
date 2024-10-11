package code;

import java.util.PriorityQueue;

public class UCS extends WaterSortSearch {
    public UCS(State initialState) {
        super(initialState);
    }

    public SearchResult ucs() {
        // Initialize the NodeStructure as a PriorityQueue
        PriorityQueue<Node> NodeStructure = new PriorityQueue<>((n1, n2) -> Integer.compare(n1.cost, n2.cost));

        // Call the search method with the PriorityQueue
        return search(initialState, NodeStructure);
    }
}
