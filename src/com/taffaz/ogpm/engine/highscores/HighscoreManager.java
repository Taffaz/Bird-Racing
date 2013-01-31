package com.taffaz.ogpm.engine.highscores;

import java.util.*;
import java.io.*;

public class HighscoreManager {
	// An arraylist of the type "score" we will use to work with the scores inside the class
	private ArrayList<Score> scores;

	// The name of the file where the highscores will be saved
	private static final String HIGHSCORE_FILE = "scores.dat";

	//Initialising an in and outputStream for working with the file
	ObjectOutputStream outputStream = null;
	ObjectInputStream inputStream = null;

	public HighscoreManager() {
		//initialising the scores-arraylist
		scores = new ArrayList<Score>();
	}

	public ArrayList<Score> getScores() {
		loadScoreFile();
		sort();
		return scores;
	}

	private void sort() {
		Collections.sort(scores);
	}

	public void addScore(String name, int score) {
		loadScoreFile();
		scores.add(new Score(name, score));
		updateScoreFile();
	}

	public void loadScoreFile() {
		try {
			inputStream = new ObjectInputStream(new FileInputStream(HIGHSCORE_FILE));
			scores = (ArrayList<Score>) inputStream.readObject();
		} catch (FileNotFoundException e) {
			System.out.println("FNF Error: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO Error: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("CNF Error: " + e.getMessage());
		} finally {
			try {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
			} catch (IOException e) {
				System.out.println("IO Error: " + e.getMessage());
			}
		}
	}

	public void updateScoreFile() {
		try {
			outputStream = new ObjectOutputStream(new FileOutputStream(HIGHSCORE_FILE));
			outputStream.writeObject(scores);
		} catch (FileNotFoundException e) {
			System.out.println("[Update] FNF Error: " + e.getMessage() + ",the program will try and make a new file");
		} catch (IOException e) {
			System.out.println("[Update] IO Error: " + e.getMessage());
		} finally {
			try {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
			} catch (IOException e) {
				System.out.println("[Update] Error: " + e.getMessage());
			}
		}
	}

	public ArrayList<Score> getHighscores() {
		ArrayList<Score> scores = new ArrayList<Score>();
		ArrayList<Score> tempScores;
		
		int max = 5;
		tempScores = getScores();
		
		if (tempScores.size() > max){
			for(int i = 0; i < 5; i++){
				scores.add(tempScores.get(i));
			}
			return scores;
		} else {
			return tempScores;
		}
		
		
	}

}