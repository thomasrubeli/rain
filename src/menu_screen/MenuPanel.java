package menu_screen;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuPanel extends JPanel implements ActionListener {
	private JButton start;
	private JButton option;
	private JButton stats;
	private BlocksGame main;
	private JLabel label;
	private JLabel name;
	private JTextField field;
	private JButton save;
	private StatsPanel statPane;
	private int point;
	private int level;	
	public MenuPanel(BlocksGame main,StatsPanel statPane) {
		this.main=main;
		this.statPane=statPane;
		setBackground(Color.gray);
		setFocusable(false);
		setPreferredSize(new Dimension(800,800));		
//		setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		start=new JButton("New Game");
		option=new JButton("Options");
		stats=new JButton("View Stats");
		name=new JLabel();
		field=new JTextField();
		save=new JButton("Save");
		label=new JLabel();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		start.setAlignmentX(
				Component.CENTER_ALIGNMENT);
		add(Box.createVerticalStrut(45));
		option.setAlignmentX(
				Component.CENTER_ALIGNMENT);
		stats.setAlignmentX(
				Component.CENTER_ALIGNMENT);
		label.setAlignmentX(
				Component.CENTER_ALIGNMENT);
		name.setAlignmentX(
				Component.CENTER_ALIGNMENT);
		field.setAlignmentX(
				Component.CENTER_ALIGNMENT);
		field.setMaximumSize(new Dimension(200,30));
		save.setAlignmentX(
				Component.CENTER_ALIGNMENT);
		save.addActionListener(this);
		
		start.setFocusable(false);
		option.setFocusable(false);
		stats.setFocusable(false);
		start.addActionListener(this);
		option.addActionListener(this);
		stats.addActionListener(this);
		label.setText("Welcome");
		add(label);
		add(Box.createVerticalStrut(45));
		add(start);
		add(Box.createVerticalStrut(45));
		add(option);
		add(Box.createVerticalStrut(45));
		add(stats);
		add(Box.createVerticalStrut(45));
		setComp(false);
		name.setText("Enter your name:");
		add(name);
		add(Box.createVerticalStrut(45));
		add(field);
		add(Box.createVerticalStrut(45));
		add(save);
		add(Box.createVerticalStrut(45));
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==start){
			main.startGame();
		}
		else if(e.getSource()==option){
			main.setOptionPane();
		}
		else if(e.getSource()==stats){
			main.setStatsPanel();
		}
		else if(e.getSource()==save){
			String name=field.getText();
			statPane.updateScores(name,point,level);
			setComp(false);
		}
		
	}
	public void setdeadScreen(int lvl,int points){
		if(lvl<0){
			return;
		}
		label.setText("You are dead! You made it to lvl : "+lvl +" and your score is : "+points+" ! Try again :)");
		level=lvl;
		point=points;
		setComp(true);
				
	}
	public void setComp(boolean b){
		name.setVisible(b);
		field.setVisible(b);
		save.setVisible(b);
	}
	

}
