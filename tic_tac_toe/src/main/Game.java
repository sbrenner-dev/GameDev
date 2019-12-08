package main;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import components.Grid;
import components.Player;
import shapes.ShapeTag;

/**
 * Main Game instance for running the game
 * 
 * @author Samuel Brenner
 * @version 1.0
 */

public class Game extends JFrame {

	/**
	 * Random generated serialVersionUID for this JFrame
	 */
	private static final long serialVersionUID = -7071532049979466544L;

	/**
	 * Width for this Game
	 */
	public static final int WIDTH = 500;

	/**
	 * Height for this Game
	 */
	public static final int HEIGHT = 400;

	/**
	 * JPanel that has listeners as components
	 */
	private JPanel game_Panel;

	/**
	 * Thread to run game on
	 */
	private Thread game_Thread;

	/**
	 * Grid that contains runtime game components
	 */
	private Grid game_Grid;

	/**
	 * Player X
	 */
	private Player pX;

	/**
	 * Player Y
	 */
	private Player pY;

	/**
	 * Player actively making a move
	 */
	private Player active_Player;

	/**
	 * Constructor for this game
	 * 
	 * @param title Title of Game
	 */
	public Game(String title) {
		super(title);

		this.init();

	}

	/**
	 * Initializes specifications and fields for this Game
	 */
	public void init() {
		this.game_Panel = new JPanel();

		UserMouseInput umi = new UserMouseInput();
		this.game_Panel.addMouseListener(umi);
		this.game_Panel.addMouseMotionListener(umi);

		this.add(this.game_Panel);

		this.setSize(new Dimension(Game.WIDTH, Game.HEIGHT));
		this.setVisible(true);

		this.game_Grid = new Grid((int) (Game.WIDTH * 0.1), (int) (Game.HEIGHT * 0.1));

		this.pX = new Player(ShapeTag.SHAPE_X);
		this.pY = new Player(ShapeTag.SHAPE_O);
		this.active_Player = new Random().nextInt(2) == 1 ? pX : pY;

		this.game_Thread = new Thread(new GameRunner());
		this.game_Thread.start();
	}

	/**
	 * Draws to this JFrame
	 * 
	 * @param g Graphics object to draw to this
	 */
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		this.game_Grid.draw(g);
		if (this.game_Grid.isFilled()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			this.game_Grid.clear();
		}
	}

	/**
	 * Inner class that can be used on a game thread
	 * <p>
	 * Contains the Game loop
	 * 
	 * @author Samuel Brennero
	 *
	 */
	private class GameRunner implements Runnable {

		/**
		 * Sleep time for proper rendering and visualization of the Game
		 */
		private static final int REFRESH_TIME = 100;

		/**
		 * Method that runs the game loop on the thread
		 */
		@Override
		public void run() {

			while (true) {
				try {
					Game.this.repaint();
					Thread.sleep(GameRunner.REFRESH_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

	/**
	 * Inner class that contains listeners for user mouse inputi
	 * 
	 * @author Samuel Brenner
	 *
	 */
	private class UserMouseInput implements MouseListener, MouseMotionListener {

		/**
		 * Field representing if the mouse has been pressed
		 */
		private boolean mouse_Pressed;

		/**
		 * Field representing if the mouse has been moved
		 */
		private boolean mouse_Moved;

		/**
		 * Default constructor
		 */
		private UserMouseInput() {
			this.mouse_Pressed = false;
			this.mouse_Moved = false;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		/**
		 * Sets {@code mouse_Pressed} value to true
		 * 
		 * @param e MouseEvent object
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			this.mouse_Pressed = true;
		}

		/**
		 * Adds Shape object, respective to {@code Game.this.active_Player}
		 * 
		 * @param e MouseEvent object
		 */
		@Override
		public void mouseReleased(MouseEvent e) {
			if (this.mouse_Pressed && this.mouse_Moved) {
				this.mouse_Moved = false;
				this.mouse_Pressed = false;
			} else if (this.mouse_Pressed && !this.mouse_Moved) {

				if (Game.this.game_Grid.isFilled()) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
					Game.this.game_Grid.clear();
				}

				/*
				 * Get x and y coordinates Deal with input
				 */

				int x = e.getX();
				int y = e.getY();

				if (Game.this.game_Grid.placeShape(Game.this.active_Player.getShapeTypeAsTag(), x, y)) {
					Game.this.active_Player = Game.this.active_Player == Game.this.pX ? pY : pX;
				}

				if (Game.this.game_Grid.checkWin()) {

				}
			}
		}

		/**
		 * Sets {@code mouse_Moved} value to true
		 * 
		 * @param e MouseEvent object
		 */
		@Override
		public void mouseDragged(MouseEvent e) {
			this.mouse_Pressed = false;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}

	}

}
