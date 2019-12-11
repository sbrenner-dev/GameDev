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
			return new int[] { this.startX, this.startY, this.startX + this.width, this.startY + this.width };
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

	/**
	 * Internal flag for the state of this Grid being first initialized
	 */
	private boolean state_Init;

	/**
	 * The current {@code Shape} being added to this Grid
	 */
	private Shape current_Shape;

	/**
	 * Internal flag for letting the draw() method know if the {@code Shape} objects
	 * need to be cleared
	 */
	private boolean clearShapes;

	private int curr_Matches;

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

		boolean validated = false;

		for(Shape shapes[] : this.shapes) {
			for(Shape me : shapes) {
				if (me != null) {
					Shape[] myBounds = me.getBounds();
					for (int n = 0; n < myBounds.length; n++) {
						this.curr_Matches = 0;
						Shape next = myBounds[n];
						Shape opp = myBounds[this.oppositeN(n)];

						if (next == null) {
							continue;
						}

						boolean nextSameAsOpp = false;

						if (next != null && opp != null) {
							nextSameAsOpp = next.getTag() == opp.getTag();
						}

						if (!nextSameAsOpp) {
							validated = this.validate(me, next, n);
							if (validated) {
								return new Object[] { validated, me.getTag() };
							}
						}
					}
				}
			}
		}

		this.curr_Matches = 0;

		return new Object[] { validated };

	}

	private boolean validate(Shape me, Shape next, int n) {
		if (next == null && this.curr_Matches < Main.NUM_TO_MATCH) {
			return false;
		} else if (this.curr_Matches == Main.NUM_TO_MATCH) {
			return true;
		} else if (me.getTag() == next.getTag()) {
			this.curr_Matches++;
			return this.validate(next, next.getBounds()[n], n);
		} else {
			return false;
		}
	}

	private int oppositeN(int n) {
		if (n < Shape.BOUNDS_SIZE / 2) {
			return n + Shape.BOUNDS_SIZE / 2;
		} else {
			return n - Shape.BOUNDS_SIZE / 2;
		}
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
		this.curr_Matches = 0;
	}

	/**
	 * Draws the Grid and all of its components to the JPanel
	 * 
	 * @param g Graphics for painting to JPanel
	 */
	public void draw(Graphics g) {
		g.setColor(Grid.COLOR);

		if (this.current_Shape != null) {
			this.current_Shape.draw(g);
			this.current_Shape = null;
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

		if (this.state_Init) {
			this.state_Init = false;
			for (int mult = 1; mult < Main.GAME_SIZE; mult++) {
				g.drawLine(this.x + mult * Grid.BOX_WIDTH, this.y, this.x + mult * Grid.BOX_WIDTH,
						this.y + Main.GAME_SIZE * Grid.BOX_WIDTH);
				g.drawLine(this.x, this.y + mult * Grid.BOX_WIDTH, this.x + Main.GAME_SIZE * Grid.BOX_WIDTH,
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

		this.curr_Matches = 0;

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

			int relRow = index / Main.GAME_SIZE;
			int relCol = index % Main.GAME_SIZE;

			if (x >= startX && x <= endX && y >= startY && y <= endY && this.shapes[relRow][relCol] == null) {

				this.current_Shape = tag == ShapeTag.SHAPE_X ? new X(startX + Shape.INDENT, startY + Shape.INDENT)
						: new O(startX + Shape.INDENT, startY + Shape.INDENT);

				this.fillRespectiveBounds(relRow, relCol, this.current_Shape);

				this.shapes[relRow][relCol] = this.current_Shape;
				this.filledBoxes++;
				return true;
			}
		}

		return false;
	}

	private void fillRespectiveBounds(int row, int col, Shape shape) {

		// Needs optimization - very hardcoded right now!

		Shape[] bounds = shape.getBounds();

		for (int n = 0; n < Shape.BOUNDS_SIZE; n++) {

			Shape comp;

			switch (n) {
				case 0:
					if (row - 1 >= 0 && col - 1 >= 0) {
						comp = this.shapes[row - 1][col - 1];
						if (comp != null) {
							bounds[n] = comp;
							comp.getBounds()[n + 4] = shape;
						}
					}
					break;
				case 1:
					if (row - 1 >= 0) {
						comp = this.shapes[row - 1][col];
						if (comp != null) {
							bounds[n] = comp;
							comp.getBounds()[n + 4] = shape;
						}
					}
					break;
				case 2:
					if (row - 1 >= 0 && col + 1 < this.shapes.length) {
						comp = this.shapes[row - 1][col + 1];
						if (comp != null) {
							bounds[n] = comp;
							comp.getBounds()[n + 4] = shape;
						}
					}
					break;
				case 3:
					if (col + 1 < this.shapes.length) {
						comp = this.shapes[row][col + 1];
						if (comp != null) {
							bounds[n] = comp;
							comp.getBounds()[n + 4] = shape;
						}
					}
					break;
				case 4:
					if (row + 1 < this.shapes.length && col + 1 < this.shapes.length) {
						comp = this.shapes[row + 1][col + 1];
						if (comp != null) {
							bounds[n] = comp;
							comp.getBounds()[n - 4] = shape;
						}
					}
					break;
				case 5:
					if (row + 1 < this.shapes.length) {
						comp = this.shapes[row + 1][col];
						if (comp != null) {
							bounds[n] = comp;
							comp.getBounds()[n - 4] = shape;
						}
					}
					break;
				case 6:
					if (row + 1 < this.shapes.length && col - 1 >= 0) {
						comp = this.shapes[row + 1][col - 1];
						if (comp != null) {
							bounds[n] = comp;
							comp.getBounds()[n - 4] = shape;
						}
					}
					break;
				case 7:
					if (col - 1 >= 0) {
						comp = this.shapes[row][col - 1];
						if (comp != null) {
							bounds[n] = comp;
							comp.getBounds()[n - 4] = shape;
						}
					}
					break;
			}

		}

	}

}
