/**
 * Interface representing the actions that a robot can perform.
 * Should provide for editing and viewing as well.
 */
public interface Command {
	public abstract void edit();
	public abstract void execute();
	public abstract void viewInfo();
}
