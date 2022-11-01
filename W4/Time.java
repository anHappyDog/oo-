import java.util.ArrayList;

public class Time {
    //存储相应时间的消息
    //在MainClass中通过ArrayList查询
    private int year;
    private int month;
    private int day;
    private ArrayList<String> timeOfMessage;

    public Time() {
        timeOfMessage = new ArrayList<>();
    }

    public Time(int year,int month,int day) {
        setDay(day);
        setMonth(month);
        setYear(year);
        timeOfMessage = new ArrayList<>();
    }

    public void setTimeOfMessage(ArrayList<String> timeOfMessage) {
        this.timeOfMessage = timeOfMessage;
    }

    public ArrayList<String> getTimeOfMessage() {
        return timeOfMessage;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
