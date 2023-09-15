package com.zach.util;

import com.zach.data.ticket.TicketConfiguration;
import org.aeonbits.owner.ConfigCache;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.json.Json;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TrainUtil {
    /**
     * var seatTypeForHB = {
     * SWZ: "9_商务座",
     * TZ: "P_特等座",
     * ZY: "M_一等座",
     * ZE: "O_二等座",
     * GR: "6_高级软卧",
     * RW: "4_软卧",
     * SRRB: "F_动卧",
     * YW: "3_硬卧",
     * RZ: "2_软座",
     * YZ: "1_硬座",
     * WZ: "1_无座",
     * QT: "H_其他"
     * };
     *
     * @param seat
     * @return
     */

    private static Map<String, String> stationMap = new HashMap<>(2048);
    private static TicketConfiguration ticketConfiguration;

    private static Map<String, String> seatType;

    static {
        ticketConfiguration = ConfigCache.getOrCreate(TicketConfiguration.class);
        String stationStr = ticketConfiguration.stationStr();
        if (StringUtils.isEmpty(stationStr)) {
            stationStr = loadStationByApi(ticketConfiguration.stationLink());
        }
        parseStationStr(stationStr, stationMap);
        seatType = parseSeatTypeStr(ticketConfiguration.seatType());
    }


    private static Map<String, String> parseSeatTypeStr(String seatStr) {
        Json json = new Json();
        LinkedHashMap map = json.toType(seatStr, Json.MAP_TYPE);
        return new HashMap<>(map);
    }


    private static void parseStationStr(String stationStr, Map<String, String> stationMap) {
        String[] split = stationStr.split("\\|\\|\\|@");
        for (String station : split) {
            String[] current = station.split("\\|");
            stationMap.put(current[1], current[2]);
        }
    }

    private static String loadStationByApi(String s) {
        return "";
    }


    public static Map<String, Boolean> parseSeat(String seat) {
        String[] seatLevel = seat.split(" ");
        Map<String, Boolean> res = new HashMap<>();
        /**
         *  * var seatTypeCodeForName = {
         *      * P: "特等座",
         *      * "9": "商务座",
         *      * A: "高级动卧",
         *      * M: "一等座",
         *      * O: "二等座",
         *      * "6": "高级软卧",
         *      * I: "一等卧",
         *      * J: "二等卧",
         *      * "4": "软卧",
         *      * "3": "硬卧",
         *      * F: "动卧",
         *      * "2": "软座",
         *      * "1": "硬座",
         *      * H: "其他",
         *      * WZ: "无座",
         *      * W: "无座"
         *      * };
         */
        // 特等座 一等座	二等座/二等包座	高级软卧	软卧一等卧	动卧	硬卧二等卧	软座	硬座	无座	其他
        for (int i = 0; i < seatLevel.length; i++) {
            if (i == 0) {
                res.put("P", StringUtils.equals("有", seatLevel[i]));
            } else if (i == 1) {
                res.put("M", StringUtils.equals("有", seatLevel[i]));
            } else if (i == 2) {
                res.put("O", StringUtils.equals("有", seatLevel[i]));
            } else if (i == 3) {
                res.put("6", StringUtils.equals("有", seatLevel[i]));
            } else if (i == 4) {
                res.put("I", StringUtils.equals("有", seatLevel[i]));
            } else if (i == 5) {
                res.put("F", StringUtils.equals("有", seatLevel[i]));
            } else if (i == 6) {
                res.put("3", StringUtils.equals("有", seatLevel[i]));
            } else if (i == 7) {
                res.put("2", StringUtils.equals("有", seatLevel[i]));
            } else if (i == 8) {
                res.put("1", StringUtils.equals("有", seatLevel[i]));
            } else if (i == 9) {
                res.put("W", StringUtils.equals("有", seatLevel[i]));
            } else if (i == 10) {
                res.put("H", StringUtils.equals("有", seatLevel[i]));
            }
        }

        return res;
    }

    public static String codeByCity(String code) {
        return stationMap.getOrDefault(code, code);
    }
}
