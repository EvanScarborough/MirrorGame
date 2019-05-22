package game;

import java.awt.Color;
import java.awt.Graphics;
public class Target{
	public int xCord;
	public int yCord;
	boolean hit = false;
	public Target(int xCord, int yCord) {
		super();
		this.xCord = xCord;
		this.yCord = yCord;
	}
	public boolean isOnLine(int x1, int y1, int x2 , int y2){
		// if the distance from the start of the laser to the target + the distance from the target to the end of the laser
		// equals the distance from the start of the laser to the end, the laser must hit the target
		if (Math.hypot(x1-xCord, y1-yCord) + Math.hypot(x2-xCord, y2-yCord) < Math.hypot(x1-x2, y1-y2) + .3 && Math.hypot(x1-xCord, y1-yCord) + Math.hypot(x2-xCord, y2-yCord) > Math.hypot(x1-x2, y1-y2) - .3)
			return true;
		return false;
	}
	public int getxCord() {
		return xCord;
	}
	public int getyCord() {
		return yCord;
	}
	public void draw(Graphics g){
		// draws the target, cyan if hit with laser
		g.setColor(Color.GRAY);
		g.fillOval(xCord-10,yCord-10,20,20);
		g.setColor(Color.RED);
		if(hit) g.setColor(Color.CYAN);
		g.fillOval(xCord-7,yCord-7,14,14);
	}
}
