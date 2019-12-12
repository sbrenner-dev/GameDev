package main;

/**
 * Launches the game on the basis of a game name
 * <p>
 * Known issues: Mouse click boxes not properly centered around visual boxes
 * 
 * @author Samuel Brenner
 * @version 1.0
 * 
 *
 */

public class Main {

	/**
	 * Title of the game to launch
	 */
	public static final String GAME_NAME = "Tic Tac Toe";

	/**
	 * Default size of Grid on the Game
	 */
	public static int GAME_SIZE = 3;

	/**
	 * Number of matches one shape needs to match with another in a row in order to
	 * win
	 * 
	 * @version 1.0 changed manually/internally, no UI set up to handle custom sizes
	 */
	public static int NUM_TO_MATCH = Main.GAME_SIZE - 1;

	/**
	 * Launches the instance of the game
	 * 
	 * @param args VM parameters at runtime
	 */
	public static void main(String[] args) {
		new LoadScreen();
	}

}
