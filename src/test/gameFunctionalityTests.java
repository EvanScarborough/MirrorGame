package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertNotEquals;
import game.Board;
import game.Mirror;
import game.Ray;
import game.Target;

public class gameFunctionalityTests {
	private static Board board;
	@BeforeClass
	public static void setUp() {	
		board = new Board();
		board.createLevel(6);
	}
	@Test
	public void testCannonAngle(){
		// test that the laser can aim 360 degrees and only 360 degrees
		board.cannon.setFireAngle(-1);
		assert(board.cannon.fireAngle == 359);
		board.cannon.setFireAngle(0);
		assert(board.cannon.fireAngle == 0);
		board.cannon.setFireAngle(360);
		assert(board.cannon.fireAngle == 0);
		board.cannon.setFireAngle(390);
		assert(board.cannon.fireAngle == 30);
	}
	//Tests that simple functions are set up properly and randomly
	@Test
	public void testBoardSetUp() {
		// test random target set up
		Set<int[]> targets = new HashSet<int[]>();
		//creates 10 targets to test that they are properly set up
		board.createLevel(10);
		for(int i = 0; i < 10; i++){
			int[] point = new int[2];
			point[0] = board.target.getxCord();
			point[1] = board.target.getyCord();
			targets.add(point);
		}
		assertEquals(targets.size(), 10);
		// test that mirrors were placed
		for(int i = 0; i < 10; i++){
			board.createLevel(i + 2);
			assert(board.mirrors.size() == i + 2);
		}
	}
	// tests that all levels can be solved
	@Test
	public void testLevelGeneration(){
		for(int i = 0; i < 20; i++){
			board.createLevel(i % 10 + 2);
			assert(board.fire(board.perfectAngle) >= 0);
			//board.perfectAngle stores the angle that was used to create level, thus, it should always win.
		}
	}
}
