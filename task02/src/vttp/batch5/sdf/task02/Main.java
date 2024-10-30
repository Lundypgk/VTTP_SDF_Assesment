package vttp.batch5.sdf.task02;

import java.io.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

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
        // get empty pos
        System.out.println("------------------------");
        ArrayList<int[]> storage = getEmptyCords(board);
        for (int[] corrds : storage) {
            System.out.println("y="+ corrds[0] + ", x=" + corrds[1] + ", utility=" + evaluateMove(board, corrds));
        }

    }

    public static int evaluateMove(char[][] toClone, int[] coords) {
        char[][] board = Arrays.stream(toClone)
            .map(line -> Arrays.copyOf(line, line.length))
            .toArray(char[][]::new);
        board[coords[0]][coords[1]] = X;
        int gotWin = -99;
        int gotLose = 99;

        for (int i = 0; i < 3; i++) {
            int rowScore = evaluateLine(board[i][0], board[i][1], board[i][2]);
            int colScore = evaluateLine(board[0][i], board[1][i], board[2][i]);
            gotWin = Math.max(Math.max(rowScore, colScore), gotWin);
            gotLose = Math.min(Math.min(rowScore, colScore), gotLose);
        }

        int diag1Score = evaluateLine(board[0][0], board[1][1], board[2][2]);
        int diag2Score = evaluateLine(board[0][2], board[1][1], board[2][0]);

        gotWin = Math.max(Math.max(gotWin,diag1Score),diag2Score);
        gotLose = Math.min(Math.min(gotLose,diag1Score),diag2Score);
        //as long as one wins

        if (gotWin == 1) return 1;
        else if (gotLose == -1) return -1;
        else return 0;
    }

    // Helper function to evaluate a line of three cells
    private static int evaluateLine(char a, char b, char c) {
        if (a == X && b == X && c == X) {
            return 1;
        } else if ((a == O && b == O && c == E) ||
                   (a == O && c == O && b == E) ||
                   (b == O && c == O && a == E)) {
            return -1;
        } else return 0;
    }

    public static char[][] getBoard(String fileloc) {
        try {
            File incoming = new File(fileloc);
            FileInputStream fis = new FileInputStream(incoming);
            BufferedInputStream bis = new BufferedInputStream(fis);
            StringBuilder sb = new StringBuilder();
            int ch;
            char[][] board = new char[3][3];
            int i = 0;
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
            bis.close();
            return board;
        } catch (IOException e) {
            System.out.println("No Such file exists!");
            // e.printStackTrace();
            return null;
        }
    }

    public static  void printBoard(char[][] board) {
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                System.out.print(board[j][i]);
                if (i < 2) {
                    // System.out.print("|");
                }
            }
            System.out.println("");
            if (j < 2) {
                // System.out.println("------");
            }
        }
        System.out.println("");
    }

    public static ArrayList<int[]> getEmptyCords(char[][] board) {
        ArrayList<int[]> returnList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j =0 ; j < 3; j++) {
                if (board[i][j] == E) {
                    int[] toAdd = {i,j};
                    returnList.add(toAdd);
                }
            }
        }
        return returnList;
    }
}
