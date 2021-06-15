public class Utills{

	public int hashCode(int x, int y){
		return (x*10)+y;
	}

	public String pieceString(int id){
		if(id==0){
			return "Blank";
		}
		else if(id==-1){
			return "Player 2";
		}
		else if(id==-2){
			return "Player 2 King";
		}
		else if(id==1){
			return "Player 1";
		}
		else if(id==2){
			return "Player 1 King";
		}
		
		return "invalid id";
	}

}