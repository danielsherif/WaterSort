package code;  // Make sure this matches your package name

public class Main {
    public static void main(String[] args) {
    	
        // Example input
  
    	String init1 = "7;4;" + "b,y,r,b;" + "b,y,r,r;" + "y,r,b,y;" + "e,e,e,b;" + "e,b,e,e;" + "e,e,e,b;" + "b,e,e,e;"; // this is take around 6 mins to find an answer for the bf

    	String init3 ="5;4;" + "b,y,r,b;" + "b,y,r,r;" + "y,r,b,y;" + "e,e,e,e;" + "e,e,e,e;";
    	String init2 = "3;3;g,g,b;e,e,e;g,b,r"; // no solution
    	//String init = "2;3;" +"r,r,g;" + "e,e,r;";
    	//String init = "2;2;r,g;e,e;";
    	//String init = "3;3;" + "r,b,e;" + "e,e,e;" + "e,e,e;";
    	//String init = "3;2;" + "e,r;" + "r,g;" + "e,g;";   
        String init = "3;2;" + "e,e;" + "e,e;" + "e,e;";    // if all bottles are empty 
    	//String init = "4;3;" + "b,y,b;" + "r,e,r;" + "e,e,e;" + "e,e,e;";
    	//String init = "9;5;r,g,b,y,e;r,y,y,e,e;g,b,r,g,e;y,y,b,e,e;b,g,r,y,e;b,g,y,e,e;b,r,g,e,e;g,g,g,g,e;y,b,r,y,e;"; 
    	// i think DFS stuck forover in this case 
    	// i need to try bfs
    	


   
        String strategy = "BF";  // choose any of the stratigies
        boolean visualize = false;   // if we put it true > more details about the expansion process / false >> the result 

        // Call the solve method from WaterSortSearch and store the result
        String result = WaterSortSearch.solve(init3, strategy, visualize);

        // Print the result (sequence of actions, path cost, nodes expanded)
        System.out.println(result);
    }
}
