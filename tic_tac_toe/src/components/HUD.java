package components;

import java.awt.Color;
import java.awt.Graphics;

import constants.Constants;
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

	private static final Color RESET_COLOR = Color.BLACK;

	/**
	 * Player X
	 */
	private Player p1;

	public Player getP1() {
		return p1;
	}

	public void setP1(Player p1) {
		this.p1 = p1;
	}

	public Player getP2() {
		return p2;
	}

	public void setP2(Player p2) {
		this.p2 = p2;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public static Color getColor() {
		return COLOR;
	}

	public static Color getResetColor() {
		return RESET_COLOR;
	}

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

	public HUD() {

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

		g.setColor(HUD.RESET_COLOR);

		g.fillRect(3 * Game.WIDTH / 4, Game.HEIGHT / 2 - 15, 150, 75);

		g.setColor(HUD.COLOR);
		g.drawString("Active Player: " + this.active, 3 * Game.WIDTH / 4, Game.HEIGHT / 2);
		g.drawString("Player X Wins: " + this.p1.getWins(), 3 * Game.WIDTH / 4, Game.HEIGHT / 2 + 15);
		g.drawString("Player O Wins: " + this.p2.getWins(), 3 * Game.WIDTH / 4, Game.HEIGHT / 2 + 30);
		g.drawString("Number needed to win: " + (Constants.NUM_TO_MATCH + 1), 3 * Game.WIDTH / 4, Game.HEIGHT / 2 + 45);
	}

}
