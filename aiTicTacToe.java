import java.util.*;
public class aiTicTacToe {

	public int player; //1 for player 1 and 2 for player 2
	private int getStateOfPositionFromBoard(positionTicTacToe position, List<positionTicTacToe> board)
	{
		//a helper function to get state of a certain position in the Tic-Tac-Toe board by given position TicTacToe
		int index = position.x*16+position.y*4+position.z;
		return board.get(index).state;
	}
	public positionTicTacToe myAIAlgorithm(List<positionTicTacToe> board, int player)
	{
		//TODO: this is where you are going to implement your AI algorithm to win the game. The default is an AI randomly choose any available move.
		// positionTicTacToe myNextMove = new positionTicTacToe(0,0,0);
		positionTicTacToe myNextMove = getMove(3, board, player);
		
		// do
		// 	{
		// 		Random rand = new Random();
		// 		int x = rand.nextInt(4);
		// 		int y = rand.nextInt(4);
		// 		int z = rand.nextInt(4);
		// 		myNextMove = new positionTicTacToe(x,y,z);
		// 	}while(getStateOfPositionFromBoard(myNextMove,board)!=0);
		return myNextMove;
			
		
	}


	// TODO
	// static eval = what we are saying is heuristic now
	// alpha beta pruning is actual heuristic
	// data types and return types
	// Test structure with simple cases
	// function to get possible moves
	// function to add position to board
	// test minimax on simple test cases
	// getHeuristic function
	// test depths
	// maybe multi-threading?

	private positionTicTacToe getMove(int depth, List<positionTicTacToe> board, int player){

		// create node and init with current board
		Node node = new Node();
		node.setBoard(board);

		Node ret_node = createChildren(node, depth, player);

		System.out.println("ret_node.getChildren().size(): " + ret_node.getChildren().get(0).getChildren().get(0).getChildren().size());

		// printBoardTicTacToe(board);

		boolean maximizingPlayer = false;

		if(player == 1){
			maximizingPlayer = true;
		}

		System.out.println(minimax(ret_node, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, maximizingPlayer));

		return ret_node.getPosition();
		
	}

	private Node createChildren(Node node, int depth, int player){

		// loop over possible moves and create Node for each move and 
		// init board with that position added

		// append each node to the first node's children List

		// when looping over every node, do the same thing for its children

		// repeat this according to the depth 

		if(depth == 0){ // when the depth is reached the max (leaf node) compute that Node's board's heuristic 
			int val = staticEval(node.getBoard(), player, node.getPosition());
			node.setHeuristic_value(val);//getHeuristic(node.board);
			return node;
		}

		if(player == 1){
			player = -1;
		}else{
			player = 1;
		}

		// get possible moves (x,y,z) for the X player or O player
		List<positionTicTacToe> possible_positions = node.getPossible_positions();

		// init node's children
		node.setChildren(new ArrayList<Node>());

		for(int i = 0; i < possible_positions.size(); i++){

			Node child = new Node();

			// set position of move
			child.setPosition(possible_positions.get(i));

			// copy over current board
			child.setBoard(shallowCopy(node.getBoard()));

			// add position of move to board
			child.addToBoard(child.getPosition(), player);

			Node child_node = createChildren(child, depth - 1, player);

			node.addChild(child_node);

		}

		return node;

	}

	public static List<positionTicTacToe> shallowCopy(List<positionTicTacToe> board){

		List<positionTicTacToe> copiedBoard = new ArrayList<positionTicTacToe>();
		// for(Integer element : board) copiedBoard.add(element);
		for(int i=0;i<board.size();i++)
		{
			copiedBoard.add(board.get(i));
		}
		return copiedBoard;

	}

	public positionTicTacToe getnew (int x, int y, int z, int state) {
		// Helper function to create new positions for around the original positions
		positionTicTacToe hello = new positionTicTacToe(x, y ,z , state);
		return hello;
	}
	
