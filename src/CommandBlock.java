import java.awt.*;
import java.awt.geom.*;

public abstract class CommandBlock {
	private Rectangle hitbox;
	private Color primCol;
	private Color secCol;
	
	public CommandBlock(Rectangle r, Color p, Color s) {
		hitbox = r;
		primCol = p;
		secCol = s;
	}
	public CommandBlock(int x, int y, int h, int w, Color p, Color s) {
		hitbox = new Rectangle(x, y, h, w);
		primCol = p;
		secCol = s;
	}
	
	
	/*
	 * Methods for running and editing the program created.
	 */
	public abstract void execute();
	public abstract void edit();
	public abstract void viewInfo();
	
	
	//setters
	public void setX(int x) {
		hitbox.x = x;
	}
	public void setY(int y) {
		hitbox.y = y;
	}
	
	//getters
	public Rectangle getHitBox() {
		return hitbox;	
	}

	public Color getPrimary() {
		return primCol;
	}
	public Color getSecondary() {
		return secCol;
	}
	
	
	/**
	 * Draws the command block on the passed graphics
	 * @param g
	 */
	public void paint(Graphics2D g) {
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		g.drawString(this.getClass().getName(), hitbox.x + 2, hitbox.y + 18);
		g.draw(new RoundRectangle2D.Double(hitbox.x, hitbox.y, hitbox.width, 20, 8, 8));
		g.draw(new RoundRectangle2D.Double(hitbox.x, hitbox.y + 20, 60, 100, 8, 8)); 
		g.draw(new RoundRectangle2D.Double(hitbox.x + 60, hitbox.y + 20, 60, 100, 8, 8));
	}
	
}
