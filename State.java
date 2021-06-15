import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;
import java.lang.Math;

public class State extends Observable{

	public HashMap<Integer,Integer> board;
	public int playerTurn;
	public ArrayList<Integer> clicked;
	public int player1Pieces, player2Pieces;

	public State(){
		this.playerTurn = 1;
		this.player1Pieces = 8;
		this.player2Pieces = 8;
		this.clicked = new ArrayList<Integer>();
		this.board = new HashMap<Integer, Integer>();
		Utills util = new Utills();
		for (int x=0;x<8;x++){
			for(int y=0;y<8;y++){
				if(((x == 0) && (y%2 == 1)) || ((x == 1) && (y%2 == 0))){ 
					this.board.put(util.hashCode(x,y),1);
				}
				else if(((x == 7) && (y%2 == 0)) || ((x == 6) && (y%2 == 1))){
					this.board.put(util.hashCode(x,y),-1);
				}
				else{
					this.board.put(util.hashCode(x,y),0);
				}
			}
		}
		update();
	}

	public void ai_state(AI ai){
		this.playerTurn = ai.playerTurn;
		this.player1Pieces = ai.player1Pieces;
		this.player2Pieces = ai.player2Pieces;
		this.board = ai.board;
		this.clicked = new ArrayList<Integer>();
	}

	public void next_ai(){
		AI ai=new AI(this);
		//this.boardDisplay();
		this.ai_state(ai.shortest(this));//.boardDisplay();
		this.boardDisplay();
		System.out.println(playerTurn);
		update();
		//(new State(ai.shortest(this))).boardDisplay();
	}

	public void update(){
		setChanged();
		notifyObservers();
	}

	public int getPiece(int x, int y){
		return this.board.get((x*10)+y);
	}

	public void clicked(int block){
		if(this.clicked.size()==0 && this.playerTurn*this.board.get(block)>0){
			this.clicked.add(block);
		}
		else if(this.clicked.size()==1 && this.playerTurn*this.board.get(block)==0){
			this.clicked.add(block);
			if(this.jumpOne(this.clicked.get(0), this.clicked.get(1))){
				this.clicked.clear();
				this.playerTurn=(-1)*this.playerTurn;
			}
			else if(this.jumpTwo(this.clicked.get(0), this.clicked.get(1))){
				this.clicked.clear();
				this.playerTurn=(-1)*this.playerTurn;
			}
		}
		else{
			this.clicked.clear();
		}
		update();
	}

	public boolean jumpOne(int jump1, int jump2){
		//System.out.println("jumpOne");
		int piece=this.board.get(jump1);
		if(((jump2/10-jump1/10)==playerTurn)&&(Math.abs(jump2%10-jump1%10)==1)){
			if((jump2/10==0 || jump2/10==7)&&(piece!=2 && piece!=-2)){
				this.board.put(jump2,piece*2);
			}
			else{
				this.board.put(jump2,piece);
			}
			this.board.put(jump1,0);
			return true;
		}
		else if(Math.abs(piece)==2 && (Math.abs(jump2/10-jump1/10)==1)&&(Math.abs(jump2%10-jump1%10)==1)){
			this.board.put(jump2,piece);
			this.board.put(jump1,0);
			return true;
		}
		//System.out.println(jump1+","+jump2+","+playerTurn+":"+(jump2/10-jump1/10));
		return false;
	}

	public void boardDisplay(){
		for(int i=0;i<8;++i){
			for(int j=0;j<8;++j){
				System.out.printf("%d ",this.board.get((i*10)+j));
			}
			System.out.printf("\n");
		}
	}

	public boolean jumpTwo(int jump1, int jump2){
		//System.out.println("jumpTwo");
		int piece_move=this.board.get(jump1);
		if(((jump2/10-jump1/10)==playerTurn*2)&&(Math.abs(jump2%10-jump1%10)==2)){
			int jump_remove=(jump1+jump2)/2;
			int piece_remove=this.board.get(jump_remove);
			System.out.println(jump1+","+jump2+","+jump_remove);
			if(piece_remove*piece_move<0){
				if((jump2/10==0 || jump2/10==7)&&(piece_move!=2 && piece_move!=-2)){
					this.board.put(jump2,piece_move*2);

				}
				else{
					this.board.put(jump2,piece_move);
				}
				this.board.put(jump1,0);
				this.board.put(jump_remove,0);
				if(piece_remove<0)
					player2Pieces--;
				else
					player1Pieces--;
				return true;
			}
		}
		else if(Math.abs(piece_move)==2 && (Math.abs(jump2/10-jump1/10)==2)&&(Math.abs(jump2%10-jump1%10)==2)){
			int jump_remove=(jump1+jump2)/2;
			int piece_remove=this.board.get(jump_remove);
			//System.out.println(jump1+","+jump2+","+jump_remove);
			if(piece_remove*piece_move<0){
				this.board.put(jump2,piece_move);
				this.board.put(jump1,0);
				this.board.put(jump_remove,0);
				if(piece_remove<0)
					player2Pieces--;
				else
					player1Pieces--;
				return true;
			}
		}
		return false;
	}

	public boolean isOver(){
		if(player1Pieces==0 || player2Pieces==0){
			return true;
		}
		return false;
	}


/*	public static void main(String[] args) {
		State source = new State();
		source.next_ai();
	}*/

}