package game;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Ray {
	// A Ray is just a way to hold a starting location and an angle all packaged up into one data structure.
	public double xstart;
	public double ystart;
	public double angle;
	public double length;
	public Ray(double xstart, double ystart, double angle){
		this.xstart = xstart;
		this.ystart = ystart;
		this.angle = angle % 360;
		length = 0;
	}
	public Ray(double xstart, double ystart, double angle, double length){
		this.xstart = xstart;
		this.ystart = ystart;
		this.angle = angle % 360;
		this.length = length;
	}
	public Ray(Ray copy){
		xstart = copy.xstart;
		ystart = copy.ystart;
		angle = copy.angle;
		length = copy.length;
	}
	public void draw(Graphics g, Color c){
		g.setColor(c);
		g.drawLine((int)xstart, (int)ystart, (int)(xstart + length * Math.cos(Math.toRadians(angle))), (int)(ystart + length * Math.sin(Math.toRadians(angle))));
	}
	public boolean hitTarget(){
		//tests for if this ray hits the target
		if(Board.getInstance().target == null) return false;
		if(GamePlay.getInstance().won == false && Board.getInstance().getTarget().isOnLine((int)xstart, (int)ystart, (int)(xstart + length * Math.cos(Math.toRadians(angle))), (int)(ystart + length * Math.sin(Math.toRadians(angle))))){
			int a = Math.abs((int)xstart - (int)Board.getInstance().target.getxCord());
			int b = Math.abs((int)ystart - (int)Board.getInstance().target.getyCord());
			length = Math.sqrt(a*a+b*b);
			return true;
		}
		return false;
	}
}
