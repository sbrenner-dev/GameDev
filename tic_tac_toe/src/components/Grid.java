package components;

import java.awt.Color;
import java.awt.Graphics;

import main.Game;
import main.Main;
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

	public static final int BOX_WIDTH = ((3 * Game.HEIGHT) / 4) / Main.GAME_SIZE;

	private static final Color COLOR = Color.WHITE;

	private static final int QUADS = Main.GAME_SIZE * Main.GAME_SIZE;

	private static final int MATCH_NUM = Main.GAME_SIZE - 1;

	private Shape[][] shapes;

	private Box[] boxes;

	private final int x;
	private final int y;

	private int filledShapes;

	public Grid(int x, int y) {
		
		this.shapes = new Shape[Main.GAME_SIZE][Main.GAME_SIZE];
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

			if (index % Main.GAME_SIZE == 0 && index > 0) {
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

		for (Shape[] rShapes : this.shapes) {
			for (Shape s : rShapes) {
				if (s != null) {
					s.draw(g);
				}
			}
		}

		// g.drawLine(this.x, this.y, this.x + 3 * Grid.BOX_WIDTH, this.y);

		for (int mult = 1; mult < Main.GAME_SIZE; mult++) {
			g.drawLine(this.x + mult * Grid.BOX_WIDTH, this.y, this.x + mult * Grid.BOX_WIDTH,
					this.y + Main.GAME_SIZE * Grid.BOX_WIDTH);
			g.drawLine(this.x, this.y + mult * Grid.BOX_WIDTH, this.x + Main.GAME_SIZE * Grid.BOX_WIDTH,
					this.y + mult * Grid.BOX_WIDTH);
		}

		/*
		 * g.setColor(Color.RED);
		 * 
		 * for (Box b : this.boxes) { g.drawRect(b.startX, b.startY, b.width, b.width);
		 * }
		 */

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

			if (x >= startX && x <= endX && y >= startY && y <= endY
					&& this.shapes[index / Main.GAME_SIZE][index % Main.GAME_SIZE] == null) {
				this.shapes[index / Main.GAME_SIZE][index % Main.GAME_SIZE] = (tag == ShapeTag.SHAPE_X)
						? new X(startX + Shape.INDENT, startY + Shape.INDENT)
						: new O(startX + Shape.INDENT, startY + Shape.INDENT);
				this.filledShapes++;
				return true;
			}
		}

		return false;
	}

	/**
	 * Clears the Shape objects from the Grid
	 */
	public void clear() {
		for (int i = 0; i < this.shapes.length; i++) {
			for (int j = 0; j < this.shapes[i].length; j++) {
				this.shapes[i][j] = null;
			}
		}
		this.filledShapes = 0;
	}

	public Object[] checkWin() {

		int matchPair1 = 0;
		int matchPair2 = 0;

		for (int i = 0; i < this.shapes[0].length; i++) {
			for (int j = 1; j < this.shapes.length; j++) {
				if (this.shapes[0][i] != null && this.shapes[j][i] != null) {
					if (this.shapes[0][i].getTag() == this.shapes[j][i].getTag()) {
						matchPair1++;
					}
				}
				if (this.shapes[i][0] != null && this.shapes[i][j] != null) {
					if (this.shapes[i][0].getTag() == this.shapes[i][j].getTag()) {
						matchPair2++;
					}
				}
			}
			if (matchPair1 == Grid.MATCH_NUM) {
				return new Object[] { true, this.shapes[0][i].getTag() };
			} else if (matchPair2 == Grid.MATCH_NUM) {
				return new Object[] { true, this.shapes[i][0].getTag() };
			}
			matchPair1 = 0;
			matchPair2 = 0;
		}

		matchPair1 = 0;
		matchPair2 = 0;

		int length = this.shapes.length - 1;
		System.out.println();
		for (int i = 1; i < this.shapes.length; i++) {
			if (this.shapes[0][0] != null && this.shapes[i][i] != null) {
				if (this.shapes[0][0].getTag() == this.shapes[i][i].getTag()) {
					matchPair1++;
				}
			}

			if (this.shapes[0][length] != null && this.shapes[i][length - i] != null) {
				if (this.shapes[0][length].getTag() == this.shapes[i][length - i].getTag()) {
					matchPair2++;
				}
			}
		}

		if (matchPair1 == Grid.MATCH_NUM) {
			return new Object[] { true, this.shapes[0][0].getTag() };
		} else if (matchPair2 == Grid.MATCH_NUM) {
			return new Object[] { true, this.shapes[0][length].getTag() };
		}

		return new Object[] { false };
	}

	public int filledBoxes() {
		return this.filledShapes;
	}

	/**
	 * Inner class representing Box object
	 * 
	 * @author Samuel Brenner
	 *
	 */
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