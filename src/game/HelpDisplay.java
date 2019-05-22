package game;

import java.awt.Color;
import java.awt.Graphics;

public class HelpDisplay {
	//Displays helpful information
	public void draw(Graphics g){
		//Draw the help screen. This provides information on angles and the game
		g.setColor(Color.WHITE);
		g.drawLine(Board.WIDTH/2, 0, Board.WIDTH/2, Board.HEIGHT-30);
		g.drawLine(0, Board.HEIGHT/2, Board.WIDTH, Board.HEIGHT/2);
		g.drawString("Press Info again to close!", Board.WIDTH / 3 + 30, Board.HEIGHT-16);
		
		// Teach about types of angles
		g.drawString("Angles can be classified by their size.", 5, 15);
		drawAngle(g,60,40,50,60.0,20);
		g.drawString("    This is an acute angle", 100, 80);
		g.drawString("Acute angles are small and cute!", 100, 100);
		drawAngle(g,90,50,150,60.0,20);
		g.drawString("    This is a right angle", 100, 180);
		g.drawString("The square means 90 degrees!", 100, 200);
		drawAngle(g,120,70,250,60.0,20);
		g.drawString("    This is an obtuse angle", 120, 280);
		g.drawString("Obtuse angles are fat!", 120, 300);
		
		// teach about Mirror geometry
		g.setColor(Color.WHITE);
		g.drawString("Mirrors reflect lasers in a special way.", Board.WIDTH/2 + 5, 15);
		drawMirrorNormal(g,Board.WIDTH/2 + 80, 100, 100.0, 30, true);
		g.setColor(Color.WHITE);
		g.drawString("The NORMAL line is 90 degrees", Board.WIDTH/2 + 120, 80);
		g.drawString("from the face of a mirror", Board.WIDTH/2 + 120, 100);
		g.drawString("The angle between an incoming laser and the", Board.WIDTH/2 + 10, 140);
		g.drawString("normal line is the same as the angle that", Board.WIDTH/2 + 10, 160);
		g.drawString("the laser leaves at.", Board.WIDTH/2 + 10, 180);
		drawMirrorWithLaser(g,Board.WIDTH/2 + 160, 300, 170.0, 60);
		
		//Explain game rules in case on forgets or skips initial prompt
		g.setColor(Color.WHITE);
		g.drawString("Parts of the game", 5, Board.HEIGHT/2 + 15);
		//Draw cannon
		int xCord = 30;
		int yCord = Board.HEIGHT/2 + 50;
		g.setColor(new Color(70,200,234));
		g.fillOval(xCord-10,yCord-10,20,20);
		g.setColor(new Color(34,166,199));
		g.fillOval(xCord-7,yCord-7,14,14);
		g.setColor(new Color(34,166,199));
		g.drawRect((int)xCord,(int)yCord-3, 20, 6);
		g.fillRect((int)xCord,(int)yCord-3, 20, 6);
		g.setColor(Color.WHITE);
		g.drawString("Laser Cannon", 80, Board.HEIGHT/2 + 40);
		g.drawString("Type in an angle and the cannon", 80, Board.HEIGHT/2 + 60);
		g.drawString("shoots a laser in that direction!", 80, Board.HEIGHT/2 + 80);
		//Draw mirror
		g.drawLine(10, Board.HEIGHT/2 + 120, 60, Board.HEIGHT/2 + 150);
		g.drawString("Mirror", 80, Board.HEIGHT/2 + 120);
		g.drawString("Will reflect a laser that hits it!", 80, Board.HEIGHT/2 + 140);
		//Draw laser
		g.setColor(Color.CYAN);
		g.drawLine(10, Board.HEIGHT/2 + 170, 40, Board.HEIGHT/2 + 190);
		g.drawLine(40, Board.HEIGHT/2 + 190, 60, Board.HEIGHT/2 + 180);
		g.drawLine(60, Board.HEIGHT/2 + 180, 60, Board.HEIGHT/2 + 220);
		g.setColor(Color.WHITE);
		g.drawString("Laser", 80, Board.HEIGHT/2 + 180);
		g.drawString("Shows the path of the laser", 80, Board.HEIGHT/2 + 200);
		//Draw target
		xCord = 30;
		yCord = Board.HEIGHT/2 + 250;
		g.setColor(Color.GRAY);
		g.fillOval(xCord-10,yCord-10,20,20);
		g.setColor(Color.RED);
		g.fillOval(xCord-7,yCord-7,14,14);
		g.setColor(Color.WHITE);
		g.drawString("Target", 80, Board.HEIGHT/2 + 240);
		g.drawString("Hit the target with a laser to", 80, Board.HEIGHT/2 + 260);
		g.drawString("advance! Turns blue when hit!", 80, Board.HEIGHT/2 + 280);
		
		//Credits
		g.setColor(Color.WHITE);
		int off = 40;
		g.drawString("GAME CREATED BY", Board.WIDTH/2 + 63 + off, Board.HEIGHT/2 + 65);
		g.drawString("Matt Clayton", Board.WIDTH/2 + 80 + off, Board.HEIGHT/2 + 85);
		g.drawString("Caleb Micho", Board.WIDTH/2 + 81 + off, Board.HEIGHT/2 + 105);
		g.drawString("Niall Miner", Board.WIDTH/2 + 85 + off, Board.HEIGHT/2 + 125);
		g.drawString("Evan Scarborough", Board.WIDTH/2 + 65 + off, Board.HEIGHT/2 + 145);
	}
	public void drawMirrorWithLaser(Graphics g, int xp, int yp, double length, int angle){
		//Draws a mirror with the normal marked as a dotted line
		//then draws in a laser and puts the angle in.
		drawMirrorNormal(g,xp,yp,length,0,false);
		int xf = xp - (int)(length/1.5 * Math.cos(Math.toRadians(angle-90)));
		int yf = yp + (int)(length/1.5 * Math.sin(Math.toRadians(angle-90)));

		g.setColor(new Color(150,150,150));
		drawAngleTicks(g, angle, xp, yp, length/1.5, 180 + (90-angle),false);
		drawAngleTicks(g, angle, xp, yp, length/1.5, 270,false);
		g.drawString(Integer.toString(angle) + "°", (int)(xp-length/4.0), (int)(yp - length/4.0));
		g.drawString(Integer.toString(angle) + "°", (int)(xp+length/4.0)-9, (int)(yp - length/4.0));
		
		g.setColor(Color.CYAN);
		g.drawLine(xp,yp,xf,yf);
		xf = xp + (xp - xf);
		g.drawLine(xp,yp,xf,yf);
	}
	public void drawMirrorNormal(Graphics g, int xcenter, int ycenter, double length, int angle, boolean drawBox){
		//draws a mirror with the normal marked as a dotted line
		int xs = xcenter - (int)(length/2.0 * Math.cos(Math.toRadians(angle)));
		int ys = ycenter - (int)(length/2.0 * Math.sin(Math.toRadians(angle)));
		int xf = xcenter + (int)(length/2.0 * Math.cos(Math.toRadians(angle)));
		int yf = ycenter + (int)(length/2.0 * Math.sin(Math.toRadians(angle)));
		g.setColor(Color.WHITE);
		g.drawLine(xs,ys,xf,yf);
		
		g.setColor(new Color(150,150,150));
		xf = xcenter + (int)(length/1.5 * Math.cos(Math.toRadians(angle-90)));
		yf = ycenter + (int)(length/1.5 * Math.sin(Math.toRadians(angle-90)));
		drawDottedLine(g,xcenter,ycenter,xf,yf,(int)(length/30.0));
		
		if(!drawBox) return;
		xs = xcenter - (int)(10.0 * Math.cos(Math.toRadians(angle)));
		ys = ycenter - (int)(10.0 * Math.sin(Math.toRadians(angle)));
		xf = xs + (int)(10.0 * Math.cos(Math.toRadians(angle-90)));
		yf = ys + (int)(10.0 * Math.sin(Math.toRadians(angle-90)));
		g.drawLine(xs,ys,xf,yf);
		xs = xcenter + (int)(10.0 * Math.cos(Math.toRadians(angle-90)));
		ys = ycenter + (int)(10.0 * Math.sin(Math.toRadians(angle-90)));
		g.drawLine(xs,ys,xf,yf);
	}
	public void drawDottedLine(Graphics g, int xs, int ys, int xf, int yf, int dotLength){
		//draw a dotted line! A beautiful self-made addition to Graphics!
		int a = Math.abs(xf-xs);
		int b = Math.abs(yf-ys);
		int length = (int)Math.sqrt(a*a + b*b);
		
		boolean on = false;
		for(int i = 0; i < length; i += dotLength){
			if(!on){ on = true; continue; }
			int xstart = xs + (int)(((float)i / (float)length) * (float)(xf - xs));
			int ystart = ys + (int)(((float)i / (float)length) * (float)(yf - ys));
			int xfin = xs + (int)(((float)(i+dotLength) / (float)length) * (float)(xf - xs));
			int yfin = ys + (int)(((float)(i+dotLength) / (float)length) * (float)(yf - ys));
			g.drawLine(xstart, ystart, xfin, yfin);
			on = false;
		}
	}
	public void drawAngle(Graphics g, int theta, int xp, int yp, double length, int rotation){
		//draws an angle in the help screen
		g.setColor(Color.WHITE);
		int xf = xp + (int)(length * Math.cos(Math.toRadians(theta + rotation)));
		int yf = yp + (int)(length * Math.sin(Math.toRadians(theta + rotation)));
		g.drawLine(xp, yp, xf, yf);
		xf = xp + (int)(length * Math.cos(Math.toRadians(rotation)));
		yf = yp + (int)(length * Math.sin(Math.toRadians(rotation)));
		g.drawLine(xp, yp, xf, yf);
		g.setColor(new Color(150,150,150));
		drawAngleTicks(g,theta,xp,yp,length,rotation,true);
		g.setColor(Color.WHITE);
	}
	public void drawAngleTicks(Graphics g, int theta, int xp, int yp, double length, int rotation, boolean drawLabel){
		//draws little ticks for the inside of an angle in the help screen
		if(theta == 90){
			double l = 15.0;
			int xs = xp + (int)(l * Math.cos(Math.toRadians(rotation)));
			int ys = yp + (int)(l * Math.sin(Math.toRadians(rotation)));
			int xf = xs - (int)(l * Math.cos(Math.toRadians(rotation-90)));
			int yf = ys - (int)(l * Math.sin(Math.toRadians(rotation-90)));
			g.drawLine(xs,ys,xf,yf);
			xs = xp - (int)(l * Math.cos(Math.toRadians(rotation-90)));
			ys = yp - (int)(l * Math.sin(Math.toRadians(rotation-90)));
			g.drawLine(xs,ys,xf,yf);
		}
		else{
			int n = 10;
			double tickStart = (length / 6.0) * 2.0;
			double tickEnd = (length / 12.0) * 5.0;
			for(int i = rotation; i < theta + rotation; i += n){
				int xs = xp + (int)(tickStart * Math.cos(Math.toRadians(i)));
				int ys = yp + (int)(tickStart * Math.sin(Math.toRadians(i)));
				int xt = xp + (int)(tickEnd * Math.cos(Math.toRadians(i)));
				int yt = yp + (int)(tickEnd * Math.sin(Math.toRadians(i)));
				g.drawLine(xs, ys, xt, yt);
			}
		}
		if(!drawLabel)return;
		int labelAngle = rotation + (rotation + theta) / 2;
		int xs = xp + (int)(length * 0.75 * Math.cos(Math.toRadians(labelAngle)));
		int ys = yp + (int)(length * 0.75 * Math.sin(Math.toRadians(labelAngle)));
		g.drawString(Integer.toString(theta) + "°", xs-8, ys);
	}
	public void drawMarker(Graphics g, int xp, int yp, int showHelp){
		//Draws the angle markers to help with aiming
		int itteration = 15;
		for(int theta = 0; theta < 360; theta += itteration){
			if(showHelp > 0 && theta >= Board.getInstance().perfectAngle && theta < Board.getInstance().perfectAngle+itteration){
				if(showHelp > 2) showHelp = 2;
				g.setColor(new Color(5 * showHelp,5 * showHelp,5 * showHelp));
				g.fillArc(xp - 1000, yp - 1000, 2000, 2000, 0-theta, itteration); //shows a hint!
			}
		}
		for(int theta = 0; theta < 360; theta += itteration){
			int xs = xp + (int)(40.0 * Math.cos(Math.toRadians(theta)));
			int ys = yp + (int)(40.0 * Math.sin(Math.toRadians(theta)));
			int xf = xp + (int)(180.0 * Math.cos(Math.toRadians(theta)));
			int yf = yp + (int)(180.0 * Math.sin(Math.toRadians(theta)));
			g.setColor(new Color(30,30,30));
			g.drawLine(xs, ys, xf, yf);
			int xm = xp + (int)(200.0 * Math.cos(Math.toRadians(theta)));
			int ym = yp + (int)(200.0 * Math.sin(Math.toRadians(theta)));
			g.setColor(new Color(60,60,60));
			g.drawString(Integer.toString(theta), xm - 8, ym + 5);
			xs = xp + (int)(220.0 * Math.cos(Math.toRadians(theta)));
			ys = yp + (int)(220.0 * Math.sin(Math.toRadians(theta)));
			xf = xp + (int)(3000.0 * Math.cos(Math.toRadians(theta)));
			yf = yp + (int)(3000.0 * Math.sin(Math.toRadians(theta)));
			g.setColor(new Color(50,50,50));
			g.drawLine(xs, ys, xf, yf);
		}
	}
}
