import java.util.*;
public class Node {

	private List<Node> children;
	private List<positionTicTacToe> board;
	private int heuristic_value;
	private positionTicTacToe position;

	public Node(){
		this.heuristic_value = 0;
	}

	public void setChildren(List<Node> children){
		this.children = children;
	}

	public void addChild(Node child){
		this.children.add(child);
	}

	public List<Node> getChildren(){
		return this.children;
	}

	public void setBoard(List<positionTicTacToe> board){
		this.board = board;
	}

	public List<positionTicTacToe> getBoard(){
		return this.board;
	}

	public void addToBoard(positionTicTacToe position, int player){
		int idx = getStateOfPositionFromBoard(position,this.board);
		this.board.get(idx).state = player;
	}

	private int getStateOfPositionFromBoard(positionTicTacToe position, List<positionTicTacToe> targetBoard)
	{
		//a helper function to get state of a certain position in the Tic-Tac-Toe board by given position TicTacToe
		int index = position.x*16+position.y*4+position.z;
		return targetBoard.get(index).state;
	}

	public List<positionTicTacToe> getPossible_positions(){
		List<positionTicTacToe> free_spaces = new ArrayList<positionTicTacToe>();
		for (int i=0;i<4;i++)
		{
			for(int j=0;j<4;j++)
			{
				for(int k=0;k<4;k++)
				{
					int pos = getStateOfPositionFromBoard(new positionTicTacToe(j,k,i),this.board); 
					if (pos==0)
					{
						free_spaces.add(new positionTicTacToe(j,k,i));
					}
				}

			}
		}
		return free_spaces;
	}

	public int getHeuristic_value(){
		return this.heuristic_value;
	}

	public void setHeuristic_value(int val){
		this.heuristic_value = val;
	}

	public positionTicTacToe getPosition(){
		return this.position;
	}

	public void setPosition(positionTicTacToe pos){
		this.position = pos;
	}

	public boolean isTerminal(){
		return this.children.size() < 1;
	}

}