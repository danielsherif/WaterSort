package code;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class WaterSortSearch extends GenericSearch {
    protected State initialState;
    private SearchResult searchResult;
    private HashSet<String> visitedStates; // Set to track visited states
    

    public WaterSortSearch(State initialState) {
        this.initialState = initialState;
        this.visitedStates = new HashSet<>(); // Initialize the visited states set
    }

    @Override
    protected State getInitialState() {
        return initialState;
    }

    @Override
    protected boolean isGoalState(State state) {
        return state.isGoalState();
    }

    protected void setSearchResult(SearchResult searchResult) {
        this.searchResult = searchResult;
    }

    public Node getSolutionNode() {
        return searchResult != null ? searchResult.getSolutionNode() : null; 
    }

    public int getNodesExpanded() {
        return searchResult != null ? searchResult.getNodesExpanded() : 0; 
    }

    @Override
    protected List<String> getActions(State state) {
        List<String> actions = new ArrayList<>();
        String[] bottles = state.getBottles();

        for (int i = 0; i < bottles.length; i++) {
            for (int j = 0; j < bottles.length; j++) {
                if (i != j && state.checkPour(i, j)) {
                    actions.add("pour(" + i + "," + j + ")");
                }
            }
        }
        
        // Just for testing
        System.out.println("Available actions from state: ");
        System.out.println(state.visualize());
        return actions;
    }

    @Override
    protected State getResult(State state, String action) {
        String[] parts = action.replace("pour(", "").replace(")", "").split(",");
        System.out.println("Pouring " + action.replace("pour(", "").replace(")", ""));
        int m = Integer.parseInt(parts[0]);
        int n = Integer.parseInt(parts[1]);

        // Use the pour method from the State class
        State newState = state.pour(m, n);


        // Check if the new state is valid (not null)
        if (newState == null) {
            System.out.println("Invalid pour action: " + action);
            return null; // Indicate the pour was not successful
        }

        // Check if the new state has already been visited
        String stateKey = generateStateKey(newState);
        if (visitedStates.contains(stateKey)) {
            System.out.println("State already visited: " + newState);
            return null; // Skip this state as it's already visited
        }

        visitedStates.add(stateKey); // Mark this state as visited
        return newState; // Return the new state if the pour was successful
    }

    private String generateStateKey(State state) {
        // Create a unique key for the state (you can customize this as needed)
        return String.join(",", state.getBottles());
    }
  


    private static State parseInitialState(String initialStateString) {
    	
        String[] lines = initialStateString.split(";");
        int numberOfBottles = Integer.parseInt(lines[0]);
        int capacity = Integer.parseInt(lines[1]);
        String[] bottles = new String[numberOfBottles];
        
        for (int i = 0; i < numberOfBottles; i++) {
            String[] colors = lines[i + 2].split(",");
            
            // Reverse the colors to make the first listed color at the top
            StringBuilder reversedBottle = new StringBuilder();
            for (int j = colors.length - 1; j >= 0; j--) {
                reversedBottle.append(colors[j]);
            }
            bottles[i] = reversedBottle.toString();
        }
        return new State(bottles, capacity);
    }

    public static String solve(String initialState, String strategy, boolean visualize) {
        State initialState1 = parseInitialState(initialState);
        WaterSortSearch searchProblem = new WaterSortSearch(initialState1);

        if (strategy.equals("BF")) {
            SearchResult searchResult = new BFS(initialState1).bfs();
            searchProblem.setSearchResult(searchResult);
        } else if (strategy.equals("DF")) {
            SearchResult searchResult = new DFS(initialState1).dfs();
            searchProblem.setSearchResult(searchResult);
        } else if (strategy.equals("UC")) {
            SearchResult searchResult = new UCS(initialState1).ucs();
            searchProblem.setSearchResult(searchResult);
        } else {
            throw new IllegalArgumentException("Unsupported search strategy: " + strategy);
        }


        if (searchProblem.getSolutionNode() == null) {
            return "NOSOLUTION";
        }

        Node solutionNode = searchProblem.getSolutionNode();
        int nodesExpanded = searchProblem.getNodesExpanded();

        List<String> plan = new ArrayList<>();
        int pathCost = 0;
        Node currentNode = solutionNode;

        while (currentNode.parent != null) {

            // the string that is required in the description
            String formattedAction = currentNode.action.replace("(", "_").replace(",", "_").replace(")", "");
            plan.add(0, formattedAction); // Add to the front of the list to maintain order
            pathCost++;
            currentNode = currentNode.parent;
        }

        if (visualize) {
            currentNode = solutionNode;
            while (currentNode != null) {
                System.out.println("====================================="); // Add a separator
                System.out.println(currentNode.state.visualize()); // Visualize the state
                System.out.println("====================================="); // Add a separator
                currentNode = currentNode.parent;
            }
        }

        return String.join(",", plan) + ";" + pathCost + ";" + nodesExpanded;
    }

}