	public int staticEval(List<positionTicTacToe> board, int player, positionTicTacToe position) {
			int changethisshit = 0;
			
			getStateOfPositionFromBoard(position, board);
			int currentzvalue = position.z;
			
			// LAYER BELOW
			if (currentzvalue-1 >= 0) {
				if (position.z - 1 >= 0) {
					positionTicTacToe test = getnew(position.x,position.y,position.z-1,position.state); 
					if (test.state == player)
						changethisshit++;
					
					if (position.x + 1 <= 3) {
						positionTicTacToe test2 = getnew(position.x+1,position.y,position.z-1,position.state);
						positionTicTacToe test3 = getnew(position.x+1,position.y+1,position.z-1,position.state); 
						positionTicTacToe test4 = getnew(position.x+1,position.y-1,position.z-1,position.state);
						
						if (test2.state == player)
							changethisshit++;
						if (test3.state == player)
							changethisshit++;
						if (test4.state == player)
							changethisshit++;
					}
					if (position.x - 1 >= 0) {
						positionTicTacToe test5 = getnew(position.x-1,position.y,position.z-1,position.state);
						positionTicTacToe test6 = getnew(position.x-1,position.y+1,position.z-1,position.state); 
						positionTicTacToe test7 = getnew(position.x-1,position.y-1,position.z-1,position.state); 
						
						if (test5.state == player)
							changethisshit++;
						if (test6.state == player)
							changethisshit++;
						if (test7.state == player)
							changethisshit++;
					}
					if (position.y + 1 <= 3) {
						positionTicTacToe test8 = getnew(position.x,position.y+1,position.z-1,position.state);
						
						if (test8.state == player)
							changethisshit++;
					}
					if (position.y - 1 >= 0) {
						positionTicTacToe test11 = getnew(position.x,position.y-1,position.z-1,position.state);
						
						if (test11.state == player)
							changethisshit++;
					}
				}
			}
			
			// LAYER ABOVE
			if (currentzvalue+1 <= 3) {
				if (position.z + 1 <= 3) {
					positionTicTacToe test = getnew(position.x,position.y,position.z+1,position.state); 
					if (test.state == player)
						changethisshit++;
					
					if (position.x + 1 <= 3) {
						positionTicTacToe test2 = getnew(position.x+1,position.y,position.z+1,position.state);
						positionTicTacToe test3 = getnew(position.x+1,position.y+1,position.z+1,position.state); 
						positionTicTacToe test4 = getnew(position.x+1,position.y-1,position.z+1,position.state);
						
						if (test2.state == player)
							changethisshit++;
						if (test3.state == player)
							changethisshit++;
						if (test4.state == player)
							changethisshit++;
					}
					if (position.x - 1 >= 0) {
						positionTicTacToe test5 = getnew(position.x-1,position.y,position.z+1,position.state);
						positionTicTacToe test6 = getnew(position.x-1,position.y+1,position.z+1,position.state); 
						positionTicTacToe test7 = getnew(position.x-1,position.y-1,position.z+1,position.state); 
						
						if (test5.state == player)
							changethisshit++;
						if (test6.state == player)
							changethisshit++;
						if (test7.state == player)
							changethisshit++;
					}
					if (position.y + 1 <= 3) {
						positionTicTacToe test8 = getnew(position.x,position.y+1,position.z+1,position.state);
						
						if (test8.state == player)
							changethisshit++;
					}
					if (position.y - 1 >= 0) {
						positionTicTacToe test11 = getnew(position.x,position.y-1,position.z+1,position.state);
						
						if (test11.state == player)
							changethisshit++;
					}
				}
			}
			
			// CURRENT LAYER
			if (position.x + 1 <= 3) {
				positionTicTacToe test2 = getnew(position.x+1,position.y,position.z,position.state);
				positionTicTacToe test3 = getnew(position.x+1,position.y+1,position.z,position.state); 
				positionTicTacToe test4 = getnew(position.x+1,position.y-1,position.z,position.state); 
				
				if (test2.state == player)
					changethisshit++;
				if (test3.state == player)
					changethisshit++;
				if (test4.state == player)
					changethisshit++;
				
			}
			if (position.x - 1 >= 0) {
				positionTicTacToe test5 = getnew(position.x-1,position.y,position.z,position.state);
				positionTicTacToe test6 = getnew(position.x-1,position.y+1,position.z,position.state); 
				positionTicTacToe test7 = getnew(position.x-1,position.y-1,position.z,position.state); 
				
				if (test5.state == player)
					changethisshit++;
				if (test6.state == player)
					changethisshit++;
				if (test7.state == player)
					changethisshit++;
				
			}
			if (position.y + 1 <= 3) {
				positionTicTacToe test8 = getnew(position.x,position.y+1,position.z,position.state);
				
				if (test8.state == player)
					changethisshit++;
			}
			if (position.y - 1 >= 0) {
				positionTicTacToe test11 = getnew(position.x,position.y-1,position.z,position.state);
				
				if (test11.state == player)
					changethisshit++;
			}
				
			
		
		
		return changethisshit;
	}


