package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GamePlay extends JFrame {
	public int levelOn = 1;
	public JTextField angle;
	public JTextField level;
	public JTextField score;
	public JButton nextButton;
	public int scoreinfo = 0;
	public boolean won = false;
	public Board gameBoard;
	private static GamePlay theInstance = new GamePlay();
	public GamePlay(){
		gameBoard = Board.getInstance();
		setSize(Board.WIDTH, Board.HEIGHT + 100);
		setResizable(false);
		setTitle("Final Project");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameBoard.createLevel(1);
		add(gameBoard,BorderLayout.CENTER);
		add(createButtonPanel(),BorderLayout.SOUTH);
	}
	private JPanel createButtonPanel() {
		//set up the control panel
		JPanel panel = new JPanel();
		JPanel panel2 = new JPanel();
		JButton shoot= new JButton("Shoot");
		nextButton = new JButton("Next Level");
		JLabel anglelabel = new JLabel("Angle");
		angle = new JTextField(22);
		angle.setEditable(true);
		JPanel panel3 = new JPanel();
		JLabel levellabel = new JLabel("Current Level");
		level = new JTextField(2);
		level.setEditable(false);
		level.setText(Integer.toString(gameBoard.currentLevel));
		JLabel scoreLabel = new JLabel("Score");
		score = new JTextField(3);
		score.setEditable(false);
		score.setText(Integer.toString(scoreinfo));
		panel.setLayout(new GridLayout(0,2));
		panel2.add(anglelabel);
		panel2.add(angle);
		panel.add(panel2);
		panel.add(shoot);
		panel3.add(levellabel);
		panel3.add(level);
		panel3.add(scoreLabel);
		panel3.add(score);
		JButton help = new JButton("Info");
		help.addActionListener(new HelpButtonListener());
		panel3.add(help);
		panel.add(panel3);
		nextButton.setEnabled(false);
		panel.add(nextButton);
		shoot.addActionListener(new SubmitListener());
		nextButton.addActionListener(new NextListener());
		return panel;
	}
	class HelpButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(!gameBoard.showHelp) gameBoard.showHelp = true;
			else gameBoard.showHelp = false;
			repaint();
		}
	}
	class SubmitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//sets the angle
			if(gameBoard.showHelp) return;
			won = false;
			gameBoard.target.hit = false;
			String text = angle.getText();
			if(text.equals("sonic the hedgehog")){ //cheat codes! don't look!
				gameBoard.angle = (int)gameBoard.perfectAngle;
				repaint();
			}
			else if(text.equals("maximum borkdrive")){
				gameBoard.angle = (int)gameBoard.perfectAngle;
				angle.setText("");
				gameBoard.currentLevel = 30;
				repaint();
			}
			else{
				try{
					gameBoard.angle = Integer.valueOf(text); //player put in a number
					gameBoard.numShots++;
					angle.setText("");
					repaint();
				}catch(NumberFormatException error){
					JOptionPane.showMessageDialog(getInstance(), "Enter a number" , "Error!", JOptionPane.INFORMATION_MESSAGE);
					angle.setText("");
				}
			}
		}
	}
	class NextListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(gameBoard.showHelp) return;
			if(won){
				scoreinfo += gameBoard.laser.hits;
				score.setText(Integer.toString(scoreinfo));
				gameBoard.currentLevel++;
				gameBoard.numShots = 0;
				if(gameBoard.currentLevel > 80) gameBoard.currentLevel--;
				level.setText(Integer.toString(Board.getInstance().currentLevel));
				gameBoard.target = null;
				gameBoard.createLevel(Board.getInstance().currentLevel);
				gameBoard.angle = 90;
				angle.selectAll();
				repaint();
				won = false;
			}
			else
				JOptionPane.showMessageDialog(getInstance(), "You have not hit the target yet!" , "Error!", JOptionPane.INFORMATION_MESSAGE);
		}

	}
	public static GamePlay getInstance() {
		return theInstance;
	}
	public static void main(String[] args) {
		GamePlay gui = getInstance();	
		gui.setVisible(true);
		JOptionPane.showMessageDialog(gui, "Try to aim the laser cannon to bounce off the mirrors and hit the Target!!\nIt will light up when hit. Hit next when the target is lit. \nThe more mirros you hit, the higher your score!" , "Welcome!", JOptionPane.INFORMATION_MESSAGE);
	}
}