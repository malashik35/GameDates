package GameWithDates;

import java.util.Random;

/**
 * Created by user on 14.12.2017.
 */
public class Date {
    private int day;
    private int month;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Date(int day, int month) {
        this.day = day;
        this.month = month;
    }

    public void printDate() {
        System.out.println((day + 1) + " " + (month + 1));
    }

    public static Date generateDay() { //считывание даты случайным образом
        Random r = new Random();
        int month = r.nextInt(Game.COUNT_MONTHS) ;
        int day = r.nextInt(Game.DAYS_IN_MONTH[month]);
        return new Date(day, month);
    }

    public static boolean isCorrectDate(int day, int month) { //проверка даты на корректность
        if ((month >= 0) && (month < 12)) {
            if ((day >= 0) && (day < Game.DAYS_IN_MONTH[month])) {
                return true;
            }
        }
        return false;
    }

}
