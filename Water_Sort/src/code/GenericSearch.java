package code;

import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.HashSet;
import java.util.List;

public abstract class GenericSearch {

    // Abstract methods that child classes (like WaterSortSearch) need to implement
    protected abstract State getInitialState();
    protected abstract boolean isGoalState(State state);
    protected abstract List<String> getActions(State state);
    protected abstract State getResult(State state, String action);

    // Method to check if the state is the final goal state
    protected boolean isFinalGoalState(State state) {
        for (String bottle : state.getBottles()) {
            if (!isUniformOrEmpty(bottle)) {
                return false;
            }
        }
        return true;
    }

    // Helper method to determine if a bottle is uniform (has a single color) or empty
    protected boolean isUniformOrEmpty(String bottle) {
        return bottle.isEmpty() || (bottle.chars().distinct().count() == 1);
    }

    // Search method that takes a problem and a strategy (NodeStructure) as inputs
    // problem >> iam stating the initialstate and the nodestructure >> will be treated like the searchstrategy that will be used
    public SearchResult search(State initialState, Collection<Node> NodeStructure) {
        // Set to track visited states
        Set<State> visitedStates = new HashSet<>();
        int newcost = 0;

        // Add the initial state to the frontier and visited states
        Node initialNode = new Node(initialState, null, null, newcost);
        NodeStructure.add(initialNode);
        visitedStates.add(initialNode.state); // Mark initial state as visited

        int nodesExpanded = 0; // Keep track of the number of nodes expanded

        // Main loop: Expand nodes until we either find a goal or exhaust the frontier
        while (!NodeStructure.isEmpty()) {
            Node node;

            // Check if the NodeStructure is a PriorityQueue for UCS, else handle Queue or Stack
            if (NodeStructure instanceof PriorityQueue) {
                node = ((PriorityQueue<Node>) NodeStructure).poll(); // Extract the node with the least cost
            } else if (NodeStructure instanceof Queue) {
                node = ((Queue<Node>) NodeStructure).poll();
            } else if (NodeStructure instanceof Stack) {
                node = ((Stack<Node>) NodeStructure).pop();
            } else {
                throw new IllegalArgumentException("Unsupported collection type for frontier");
            }

            // Debug output
            System.out.println("Expanding node with state: " + node.state);
            nodesExpanded++; // Increment nodes expanded

            // Check if the current state is the goal state
            if (isFinalGoalState(node.state)) {
                // Final goal found, return the search result
                System.out.println("Final goal found! Nodes expanded: " + nodesExpanded);
                return new SearchResult(node, nodesExpanded); // Solution found
            }

            // Expand the node by getting the possible actions
            List<String> actions = getActions(node.state);
            System.out.println("Actions generated: " + actions); // Debugging output

            // For each valid action, get the resulting state and add a new node to the frontier
            for (String action : actions) {
                State newState = getResult(node.state, action);

                // Check if the new state is not null and not visited
                if (newState != null && !visitedStates.contains(newState)) {
                    // Add new state to visitedStates
                    visitedStates.add(newState);

                    // Accumulate cost (if UCS, we are prioritizing based on cost)
                    int newCost = node.cost + 1; // Assuming all actions have a uniform cost of 1

                    // Add the new state, along with the parent node and action taken, to the frontier
                    NodeStructure.add(new Node(newState, node, action, newCost)); // Add to PQ or Queue/Stack
                } else {
                    System.out.println("Discarding invalid pour action: " + action);
                }
            }
        }

        // If no solution is found after expanding all nodes
        System.out.println("No solution found. Nodes expanded: " + nodesExpanded);
        return new SearchResult(null, nodesExpanded); // Return no solution, with the total nodes expanded
    }


}
