import java.awt.event.*;
import java.awt.geom.Line2D;

import javax.swing.*;

import java.awt.*;
import java.util.*;

public class BuildAnAuton extends JFrame {
	private ArrayList<CommandBlock> commands = new ArrayList<CommandBlock>();
	
	JComponent workArea = new JComponent() {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.draw(new Line2D.Double(0, this.getHeight()/2, this.getWidth(), this.getHeight()/2));
			for(CommandBlock c:commands) {
				c.paint(g2);
			}
		}
	};
	private JScrollPane workAreaPane = new JScrollPane(workArea, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	private JMenuBar menu = new JMenuBar();
	private JMenu fileMenu = new JMenu("FIle");
	private JMenuItem save = new JMenuItem("Save");
	
	private JPanel buttons = new JPanel();
	private JButton add = new JButton("add");
	
	private int xOffset;
	private int yOffset;
	private int focus = -1;
	
	private int snapGap = 60;
	
	public BuildAnAuton() {
		
		setLayout(new BorderLayout());
		
		
		buttons.add(add);
		
		add.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				commands.add(new SimpleCommand(workArea, Color.WHITE, Color.BLACK, "Hello World"));
				
			}
			
		});

		workArea.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				for(int i = commands.size() - 1; i >= 0; i--) {

					if(commands.get(i).getEditPortion().contains(e.getPoint())) {
						commands.get(i).edit();
					}
					if(commands.get(i).getViewPortion().contains(e.getPoint())) {
						commands.get(i).execute();
					}
				
					break;
					
				}
			}
			public void mouseEntered(MouseEvent e) {
				
			}
			public void mouseExited(MouseEvent e) {
				
			}
			public void mouseReleased(MouseEvent e) {
				int temp = focus;
				focus = -1;
				if(Math.abs(commands.get(temp).getHitBox().y + 60 - workArea.getHeight()/2) < snapGap){

					commands.get(temp).setY(workArea.getHeight()/2 - 60);
					commands.get(temp).snap();
				}
				if(commands.get(temp).getHitBox().x < 0){
					commands.get(temp).setX(0);
				}
				place(temp);
			}
			public void mousePressed(MouseEvent e) {
				
				for(int i = commands.size() - 1; i >= 0; i--) {
					Rectangle r = commands.get(i).getDragPortion();
					if(r.contains(e.getPoint())) {
						commands.get(i).unsnap();
						focus = i;
						xOffset = e.getX() - r.x;
						yOffset = e.getY() - r.y;

						break;
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
						Thread.sleep(10);
						commands.get(focus).setX(workArea.getMousePosition().x - xOffset);
						commands.get(focus).setY(Math.abs(workArea.getMousePosition().y - yOffset + 60 - workArea.getHeight()/2) < snapGap ? 
							workArea.getHeight()/2 - 60: 
							workArea.getMousePosition().y - yOffset);
					}
					catch(Exception e){}
					
					}
				}
			}
		});
		
		
		menu.add(fileMenu);
		fileMenu.add(save);
		add(menu);
		
		add(buttons, BorderLayout.SOUTH);
		add(workArea, BorderLayout.CENTER);
		
		validate();
		t.start();
	}

	public static void main(String[] args) {
		BuildAnAuton x = new BuildAnAuton();
		x.setSize(500, 500);
		x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		x.setVisible(true);
	}
	
	public void place(int f) {
		if(f != -1) {
			CommandBlock temp = commands.get(f);
			int xtoswap = temp.getHitBox().x;
			int indexToPlace = 0;
			commands.remove(temp);
			for (int i = 0; i < commands.size(); i++) {
				if(xtoswap > commands.get(i).getHitBox().x) {
					indexToPlace += 1;
				}
			}
			commands.add(indexToPlace, temp);
		}
	}
	
	public void swap(int a, int b) {
		CommandBlock temp = commands.get(a);
		commands.set(a, commands.get(b));
		commands.set(b, temp);
	}
	
}
