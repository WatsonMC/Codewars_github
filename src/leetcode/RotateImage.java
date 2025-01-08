package leetcode;

public class RotateImage {

    public void rotate(int[][] matrix) {
        int n = matrix[0].length;

        /**
         * loop through cycles
         *  loop through rotations
         *  loop through rotation moves orr loop through until starting
         */

        for(int i =0; i<n/2;i++){
            for(int j = 0; j<n-2*i-1;j++){
                int x = i;
                int y = i+j;
                int val = matrix[x][y];

                for(int a = 0; a<4; a++){   // always 4, always four sides to a square

                    int newX = y;
                    int newY = n -1 -x;
                    int newVal = matrix[newX][newY];
                    matrix[newX][newY] = val;

                    val = newVal;
                    x = newX;
                    y = newY;
                }
            }

        }
        int x = 0;
    }
}
