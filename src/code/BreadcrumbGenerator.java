package code;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BreadcrumbGenerator{
        static final String HOME = "<a href=\"/\">HOME</a>";
        static final String MID_LEFT = "<a href=";
        static final String MID_RIGHT = "</a>";
        static final String SPAN_LEFT = "<span class=\"active\">";
        static final String SPAN_RIGHT = "</span>";
        static final String SPAN_HOME = "<span class=\"active\">HOME</span>";
        static final Set<String> dontAcronymize = new HashSet<>(Arrays.asList("the","of","in","from","by","with","and", "or", "for", "to", "at", "a"));

        public static String generate_bc(String url, String separator) {
            if(url.indexOf("https://") == 0 || url.indexOf("http://") == 0){
                url = url.split("//")[1];
            }

            List<String> urlElements = new ArrayList<>();
            List<String> breadcrumbElements = new ArrayList<>();
            urlElements.addAll(Arrays.asList(url.split("/{1}")));

            String lastEle = urlElements.get(urlElements.size()-1).toLowerCase();
            if(lastEle.indexOf("index.") == 0 || lastEle.charAt(0) == '#' || lastEle.charAt(0) == '?'){
                urlElements.remove(urlElements.size()-1);   //remove index
            }

            if(urlElements.size() == 1){
                return SPAN_HOME;
            }

            breadcrumbElements.add(HOME);
            for(int i =1; i<urlElements.size()-1; i++) {
                breadcrumbElements.add(
                        MID_LEFT +"\"/" +
                        IntStream.range(1,i+1).mapToObj(x -> urlElements.get(x)).collect(Collectors.joining("/")) +
                         "/\">" + getBreadcrumb(urlElements.get(i))+
                        MID_RIGHT
                );
            }
            breadcrumbElements.add(
                    SPAN_LEFT +
                    getSpanCrumb(urlElements.get(urlElements.size()-1)) +
                    SPAN_RIGHT
            );
            return breadcrumbElements.stream().collect(Collectors.joining(separator));
        }

        public static String getBreadcrumb(String urlElement) {
            if(urlElement.length()<=30){return urlElement.toUpperCase().replaceAll("-", " ");}
            StringBuilder sb = new StringBuilder();
            for(String word: urlElement.split("-")) {
                if (!dontAcronymize.contains(word)) {
                    sb.append(word.charAt(0));
                }
            }
            return sb.toString().toUpperCase();
        }

        public static String getSpanCrumb(String spanElement) {
            String spanRaw = spanElement.split("\\.")[0].split("#")[0].split("\\?")[0];
            String span = Arrays.stream(getBreadcrumb(spanRaw).split("-")).map(x ->x.toUpperCase()).collect(Collectors.joining(" "));
            return span;
        }

}
