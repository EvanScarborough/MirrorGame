package test;

import game.Board;
import game.Mirror;
import game.Ray;
import org.junit.*;
import static org.junit.Assert.assertNotEquals;

public class mirrorReflectionTests {
	private static Board board;
	@BeforeClass
	public static void setUp() {	
		board = Board.getInstance();
	}
	// test that the mirrors indeed get hit
	@Test
	public void testMirrorHit(){
		// a ray that should hit mirror 1 and 2 but not 3
		Ray ray = new Ray(0,0,45,100);
		//hits middle of mirror
		Mirror mirror1 = new Mirror(50, 50, 45);
		assertNotEquals(mirror1.calcBounce(ray), null);
		//misses
		Mirror mirror2 = new Mirror(0, -50, 45);
		assert(mirror2.calcBounce(ray) == null);
		//hits edge of the mirror
		Mirror mirror3 = new Mirror(50, 23, 30);
		assertNotEquals(mirror3.calcBounce(ray), null);
	}
	// test that rays bounce off of mirrors at proper angles
	@Test
	public void testMirrorBounceAngle(){
		//creates ray that should hit mirror 1 
		Ray ray = new Ray(0,0,45,100);
		// 180 + alpha(ray angle) - 2(alpha - beta(mirror normal angle))
		Mirror mirror1 = new Mirror(50, 50, 45);
		assert(mirror1.calcBounce(ray).angle == 225);
		// should bounce and hit mirror 2
		Mirror mirror2 = new Mirror(50, 50, 126);
		assert(mirror2.calcBounce(ray).angle == 27);
		// should bounce and hit mirror3
		Mirror mirror3 = new Mirror(50, 23, 30);
		assert(mirror3.calcBounce(ray).angle == 195);
	}
}





