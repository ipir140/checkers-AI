public class Piece{

	private boolean king;
	private int player,x,y;

	public Piece(int player, int x, int y){
		this.player = player;
		this.x = x;
		this.y = y;
		this.king = false;
	}

	public void kingMake(){
		king = true;
	}

	public int playerno(){
		return player;
	}

	public boolean isKing(){
		return king;
	}

	public String toString(){
		return "" + player;
	}

}