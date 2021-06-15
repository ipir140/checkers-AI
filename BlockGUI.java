import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlockGUI extends JButton{

	public int x,y;
	public State state;

	public BlockGUI(int x, int y, State state){

		this.x = x;
		this.y = y;
		this.state=state;

		if((x+y)%2 == 0){
			this.setBackground(Color.red);
		}
		else{
			this.setBackground(Color.black);
		}
		this.setOpaque(true);
		this.addActionListener(new listener(this.x,this.y,this.state));
	}

	public void change(int piece){
		if(piece == 0){
			this.setIcon(null);
		}
		else if(piece==1){
			this.setIcon(new ImageIcon("red.jpg"));
		}
		else if(piece==-1){
			this.setIcon(new ImageIcon("black.jpg"));
		}
		else if(piece==2){
			this.setIcon(new ImageIcon("red-king.jpg"));
		}
		else if(piece==-2){
			this.setIcon(new ImageIcon("black-king.jpg"));
		}
	}
}

class listener implements ActionListener{

	public int x,y;
	public State state;

	public listener(int x, int y, State state){
		this.x = x;
		this.y = y;
		this.state = state;;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		System.out.println(this.x+","+this.y);
		state.clicked((x*10)+y);
	}
	
}
