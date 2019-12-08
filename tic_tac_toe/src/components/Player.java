package components;

import shapes.ShapeTag;

/**
 * 
 * @author Samuel Brenner
 * @version 1.0
 *
 */

public class Player {

	private ShapeTag tag;

	private int wins;

	public Player(ShapeTag tag) {
		this.tag = tag;
	}

	public ShapeTag getShapeTypeAsTag() {
		return this.tag;
	}

	public void incrementWins() {
		this.wins++;
	}

	public int getWins() {
		return this.wins;
	}

}
