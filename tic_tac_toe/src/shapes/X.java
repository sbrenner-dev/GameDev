package shapes;

import java.awt.Graphics;

import components.Grid;

/**
 * Class representing X shape and properties
 * 
 * @author Samuel Brenner
 * @version 1.0
 *
 */

public class X extends Shape {

	/**
	 * Constructor
	 * 
	 * @param x respective x coordinate
	 * @param y respective y coordinate
	 */
	public X(int x, int y) {
		super(x, y, ShapeTag.SHAPE_X);
	}

	/**
	 * Provides implementation to draw to the Game JPanel
	 * 
	 * @param g Graphics component in order to draw to panel
	 */
	@Override
	public void draw(Graphics g) {

		int width = Grid.BOX_WIDTH - 2 * Shape.INDENT;

		g.drawLine(this.x, this.y, this.x + width, this.y + width);
		g.drawLine(this.x, this.y + width, this.x + width, this.y);

	}

}
