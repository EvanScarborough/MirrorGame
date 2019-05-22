package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Cannon {
	public int xCord;
	public int yCord;
	public double fireAngle;
	public Cannon(int xCord, int yCord) {
		super();
		this.xCord = xCord;
		this.yCord = yCord;
		fireAngle = -20;
	}
	public void setFireAngle(double fireAngle) {
		while(fireAngle < 0) fireAngle += 360;
		this.fireAngle = fireAngle % 360;
	}
	public int getxCord() {
		return xCord;
	}
	public int getyCord() {
		return yCord;
	}
	public void draw(Graphics g){
		//draws in the cannon.
		//NOTE! This uses functionality that rotates the canvas, so this has to be the last thing drawn. For now that's fine.
		fireAngle = fireAngle % 360;
		g.setColor(new Color(70,200,234));
		g.fillOval(xCord-10,yCord-10,20,20);
		g.setColor(new Color(34,166,199));
		g.fillOval(xCord-7,yCord-7,14,14);
		g.setColor(Color.RED);
		Graphics2D g2d = (Graphics2D)g;
	    g2d.setColor(new Color(34,166,199));
	    Rectangle rect2 = new Rectangle((int)xCord,(int)yCord-3, 20, 6);
	    g2d.rotate(Math.toRadians(fireAngle),xCord,yCord);
	    g2d.draw(rect2);
	    g2d.fill(rect2);
	}
}