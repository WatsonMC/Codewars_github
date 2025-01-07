package leetcode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimplifyPath {
    public String simplifyPath(String path) {
        //algorithm:
        //1. replace all multiple slashes with singular slashes
        //2. Split string by '/' to find elements
        //3. cycle through elements
        //  a. if i = '.' delete
        //  b. if i = '..' delete and delete one prior
        //  c. else leave

        String pathCleaned = path.replaceAll("/+", "/"); // remove duplicate slashes
        if(pathCleaned.startsWith("/")){ pathCleaned = pathCleaned.substring(1);}
        if(pathCleaned.endsWith("/")){ pathCleaned = pathCleaned.substring(0, pathCleaned.length()-1);}  //remove start and end slashes

        String[] splitPath = pathCleaned.split("/"); // now we should only have the actual path elements
        String[] simplifiedPath = Arrays.copyOf(splitPath,splitPath.length);    //copy of split path now
        for(int i =0; i<splitPath.length;i++){
            String c= splitPath[i];
            if(c.equals(".")){
                simplifiedPath[i] = "";
            }else if(c.equals("..")){
                simplifiedPath[i] = "";
                if(i>0){ simplifiedPath[i-1] = "";}
            }else{
                simplifiedPath[i] =c;
            }
        }

        StringBuilder sb = new StringBuilder();

        for(String s: simplifiedPath){
            if(!s.equals("")){
                sb.append("/");
                sb.append(s);
            }
        }
        return sb.toString().equals("")? "/" :sb.toString();
    }

    public String simplifyPath2(String path) {
        String pathCleaned = path.replaceAll("/+", "/"); // remove duplicate slashes
        if(pathCleaned.startsWith("/")){ pathCleaned = pathCleaned.substring(1);}
        if(pathCleaned.endsWith("/")){ pathCleaned = pathCleaned.substring(0, pathCleaned.length()-1);}  //remove start and end slashes

        String[] splitPath = pathCleaned.split("/"); // now we should only have the actual path elements
        List<String> simplifiedPath =  new ArrayList<>();

        int deleteCounter = 0;
        for(int i = splitPath.length-1; i>=0; i--) {
            String c = splitPath[i];

            if (c.equals("..")) {
                deleteCounter++;
            } else if (!c.equals(".")) {
                if(deleteCounter == 0){
                    simplifiedPath.add(c);
                }else{
                    deleteCounter--;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for(int i = simplifiedPath.size()-1; i>=0;i--){
            sb.append("/");
            sb.append(simplifiedPath.get(i));
        }
        return sb.toString().equals("")? "/" :sb.toString();
    }
}
