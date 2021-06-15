import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.lang.Math;

public class AI{

	public HashMap<Integer,Integer> board;
	public int playerTurn, player1Pieces, player2Pieces, cost;
	public AI parent;

	public AI(State state){
		this.board = boardCopy(state.board);
		int playerTurn = state.playerTurn;
		this.playerTurn = playerTurn;
		int player1Pieces = state.player1Pieces;
		this.player1Pieces = player1Pieces;
		int player2Pieces = state.player2Pieces;
		this.player2Pieces = player2Pieces;
		this.cost = 0;
	}

	public AI(AI ai){
		this.board = boardCopy(ai.board);
		int playerTurn = ai.playerTurn;
		this.playerTurn = playerTurn;
		int player1Pieces = ai.player1Pieces;
		this.player1Pieces = player1Pieces;
		int player2Pieces = ai.player2Pieces;
		this.player2Pieces = player2Pieces;
		int cost = ai.cost;
		this.cost = cost+1;
		this.parent = ai;
	}

	public HashMap<Integer,Integer> boardCopy(HashMap<Integer,Integer> board){
		HashMap<Integer,Integer> boardCopy = new HashMap<Integer,Integer>();
		for(int x=0;x<8;++x){
			for(int y=0;y<8;++y){
				int val_piece = board.get((x*10)+y);
				boardCopy.put((x*10)+y,val_piece);
			}
		}
		return boardCopy;
	}

	public ArrayList<AI> allFeasible(AI ai){
		ArrayList<AI> ret = new ArrayList<AI>();
		//ret.add(ai);
		if(ai.player1Pieces==0 || ai.player2Pieces==0)
			return ret;
		//ai.boardDisplay();
		for(int x=0;x<8;++x){
			for(int y=0;y<8;++y){
				if(ai.board.get((x*10)+y)!=0){
					AI nex_pos1=new AI(ai);
					if(nex_pos1.jumpOne((x*10)+y,((x+1)*10)+(y+1))){
						//System.out.println(x+","+y);
						ret.add(nex_pos1);
					}
					AI nex_pos2=new AI(ai);
					if(nex_pos2.jumpOne((x*10)+y,((x-1)*10)+(y+1))){
						//System.out.println(x+","+y);
						ret.add(nex_pos2);
					}
					AI nex_pos3=new AI(ai);
					if(nex_pos3.jumpOne((x*10)+y,((x+1)*10)+(y-1))){
						//System.out.println(x+","+y);
						ret.add(nex_pos3);
					}
					AI nex_pos4=new AI(ai);
					if(nex_pos4.jumpOne((x*10)+y,((x-1)*10)+(y-1))){
						//System.out.println(x+","+y);
						ret.add(nex_pos4);
					}
					AI nex_pos21=new AI(ai);
					if(nex_pos21.jumpTwo((x*10)+y,((x+2)*10)+(y+2))){
						//System.out.println(x+","+y);
						ret.add(nex_pos21);
					}
					AI nex_pos22=new AI(ai);
					if(nex_pos22.jumpTwo((x*10)+y,((x-2)*10)+(y+2))){
						//System.out.println(x+","+y);
						ret.add(nex_pos22);
					}
					AI nex_pos23=new AI(ai);
					if(nex_pos23.jumpTwo((x*10)+y,((x+2)*10)+(y-2))){
						//System.out.println(x+","+y);
						ret.add(nex_pos23);
					}
					AI nex_pos24=new AI(ai);
					if(nex_pos24.jumpTwo((x*10)+y,((x-2)*10)+(y-2))){
						//System.out.println(x+","+y);
						ret.add(nex_pos24);
					}
				}
			}
		}
		//ai.boardDisplay();
		//System.out.println("feas");
		return ret;
	}

	public AI player1(AI ai){
		//System.out.println(ai.cost);
		//ai.boardDisplay();
		if(ai.cost==4){
			return ai;
		}
		ArrayList<AI> all_nex_pos=allFeasible(ai);
		if(all_nex_pos.size()==0)
			return ai;
		AI best_ai=all_nex_pos.get(0);
		int min_cost=0;
		for(int i=0;i<all_nex_pos.size();++i){
			AI nex_player1=player2(all_nex_pos.get(i));
			if(min_cost<nex_player1.player1Pieces-nex_player1.player2Pieces){
				min_cost=nex_player1.player1Pieces-nex_player1.player2Pieces;
				best_ai=nex_player1;
			}
		}
		return best_ai;
	}

