package components;

import java.awt.Color;
import java.awt.Graphics;

import com.fasterxml.jackson.annotation.JsonIgnore;

import constants.Constants;
import main.Game;
import shapes.Shape;
import shapes.ShapeTag;

/**
 * Representation of the Grid portion of the Game board
 * <p>
 * Consitst of n {@code Box} in an internal array of boxes
 * 
 * @author Samuel Brenner
 * @version 2.0
 * @since 1.0
 *
 */

public class Grid {

	/**
	 * Global variable representing the width of each Box lying on this Grid
	 */
	public static final int BOX_WIDTH = 3 * Game.HEIGHT / 4 / Constants.GAME_SIZE;

	/**
	 * {@code Color} of this Grid
	 */
	private static final Color COLOR = Color.WHITE;

	/**
	 * Number of {@code Box} objects on this Grid (aka Quads)
	 */
	private static final int QUADS = Constants.GAME_SIZE * Constants.GAME_SIZE;

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
	private int x;

	/**
	 * initial y coordinate for this grid
	 */
	private int y;

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

	/**
	 * The global number of current matches within the process of checking for a
	 * win, once a certain number of {@code Shape} objects are placed onto the
	 * screen
	 */
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

		this.boxes = new Box[Grid.QUADS];

		this.shapes = new Shape[Constants.GAME_SIZE][Constants.GAME_SIZE];

		this.x = x;
		this.y = y;

		this.filledBoxes = 0;

		this.curr_Matches = 0;

		this.state_Init = true;

		this.clearShapes = false;

