package com.example.lab4;

public class Game {
	public static int EmptyCell = 0;
	public static int playerOne = 1;
	public static int playerTwo = 2;
	private int currentTurn;
	private int[][] board = new int[3][3];
	private static Game gameInstance;

	private Game() {
	}

	public static Game getInstance() {
		if (gameInstance == null)
			gameInstance = new Game();
		return gameInstance;
	}

	public void init() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				board[i][j] = 0;
	}

	public void play(int i, int j) {
		board[i][j] = currentTurn;
		nextPlayer();
	}

	private void nextPlayer() {
		if (currentTurn == 1)
			currentTurn = 2;
		else
			currentTurn = 1;
	}

	public boolean isFree(int i, int j) {
		return board[i][j] == 0;
	}

	public int getPlayer(int i, int j) {
		return board[i][j];
	}

	public char getTurn() {
		return currentTurn == 2 ? 'O' : 'X';
	}

	public boolean isTie() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (board[i][j] == 0)
					return false;
		return true;
	}

	// returns playerOne or playerTwo , if no winner returns 0
	public int winner() {
		// rows
		for (int i = 0; i < 3; i++) {
			int x = board[i][0];
			boolean ok = true;
			for (int j = 1; j < 3; j++)
				if (x != board[i][j])
					ok = false;
			System.out.println("row: " + (ok ? x : 0));
			if (ok && x > 0)
				return x;
		}

		// cols
		for (int i = 0; i < 3; i++) {
			int x = board[0][i];
			boolean ok = true;
			for (int j = 1; j < 3; j++)
				if (x != board[j][i])
					ok = false;
			if (ok && x > 0)
				return x;
		}
		// diags
		boolean ok = true;
		for (int i = 0; i < 3; i++)
			if (board[0][0] != board[i][i])
				ok = false;
		if (ok && board[0][0] > 0)
			return board[0][0];
		ok = true;
		for (int i = 0; i < 3; i++)
			if (board[0][2] != board[i][2 - i])
				ok = false;
		if (ok)
			return board[0][2];

		return 0;
	}
}
