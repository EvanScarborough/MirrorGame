package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class LaserTrajectory {
	// LaserTrajectory shows the path of the laser.
	private Color color;
	public int hits;
	public ArrayList<Ray> pathPoints;
	public LaserTrajectory(){
		pathPoints = new ArrayList<Ray>();
		color = Color.CYAN;
	}
	public int runLaser(ArrayList<Mirror> mirrors, Ray ray){
		//calculates the trajectory of a laser based on the mirrors and starting ray.
		pathPoints.clear();
		Ray outray = null;
		Mirror mostRecentHit = null;
		hits = 0;
		int runs = 0;
		while(runs < 550){
			int thitdis = 10000000;
			if(ray != null && ray.hitTarget()){
				// it would hit the target... but FIRST make sure it doesn't hit a mirror before the target would be hit!
				thitdis = (int)ray.length;
			}
			ray.length = 1000; //ray.hitTarget changes the length, so reset it.
			outray = null;
			Mirror recentTemp = null;
			for(Mirror m: mirrors){
				//look at every mirror and see if it hits.
				if(m == mostRecentHit) continue; // ignore a hit on the mirror that it just bounced off of.
				Ray r = m.calcBounce(ray);
				if(r == null)continue; // it doesn't hit, no need to continue with this mirror.
				double a = (r.xstart - ray.xstart);
				double b = (r.ystart - ray.ystart);
				double dist = Math.sqrt(a*a + b*b); // find the distance to that mirror.
				if(outray == null || dist < ray.length){ // but we're only concerned with the mirror it hits first.
					if(Math.abs(r.angle - ray.angle) > 180){ 
						ray.angle++;
						r = m.calcBounce(ray);
						if(r == null)continue;// corrects a bug that sometimes occurs.
					}
					ray.length = dist;
					outray = r;
					recentTemp = m;
				}
			}
			if(ray.length > thitdis){ // it hits the target
				ray.hitTarget(); //shorten it so that it doesn't go past the target
				GamePlay.getInstance().won = true;
				GamePlay.getInstance().gameBoard.target.hit = true; // tell the game it can go to the next level!
				pathPoints.add(ray);
				break;
			}
			if(runs > 540) return -1; // there's a chance it will get caught in a loop, if so, return -1.
			mostRecentHit = recentTemp;
			pathPoints.add(new Ray(ray));
			if(outray == null){
				break;
			}
			hits++;
			ray = outray;
			runs++;
		}
		return hits;
	}
	public void draw(Graphics g){
		g.setColor(color);
		for(int i = 0; i < pathPoints.size()-1; i++)
			g.drawLine((int)pathPoints.get(i).xstart, (int)pathPoints.get(i).ystart, (int)pathPoints.get(i+1).xstart, (int)pathPoints.get(i+1).ystart);
		pathPoints.get(pathPoints.size()-1).draw(g,color);
	}
}