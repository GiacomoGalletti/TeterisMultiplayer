package BlockWar.Logic;

import BlockWar.Net.Client;

import java.util.ArrayList;

public class BoardManager {

    //private final Client myClient;

    private int[][] completeBoard;

    public BoardManager(){
        int[][] blackSpaceMatrix = {
                {9, 9, 9, 9, 9},
                {9, 9, 9, 9, 9},
                {9, 9, 9, 9, 9},
                {9, 9, 9, 9, 9},
                {9, 9, 9, 9, 9},
                {9, 9, 9, 9, 9},
                {9, 9, 9, 9, 9},
                {9, 9, 9, 9, 9},
                {9, 9, 9, 9, 9},
                {9, 9, 9, 9, 9},
                {9, 9, 9, 9, 9},
                {9, 9, 9, 9, 9},
                {9, 9, 9, 9, 9},
                {9, 9, 9, 9, 9},
                {9, 9, 9, 9, 9},
                {9, 9, 9, 9, 9},
                {9, 9, 9, 9, 9},
                {9, 9, 9, 9, 9},
                {9, 9, 9, 9, 9},
                {9, 9, 9, 9, 9},
                {9, 9, 9, 9, 9},
                {9, 9, 9, 9, 9},
                {9, 9, 9, 9, 9},
                {9, 9, 9, 9, 9},
                {9, 9, 9, 9, 9}
        };
        this.completeBoard = blackSpaceMatrix;
        //this.myClient = myClient;
        for (Players p: Client.receiver.getPlayersList()) {
           completeBoard = mergeMatrix(completeBoard,p.getBoard());
           completeBoard = mergeMatrix(completeBoard, blackSpaceMatrix);
        }

    }

    public void updateMyBoard() {
        switch (Client.myPlayer.getId()) {
            case 1 -> {
                int offset = 5;
                for (int r = 0; r < 24; r++) {
                    for (int c = 1; c < 13; c++) {
                        completeBoard[r][c+offset] = Client.myPlayer.getPoint(r,c);
                    }
                }
            }
            case 2 -> {
                int offset = 24;
                for (int r = 0; r < 24; r++) {
                    for (int c = 1; c < 13; c++) {
                        completeBoard[r][c + offset] = Client.myPlayer.getPoint(r, c);
                    }
                }
            }
            case 3 -> {
                int offset = 43;
                for (int r = 0; r < 24; r++) {
                    for (int c = 1; c < 13; c++) {
                        completeBoard[r][c + offset] = Client.myPlayer.getPoint(r, c);
                    }
                }
            }
            case 4 -> {
                int offset = 62;
                for (int r = 0; r < 24; r++) {
                    for (int c = 1; c < 13; c++) {
                        completeBoard[r][c + offset] = Client.myPlayer.getPoint(r, c);
                    }
                }
            }
            default ->{ throw new IllegalStateException("Unexpected value: " + "Player "
                    + Client.myPlayer.getId() + " at position " + Client.myPlayer);
            }
        }
    }

    public synchronized void upDateBoard(){
            ArrayList<Players> playersList = Client.receiver.getPlayersList();
            int myID = Client.myPlayer.getId();
            for (int i = 0; i<playersList.size(); i++) {
                if (playersList.get(i).getId() != myID) {
                    switch (playersList.get(i).getId()) {
                        case 1 -> {
                            int offset = 5;
                            for (int r = 0; r < 24; r++) {
                                for (int c = 1; c < 13; c++) {
                                    completeBoard[r][c+offset] = playersList.get(i).getPoint(r,c);
                                }
                            }
                        }
                        case 2 -> {
                            int offset = 24;
                            for (int r = 0; r < 24; r++) {
                                for (int c = 1; c < 13; c++) {
                                    completeBoard[r][c + offset] = playersList.get(i).getPoint(r, c);
                                }
                            }
                        }

                        case 3 -> {
                            int offset = 43;
                            for (int r = 0; r < 24; r++) {
                                for (int c = 1; c < 13; c++) {
                                    completeBoard[r][c + offset] = playersList.get(i).getPoint(r, c);
                                }
                            }
                        }

                        case 4 -> {
                            int offset = 62;
                            for (int r = 0; r < 24; r++) {
                                for (int c = 1; c < 13; c++) {
                                    completeBoard[r][c + offset] = playersList.get(i).getPoint(r, c);
                                }
                            }
                        }
                        default -> throw new IllegalStateException("Unexpected value: " + "Player "
                                + playersList.get(i).getId() + " at position " + i);
                    }
                }
            }
    }

    static int[][] mergeMatrix(int[][] A, int[][] B) {

        int m = A.length;
        int n = A[0].length + B[0].length;
        int[][] res = new int[m][n];

        for (int i = 0; i<A.length; i++){
            for (int j = 0; j<A[0].length; j++) {
                res[i][j] = A[i][j];
            }
        }

        int Aoffset = A[0].length;
        int resOffset = res[0].length;

        for (int i = 0; i<B.length; i++){
            for (int j = Aoffset; j<resOffset; j++) {
                res[i][j] = B[i][j-Aoffset];
            }
        }
        return res;
    }

    @Override
    public String toString() {
        String string = "";

        for (int i = 0; i < completeBoard.length; i++){
            for (int j = 0; j < completeBoard[0].length; j++){
                System.out.print(completeBoard[i][j]);
            }
            System.out.println();
        }
        return string;
    }

    public int[][] getCompleteBoard(){ return completeBoard; }

    //public Client getMyClient(){return myClient;}
}