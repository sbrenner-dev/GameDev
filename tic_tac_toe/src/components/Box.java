package components;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Box {
	
	/**
	 * Upper left hand x coordinate for this {@code Grid.Box}
	 */
	private int startX;

	/**
	 * Upper left hand y coordinate for this {@code Grid.Box}
	 */
	private int startY;

	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getWidth() {
		return width;
	}

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
		return new int[] { this.startX, this.startY, this.startX + this.width, this.startY + this.width };
	}

}
