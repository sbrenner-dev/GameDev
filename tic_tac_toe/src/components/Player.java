package components;

import shapes.ShapeTag;

/**
 * Player object that can be used in a HUD to display the number of wins and
 * active shape being placed down
 * 
 * 
 * @author Samuel Brenner
 * @version 1.0
 *
 */

public class Player {

	/**
	 * {@code ShapeTag} object identifying what kind of {@code Shape} this player is
	 * placing on the {@code Grid}
	 */
	private ShapeTag tag;

	/**
	 * Number of wins this Player has gotten
	 */
	private int wins;

	/**
	 * Constructs the player
	 * 
	 * @param tag ShapeTag identifying the player
	 */
	public Player(ShapeTag tag) {
		this.tag = tag;
	}

	/**
	 * Access to the ShapeTag field of this Player
	 * 
	 * @return {@code ShapeTag} representing the {@code Shape} this user places on
	 *         the Grid at runtime
	 */
	public ShapeTag getShapeTypeAsTag() {
		return this.tag;
	}

	/**
	 * Access to number of wins this Player has
	 * 
	 * @return number of wins this PLayer has
	 */
	public int getWins() {
		return this.wins;
	}

	/**
	 * Increments the number of wins this Player has by 1
	 */
	public void incrementWins() {
		this.wins++;
	}

}
