package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import org.codehaus.jackson.map.ObjectMapper;

import components.Grid;
import components.Player;
import constants.Constants;
import constants.ConstantsWrapper;

public class GameSaver {

	public static void saveGame(Grid grid, Player pX, Player pO, Player active, String gameName) {

		ObjectMapper mapper = new ObjectMapper();

		String pathName = "saved\\" + gameName;

		File mainDir = new File(pathName);

		makeDir(mainDir);

		File gridFile = new File(pathName + "\\grid.txt");

		File pXFile = new File(pathName + "\\pX.txt");

		File pOFile = new File(pathName + "\\pO.txt");

		File activeFile = new File(pathName + "\\active.txt");

		File constantsFile = new File(pathName + "\\constants.txt");

		try {
			mapper.writeValue(gridFile, grid);
			mapper.writeValue(pXFile, pX);
			mapper.writeValue(pOFile, pO);
			mapper.writeValue(activeFile, active);
			mapper.writeValue(constantsFile, new ConstantsWrapper(Constants.GAME_SIZE, Constants.NUM_TO_MATCH));
		} catch (IOException e) {
			try {
				e.printStackTrace(new PrintStream(new File("saveBug.txt")));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
		}

	}

	private static void makeDir(File f) {
		if (!f.exists()) {
			f.mkdir();
		}
	}

}
