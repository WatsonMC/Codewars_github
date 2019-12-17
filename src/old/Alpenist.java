
package old;
import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Alpenist{

    public static Map<String,Integer> distances;
    public static HashMap<String,Integer> mazeHeights;

    public static int pathFinder(String maze){
        Comparator<String> distanceComparator = new Comparator<String>(){
            @Override
            public int compare(String s1, String s2){
                int d1 = distances.get(s1);
                int d2 = distances.get(s2);
                if(d1==d2){return 0;}
                return d1>d2 ? 1:-1;
            }
        };
        PriorityQueue<String>vertices = new PriorityQueue<>(distanceComparator);
        int n = maze.split("\\n").length;
        String target = String.valueOf(n - 1) + "," + String.valueOf(n - 1);
        mazeHeights = new HashMap<>();
        distances = new HashMap<>();
        Set<String> vertexSet = getVertexSet(maze, n);
        //init map of distances
        for(String vertex:vertexSet){
            distances.put(vertex,Integer.MAX_VALUE/2);
        }
        String currentVertex = "0,0";
        distances.put(currentVertex,0);
        String nextVertex = "";
        vertices.add(currentVertex);
        while(!currentVertex.equals(target)){
            vertices.remove(currentVertex);
            for(String neighbour: getNeighbours(currentVertex,n)){
                int edgeDistance = Math.abs(getHeightDifference(currentVertex,neighbour));
                int currDistance = distances.get(currentVertex);
                if (currDistance + edgeDistance < distances.get(neighbour)) {
                    distances.put(neighbour,currDistance +edgeDistance);
                    vertices.add(neighbour);
                }
            }
            currentVertex = vertices.remove();
        }
        return distances.get(target);
    }

    public static Integer getHeightDifference(String v1, String v2){
        return(mazeHeights.get(v1)-mazeHeights.get(v2));
    }

    public static Integer getHeight(String vertex, String maze){
        return Character.getNumericValue(maze.split("\\n")[Integer.parseInt(vertex.split(",")[0])].charAt(Integer.parseInt(vertex.split(",")[1])));
    }

    static Set<String> getVertexSet(String maze, int n){
        Set<String> vertices = new HashSet<>();
        String[] mazeRows = maze.split("\\n");
        for(int x = 0; x<n; x++){
            for(int y = 0;y<n;y++){
                String vertex = String.valueOf(x) + "," + String.valueOf(y);
                vertices.add(vertex);
                mazeHeights.put(vertex,Character.getNumericValue(mazeRows[x].charAt(y)));
            }
        }
        return vertices;
    }

    public static Set<String> getNeighbours(String currPos, int n){
        int x = Integer.parseInt(currPos.split(",")[0]);
        int y = Integer.parseInt(currPos.split(",")[1]);
        Set<String> neighbs = new HashSet<>();

        for(int i = -1; i<3; i +=2){
            if(x+i >=0 && x+i <n){
                neighbs.add(String.valueOf(x+i) + "," + y);
            }
            if(y+i >=0 && y+i <n){
                neighbs.add(x + "," + String.valueOf(y+i));
            }
        }
        return neighbs;
    }
}