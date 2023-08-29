package code.playerMovement;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Input {
    private static Map<Integer, Boolean> pressMap = Stream.of(new Object[][]{
            {2,false},
            {4,false},
            {6,false},
            {8,false},
    }).collect(Collectors.toMap( data -> (Integer)data[0], data -> (Boolean)data[1]));
    public static boolean getState(int direction){
        return pressMap.keySet().contains(direction) ? pressMap.get(direction) : false;
    }

    public static void press(int key){
        pressMap.put(key,true);
    }

    public static void release(int key){
        pressMap.put(key,false);

    }

    public static void clear(){
        for(int key: pressMap.keySet()){
            pressMap.put(key, false);
        }
    }
}
