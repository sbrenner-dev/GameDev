package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import components.Grid;
import components.HUD;
import components.Player;
import constants.Constants;
import io.GameSaver;
import shapes.ShapeTag;

/**
 * Main Game instance for running the game
 * 
 * @author Samuel Brenner
 * @version 1.0
 */

public class Game extends JFrame {

	/**
	 * Title of the game to launch
	 */
	private String name;

	public static boolean FROM_SAVE_STATE = false;

	/**
	 * Random generated serialVersionUID for this JFrame
	 */
	private static final long serialVersionUID = -7071532049979466544L;

	/**
	 * Width for this Game
	 */
	public static final int WIDTH = 1920;

	/**
	 * Height for this Game
	 */
	public static final int HEIGHT = 1080;

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
	 * Player O
	 */
	private Player pO;

	/**
	 * Player actively making a move
	 */
	private Player active_Player;

	/**
	 * Field representing if the state if the JFrame has been changed
	 * <p>
	 * Used to optimize render time and overall performance
	 * <p>
	 * In this case, the JFrame will not have to re-draw every time it refreshes
	 * <p>
	 * Only if it is changed
	 */
	private boolean state_Changed;

	/**
	 * HUD object for this Game
	 */
	private HUD hud;

	/**
	 * Internal flag for the state of the game being a won state
	 */
	private boolean state_Won;

	/**
	 * Internal flag for if the state of the game being initialized
	 */
	private boolean state_Init;

	/**
	 * Inner class that can be used on a game thread
	 * <p>
	 * Contains the Game loop
	 * 
	 * @author Samuel Brenner
	 *
	 */
	private class GameRunner implements Runnable {

		/**
		 * Sleep time for proper rendering and visualization of the Game
		 */
		private static final int REFRESH_TIME = 1;

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
	 * Inner class that contains listeners for user mouse input
	 * 
	 * @author Samuel Brenner
	 *
	 */
	private class UserMouseInput extends MouseAdapter {

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
		 * Sets {@code mouse_Moved} value to true
		 * 
		 * @param e MouseEvent object
		 */
		@Override
		public void mouseDragged(MouseEvent e) {
			this.mouse_Pressed = false;
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
					Game.this.state_Changed = true;
					Game.this.active_Player = Game.this.active_Player == Game.this.pX ? Game.this.pO : Game.this.pX;
					Game.this.hud.changeTag(Game.this.active_Player.getShapeTypeAsTag());
					if (Game.this.name != null && !Game.this.name.endsWith("*")) {
						Game.this.name += "*";
						Game.this.setTitle(Game.this.name);
					}
				}

				if (Game.this.game_Grid.getFilledBoxes() >= 2 * Constants.NUM_TO_MATCH + 1) {
					Object[] out = Game.this.game_Grid.checkWin();
					if ((boolean) out[0]) {

						Player winner = null;

						if ((ShapeTag) out[1] == ShapeTag.SHAPE_O) {
							winner = Game.this.pO;
							winner.incrementWins();
						} else {
							winner = Game.this.pX;
							winner.incrementWins();
						}

						Game.this.active_Player = winner;

						Game.this.hud.changeTag(winner.getShapeTypeAsTag());

						Game.this.state_Won = true;
					}
				}
			}
		}

	}

	/**
	 * Constructor for this game
	 * 
	 * @param title Title of Game
	 */
	public Game() {
		this.setTitle(this.name);

		this.state_Changed = true;
		this.state_Won = false;
		this.state_Init = true;

		this.game_Grid = new Grid((int) (0.1 * Game.WIDTH), (int) (0.1 * Game.HEIGHT));

		this.pX = new Player(ShapeTag.SHAPE_X);
		this.pO = new Player(ShapeTag.SHAPE_O);
		this.active_Player = new Random().nextInt(2) == 1 ? this.pX : this.pO;

		this.hud = new HUD(this.pX, this.pO, this.active_Player.getShapeTypeAsTag());

		this.init();

	}

	public Game(Grid grid, Player pX, Player pO, Player active, String name) {

		this.name = name;
		this.setTitle(this.name);

		Game.FROM_SAVE_STATE = true;

		this.game_Grid = grid;
		this.game_Grid.init();

		this.pX = pX;
		this.pO = pO;
		this.active_Player = active.getShapeTypeAsTag() == ShapeTag.SHAPE_O ? this.pO : this.pX;

		this.hud = new HUD(this.pX, this.pO, this.active_Player.getShapeTypeAsTag());

		this.state_Init = true;

		this.init();
	}

	/**
	 * Initializes specifications and fields for this Game
	 */
	public void init() {
		this.setSize(new Dimension(Game.WIDTH, Game.HEIGHT));

		UserMouseInput umi = new UserMouseInput();
		this.addMouseListener(umi);
		this.addMouseMotionListener(umi);

		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_S) {
					String name = Game.this.name;
					if (Game.this.name == null) {
						name = JOptionPane.showInputDialog("Enter save name: ");
						Game.this.name = name;
						Game.this.setTitle(name);
					} else {
						Game.this.setTitle(name = name.substring(0, name.length() - 1));
						Game.this.name = name;
					}
					GameSaver.saveGame(Game.this.game_Grid, Game.this.pX, Game.this.pO, Game.this.active_Player, name);
				}
			}
		});

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Game.this.setDefaultCloseOperation(EXIT_ON_CLOSE);
				if (Game.this.name == null || (Game.this.name != null && Game.this.name.endsWith("*"))) {
					int choice = JOptionPane.showOptionDialog(Game.this.getComponent(0), "Save Game?",
							"Warning: Game Not Saved", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
							null, null, null);
					if (choice == 0) {
						Game.this.getKeyListeners()[0]
								.keyReleased(new KeyEvent(Game.this.getComponent(0), 0, 0, 0, KeyEvent.VK_S, 'S'));
					} else if (choice == 2) {
						Game.this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
					}
				}
			}

		});

		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);

		this.game_Thread = new Thread(new GameRunner());
		this.game_Thread.start();
	}

	public void paint(Graphics g) {
		if (Game.this.state_Init) {
			Game.this.state_Init = false;
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
			Game.this.game_Grid.draw(g);
			Game.this.hud.draw(g);
		}

		if (Game.this.state_Changed) {
			Game.this.game_Grid.draw(g);
			Game.this.hud.draw(g);
			Game.this.state_Changed = false;
		}

		if (Game.this.game_Grid.isFilled() || Game.this.state_Won) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			Game.this.game_Grid.clear();
			Game.this.state_Won = false;
			Game.this.state_Changed = true;
		}
	}

}
