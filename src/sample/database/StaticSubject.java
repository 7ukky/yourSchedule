package sample.database;

import java.time.LocalDate;

public class StaticSubject {
   public static int serial_num;
   public static String type;
   public static String name;
   public static String teacher;
   public static String room;
   public static String day;
   public static String week;
   public static String week2;
   public static String time_start;
   public static String time_end;
   public static int group_id;
   public static LocalDate date;
   public static int conditionSignIn = 0;
   public static int conditionSignOut = 0;
   public static int error_code = 2;

   public static void setAllNull() {
      serial_num = 0;
      type = null;
      name = null;
      teacher = null;
      room = null;
      day = null;
      week = null;
      week2 = null;
      time_start = null;
      time_end = null;
      group_id = 0;
      date = null;
   }

   public static void setConditionZero(){
      conditionSignIn = 0;
   }
}
