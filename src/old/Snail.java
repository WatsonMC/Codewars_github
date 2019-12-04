package old;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Snail {

    private int xPos = 0;
    private int yPos = 0;
    private int rc = 0;
    private boolean asc = true;
    private boolean hor = true;
    int runLength = 0;
    int moves = 0;
    int n;

    public static int[] snail(int[][] array) {
        int n = array[0].length;

        Snail iSnail = new Snail(n);
        List<Integer> res = new ArrayList<>();
        while(iSnail.hasNext()){
            int[] next = iSnail.getNext();
            res.add(array[next[1]][next[0]]);
        }

        int[] resArray = new int[res.size()];
        for(int i = 0; i <res.size();i++){
            resArray[i] = res.get(i);
        }
        return resArray;
    }

    public Snail(int n){
        moves = n*n;  // moves coundown
        runLength = getRunLength(n,rc)-1; //start is 1 less than formula
        this.n = n;
    }

    public boolean hasNext(){
        return moves>0;
    }

    public int[] getNext(){
        int[] res = {xPos,yPos};
        moves--;  //decrement moves
        runLength--;  //current run length

        //increment posn
        if(asc&&hor){xPos++;}
        if(asc&&!hor){yPos++;}
        if(!asc&&hor){xPos--;}
        if(!asc&&!hor){yPos--;}

        //handle end of runs
        if(runLength==0){
            rc++;
            hor = (rc%2 ==0); //all odd runs vertical
            asc = ((rc+2)/2)%2==1; //direction switches every second run
            runLength = getRunLength(n, rc);
        }
        return res;
    }

    //gets run length based on formula
    private int getRunLength(int n,int rc){
        return n-((rc+1)/2);
    }
}