import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Main extends JFrame implements Observer{

	public JLabel status;
	public BlockGUI buttonMap[];
	public State state;

	public Main(){
		super("Checkers(IIT2018140)");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel statusbar = new JPanel();
		statusbar.setPreferredSize(new Dimension(10,30));
		this.status = new JLabel("Status: Select the piece you want to move");
		statusbar.add(status);
		this.add(statusbar,BorderLayout.NORTH);

		this.state = new State();
		this.state.addObserver(this);
		JPanel boardGUI = new JPanel();
		buttonMap = new BlockGUI[100];
		boardGUI.setLayout(new GridLayout(8,8));
		for(int x=0;x<8;x++){
			for(int y=0;y<8;y++){
				BlockGUI button = new BlockGUI(x,y,this.state);
				boardGUI.add(button);
				this.buttonMap[(x*10)+y]=button;
			}
		}
		this.add(boardGUI,BorderLayout.CENTER);

		JPanel lowBar = new JPanel();
		JButton aiPlay = new JButton("Let AI to play this step");
		aiPlay.addActionListener(new aiPlayListener(this.state));
		lowBar.add(aiPlay);
		this.add(lowBar,BorderLayout.SOUTH);

		this.setSize(500, 540);
		this.setResizable(false);
		this.setVisible(true);
		refresh();
	}

	public void refresh(){
		System.out.println("refresh");
		String status_string;
		if(this.state.isOver()){
			status_string="Status: GameOver";
		}
		else if(this.state.clicked.size()==0){
			status_string="Status: Select the piece you want to move";
			if(this.state.playerTurn==1)
				status_string+="(red)";
			if(this.state.playerTurn==-1)
				status_string+="(black)";
			status_string+=" or let AI make move";
		}
		else if(this.state.clicked.size()==1){
			status_string="Status: Select the block where you want to move";
			if(this.state.playerTurn==1)
				status_string+="(red)";
			if(this.state.playerTurn==-1)
				status_string+="(black)";
			status_string+=" or let AI make move";
		}
		else{
			status_string="";	
		}
		this.status.setText(status_string);
		for(int x=0;x<8;x++){
			for(int y=0;y<8;y++){
				BlockGUI button = this.buttonMap[(x*10)+y];
				int piece = this.state.getPiece(x,y);
				button.change(piece);
			}
		}
	}
	
	@Override
	public void update(Observable arg0, Object arg1){
		System.out.println("update");
		refresh();
		/*refresh();
		if((this.model.hasWon() != 0)){
				this.setEnabled(false);
				new GameOver(this,this.model);
				this.setEnabled(true);
			}
			else if (this.model.clicked.size() == 0){
				this.status.setText("Status: Select the piece you want to move");
			}
			else{
				this.status.setText("Status: Select where to move this piece to");
			}*/
	}

	public static void main(String[] args) {
		Main home = new Main();
		/*while(true){
			home.refresh();
		}*/
	}
}

class aiPlayListener implements ActionListener{

	public State state;

	public aiPlayListener(State state){
		this.state=state;
	}

	@Override
	public void actionPerformed(ActionEvent e1){
		this.state.next_ai();
	}
}