	private int minimax(Node node, int depth, int alpha, int beta, boolean maximizingPlayer){

		if(depth == 0 || node.isTerminal()){ //depth = 0 or node is a terminal node then
			//return the heuristic value of node
			return node.getHeuristic_value();
		}

	    if(maximizingPlayer){

	    	int value = Integer.MIN_VALUE;// −infinity
	    	int rec = 0;

	    	List<Node> children = node.getChildren();

	        for(int i = 0; i < children.size(); i++){
	        	rec = minimax(children.get(i), depth - 1, alpha, beta, false);
	        	value = Math.max(value, rec);
	        	alpha = Math.max(alpha, rec);
	        	if (beta <= alpha){
	        		break;
	        	}
	        }

	        return value;

	    } else { // (* minimizing player *)

	    	int value = Integer.MAX_VALUE;// infinity
	    	int rec = 0;

	    	List<Node> children = node.getChildren();

	        for(int i = 0; i < children.size(); i++){
	        	rec = minimax(children.get(i), depth - 1, alpha, beta, true);
	        	value = Math.min(value, rec);
	        	beta = Math.min(beta, rec);
	        	if (beta <= alpha){
        			break;
	        	}
	        }

	        return value;

	    }

	}

    
	public void printBoardTicTacToe(List<positionTicTacToe> targetBoard)
	{
		//print each position on the board, uncomment this for debugging if necessary
		/*
		System.out.println("board:");
		System.out.println("board slots: "+board.size());
		for (int i=0;i<board.size();i++)
		{
			board.get(i).printPosition();
		}
		*/
		
		//print in "graphical" display
		for (int i=0;i<4;i++)
		{
			System.out.println("level(z) "+i);
			for(int j=0;j<4;j++)
			{
				System.out.print("["); // boundary
				for(int k=0;k<4;k++)
				{
					if (getStateOfPositionFromBoard(new positionTicTacToe(j,k,i),targetBoard)==1)
					{
						System.out.print("X"); //print cross "X" for position marked by player 1
					}
					else if(getStateOfPositionFromBoard(new positionTicTacToe(j,k,i),targetBoard)==2)
					{
						System.out.print("O"); //print cross "O" for position marked by player 2
					}
					else if(getStateOfPositionFromBoard(new positionTicTacToe(j,k,i),targetBoard)==0)
					{
						System.out.print("_"); //print "_" if the position is not marked
					}
					if(k==3)
					{
						System.out.print("]"); // boundary
						System.out.println();
					}
					
					
				}

			}
			System.out.println();
		}
	}


