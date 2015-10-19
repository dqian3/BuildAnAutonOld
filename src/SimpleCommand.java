import java.awt.*;

import javax.swing.*;
public class SimpleCommand extends CommandBlock {
    private String message;
	
	public SimpleCommand(JComponent parent, Color p, Color s, String m) {
		super(parent, p, s);
		message = m;
		
		super.command = new Command() {
			public void execute() {
				System.out.println(message);

			}

			public void edit() {
				String temp;
				temp = JOptionPane.showInputDialog(null, "Message:");
				if(temp != null) {
					message = temp;
				}
			}

			public void viewInfo() {
				execute();
			}
		};
	}

}
	
