package components;

import java.awt.Color;
import java.awt.Graphics;

import main.Game;
import shapes.ShapeTag;

public class HUD {

	private static final Color COLOR = Color.WHITE;

	private Player p1;
	private Player p2;

	private String active;

	public HUD(Player p1, Player p2, ShapeTag tag) {
		this.p1 = p1;
		this.p2 = p2;

		this.changeTag(tag);
	}

	public void draw(Graphics g) {
		g.setColor(HUD.COLOR);
		g.drawString("Active Player: " + this.active, (3 * Game.WIDTH) / 4, Game.HEIGHT / 2);
		g.drawString("\nPlayer X Wins: " + this.p1.getWins(), (3 * Game.WIDTH) / 4, Game.HEIGHT / 2 + 15);
		g.drawString("\nPlayer O Wins: " + this.p2.getWins(), (3 * Game.WIDTH) / 4, Game.HEIGHT / 2 + 30);
	}

	public void changeTag(ShapeTag tag) {
		this.active = tag == ShapeTag.SHAPE_O ? "O" : "X";
	}

}
