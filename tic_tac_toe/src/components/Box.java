package components;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Stores information about a box on a {@code Grid} object, which contain an
 * array of {@code Box objects}
 * 
 * @author Samuel Brenner
 * @version 2.0
 *
 */

public class Box {

	/**
	 * Upper left hand x coordinate for this {@code Grid.Box}
	 */
	private int startX;

	/**
	 * Upper left hand y coordinate for this {@code Grid.Box}
	 */
	private int startY;

	/**
	 * Getter for starting x coordinate
	 * @return starting x coordinate
	 */
	public int getStartX() {
		return startX;
	}

	/**
	 * Setter for starting x coordinate
	 * @param startX new starting x coordinate
	 */
	public void setStartX(int startX) {
		this.startX = startX;
	}

	/**
	 * Getter for starting y coordinate
	 * @return starting y coordinate
	 */
	public int getStartY() {
		return startY;
	}

	/**
	 * Setter for starting y coordinate
	 * @param startY new starting y coordinate
	 */
	public void setStartY(int startY) {
		this.startY = startY;
	}

	/**
	 * Getter for width of box
	 * @return width of box
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Setter for width of box
	 * @param width new width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Width and hence height for this {@code Grid.Box}
	 */
	private int width;

	/**
	 * Constructs a box from an initial (x,y) pair in the upper left hand corner and
	 * a width
	 * 
	 * @param startX initial x value for this {@code Grid.Box}
	 * @param startY initial y value for this {@code Grid.Box}
	 * @param width  width for this {@code Grid.Box}
	 */
	Box(int startX, int startY, int width) {
		this.startX = startX;
		this.startY = startY;
		this.width = width;
	}

	/**
	 * Default constructor
	 */
	public Box() {

	}

	/**
	 * Returns the coordinates of this box as an array, consitsting of [x1, y1, x2,
	 * y2], where x1 and y1 are the upper left coordinates and x2 and y2 are the
	 * lower right coordinates
	 * <p>
	 * Can be used as a boundary assistant for validating mouse input from
	 * {@code Game}
	 * </p>
	 * 
	 * @return array of integers specifying the outer coordinates of this
	 *         {@code Grid.Box}
	 */
	@JsonIgnore
	public int[] boxCoordinates() {
		return new int[] { this.startX, this.startY, this.startX + this.width,
				this.startY + this.width };
	}

}
