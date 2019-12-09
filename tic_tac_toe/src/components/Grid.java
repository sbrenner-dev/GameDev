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
 * Consitst of n {@code Box} in an internal array of boxes
 * 
 * @author Samuel Brenner
 * @version 1.0
 *
 */

public class Grid {

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
			return new int[] { this.startX, this.startY, this.startX + this.width,
					this.startY + this.width };
		}

	}

	/**
	 * Global variable representing the width of each Box lying on this Grid
	 */
	public static final int BOX_WIDTH = 3 * Game.HEIGHT / 4 / Main.GAME_SIZE;

	/**
	 * {@code Color} of this Grid
	 */
	private static final Color COLOR = Color.WHITE;

	/**
	 * Number of {@code Box} objects on this Grid (aka Quads)
	 */
	private static final int QUADS = Main.GAME_SIZE * Main.GAME_SIZE;

	/**
	 * Global number of {@code Shape} must match with in order to be a valid win on
	 * this Grid
	 */
	private static final int MATCH_NUM = Main.NUM_TO_MATCH;

	/**
	 * Matrix of Shapes that represent the board they sit on visually
	 * <p>
	 * Matrix makes algorithmic win logic easier
	 */
	private Shape[][] shapes;

	/**
	 * 1 dimensional array of {@code Box} objects that hold the bounds for each
	 * {@code Box} on this Grid
	 */
	private Box[] boxes;

	/**
	 * initial x coordinate for this Grid
	 */
	private final int x;

	/**
	 * initial y coordinate for this grid
	 */
	private final int y;

	/**
	 * Number of shapes in boxes that have been filled on this Grid
	 */
	private int filledBoxes;

	private boolean state_Init;

	private Shape current_Shape;

	private boolean clearShapes;

	/**
	 * Constructor for this Grid
	 * <p>
	 * Constructs on an (x, y) pair that holds the origin point in the upper left,
	 * as like other drawing components
	 * 
	 * @param x initial x coordinate
	 * @param y initial y coordinate
	 */
	public Grid(int x, int y) {

		this.shapes = new Shape[Main.GAME_SIZE][Main.GAME_SIZE];
		this.boxes = new Box[Grid.QUADS];

		this.x = x;
		this.y = y;

		this.filledBoxes = 0;

		this.init();
	}

	/**
	 * Determines if the state of this Grid is one of a win Only called when the
	 * number of objects on the board is 2 * {@code Main.GAME_SIZE} - 1
	 * 
	 * @return Object array of at least a Boolean, true if the current state of this
	 *         Grid is a win, and an additional {@code ShapeTag} if the state is a
	 *         win
	 */
	public Object[] checkWin() {

		int matchPair1 = 0;
		int matchPair2 = 0;

		/*
		 * Checking rows and columns in one sweep
		 */

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

		/*
		 * Checking both diagonals in on sweep
		 */

		int length = this.shapes.length - 1;
		System.out.println();
		for (int i = 1; i < this.shapes.length; i++) {
			if (this.shapes[0][0] != null && this.shapes[i][i] != null) {
				if (this.shapes[0][0].getTag() == this.shapes[i][i].getTag()) {
					matchPair1++;
				}
			}

			if (this.shapes[0][length] != null && this.shapes[i][length - i] != null) {
				if (this.shapes[0][length].getTag() == this.shapes[i][length - i]
						.getTag()) {
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

	/**
	 * Clears the Shape objects from the Grid
	 */
	public void clear() {
		for (int i = 0; i < this.shapes.length; i++) {
			for (int j = 0; j < this.shapes[i].length; j++) {
				if (this.shapes[i][j] != null) {
					this.shapes[i][j].setColor(Color.BLACK);
				}
				this.clearShapes = true;
			}
		}
		this.filledBoxes = 0;
		this.current_Shape = null;
	}

	/**
	 * Draws the Grid and all of its components to the JPanel
	 * 
	 * @param g Graphics for painting to JPanel
	 */
	public void draw(Graphics g) {
		g.setColor(Grid.COLOR);

		/*
		 * ONLY NEED TO UPDATE MOST RECENTLY ADDED SHAPE
		 */

		if (this.current_Shape != null) {
			this.current_Shape.draw(g);
		}

		if (this.clearShapes) {
			for (int i = 0; i < this.shapes.length; i++) {
				for (int j = 0; j < this.shapes[i].length; j++) {
					Shape s = this.shapes[i][j];
					if (s != null) {
						s.draw(g);
						s.setColor(Color.GREEN);
						this.shapes[i][j] = null;
					}
				}
			}
			this.clearShapes = false;
		}

		/*
		 * DO NOT ALWAYS NEED TO RENDER THE ENTIRE GRID EVERY TIME A CHANGE IS MADE
		 * ONLY NEED TO ENTER ONCE WHEN GRID IS FIRST INITIALIZES
		 */

		if (this.state_Init) {
			this.state_Init = false;
			for (int mult = 1; mult < Main.GAME_SIZE; mult++) {
				g.drawLine(this.x + mult * Grid.BOX_WIDTH, this.y,
						this.x + mult * Grid.BOX_WIDTH,
						this.y + Main.GAME_SIZE * Grid.BOX_WIDTH);
				g.drawLine(this.x, this.y + mult * Grid.BOX_WIDTH,
						this.x + Main.GAME_SIZE * Grid.BOX_WIDTH,
						this.y + mult * Grid.BOX_WIDTH);
			}
		}

	}

	/**
	 * Accesses number of filled boxes on this Grid
	 * 
	 * @return number of {@code Box} objects filled in this.boxes
	 */
	public int filledBoxes() {
		return this.filledBoxes;
	}

	/**
	 * Setup for this.boxes
	 */
	private void init() {

		this.state_Init = true;

		this.clearShapes = false;

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
	 * Returns true if all the {@code Box} objects in the interal array are filled
	 * with {@code Shape} objects
	 * 
	 * @return true if all the boxes in this Grid are filled with Shapes
	 */
	public boolean isFilled() {
		return this.filledBoxes == Grid.QUADS;
	}

	/**
	 * Adds a Shape to the Grid
	 * 
	 * @param tag {@code ShapeTag} identifier as to what kind of {@code Shape} to
	 *            place in a {@code Box}
	 * @param x   x location of cursor
	 * @param y   y location of cursor
	 * @return true if a {@code Shape} of type tag was added to the Grid
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
					&& this.shapes[index / Main.GAME_SIZE][index
							% Main.GAME_SIZE] == null) {

				this.current_Shape = tag == ShapeTag.SHAPE_X
						? new X(startX + Shape.INDENT, startY + Shape.INDENT)
						: new O(startX + Shape.INDENT, startY + Shape.INDENT);

				this.shapes[index / Main.GAME_SIZE][index
						% Main.GAME_SIZE] = this.current_Shape;
				this.filledBoxes++;
				return true;
			}
		}

		return false;
	}

}
