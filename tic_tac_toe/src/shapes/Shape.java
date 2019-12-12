package shapes;

import java.awt.Color;
import java.awt.Graphics;

import components.Grid;

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
	public final static int INDENT = Grid.BOX_WIDTH / 10 > 0 ? Grid.BOX_WIDTH / 10 : 1;

	public final static int BOUNDS_SIZE = 8;

	/**
	 * x coordinate for this Shape
	 */
	protected int x;

	/**
	 * y coordinate for this Shape
	 */
	protected int y;

	/**
	 * ShapeTage for this Shape
	 */
	private ShapeTag tag;

	/**
	 * {@code Color} for this Shape when being drawn to the {@code Grid}
	 */
	protected Color color;

	/**
	 * All the bordering shapes around this {@code Shape} Meaning of indecies in
	 * ascending order: 0 - Top Left, 1 - Top 2, - Top Right, 3 - Right, 4 - Bottom
	 * Righ,t 5 - Bottom, 6 - Bottom, Left 7 - Left
	 */
	private Shape[] bounds;

	/**
	 * Constructor
	 * 
	 * @param x   relative x coordinate
	 * @param y   relative y coordinate
	 * @param tag ShapeTag to identify this Shape
	 */
	public Shape(int x, int y, ShapeTag tag) {
		this.x = x;
		this.y = y;
		this.tag = tag;
		this.color = Color.GREEN;

		this.bounds = new Shape[Shape.BOUNDS_SIZE];
	}

	/**
	 * Draws the shape to the JPanel
	 * 
	 * @param g Graphics component
	 */
	public abstract void draw(Graphics g);

	/**
	 * Access to the {@code ShapeTag} object that identifies this Shape
	 * 
	 * @return ShapeTag that identifies this Shape
	 */
	public ShapeTag getTag() {
		return this.tag;
	}

	/**
	 * Sets the color for this {@code Shape} to be drawn to the {@code Grid}
	 * 
	 * @param color {@code Color} of this {@code Shape}
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Returns this {@code Shape}'s bounds array
	 * 
	 * @return
	 */
	public Shape[] getBounds() {
		return this.bounds;
	}

}
