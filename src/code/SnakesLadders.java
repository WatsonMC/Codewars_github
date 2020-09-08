package code;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class SnakesLadders {
    int currentPlayer = 1;
    Map<Integer, Integer> posns = new HashMap<>();
    int lastSquare  = 100;

    boolean gameOver = false;

    Map<Integer,BoardPosition> board;
    public SnakesLadders(){
        posns.put(1,0);
        posns.put(2,0);
        board = instantiateBoard();
    }

    public String play(int die1, int die2){
        if(gameOver){return "Game over!";}

        int tempPos= posns.get(currentPlayer) + die1 + die2;
        //decrement if greater than board size
        tempPos  = tempPos>lastSquare ? tempPos = lastSquare- (tempPos-lastSquare):tempPos;

        //check for ssnake or ladder, adjust posn as necessary
        if(board.get(tempPos).isSL){
            tempPos = board.get(tempPos).end;
        }
        posns.put(currentPlayer,tempPos);

        //Determine return result
        String result;
        if(tempPos == lastSquare){
            result = String.format("Player %d Wins!",currentPlayer);
            gameOver = true;
        }
        else{
            result =  String.format("Player %d is on square %d", currentPlayer,tempPos);
        }
        //change palyer
        if(die1 != die2){
            currentPlayer = currentPlayer ==1 ? 2:1;
        }
        return result;
    }





    public Map<Integer,BoardPosition> instantiateBoard(){
        Map<Integer,BoardPosition> board = new HashMap<>();

        //ladders;
        loadBoardPosition(board,2,38);
        loadBoardPosition(board,7,14);
        loadBoardPosition(board,8,31);
        loadBoardPosition(board,15,26);
        loadBoardPosition(board,28,84);
        loadBoardPosition(board,21,42);
        loadBoardPosition(board,36,44);
        loadBoardPosition(board,51,67);
        loadBoardPosition(board,71,91);
        loadBoardPosition(board,78,98);
        loadBoardPosition(board,87,94);

        //snakes
        loadBoardPosition(board,99,80);
        loadBoardPosition(board,95,75);
        loadBoardPosition(board,92,88);
        loadBoardPosition(board,89,68);
        loadBoardPosition(board,74,53);
        loadBoardPosition(board,64,60);
        loadBoardPosition(board,62,19);
        loadBoardPosition(board,46,25);
        loadBoardPosition(board,49,11);
        loadBoardPosition(board,16,6);

        // load defaults
        IntStream.range(1,lastSquare+1).forEach(i -> {
            if (!board.containsKey(i)) {
                board.put(i, new BoardPosition());
            }

        });
        return board;
    }

    public void loadBoardPosition(Map<Integer,BoardPosition> board, int start,int end){
        board.put(start, new BoardPosition(start,end));
    }

    class BoardPosition{
        boolean isSL =false;
        int start;
        int end;
        public BoardPosition(){

        }

        public BoardPosition(int start, int end){
            this.start = start;
            this.end = end;
            this.isSL = true;
        }
    }

}
