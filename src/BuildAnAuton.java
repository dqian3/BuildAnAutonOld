import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * The graphical interface used to build a series of user defined commands. Each command is
 * encapsulated by a <code>CommandBlock</code> object that defines it's position in the user interface.
 * When the "save" menuItem is selected, you will save it to a .dat file as an editable ArrayList of commandBlock objects.
 * These files can be opened using the "file" option.
 * When the "export" menuItem is selected, the program will export the ArrayList of commands to a .dat file.
 * 
 * 
 * @author Daniel Qian
 * @author Dominic Rubino
 * 
 */
public class BuildAnAuton extends JFrame  {
	private ArrayList<CommandBlock> commands = new ArrayList<CommandBlock>();
	
	private JComponent workArea = new JComponent() {
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			for(CommandBlock c:commands) {
				c.paint(g2);
			}
		}
	};
	
	private JPanel buttons = new JPanel();
	private JButton add = new JButton("add");
	
	private int xOffset;
	private int yOffset;
	private int focus = -1;
	
	public BuildAnAuton() {
		
		setLayout(new BorderLayout());
		
		
		buttons.add(add);
		
		add.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {
				
			}
			
		});

		workArea.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				for(int i = commands.size() - 1; i >= 0; i--) {
					Rectangle r = commands.get(i).getHitBox();
					if(r.contains(e.getPoint())) {
						if(e.getPoint().y < r.y + 20) {
						}
						else if(e.getPoint().y > r.y + 120) {
							
						}
						else {
							if(e.getPoint().x > r.x + 60) {
								commands.get(i).edit();
							}
							else {
								commands.get(i).execute();
							}
						}
						break;
						
						
						/*
						r.x -= 5;
						r.y -= 5;
						
						r.height += 10;
						r.width  += 10;
						
						*/
					}
				}
			}
			public void mouseEntered(MouseEvent e) {
				
			}
			public void mouseExited(MouseEvent e) {
				
			}
			public void mouseReleased(MouseEvent e) {

				focus = -1;
			}
			public void mousePressed(MouseEvent e) {
				
				for(int i = commands.size() - 1; i >= 0; i--) {
					Rectangle r = commands.get(i).getHitBox();
					if(r.contains(e.getPoint())) {
						if(e.getPoint().y < r.y + 20) {
							focus = i;
							xOffset = e.getX() - r.x;
							yOffset = e.getY() - r.y;
							break;
						}
					}
				}
			}
			
		}); 

		Thread t = new Thread(new Runnable() {
			public void run() {
				while(true) {
					workArea.repaint();	
					
					if(focus != -1) {
						try{
						commands.get(focus).setX(workArea.getMousePosition().x - xOffset);
						commands.get(focus).setY(workArea.getMousePosition().y - yOffset);
						}
						catch(Exception e){}
							
					}
				}
			}
		});
		
		add(buttons, BorderLayout.SOUTH);
		add(workArea, BorderLayout.CENTER);
		t.start();
	}

	//getters
	public ArrayList<CommandBlock> getCommands() {
		return commands;
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		BuildAnAuton x = new BuildAnAuton();
		x.setSize(500, 500);
		x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		x.setVisible(true);
	}
}
