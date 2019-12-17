package main;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import constants.Constants;
import io.GameLoader;

/**
 * 
 * @author Samuel Brenner
 * @version 1.1
 *
 */

public class LoadScreen extends JFrame {

	/**
	 * Randomly generated serialVersionUID
	 */
	private static final long serialVersionUID = 196991606721642137L;

	/**
	 * Title name for this {@code LoadScreen}
	 */
	private static final String SCREEN_NAME = "Home";

	/**
	 * Width for this {@code LoadScreen}
	 */
	public static final int WIDTH = 300;

	/**
	 * Height for this {@code LoadScreen}
	 */
	public static final int HEIGHT = 100;

	/**
	 * {@code Button} used to send input information to main game
	 */
	private Button submit_Button;

	/**
	 * {@code TextField} that takes in the size for the {@code Grid} of the
	 * {@code Game}
	 */
	private TextField size_TField;

	/**
	 * {@code TextField} that takes in the number of shapes in a row on the
	 * {@code Grid} constitutes a win
	 */
	private TextField win_TField;

	/**
	 * {@code JPanel} on which to place the components
	 */
	private JPanel panel;

	private Button load_Button;

	/**
	 * Default constructor for this {@code LoadScreen}
	 */
	public LoadScreen() {
		super(LoadScreen.SCREEN_NAME);

		this.init();
	}

	/**
	 * Initializes the member variables for this {@code LoadScreen}
	 */
	private void init() {
		this.setSize(new Dimension(LoadScreen.WIDTH, LoadScreen.HEIGHT));

		this.panel = new JPanel() {

			/**
			 * Inner class randomly generated serialVersionUID
			 */
			private static final long serialVersionUID = -2289768367456128276L;

			@Override
			public void paint(Graphics g) {
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, LoadScreen.WIDTH, LoadScreen.HEIGHT);
			}

		};

		this.add(this.panel);

		this.size_TField = new TextField("Size...", 2);
		this.size_TField.addKeyListener(new TextFieldEnterOption());
		this.size_TField.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				TextField temp = LoadScreen.this.size_TField;

				temp.setText(null);
				temp.getKeyListeners()[0].keyReleased(new KeyEvent(LoadScreen.this.size_TField, 0, e.getWhen(), 0,
						KeyEvent.VK_UNDEFINED, KeyEvent.CHAR_UNDEFINED));
			}

		});
		this.size_TField.setFocusTraversalKeysEnabled(false);
		this.panel.add(this.size_TField);

		this.win_TField = new TextField("Number to win...", 10);
		this.win_TField.addKeyListener(new TextFieldEnterOption());
		this.win_TField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				LoadScreen.this.win_TField.selectAll();
			}
		});
		this.panel.add(this.win_TField);

		this.submit_Button = new Button("Start!");
		this.submit_Button.setPreferredSize(new Dimension(100, 20));
		this.submit_Button.addActionListener((e) -> {
			int size = 3;
			int match = 3;
			try {
				size = Integer.parseInt(this.size_TField.getText());
				match = Integer.parseInt(this.win_TField.getText());
				if (match < 3 || match > size || size < 3 || size > 3 * Game.HEIGHT / 4) {
					throw new NumberFormatException();
				}
			} catch (NumberFormatException ex) {
				size = 3;
				match = 3;
			} finally {
				Constants.GAME_SIZE = size;
				Constants.NUM_TO_MATCH = match - 1;
				this.dispose();
				new Game();
			}
		});
		this.panel.add(this.submit_Button);

		this.load_Button = new Button("Load Game");
		this.load_Button.setPreferredSize(new Dimension(100, 20));
		this.load_Button.addActionListener((e) -> {
			String gameName = JOptionPane.showInputDialog("Game Name: ");
			boolean loaded = GameLoader.loadGame(this, gameName);
			if (loaded) {
				this.dispose();
			}
		});
		this.panel.add(this.load_Button);

		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setFocusable(true);
		this.requestFocus();

	}

	/**
	 * Paints onto the internal {@code JPanel} of this {@code JFrame}
	 * 
	 * @param g {@code Graphics} component for this {@code LoadScreen}
	 */
	@Override
	public void paint(Graphics g) {
		this.panel.paint(g);
	}

	/**
	 * Private inner class that handles similar keyboard input between the two
	 * {@code TextField} members of this {@code LoadScreen} instance
	 * 
	 * @author Samuel Brenner
	 * @version 1.1
	 *
	 */
	private class TextFieldEnterOption extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {

			/*
			 * Only executed if the source of this action event is the size_TField TextField
			 * in this LoadScreen
			 */
			if (e.getSource().equals(LoadScreen.this.size_TField)) {
				LoadScreen.this.win_TField.setText(LoadScreen.this.size_TField.getText());
			}

			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				// manually trigger button press
				LoadScreen.this.submit_Button.getActionListeners()[0]
						.actionPerformed(new ActionEvent(this, 0, "Start"));
			} else if (e.getKeyCode() == KeyEvent.VK_TAB && e.getSource().equals(LoadScreen.this.size_TField)) {

				LoadScreen.this.size_TField.setText(LoadScreen.this.size_TField.getText().trim());

				TextField temp = LoadScreen.this.win_TField;

				temp.setFocusable(true);
				temp.requestFocus();
				temp.setText(temp.getText().trim());
				temp.selectAll();
			}
		}

	}

}
