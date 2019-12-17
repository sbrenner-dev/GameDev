package shapes;

import java.awt.Color;
import java.awt.Graphics;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import components.Grid;

/**
 * Represents abstract Shape object that can be drawn to the Game JPanel
 * 
 * @author Samuel Brenner
 * @version 1.0
 *
 */

@JsonIgnoreProperties(value = {"bounds"})
public class Shape {

	/**
	 * Indent amount for a Shape relative to a {@code Game.Box}
	 */
	public final static int INDENT = Grid.BOX_WIDTH / 10 > 0 ? Grid.BOX_WIDTH / 10 : 1;

	public final static int BOUNDS_SIZE = 8;

	/**
	 * x coordinate for this Shape
	 */
	private int x;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setTag(ShapeTag tag) {
		this.tag = tag;
	}

	/**
	 * y coordinate for this Shape
	 */
	private int y;

	/**
	 * ShapeTage for this Shape
	 */
	private ShapeTag tag;

	/**
	 * {@code Color} for this Shape when being drawn to the {@code Grid}
	 */
	private Color color;

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

	public Shape() {
		this.bounds = new Shape[Shape.BOUNDS_SIZE];
	}

	/**
	 * Draws the shape to the JPanel
	 * 
	 * @param g Graphics component
	 */
	public void draw(Graphics g) {
		if (tag == ShapeTag.SHAPE_O) {
			g.setColor(this.color);

			int width = Grid.BOX_WIDTH - 2 * Shape.INDENT;

			g.drawOval(this.x, this.y, width, width);
		} else {
			g.setColor(this.color);

			int width = Grid.BOX_WIDTH - 2 * Shape.INDENT;

			g.drawLine(this.x, this.y, this.x + width, this.y + width);
			g.drawLine(this.x, this.y + width, this.x + width, this.y);
		}
	}

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
	@JsonIgnore
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Returns this {@code Shape}'s bounds array
	 * 
	 * @return array {@code Shape} objects that border this {@code Shape} object
	 */
	@JsonIgnore
	public Shape[] getBounds() {
		return this.bounds;
	}

}
