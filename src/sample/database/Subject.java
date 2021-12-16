package sample.database;

public class Subject {
    private int serial_num;
    private String type;
    private String name;
    private String teacher;
    private String room;
    private String day;
    private String week;
    private String week2;
    private String time_start;
    private String time_end;
    private String time = time_start + " - " + time_end;
    private int group_id;


    public Subject(int serial_num, String type, String name, String teacher, String room, String day, String week, String time_start, String time_end, int group_id) {
        this.serial_num = serial_num;
        this.type = type;
        this.name = name;
        this.teacher = teacher;
        this.room = room;
        this.day = day;
        this.week = week;
        this.time_start = time_start;
        this.time_end = time_end;
        this.group_id = group_id;
    }

    public Subject(String day, int serial_num, String time_start, String type, String name,  String teacher, String room) {
        this.serial_num = serial_num;
        this.type = type;
        this.name = name;
        this.teacher = teacher;
        this.room = room;
        this.day = day;
        this.time_start = time_start;
    }

    public Subject(String day, String week, int group_id) {
        this.day = day;
        this.week = week;
        this.group_id = group_id;
    }

    public Subject(int group_id, String day, int serial_num ) {
        this.day = day;
        this.serial_num = serial_num;
        this.group_id = group_id;
    }

    public Subject(String day, String week, String week2, int group_id) {
        this.day = day;
        this.week = week;
        this.week2 = week2;
        this.group_id = group_id;
    }

    public Subject(String day, String name) {
        this.day = day;
        this.name = name;
    }

    public int getSerial_num() {
        return serial_num;
    }

    public void setSerial_num(int serial_num) {
        this.serial_num = serial_num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getWeek2() {
        return week2;
    }

    public void setWeek2(String week2) {
        this.week2 = week2;
    }
}
