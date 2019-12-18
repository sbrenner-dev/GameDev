package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JOptionPane;

import com.fasterxml.jackson.databind.ObjectMapper;

import components.Grid;
import components.Player;
import constants.Constants;
import constants.ConstantsWrapper;
import main.Game;
import main.LoadScreen;

public class GameLoader {

	public static boolean loadGame(LoadScreen loader, String gameName) {

		ObjectMapper mapper = new ObjectMapper();

		String pathName = "saved\\" + gameName;
		
		File gridFile = new File(pathName + "\\grid.txt");

		if (!gridFile.exists()) {
			JOptionPane.showMessageDialog(loader, "Game name does not exist");
			return false;
		}

		File pXFile = new File(pathName + "\\pX.txt");

		File pOFile = new File(pathName + "\\pO.txt");

		File activeFile = new File(pathName + "\\active.txt");

		File constantsFile = new File(pathName + "\\constants.txt");

		try {

			ConstantsWrapper cwrap = mapper.readValue(constantsFile, ConstantsWrapper.class);

			Constants.GAME_SIZE = cwrap.getSize();
			Constants.NUM_TO_MATCH = cwrap.getNum();

			Grid grid = mapper.readValue(gridFile, Grid.class);

			Player pX = mapper.readValue(pXFile, Player.class);
			Player pO = mapper.readValue(pOFile, Player.class);
			Player active = mapper.readValue(activeFile, Player.class);

			new Game(grid, pX, pO, active, gameName);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;

	}

}
