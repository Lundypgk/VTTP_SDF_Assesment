// package vttp.batch5.sdf.task02;

import java.io.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.lang.*;
import java.nio.*;

public class Main {
	 static final char X = 'X';
    static final char O = 'O';
    static final char E = '.';


	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			System.out.println("please provide a board");
			return;
		}
		// read board config
		char[][] board = getBoard(args[0]);
		printBoard(board);
		//get empty pos
		ArrayList<int[]> storage = getEmptyCords(board);
		storage.forEach(e -> {
				System.out.println(e[0] +" , " + e[1]);
			});

	}

	public static ArrayList<int[]> getEmptyCords(char[][] board) {
		ArrayList<int[]> storage = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == '.') {
					int[] cords = new int[2];
					cords[0]= i;
					cords[1] = j;
					storage.add(cords);
				}
			}
		}
		return storage;
	}

	public static char[][] getBoard(String fileloc) {
		try {
			File incoming = new File(fileloc);
			FileInputStream fis = new FileInputStream(incoming);
			BufferedInputStream bis = new BufferedInputStream(fis);
			StringBuilder sb = new StringBuilder();
			int ch;
			char[][] board = new char[3][3];
			int i =0;
			int j = 0;
			while ((ch = bis.read()) != -1) {
				if (ch == '\n') {
					i++;
					j = 0;
					continue;
				}
				board[i][j] = (char) ch;
				j++;
			}
			printBoard(board);
			bis.close();
			return board;
		} catch (IOException e) {
			System.out.println("No Such file exists!");
			// e.printStackTrace();
			return null;
		}
	}

	 public static void printBoard(char[][] board) {
        for(int j =0; j < 3; j++){
            for (int i = 0; i < 3; i++) {
                System.out.print(board[j][i]);
                if (i<2) {
                    System.out.print("|");
                }
            }
            System.out.println("");
            if (j<2) {
            System.out.println("------");
            }
        }
        System.out.println("");
    }

	public static int minMax(boolean isMax, char[][] board) {
        int currentScore = evaluateBoard(board);
        if (currentScore == 1 || currentScore == -1) return currentScore;
        if (finished(board)) return 0;

        if (isMax) {
            int bestScore = Integer.MIN_VALUE;
            for (int i =0; i < 3; i++) {
                for (int j =0 ; j < 3; j++) {
                    if (board[i][j] == E) {
                        board[i][j] = X;
                        bestScore = Math.max(bestScore, minMax(false, board));
                        board[i][j] = E;
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i =0; i < 3; i++) {
                for (int j =0 ; j < 3; j++) {
                    if (board[i][j] == E) {
                        board[i][j] = O;
                        bestScore = Math.min(bestScore, minMax(true, board));
                        board[i][j] = E;
                    }
                }
            }
            return bestScore;
        }
    }


    public static boolean finished(char[][] board) {
       return evaluateBoard(board) != 0 || isBoardFull(board);
    }
    public static boolean isBoardFull(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j=0; j<3; j++) {
                if (board[i][j] == E) return false;
            }
        }
        return true;
    }
    public static int evaluateBoard(char[][] board) {
        //check horizontal and vert
        for (int i =0; i < 3; i ++) {
            if ((board[i][0] == X) && (board[i][1] == X) && (board[i][2] == X))  return 1;
            if ((board[i][0] == O) && (board[i][1] == O) && (board[i][2] == O))  return -1;
            if ((board[0][i] == X) && (board[1][i] == X) && (board[2][i] == X))  return 1;
            if ((board[0][i] == O) && (board[1][i] == O) && (board[2][i] == O))  return -1;
        }

        // check diagonals
        if (board[0][0] == X && board[1][1] == X && board[2][2] == X) return +1;
        if (board[0][2] == X && board[1][1] == X && board[2][0] == X) return +1;
        if (board[0][0] == O && board[1][1] == O && board[2][2] == O) return -1;
        if (board[0][2] == O && board[1][1] == O && board[2][0] == O) return -1;

        return 0;
    }
}
