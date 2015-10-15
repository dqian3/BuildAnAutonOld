import java.awt.*;
import javax.swing.*;
public class SimpleCommand extends CommandBlock {
    private String message;
    
	public SimpleCommand(Rectangle r, Color p, Color s, String m) {
		super(r, p, s);
		message = m;
	}
	
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
}
	
