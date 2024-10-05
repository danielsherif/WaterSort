package code;

public class SearchResult {
   private Node solutionNode;
   private int nodesExpanded;

    public SearchResult(Node solutionNode, int nodesExpanded) {
        this.solutionNode = solutionNode;
        this.nodesExpanded = nodesExpanded;
    }

    public Node getSolutionNode() {
        return solutionNode;
    }

    public int getNodesExpanded() {
        return nodesExpanded;
    }
}
