package GameWithDates;

import java.util.Scanner;

class Game {

    final static int COUNT_MONTHS = 12;
    final static int[] DAYS_IN_MONTH = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private int[][] solution = new int[COUNT_MONTHS][31];
    private int[][] nextDay = new int[COUNT_MONTHS][31];
    private int[][] nextMonth = new int[COUNT_MONTHS][31];

    public int[][] getSolution() {
        return solution;
    }

    void run() {
        solve();
        Scanner sc = new Scanner(System.in); //создаем объект класса Scanner
        Date curDate = Date.generateDay();
        System.out.println("Вы: Игрок 1, Компьютер: Игрок 2");
        System.out.print("Исходная дата: ");
        curDate.printDate();
        int prevDay = curDate.getDay(); //текущий день
        int prevMonth = curDate.getMonth(); // текущий месяц
        while (true) {
            int day = 0;
            int month = 0;
            boolean isCorrectMove = false;
            while (!isCorrectMove) { //пока корректный ход
                System.out.print("Игрок 1: ");
                day = sc.nextInt() - 1; //считываем день
                month = sc.nextInt() - 1; //считываем месяц
                if (Date.isCorrectDate(day, month)) {
                    if ((prevDay == day) && ((month - prevMonth == 1) || (month - prevMonth == 2))) {
                        isCorrectMove = true;
                    }
                    if ((prevMonth == month) && ((day - prevDay == 1) || (day - prevDay == 2))) {
                        isCorrectMove = true;
                    }
                }
                if (!isCorrectMove) {
                    System.out.println ("Некорректный ход");
                }
            }
            if ((day == 30) && (month == 11)) {
                System.out.println("Вы проиграли");
                return;
            }
            System.out.print("Игрок 2: ");
            if (solution[month][day] == 1) {
                prevDay = nextDay[month][day];
                prevMonth = nextMonth[month][day];
                System.out.println((nextDay[month][day] + 1) + " " + (nextMonth[month][day] + 1));
            } else {
                if (Date.isCorrectDate(day + 1, month)) {
                    prevDay = day + 1;
                    prevMonth = month;
                    System.out.println((day + 2) + " " + (month + 1));
                    if ((day + 1 == 30) && (month == 11)) {
                        System.out.println("Вы проиграли");
                        return;
                    }
                } else {
                    prevDay = day;
                    prevMonth = month + 1;
                    System.out.println((day + 1) + " " + (month + 2));
                    if ((day == 30) && (month + 1 == 11)) {
                        System.out.println("Вы проиграли ");
                        return;
                    }
                }
            }
        }
    }

    void solve() {
        solution[COUNT_MONTHS - 1][30] = 1;
        for (int month = COUNT_MONTHS - 1; month >= 0; month--) {
            for (int day = DAYS_IN_MONTH[month] - 1; day >= 0; day--) {

                if (Date.isCorrectDate(day + 1, month) && (solution[month][day + 1] == -1)) {
                    solution[month][day] = 1;
                    nextDay[month][day] = day + 1;
                    nextMonth[month][day] = month;
                }
                if (Date.isCorrectDate(day, month + 1) && (solution[month + 1][day] == -1)) {
                    solution[month][day] = 1;
                    nextDay[month][day] = day;
                    nextMonth[month][day] = month + 1;
                }
                if (Date.isCorrectDate(day + 2, month) && (solution[month][day + 2] == -1)) {
                    solution[month][day] = 1;
                    nextDay[month][day] = day + 2;
                    nextMonth[month][day] = month;
                }
                if (Date.isCorrectDate(day, month + 2) && (solution[month + 2][day] == -1)) {
                    solution[month][day] = 1;
                    nextDay[month][day] = day;
                    nextMonth[month][day] = month + 2;
                }

                if (solution[month][day] == 0) {
                    solution[month][day] = -1;
                }
            }
        }
    }

}