	public AI player2(AI ai){
		//System.out.println(ai.cost);
		//ai.boardDisplay();
		if(ai.cost==4){
			return ai;
		}
		ArrayList<AI> all_nex_pos=allFeasible(ai);
		if(all_nex_pos.size()==0)
			return ai;
		AI best_ai=all_nex_pos.get(0);
		int min_cost=0;
		for(int i=0;i<all_nex_pos.size();++i){
			AI nex_player1=player1(all_nex_pos.get(i));
			if(min_cost<nex_player1.player2Pieces-nex_player1.player1Pieces){
				min_cost=nex_player1.player2Pieces-nex_player1.player1Pieces;
				best_ai=nex_player1;
			}
		}
		return best_ai;
	}

	/*public AI player2(AI ai){
		//System.out.println(ai.cost+" "+ai.playerTurn);
		//ai.boardDisplay();
		if(ai.cost==5){
			return ai;
		}
		ArrayList<AI> all_nex_pos=allFeasible(ai);
		if(all_nex_pos.size()==0)
			return ai;
		AI best_ai=all_nex_pos.get(0);
		int min_cost=0;
		for(int i=0;i<all_nex_pos.size();++i){
			AI nex_player2=player1(all_nex_pos.get(i));
			if(min_cost<nex_player2.player2Pieces-nex_player2.player1Pieces){
				min_cost=nex_player2.player2Pieces-nex_player2.player1Pieces;
				best_ai=nex_player2;
			}
		}
		return best_ai;
	}
*/
	public AI shortest(State state){
		AI start = new AI(state), best_ai;
		//start.boardDisplay();
		if(start.playerTurn>0){
			best_ai = player1(start);
		}
		else if(start.playerTurn<0){
			best_ai = player2(start);
		}
		else{
			System.out.println("::"+start.playerTurn);
			best_ai=player1(start);
		}
		//best_ai.boardDisplay();
		while(best_ai.parent!=start){
			best_ai=best_ai.parent;
		}
		//best_ai.boardDisplay();
		return best_ai;
	}

	public boolean jumpOne(int jump1, int jump2){
		if(this.playerTurn*this.board.get(jump1)<=0)
			return false;
		if((jump2/10>=8||jump2/10<=-1)||(jump2%10>=8||jump2%10<=-1))
			return false;
		if(this.board.get(jump2)!=0)
			return false;
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
			this.playerTurn=(-1)*this.playerTurn;
			//System.out.println(jump1+","+jump2+","+playerTurn+":"+(jump2/10-jump1/10));
			return true;
		}
		else if(Math.abs(piece)==2 && (Math.abs(jump2/10-jump1/10)==1)&&(Math.abs(jump2%10-jump1%10)==1)){
			this.board.put(jump2,piece);
			this.board.put(jump1,0);
			this.playerTurn=(-1)*this.playerTurn;
			//System.out.println(jump1+","+jump2+","+playerTurn+":"+(jump2/10-jump1/10));
			return true;
		}
		return false;
	}

	public void boardDisplay(){
		for(int i=0;i<8;++i){
			for(int j=0;j<8;++j){
				System.out.printf("%d ",this.board.get((i*10)+j));
			}
			System.out.printf("player turn:%d\n",playerTurn);
		}
	}

	public boolean jumpTwo(int jump1, int jump2){
		if(this.playerTurn*this.board.get(jump1)<=0)
			return false;
		if((jump2/10>7||jump2/10<0)||(jump2%10>7||jump2%10<0))
			return false;
		if(this.board.get(jump2)!=0)
			return false;
		//System.out.println("jumpTwo");
		int piece_move=this.board.get(jump1);
		if(((jump2/10-jump1/10)==playerTurn*2)&&(Math.abs(jump2%10-jump1%10)==2)){
			int jump_remove=(jump1+jump2)/2;
			int piece_remove=this.board.get(jump_remove);
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
				this.playerTurn=(-1)*this.playerTurn;
				//System.out.println(jump1+","+jump2+","+jump_remove);
				return true;
			}
		}
		else if((Math.abs(jump2/10-jump1/10)==2)&&(Math.abs(jump2%10-jump1%10)==2)){
			int jump_remove=(jump1+jump2)/2;
			if(!(this.board.get(jump_remove)!=0))
				return false;
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
				this.playerTurn=(-1)*this.playerTurn;
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

}