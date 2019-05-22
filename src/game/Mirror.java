package game;

import java.awt.Color;
import java.awt.Graphics;

public class Mirror {
	public int xCord;
	public int yCord;
	public final static int LENGTH = 40;
	public double normalAngle;
	public Mirror(int xCord, int yCord) {
		super();
		this.xCord = xCord;
		this.yCord = yCord;
	}
	public Mirror(int xCord, int yCord, double angle) {
		this.xCord = xCord;
		this.yCord = yCord;
		normalAngle = angle;
	}
	public Ray calcBounce(Ray in){
		// will calculate how an incoming ray will bounce off of this mirror.
		// returns null if ray doesn't hit.
		// this involves some pretty fancy geometry, so bear with me....
		
		int[] start = new int[2];
		int[] finish = new int[2];
		start[0] = (int)(xCord - LENGTH * Math.cos(Math.toRadians(normalAngle + 90)));
		start[1] = (int)(yCord - LENGTH * Math.sin(Math.toRadians(normalAngle + 90)));
		finish[0] = (int)(xCord + LENGTH * Math.cos(Math.toRadians(normalAngle + 90)));
		finish[1] = (int)(yCord + LENGTH * Math.sin(Math.toRadians(normalAngle + 90)));
		double sangle = Math.toDegrees(Math.atan2(start[1] - in.ystart, start[0] - in.xstart));
		double fangle = Math.toDegrees(Math.atan2(finish[1] - in.ystart, finish[0] - in.xstart));
		//first find the angle that a ray would have to be at to point at both ends of the mirror.
		// sangle and fangle are the start end of the mirror and end edge, but which is which doesn't matter.
		if(Math.abs(sangle - fangle) > 180){
			if(sangle > fangle) sangle -= 360;
			else fangle -= 360;
		}
		if(isBetween(in.angle,sangle,fangle)){ // so, if the angle of the ray is between the start and end angle, it hits!
			double outAngle = 180 + in.angle - 2*(in.angle - normalAngle);
			double xhit = start[0] + (finish[0] - start[0]) * (in.angle - sangle) / (fangle - sangle);
			double yhit = start[1] + (finish[1] - start[1]) * (in.angle - sangle) / (fangle - sangle);
			return new Ray(xhit,yhit,outAngle,1000);
		}
		if(isBetween(in.angle-360,sangle,fangle)){ // check angle-360 just to be sure.
			double outAngle = 180 + in.angle - 2*(in.angle - normalAngle);
			double xhit = start[0] + (finish[0] - start[0]) * (in.angle - 360 - sangle) / (fangle - sangle);
			double yhit = start[1] + (finish[1] - start[1]) * (in.angle - 360 - sangle) / (fangle - sangle);
			return new Ray(xhit,yhit,outAngle,1000);
		}
		return null;
	}
	
	private boolean isBetween(double val, double edge1, double edge2){
		//helper function to find if an angle is between two other angles
		if(val > edge1 && val < edge2) return true;
		if(val < edge1 && val > edge2) return true;
		return false;
	}
	public void draw(Graphics g){
		g.setColor(Color.WHITE);
		int[] start = new int[2];
		int[] finish = new int[2];
		start[0] = (int)(xCord - LENGTH * Math.cos(Math.toRadians(normalAngle + 90)));
		start[1] = (int)(yCord - LENGTH * Math.sin(Math.toRadians(normalAngle + 90)));
		finish[0] = (int)(xCord + LENGTH * Math.cos(Math.toRadians(normalAngle + 90)));
		finish[1] = (int)(yCord + LENGTH * Math.sin(Math.toRadians(normalAngle + 90)));
		g.drawLine(start[0],start[1],finish[0],finish[1]);
	}
}
