package code.playerMovement;

public class Tile {
        int x;
        int y;
        public Tile(int x, int y){
            this.x = x;
            this.y = y;
        };
        public int x(){return x;}                                   // getter
        public int y(){return y;}                                   // getter
        @Override public String  toString(){return "("+x + ", " + y +")";}    ;          // formated as: "(x,y)"
        @Override public int     hashCode(){return new Integer(x).hashCode() * new Integer(y).hashCode();};
        @Override public boolean equals(Object other){ return (other instanceof Tile ? this.equals((Tile)other): false);};
        public boolean equals(Tile otherTile){return (otherTile.x() == this.x && otherTile.y() == this.y);}

}
