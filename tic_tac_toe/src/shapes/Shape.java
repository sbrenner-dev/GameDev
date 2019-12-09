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
	public final static int INDENT = Grid.BOX_WIDTH / 10;

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
	
	protected Color color;

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
		return this.y;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

}
