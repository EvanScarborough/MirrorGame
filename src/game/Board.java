package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class Board extends JPanel{
	public final static int HEIGHT = 650;
	public final static int WIDTH = 650;
	public final static int LENGTH = 40;
	public int currentLevel = 1;
	public int angle = 90;
	private static Board theInstance = new Board();
	public Cannon cannon;
	public Target target;
	public Ray setupRay;
	public ArrayList<Mirror> mirrors;
	public double perfectAngle = 0;
	public int bestBounces = 0;
	public int tries = 0;
	public boolean showHelp = false;
	public LaserTrajectory laser = new LaserTrajectory();
	public HelpDisplay helpDisp = new HelpDisplay();
	public int numShots = 0;
	
	public Board() {
		mirrors = new ArrayList<Mirror>();
	}
	public static Board getInstance() {
		return theInstance;
	}
	public int fire(double angle){
		cannon.setFireAngle(angle);
		int result = laser.runLaser(mirrors,new Ray(cannon.xCord,cannon.yCord,angle,1000));
		return result;
	}
	public void createLevel(int numMirrors){
		Random rand = new Random();
		Integer randx = rand.nextInt(WIDTH/2) + WIDTH/4;
		Integer randy = rand.nextInt(HEIGHT/2) + HEIGHT/4;
		perfectAngle = rand.nextInt(360);
		cannon = new Cannon(randx,randy);
		setupRay = new Ray(randx,randy,perfectAngle,1000);
		mirrors = new ArrayList<Mirror>();
		ArrayList<Ray> rays = new ArrayList<Ray>();
		rays.add(setupRay);
		for(int i = 0; i < numMirrors; i++){ // place mirrors randomly
			int xp = rand.nextInt(WIDTH-20)+10;
			int yp = rand.nextInt(HEIGHT-20)+10;
			if(isTooClose(xp,yp)){
				i--;
				continue;
			}
			mirrors.add(new Mirror(xp,yp,rand.nextInt(360)));
		}
		int maxAngle = 0;
		int maxVal = 0;
		for(int i = 0; i < 360; i++){ // try every angle to find the one with the most bounces.
			setupRay.angle = i;
			int v = laser.runLaser(mirrors, new Ray(setupRay));
			if(v > maxVal){
				maxAngle = (int)setupRay.angle;
				maxVal = v;
			}
		}
		perfectAngle = maxAngle;
		bestBounces = maxVal;
		laser.runLaser(mirrors, new Ray(randx,randy,perfectAngle,1000));
		Ray r = laser.pathPoints.get(laser.pathPoints.size()-1);
		int l = 100 + rand.nextInt(500);
		int xp = (int)(r.xstart + (double)l * Math.cos(Math.toRadians(r.angle)));
		int yp = (int)(r.ystart + (double)l * Math.sin(Math.toRadians(r.angle)));
		while(l > 10 && (xp < 20 || yp < 20 || xp > WIDTH-20 || yp > HEIGHT-30)){
			l -= rand.nextInt(100);
			if(l < 10) l = 10;
			xp = (int)(r.xstart + (double)l * Math.cos(Math.toRadians(r.angle)));
			yp = (int)(r.ystart + (double)l * Math.sin(Math.toRadians(r.angle)));
		}
		target = new Target(xp,yp); //place the target at the end of the ray that produces most bounces
		if(target.xCord < 20 || target.xCord > WIDTH-20 || target.yCord < 20 || target.yCord > HEIGHT-30){
			target = null;
			createLevel(numMirrors);
		}
	}
	public boolean isTooClose(int xp, int yp){
		//checks if a mirror is too close to another mirror
		double dist = 60;
		double a = Math.abs(xp - cannon.xCord);
		double b = Math.abs(yp - cannon.yCord);
		if(a*a+b*b < dist * dist) return true;
		for(Mirror m: mirrors){
			a = Math.abs(xp - m.xCord);
			b = Math.abs(yp - m.yCord);
			if(a*a+b*b < dist * dist) return true;
		}
		return false;
	}
	public Mirror setNewMirror(ArrayList<Ray> rays){
		//NOT USED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		Random rand = new Random();
		Mirror newMirror = null;
		Ray mostRecent = rays.get(rays.size()-1);
		Ray outray = null;
		int tries = 0;
		while(!isValidMirror(newMirror,outray) && tries < 150){
			int l = rand.nextInt((int)mostRecent.length/2) + (int)mostRecent.length/2;
			int xp = (int)(mostRecent.xstart + (double)l * Math.cos(Math.toRadians(mostRecent.angle)));
			int yp = (int)(mostRecent.ystart + (double)l * Math.sin(Math.toRadians(mostRecent.angle)));
			xp += rand.nextInt(Mirror.LENGTH/2) - Mirror.LENGTH/2;
			yp += rand.nextInt(Mirror.LENGTH/2) - Mirror.LENGTH/2;
			int angle = rand.nextInt(360);
			newMirror = new Mirror(xp,yp,angle);
			outray = newMirror.calcBounce(mostRecent);
			tries++;
		}
		//place a new mirror that intersects last Ray in rays, is on the board, and doesn't overlap any other Ray in rays
		//also set length of last Ray in rays to be precise distance
		//then put a new Ray in rays
		return newMirror;
	}
	public boolean isValidMirror(Mirror mirror, Ray outray){
		//NOT USED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		if(mirror == null) return false;
		if(outray == null) return false;
		if(mirror.xCord < 20 || mirror.yCord < 20 || mirror.xCord > WIDTH-20 || mirror.yCord > HEIGHT - 40) return false;
		int l = (int)(outray.length * 3.0 / 4.0);
		int xp = (int)outray.xstart + l * (int)Math.cos(Math.toRadians(outray.angle));
		int yp = (int)outray.ystart + l * (int)Math.sin(Math.toRadians(outray.angle));
		if(xp < 10 || yp < 10 || xp > WIDTH-10 || yp > HEIGHT - 10) return false;
		return true;
	}
	public Mirror getmirror(int i){
		return mirrors.get(i);
	}
	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		if(showHelp){ //help screen is shown, only draw that.
			helpDisp.draw(g);
			return;
		}
		cannon.setFireAngle(angle);
		laser.runLaser(mirrors,new Ray(cannon.xCord,cannon.yCord,angle,1000));
		helpDisp.drawMarker(g, cannon.xCord, cannon.yCord, numShots - 3);
		for(Mirror m: mirrors){
			m.draw(g);
		}
		laser.draw(g);
		target.draw(g);
		g.setColor(new Color(100,100,100));
		g.drawString("Best possible: " + Integer.toString(bestBounces) + " reflections", 10, HEIGHT - 20);
		cannon.draw(g);
		if(target.hit) GamePlay.getInstance().nextButton.setEnabled(true);
		else GamePlay.getInstance().nextButton.setEnabled(false);
	}
	public ArrayList<Mirror> getMirrors() {
		return mirrors;
	}
	public Target getTarget() {
		return target;
	}
}
