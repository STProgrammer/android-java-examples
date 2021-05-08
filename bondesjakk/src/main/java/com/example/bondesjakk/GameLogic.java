package com.example.bondesjakk;

public class GameLogic {
    public int[][] board;
    private int winner = -1;
    private int rows;
    private int cols;

    GameLogic(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        board = new int[cols][rows];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                board[i][j] = 0;
            }
        }
    }

    public int checkColumns() {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows - 2; j++) {
                if (board[i][j] == board[i][j+1] && board[i][j+1] == board[i][j+2] && board[i][j] != 0) {
                    return board[i][j];
            }

            }
        }
        return -1;
    }

    public int checkRows() {
        for (int i = 0; i < cols - 2; i++) {
            for (int j = 0; j < rows; j++) {
                if (board[i][j] == board[i + 1][j] && board[i + 1][j] == board[i + 2][j] && board[i][j] != 0) {
                    return board[i][j];
                }
            }
        }
        return -1;
    }

    public int checkDiagonals() {
        for (int i = 0; i < cols - 2; i++) {
            for (int j = 0; j < rows - 2; j++) {
                if (board[i][j] == board[i + 1][j + 1] && board[i + 1][j + 1] == board[i + 2][j + 2] && board[i][j] != 0) {
                    return board[i][j];
                }
            }
        }

        for (int i = 0; i < cols - 2; i++) {
            for (int j = 2; j < rows; j++) {
                if (board[i][j] == board[i + 1][j - 1] && board[i + 1][j - 1] == board[i + 2][j - 2] && board[i][j] != 0) {
                    return board[i][j];
                }
            }
        }
        return -1;
    }

    public int checkWinner() {
        winner = checkDiagonals();
        if (winner != -1) return winner;
        winner = checkColumns();
        if (winner != -1) return winner;
        winner = checkRows();
        return winner;
    }


    //For å gjenta spillet
    public void removeBoard() {
        board = new int[cols][rows];
    }

    public void makeMove(int player, int column, int row) {
        board[column][row] = player;
    }

    //Disse tre under brukes i onSaveState og onRestoreInstanceState og onSaveState for å gjenopprette spillet når skjermen roterer
    public int getPlayer(int column, int row) {
        return board[column][row];
    }

    public void setCol(int[] col, int index) {
        board[index] = col;
    }

    public int[] getCol(int index) {
        return board[index];
    }




}
