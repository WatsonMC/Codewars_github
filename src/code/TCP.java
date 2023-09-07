package code;

import java.util.HashMap;
import java.util.Map;

public class TCP {

    private static final Map<String, String> closedEvents = Map.of("APP_PASSIVE_OPEN", "LISTEN", "APP_ACTIVE_OPEN", "SYN_SENT");
    private static final Map<String, String> listenEvents = Map.of("RCV_SYN", "SYN_RCVD","APP_SEND", "SYN_SENT","APP_CLOSE", "CLOSED");
    private static final Map<String, String> synRcvdEvents = Map.of("APP_CLOSE", "FIN_WAIT_1","RCV_ACK", "ESTABLISHED");
    private static final Map<String, String> synSentEvents = Map.of("RCV_SYN", "SYN_RCVD","RCV_SYN_ACK", "ESTABLISHED","APP_CLOSE", "CLOSED");
    private static final Map<String, String> estEvents = Map.of("APP_CLOSE", "FIN_WAIT_1","RCV_FIN", "CLOSE_WAIT");
    private static final Map<String, String> finWait1Events = Map.of("RCV_FIN", "CLOSING","RCV_FIN_ACK", "TIME_WAIT","RCV_ACK", "FIN_WAIT_2");
    private static final Map<String, String> closingEvents = Map.of("RCV_ACK", "TIME_WAIT","RCV_FIN", "TIME_WAIT");
    private static final Map<String, String> finWait2Events = Map.of("RCV_FIN", "TIME_WAIT","APP_TIMEOUT", "CLOSED");
    private static final Map<String, String> timeWaitEvents = Map.of("APP_TIMEOUT", "CLOSED","APP_CLOSE", "LAST_ACK");
    private static final Map<String, String> closeWaitEvents = Map.of("APP_CLOSE", "LAST_ACK","RCV_ACK", "CLOSED");
    private static final Map<String, String> lastAckEvents = Map.of("RCV_ACK", "CLOSED");
    private static final Map<String, Map<String,String>> eventsMap;
    static{
        Map<String,Map<String,String>> tempMap = new HashMap<>();
        tempMap.put("CLOSED",closedEvents);
        tempMap.put("LISTEN",listenEvents);
        tempMap.put("SYN_RCVD",synRcvdEvents);
        tempMap.put("SYN_SENT",synSentEvents);
        tempMap.put("ESTABLISHED",estEvents);
        tempMap.put("FIN_WAIT_1",finWait1Events);
        tempMap.put("FIN_WAIT_2",finWait2Events);
        tempMap.put("CLOSING",closingEvents);
        tempMap.put("TIME_WAIT",timeWaitEvents);
        tempMap.put("CLOSE_WAIT",closeWaitEvents);
        tempMap.put("LAST_ACK",lastAckEvents);
        eventsMap = new HashMap<>(tempMap);

    }

    public static String traverseStates(String[] events) {
        String state = "CLOSED";                          // initial state, always
        for(String event: events){
            if(eventsMap.get(state).keySet().contains(event)){
                state = eventsMap.get(state).get(event);
            }
            else{
                return "ERROR";
            }
        }
        return  state;
    }
}
