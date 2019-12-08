package shapes;

import java.awt.Color;
import java.awt.Graphics;

import main.Game;

/**
 * Represents abstract Shape object that can be drawn to the Game JPanel
 * 
 * @author Samuel Brenner
 * @version 1.0
 *
 */

public abstract class Shape {

	/**
	 * Indent amount for a Shape relative to a {@code Game.Box}
	 */
	public final static int INDENT = 10;

	/**
	 * x coordinate for this Shape
	 */
	protected int x;

	/**
	 * y coordinate for this Shape
	 */
	protected int y;

	/**
	 * Color for this Shape
	 */
	protected static final Color COLOR = Color.WHITE;

	/**
	 * ShapeTage for this Shape
	 */
	private ShapeTag tag;

	/**
	 * Constructor
	 * 
	 * @param x relative x coordinate
	 * @param y relative y coordinate
	 * @param tag ShapeTag to identify this Shape
	 */
	public Shape(int x, int y, ShapeTag tag) {
		this.x = x;
		this.y = y;
		this.tag = tag;
	}

	/**
	 * Draws the shape to the JPanel
	 * 
	 * @param g Graphics component
	 */
	public abstract void draw(Graphics g);

	/**
	 * Returns Shape's y value
	 * 
	 * @return Shape's y value
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Returns Shape's y value
	 * 
	 * @return Shape's y value
	 */
	public int getY() {
		return y;
	}
	
	public ShapeTag getTag() {
		return this.tag;
	}

}
