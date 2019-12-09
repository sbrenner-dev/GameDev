package components;

import java.awt.Color;
import java.awt.Graphics;

import main.Game;
import shapes.ShapeTag;

/**
 * Heads Up Display object that contains the two players scores and the player
 * actively making a move on the Grid
 * <p>
 * Can draw to a {@code java.awt.JPanel} or {@code java.awt.JFrame}
 * 
 * @author Samuel Brenner
 * @version 1.0
 *
 */

public class HUD {

	/**
	 * {@code Color} object for the Color of this HUD when drawn to the JFrame
	 */
	private static final Color COLOR = Color.WHITE;

	/**
	 * Player X
	 */
	private Player p1;

	/**
	 * Player O
	 */
	private Player p2;

	/**
	 * String representing the active player - the player making a move
	 * <p>
	 * {@code String} "X" or {@code String} "O"
	 */
	private String active;

	/**
	 * Constructs this HUD
	 * 
	 * @param p1  first player (X)
	 * @param p2  second player (O)
	 * @param tag {@code ShapeTag} object to identify the active player
	 */
	public HUD(Player p1, Player p2, ShapeTag tag) {
		this.p1 = p1;
		this.p2 = p2;

		this.changeTag(tag);
	}

	/**
	 * Changes the internal {@code String} this.active to match the identifier based
	 * on the {@code ShapeTag} tag
	 * 
	 * @param tag {@code ShapeTag} object that identifies the kind of shape on which
	 *            to change this String tag
	 */
	public void changeTag(ShapeTag tag) {
		this.active = tag == ShapeTag.SHAPE_O ? "O" : "X";
	}

	/**
	 * Draws this HUD to the JFrame
	 * 
	 * @param g {@code Graphics} component to use to draw to the JFrame
	 */
	public void draw(Graphics g) {
		g.setColor(HUD.COLOR);
		g.drawString("Active Player: " + this.active, 3 * Game.WIDTH / 4,
				Game.HEIGHT / 2);
		g.drawString("\nPlayer X Wins: " + this.p1.getWins(), 3 * Game.WIDTH / 4,
				Game.HEIGHT / 2 + 15);
		g.drawString("\nPlayer O Wins: " + this.p2.getWins(), 3 * Game.WIDTH / 4,
				Game.HEIGHT / 2 + 30);
	}

}
