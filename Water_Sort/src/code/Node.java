package code;


import java.lang.Thread.State;


// a search tree node is defined to be having a 5 tuples in the lecture

public class Node {
	
	public Node parent;
    public int depth; 
   	public code.State state;  
    public String action;   // corrosponds to the operator which is the pour (i , j ) 
     public int cost ;   
    
    public Node(code.State newState, Node parent, String action , int cost ) {
        this.state = newState;
        this.parent = parent;
        this.action = action;
        this.cost = cost ;
        // this iam saying if parent == null >> this.depth = depth else depth = depth+1
        this.depth = parent == null ? 0 : parent.depth + 1;
    }
       
}

	
