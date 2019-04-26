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
		positionTicTacToe myNextMove = new positionTicTacToe(0,0,0);
		
		do
			{
				// calls get move which is our AI helper function
				myNextMove = getMove(3, board, player);

			}while(getStateOfPositionFromBoard(myNextMove,board)!=0);
		return myNextMove;
			
		
	}

	public positionTicTacToe myAIAlgorithmRandom(List<positionTicTacToe> board, int player)
	{
		//TODO: this is where you are going to implement your AI algorithm to win the game. The default is an AI randomly choose any available move.
		positionTicTacToe myNextMove = new positionTicTacToe(0,0,0);
		
		do
			{
				Random rand = new Random();
				int x = rand.nextInt(4);
				int y = rand.nextInt(4);
				int z = rand.nextInt(4);
				myNextMove = new positionTicTacToe(x,y,z);

			}while(getStateOfPositionFromBoard(myNextMove,board)!=0);
		return myNextMove;
			
		
	}


	private positionTicTacToe getMove(int depth, List<positionTicTacToe> board, int player){

		// create node and init with current board
		Node node = new Node();
		node.setBoard(board);

		// creates tree of children of potential moves and returns root
		Node ret_node = createChildren(node, depth, player);

		// deciding maximizing player
		boolean maximizingPlayer = false;

		if(player == 1){
			maximizingPlayer = true;
		}

		// running minimax
		int value = minimax(ret_node, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, maximizingPlayer);

		// selecting the child with the value selected from minimax and getting the move to make
		for(int i = 0; i < ret_node.getChildren().size(); i++){
			Node child = ret_node.getChildren().get(i);
			int temp_val = child.getHeuristic_value();
			if(temp_val == value){
				return child.getPosition();
			}
		}

		// return the next move to make
		return ret_node.getPosition();
		
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

	private Node createChildren(Node node, int depth, int player){

		// loop over possible moves and create Node for each move and 
		// init board with that position added

		// append each node to the first node's children List

		// when looping over every node, do the same thing for its children

		// repeat this according to the depth 

		if(depth == 0){ // when the depth is reached the max (leaf node) compute that Node's board's heuristic 
			int val = staticEval(node.getBoard(), player, node.getPosition());
			node.setHeuristic_value(val);
			return node;
		}

		if(player == 1){
			player = 2;
		}else{
			player = 1;
		}

		// get possible moves (x,y,z) for the X player or O player
		List<positionTicTacToe> possible_positions = node.getPossible_positions();

		// init node's children
		node.setChildren(new ArrayList<Node>());


		// loop through possible moves for board
		for(int i = 0; i < possible_positions.size(); i++){

			Node child = new Node();

			// set position of move
			child.setPosition(possible_positions.get(i));

			// copy over current board
			child.setBoard(shallowCopy(node.getBoard()));

			// add position of move to board
			child.addToBoard(child.getPosition(), player);

			// call again for each child
			Node child_node = createChildren(child, depth - 1, player);

			// append child to list of child nodes
			node.addChild(child_node);

		}

		return node;

	}

	// create a shallow copy of the board, just to get values
	public static List<positionTicTacToe> shallowCopy(List<positionTicTacToe> board){

		List<positionTicTacToe> copiedBoard = new ArrayList<positionTicTacToe>();

		for(int i=0;i<board.size();i++)
		{
			copiedBoard.add(board.get(i));
		}
		return copiedBoard;

	}

	// function to evaluate each leaf node board to then be used in minimax
	public int staticEval(List<positionTicTacToe> board, int player, positionTicTacToe position) {

		int opposing = 1;

		if(player == 1){
			opposing = 2;
		}

		// Position Variables
		ArrayList<Integer> verticalcount = new ArrayList<Integer>();
		
		for (int i=0;i<4;i++) //Z
		{
			for(int j=0;j<4;j++) //X
			{
				int count = 0;
				int count2 = 0;
				for(int k=0;k<4;k++) //Y
				{
					if (getStateOfPositionFromBoard(new positionTicTacToe(j,k,i),board)==player)
					{
						// player's tile
						count++;

						if (count == 4) { //winning move
							count = Integer.MAX_VALUE;
						}else if (count == 3) { // one away from winning move
							count = Integer.MAX_VALUE - 2;
						}

						count2 = 0;
					}
					else if(getStateOfPositionFromBoard(new positionTicTacToe(j,k,i),board)==opposing)
					{

						// oppossing tile
						count2++;

						if (count2 == 4) {// oppossing winning move
							count2 = Integer.MAX_VALUE;
						}else if (count2 == 3) { // blocking move for player
							count2 = Integer.MAX_VALUE - 1;
						}

						count = 0;
						
					}
					else if (getStateOfPositionFromBoard(new positionTicTacToe(j,k,i),board)==0) {
						
						// reset counts because there was an empty position
						count2 = 0;
						count = 0;		
										
					}
					verticalcount.add(count);
					verticalcount.add(count2);
				}

			}
		}
		// return maximum value
		return Collections.max(verticalcount);
	}
	


	private int minimax(Node node, int depth, int alpha, int beta, boolean maximizingPlayer){

		// all based off sudo code given

		if(depth == 0){ //depth = 0 or node is a terminal node then
			//return the heuristic value of node
			return node.getHeuristic_value();
		}

	    if(maximizingPlayer){

	    	int value = Integer.MIN_VALUE;
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

	        node.setHeuristic_value(value);

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

	        node.setHeuristic_value(value);

	        return value;

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