		this.init();
	}

	/**
	 * Default constructor
	 */
	public Grid() {

	}

	/**
	 * Determines if the state of this Grid is one of a win Only called when the
	 * number of objects on the board is 2 * {@code Main.GAME_SIZE} - 1
	 * 
	 * @return Object array of at least a Boolean, true if the current state of this
	 *         Grid is a win, and an additional {@code ShapeTag} if the state is a
	 *         win
	 */
	@JsonIgnore
	public Object[] checkWin() {

		boolean validated = false;

		for (Shape[] shapes : this.shapes) {
			for (Shape me : shapes) {
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
							this.curr_Matches = 0;
						}
					}
				}
			}
		}

		this.curr_Matches = 0;

		return new Object[] { validated };

	}

	/**
	 * Recursively validates a {@code Shape} object with one of its surrounding
	 * Shapes, going from {@code Shape} to {@code Shape} until either the correct
	 * number of matches is acheived, or the end is reached and false is returned,
	 * or another shape is hit and false is returned
	 * 
	 * @param me   current {@code Shape} object being looked at for further
	 *             comparison
	 * @param next the next {@code Shape} object in the recursive sequence
	 * @param n    the index of the array surrounding the {@code Shape} object to
	 *             continue the recursion on
	 * @return boolean {@code true} if the initial {@code Shape} and its successive
	 *         {@code Shape} objects at a certain node {@code n} form a row, column,
	 *         or diagonal that constitue a win by the right number of {@code Shape}
	 *         objects in a row
	 */
	@JsonIgnore
	private boolean validate(Shape me, Shape next, int n) {
		if (next == null && this.curr_Matches < Constants.NUM_TO_MATCH) {
			return false;
		} else if (this.curr_Matches == Constants.NUM_TO_MATCH) {
			return true;
		} else if (me.getTag() == next.getTag()) {
			this.curr_Matches++;
			return this.validate(next, next.getBounds()[n], n);
		} else {
			return false;
		}
	}

	/**
	 * Getter for internal matrix of {@code Shape} objects
	 * 
	 * @return reference to internal matrix of {@code Shape} objects
	 */
	public Shape[][] getShapes() {
		return shapes;
	}

	/**
	 * Setter for internal matrix of {@code Shape} objects
	 * 
	 * @param shapes new reference to assign to internal matrix
	 */
	public void setShapes(Shape[][] shapes) {
		this.shapes = shapes;
	}

	/**
	 * Getter for internal array of {@code Box} objects
	 * 
	 * @return reference to internal array of {@code Box} objects
	 */
	public Box[] getBoxes() {
		return boxes;
	}

	/**
	 * Setter for internal array of {@code Box} objects
	 * 
	 * @param boxes new reference to assign to internal array
	 */
	public void setBoxes(Box[] boxes) {
		this.boxes = boxes;
	}

	/**
	 * Getter for number of filled boxes on this {@code Grid}
	 * 
	 * @return number of filled boxes on this {@code Grid}
	 */
	public int getFilledBoxes() {
		return filledBoxes;
	}

	/**
	 * Setter for number of filled boxes
	 * 
	 * @param filledBoxes new number of filled boxes
	 */
	public void setFilledBoxes(int filledBoxes) {
		this.filledBoxes = filledBoxes;
	}

	/**
	 * Getter for if this {@code Grid} is in an initial state
	 * 
	 * @return true if this {@code Grid} is initiating
	 */
	public boolean isState_Init() {
		return state_Init;
	}

	/**
	 * Setter for the internal initiation flag
	 * 
	 * @param state_Init true if this {@code Grid} should be put into the
	 *                   initialization state
	 */
	public void setState_Init(boolean state_Init) {
		this.state_Init = state_Init;
	}

	/**
	 * Getter for current {@code Shape} being drawn to the {@code Grid},
	 * {@code null} if there is no shape needing to be drawn to the {@code Grid}
	 * 
	 * @return current {@code Shape} for this {@code Grid}
	 */
	public Shape getCurrent_Shape() {
		return current_Shape;
	}

	/**
	 * Setter for the current {@code Shape} object
	 * 
	 * @param current_Shape new reference for current {@code Shape} for this
	 *                      {@code Grid}
	 */
	public void setCurrent_Shape(Shape current_Shape) {
		this.current_Shape = current_Shape;
	}

	/**
	 * Getter for the internal flag of if this {@code Grid} is/should clearing
	 * shapes
	 * 
	 * @return {@code true} if this {@code Grid} is clearing {@code Shape} objects
	 */
	public boolean isClearShapes() {
		return clearShapes;
	}

	/**
	 * Setter for internal flag of if a clear state should be executed
	 * 
	 * @param clearShapes {@code true} if a clear state should be initialized in
	 *                    this {@code Grid}
	 */
	public void setClearShapes(boolean clearShapes) {
		this.clearShapes = clearShapes;
	}

	/**
	 * Number of current {@code Shape} matches present during win algorithm
	 * execution
	 * 
	 * @return int representing the current number of matches on this {@code Grid}
	 */
	public int getCurr_Matches() {
		return curr_Matches;
	}

	/**
	 * Sets the number of current matches on this {@code Grid}
	 * 
	 * @param curr_Matches
	 */
	public void setCurr_Matches(int curr_Matches) {
		this.curr_Matches = curr_Matches;
	}

	/**
	 * Setter for upper left x coordinate for this {@code Grid}
	 * 
	 * @param x new x value
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Setter for upper left y coordinate for this {@code Grid}
	 * 
	 * @param y new y value
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Getter for upper left x coordinate
	 * 
	 * @return upper left x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Getter for upper left y coordinate
	 * 
	 * @return upper left y coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Based on a size of {@code Shape.BOUNDS_SIZE}, determines the opposite index
	 * of {@code n} which would appear visually on the opposite side of a given
	 * {@code Shape} on the {@code Grid}
	 * 
	 * @param n the index of comparison
	 * @return an int representing a visually flipped indexi
	 */
	@JsonIgnore
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
	@JsonIgnore
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
	@JsonIgnore
	public void draw(Graphics g) {

		g.setColor(Grid.COLOR);

		if (Game.FROM_SAVE_STATE) {

			this.state_Init = false;
			for (int mult = 1; mult < Constants.GAME_SIZE; mult++) {
				g.drawLine(this.x + mult * Grid.BOX_WIDTH, this.y, this.x + mult * Grid.BOX_WIDTH,
						this.y + Constants.GAME_SIZE * Grid.BOX_WIDTH);
				g.drawLine(this.x, this.y + mult * Grid.BOX_WIDTH, this.x + Constants.GAME_SIZE * Grid.BOX_WIDTH,
						this.y + mult * Grid.BOX_WIDTH);
			}

			if (this.shapes != null) {
				for (Shape[] shapes : this.shapes) {
					for (Shape shape : shapes) {
						if (shape != null) {
							shape.setColor(Color.GREEN);
							shape.draw(g);
						}
					}
				}
			}

			Game.FROM_SAVE_STATE = false;
		}

		if (this.current_Shape != null) {
			this.current_Shape.setColor(Color.GREEN);
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
			for (int mult = 1; mult < Constants.GAME_SIZE; mult++) {
				g.drawLine(this.x + mult * Grid.BOX_WIDTH, this.y, this.x + mult * Grid.BOX_WIDTH,
						this.y + Constants.GAME_SIZE * Grid.BOX_WIDTH);
				g.drawLine(this.x, this.y + mult * Grid.BOX_WIDTH, this.x + Constants.GAME_SIZE * Grid.BOX_WIDTH,
						this.y + mult * Grid.BOX_WIDTH);
			}
		}

	}

	/**
	 * Setup for this.boxes
	 */
	@JsonIgnore
	public void init() {

		int workingX = this.x;
		int workingY = this.y;

		if (!Game.FROM_SAVE_STATE) {
			for (int index = 0; index < this.boxes.length; index++) {

				if (index % Constants.GAME_SIZE == 0 && index > 0) {
					workingY += Grid.BOX_WIDTH;
					workingX = this.x;
				}

				this.boxes[index] = new Box(workingX, workingY, Grid.BOX_WIDTH);

				workingX += Grid.BOX_WIDTH;
			}
		}

		if (Game.FROM_SAVE_STATE) {
			for (Shape[] shapes : this.shapes) {
				for (Shape shape : shapes) {
					if (shape != null) {
						this.placeShape(shape.getTag(), shape.getX(), shape.getY());
					}
				}
			}
		}

	}

	/**
	 * Returns true if all the {@code Box} objects in the interal array are filled
	 * with {@code Shape} objects
	 * 
	 * @return true if all the boxes in this Grid are filled with Shapes
	 */
	@JsonIgnore
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
	@JsonIgnore
	public boolean placeShape(ShapeTag tag, int x, int y) {
		for (int index = 0; index < this.boxes.length; index++) {
			Box box = this.boxes[index];

			int[] coordinates = box.boxCoordinates();
			int startX = coordinates[0];
			int startY = coordinates[1];
			int endX = coordinates[2];
			int endY = coordinates[3];

			int relRow = index / Constants.GAME_SIZE;
			int relCol = index % Constants.GAME_SIZE;

			if (x >= startX && x <= endX && y >= startY && y <= endY && this.shapes[relRow][relCol] == null) {

				this.current_Shape = new Shape(startX + Shape.INDENT, startY + Shape.INDENT, tag);

				this.fillRespectiveBounds(relRow, relCol, this.current_Shape);

				this.shapes[relRow][relCol] = this.current_Shape;
				this.filledBoxes++;
				return true;
			}
		}

		return false;
	}

	@JsonIgnore
	private boolean validateIndex(int index) {
		return index >= 0 && index < this.shapes.length;
	}

	/**
	 * Fills the {@code Shape.bounds} array such that all elements around this
	 * {@code Shape} are placed into its bounds array, and it is placed into the
	 * correct location of the other respective {@code Shape} object's arrays
	 * 
	 * @param row   row of the current shape in this internal array of {@code Shape}
	 *              objects
	 * @param col   column of the current shape in this internal array of
	 *              {@code Shape} objects
	 * @param shape {@code Shape} object for comparison
	 */
	@JsonIgnore
	private void fillRespectiveBounds(int row, int col, Shape shape) {

		Shape[] bounds = shape.getBounds();

		int[] rowOffset = { -1, -1, -1, 0, 1, 1, 1, 0 };
		int[] colOffset = { -1, 0, 1, 1, 1, 0, -1, -1 };

		for (int n = 0; n < Shape.BOUNDS_SIZE; n++) {

			Shape comp;

			int tempRow = row + rowOffset[n];
			int tempCol = col + colOffset[n];
			if (this.validateIndex(tempRow) && this.validateIndex(tempCol)) {
				comp = this.shapes[tempRow][tempCol];
				if (comp != null) {
					bounds[n] = comp;
					comp.getBounds()[this.oppositeN(n)] = shape;
				}
			}

		}

	}

}
