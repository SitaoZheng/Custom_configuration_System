package data;

import java.util.Date;

public class Time {
    private java.util.Date Date1 = new Date();
    String x = Date1.toString();
    public Time() {}
    public String getTimePoint() {
        String x = Date1.toString();
        int y1 = x.indexOf(":");
        int y2 = x.lastIndexOf(":");
        String time = x.substring(y1 - 2, y2+3);
        return time;
    }
}