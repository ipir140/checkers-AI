public class Block{

	private int x,y;

	public Block(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int hashMap(){
		return (x*10)+y;
	}

/*	public boolean equals(Piece o){
		if((o.getX() == this.x) && (o.getY() == this.y)){
			return true;
		}
		return false;
	}*/

}