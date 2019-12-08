package components;

import java.awt.Color;
import java.awt.Graphics;

import shapes.O;
import shapes.Shape;
import shapes.ShapeTag;
import shapes.X;

/**
 * Representation of the Grid portion of the Game board
 * <p>
 * Consitst of 9 square quadrant borders
 * 
 * @author Samuel Brenner
 * @version 1.0
 *
 */

public class Grid {
	
	public static final int BOX_WIDTH = 100;

	private static final Color COLOR = Color.WHITE;

	private static final int QUADS = 9;

	private Shape[] shapes;

	private Box[] boxes;

	private final int x;
	private final int y;
	
	private int filledShapes;

	public Grid(int x, int y) {
		this.shapes = new Shape[Grid.QUADS];
		this.boxes = new Box[Grid.QUADS];

		this.x = x;
		this.y = y;
		
		this.filledShapes = 0;

		this.init();
	}
	
	public boolean isFilled() {
		return this.filledShapes == Grid.QUADS;
	}

	/**
	 * Setup for {@code boxes}
	 */
	private void init() {

		int workingX = this.x;
		int workingY = this.y;

		for (int index = 0; index < this.boxes.length; index++) {

			if (index % 3 == 0 && index > 0) {
				workingY += Grid.BOX_WIDTH;
				workingX = this.x;
			}

			this.boxes[index] = new Box(workingX, workingY, Grid.BOX_WIDTH);

			workingX += Grid.BOX_WIDTH;
		}

	}

	/**
	 * Draws the Grid and all of its components to the JPanel
	 * 
	 * @param g Graphics for painting to JPanel
	 */
	public void draw(Graphics g) {
		g.setColor(Grid.COLOR);

		for (Shape s : this.shapes) {
			if (s != null) {
				s.draw(g);
			}
		}

		g.drawLine(this.x + Grid.BOX_WIDTH, this.y, this.x + Grid.BOX_WIDTH, this.y + 3 * Grid.BOX_WIDTH);
		g.drawLine(this.x + 2 * Grid.BOX_WIDTH, this.y, this.x + 2 * Grid.BOX_WIDTH, this.y + 3 * Grid.BOX_WIDTH);
		g.drawLine(this.x, this.y + Grid.BOX_WIDTH, this.x + 3 * Grid.BOX_WIDTH, this.y + Grid.BOX_WIDTH);
		g.drawLine(this.x, this.y + 2 * Grid.BOX_WIDTH, this.x + 3 * Grid.BOX_WIDTH, this.y + 2 * Grid.BOX_WIDTH);
	}

	/**
	 * Adds a Shape to the Grid
	 * 
	 * @param s Shape to place in a certain box
	 * @param x x location of cursor
	 * @param y y location of cursor
	 * @return true if s was added to the Grid
	 */
	public boolean placeShape(ShapeTag tag, int x, int y) {
		for (int index = 0; index < this.boxes.length; index++) {
			Box box = this.boxes[index];
			
			int[] coordinates = box.boxCoordinates();
			int startX = coordinates[0];
			int startY = coordinates[1];
			int endX = coordinates[2];
			int endY = coordinates[3];
			
			if (x >= startX && x <= endX && y >= startY && y <= endY && this.shapes[index] == null) {
				this.shapes[index] = tag == ShapeTag.SHAPE_X ? new X(startX + Shape.INDENT, startY + Shape.INDENT)
						: new O(startX + 10, startY + 10);
				this.filledShapes++;
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Clears the Shape objects from the Gridi
	 */
	public void clear() {
		for (int index = 0; index < this.shapes.length; index++) {
			// set color to black too ?
			this.shapes[index] = null;
		}
	}
	
	public boolean checkWin() {
		return false;
	}

	private class Box {

		private int startX;
		private int startY;
		private int width;

		private Box(int startX, int startY, int width) {
			this.startX = startX;
			this.startY = startY;
			this.width = width;
		}

		public int[] boxCoordinates() {
			return new int[] { this.startX, this.startY, this.startX + this.width, this.startY + this.width };
		}

	}

}
