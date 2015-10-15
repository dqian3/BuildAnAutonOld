import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.MenuListener;

import java.awt.*;
import java.util.*;

public class BuildAnAuton extends JFrame {
	private ArrayList<CommandBlock> commands = new ArrayList<CommandBlock>();
	
	//	private Rectangle b = new Rectangle(0, 0, 50, 50);
	JComponent workArea = new JComponent() {
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			for(CommandBlock c:commands) {
				c.paint(g2);
			}
		}
	};
	
	private JMenuBar menu = new JMenuBar();
	private JMenu fileMenu = new JMenu("FIle");
	private JMenuItem save = new JMenuItem("Save");
	
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
				commands.add(new SimpleCommand(new Rectangle(0, 0, 120, 140), Color.BLUE, Color.RED, "Hello World"));
				
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
		
		//save.addActionListener(new MenuListener(this));
		
		menu.add(fileMenu);
		fileMenu.add(save);
		add(menu);
		
		add(buttons, BorderLayout.SOUTH);
		add(workArea, BorderLayout.CENTER);
		t.start();
	}

	public static void main(String[] args) {
		BuildAnAuton x = new BuildAnAuton();
		x.setSize(500, 500);
		x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		x.setVisible(true);
	}
	
	
}