	private List<List<positionTicTacToe>> initializeWinningLines()
	{
		//create a list of winning line so that the game will "brute-force" check if a player satisfied any 	winning condition(s).
		List<List<positionTicTacToe>> winningLines = new ArrayList<List<positionTicTacToe>>();
		
		//48 straight winning lines
		//z axis winning lines
		for(int i = 0; i<4; i++)
			for(int j = 0; j<4;j++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(i,j,0,-1));
				oneWinCondtion.add(new positionTicTacToe(i,j,1,-1));
				oneWinCondtion.add(new positionTicTacToe(i,j,2,-1));
				oneWinCondtion.add(new positionTicTacToe(i,j,3,-1));
				winningLines.add(oneWinCondtion);
			}
		//y axis winning lines
		for(int i = 0; i<4; i++)
			for(int j = 0; j<4;j++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(i,0,j,-1));
				oneWinCondtion.add(new positionTicTacToe(i,1,j,-1));
				oneWinCondtion.add(new positionTicTacToe(i,2,j,-1));
				oneWinCondtion.add(new positionTicTacToe(i,3,j,-1));
				winningLines.add(oneWinCondtion);
			}
		//x axis winning lines
		for(int i = 0; i<4; i++)
			for(int j = 0; j<4;j++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(0,i,j,-1));
				oneWinCondtion.add(new positionTicTacToe(1,i,j,-1));
				oneWinCondtion.add(new positionTicTacToe(2,i,j,-1));
				oneWinCondtion.add(new positionTicTacToe(3,i,j,-1));
				winningLines.add(oneWinCondtion);
			}
		
		//12 main diagonal winning lines
		//xz plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(0,i,0,-1));
				oneWinCondtion.add(new positionTicTacToe(1,i,1,-1));
				oneWinCondtion.add(new positionTicTacToe(2,i,2,-1));
				oneWinCondtion.add(new positionTicTacToe(3,i,3,-1));
				winningLines.add(oneWinCondtion);
			}
		//yz plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(i,0,0,-1));
				oneWinCondtion.add(new positionTicTacToe(i,1,1,-1));
				oneWinCondtion.add(new positionTicTacToe(i,2,2,-1));
				oneWinCondtion.add(new positionTicTacToe(i,3,3,-1));
				winningLines.add(oneWinCondtion);
			}
		//xy plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(0,0,i,-1));
				oneWinCondtion.add(new positionTicTacToe(1,1,i,-1));
				oneWinCondtion.add(new positionTicTacToe(2,2,i,-1));
				oneWinCondtion.add(new positionTicTacToe(3,3,i,-1));
				winningLines.add(oneWinCondtion);
			}
		
		//12 anti diagonal winning lines
		//xz plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(0,i,3,-1));
				oneWinCondtion.add(new positionTicTacToe(1,i,2,-1));
				oneWinCondtion.add(new positionTicTacToe(2,i,1,-1));
				oneWinCondtion.add(new positionTicTacToe(3,i,0,-1));
				winningLines.add(oneWinCondtion);
			}
		//yz plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(i,0,3,-1));
				oneWinCondtion.add(new positionTicTacToe(i,1,2,-1));
				oneWinCondtion.add(new positionTicTacToe(i,2,1,-1));
				oneWinCondtion.add(new positionTicTacToe(i,3,0,-1));
				winningLines.add(oneWinCondtion);
			}
		//xy plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(0,3,i,-1));
				oneWinCondtion.add(new positionTicTacToe(1,2,i,-1));
				oneWinCondtion.add(new positionTicTacToe(2,1,i,-1));
				oneWinCondtion.add(new positionTicTacToe(3,0,i,-1));
				winningLines.add(oneWinCondtion);
			}
		
		//4 additional diagonal winning lines
		List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
		oneWinCondtion.add(new positionTicTacToe(0,0,0,-1));
		oneWinCondtion.add(new positionTicTacToe(1,1,1,-1));
		oneWinCondtion.add(new positionTicTacToe(2,2,2,-1));
		oneWinCondtion.add(new positionTicTacToe(3,3,3,-1));
		winningLines.add(oneWinCondtion);
		
		oneWinCondtion = new ArrayList<positionTicTacToe>();
		oneWinCondtion.add(new positionTicTacToe(0,0,3,-1));
		oneWinCondtion.add(new positionTicTacToe(1,1,2,-1));
		oneWinCondtion.add(new positionTicTacToe(2,2,1,-1));
		oneWinCondtion.add(new positionTicTacToe(3,3,0,-1));
		winningLines.add(oneWinCondtion);
		
		oneWinCondtion = new ArrayList<positionTicTacToe>();
		oneWinCondtion.add(new positionTicTacToe(3,0,0,-1));
		oneWinCondtion.add(new positionTicTacToe(2,1,1,-1));
		oneWinCondtion.add(new positionTicTacToe(1,2,2,-1));
		oneWinCondtion.add(new positionTicTacToe(0,3,3,-1));
		winningLines.add(oneWinCondtion);
		
		oneWinCondtion = new ArrayList<positionTicTacToe>();
		oneWinCondtion.add(new positionTicTacToe(0,3,0,-1));
		oneWinCondtion.add(new positionTicTacToe(1,2,1,-1));
		oneWinCondtion.add(new positionTicTacToe(2,1,2,-1));
		oneWinCondtion.add(new positionTicTacToe(3,0,3,-1));
		winningLines.add(oneWinCondtion);	
		
		return winningLines;
		
	}
	public aiTicTacToe(int setPlayer)
	{
		player = setPlayer;
	}
}
