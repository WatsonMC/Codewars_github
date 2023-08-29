package code.playerMovement;


import java.util.*;

public class PlayerMovement {
    private int direction = 8;  //0 = no direction
    private Tile position;
    private int currentKeyPressed; //0 = no key pressed

    private List<Integer> pressList = new ArrayList<>();
    private Map<Integer, Boolean>  lastPressState= new HashMap<>();

    public PlayerMovement(int x, int y){
        this.position = new Tile(x,y);
        for(int i: new int[]{6,4,2,8}){
            lastPressState.put(i, false);
        }

    }
    public void update() {
        System.out.print("Start Update. CurrentDir: " +direction + " currentKey: " + currentKeyPressed +  "\n");
        Map<Integer,Boolean> keysPressed = getKeyPresses(); //current list of pressed keys as map of booleans
        if(keysPressed.equals(lastPressState)){ //no change since last time
            if(currentKeyPressed != 0){
                this.position = move(currentKeyPressed);
            }
        }
        else{
            int newKeyPress = 0;
            for(int i: new int[]{6,4,2,8}){
                //cycle through each key of currently pressed keys
                if(keysPressed.get(i) &&  !lastPressState.get(i)){ //currently pressed, previously not pressed
                    pressList.add(i);
                    newKeyPress = i;
                } else if(!keysPressed.get(i) && lastPressState.get(i)) { //currently not pressed, previously was pressed, remove
                    pressList.remove(pressList.indexOf(i));
                }
            }
            if(newKeyPress == 0){   // no new key pressed, default based on pressList
                if(pressList.size() == 0){
                    currentKeyPressed = 0;
                }
                else {
                    currentKeyPressed = pressList.get(pressList.size() - 1);
                    if (currentKeyPressed == direction) {
                        this.position = move(currentKeyPressed);
                    } else {
                        direction = currentKeyPressed;
                    }
                }
            } else{
                currentKeyPressed = newKeyPress;
                direction = newKeyPress;
            }
        }
        lastPressState = keysPressed;
        System.out.print("End Update. CurrentDir: " +direction + " currentKey: " + currentKeyPressed +  "\n");

    }
    public int getDirection(){
        return this.direction;
    }
    public Tile getPosition(){
        return this.position;
    }

    public Tile move(int direction){
        //Up = { 0, +1 } , Down = { 0, -1 }, Left = { -1, 0 }, Right = { +1, 0 }
        switch (direction){
            case 2:
                return new Tile(this.position.x(), this.position.y()-1);
            case 4:
                return new Tile(this.position.x()-1, this.position.y());
            case 6:
                return new Tile(this.position.x()+1, this.position.y());
            case 8:
                return new Tile(this.position.x(), this.position.y()+1);
            default:
                return this.position;
        }
    }

    public Map<Integer, Boolean> getKeyPresses(){
        Map<Integer,Boolean> keyPresses = new HashMap<>();
        for(int i: new int[]{8, 2, 4, 6}) {
            if (Input.getState(i)) {
                keyPresses.put(i,true);
            }
            else{
                keyPresses.put(i,false);
            }
        }
        return keyPresses;
    }

}
