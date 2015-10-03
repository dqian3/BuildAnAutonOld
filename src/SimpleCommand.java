
import java.awt.*;
 
import javax.swing.*;


public class SimpleCommand extends CommandBlock {
    private String message;
    
	public SimpleCommand(JComponent com, Color p, Color s, Command c, String m) {
		super(com, p, s, c);
		message = m;
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
}

