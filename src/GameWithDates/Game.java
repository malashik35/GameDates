package GameWithDates;

import java.util.Scanner;

class Game {

    final static int COUNT_MONTHS = 12;
    final static int[] DAYS_IN_MONTH = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private int[][] solution = new int[COUNT_MONTHS][31];//победитель в конкретной позиции
    private int[][] nextDay = new int[COUNT_MONTHS][31]; //для новых ходов
    private int[][] nextMonth = new int[COUNT_MONTHS][31]; // для новых ходов

    int[][] getSolution() {
        return solution;
    }

    void run() {
        solve();
        Scanner sc = new Scanner(System.in); //создаем объект класса Scanner
        Date curDate = Date.generateDay(); //генерируем дату
        System.out.println("Вы: Игрок 1, Компьютер: Игрок 2");
        System.out.print("Введите два числа через пробел\n");
        System.out.print("Исходная дата: ");
        curDate.printDate();//печатаем дату

        int prevDay = curDate.getDay(); //текущий день
        int prevMonth = curDate.getMonth(); // текущий месяц
        while (true) {
            int day = 0;
            int month = 0;
            String days;
            String months;
            boolean isCorrectMove = false;
            while (!isCorrectMove) { //пока корректный ход
                System.out.print("Игрок 1: "); //ход игрока
                days = sc.next(); //считываем день
                months = sc.next(); //считываем месяц
                while (!checkString(days) || !checkString(months)) {
                    System.out.print("Некорректный ход\nИгрок 1: ");
                    days = sc.next(); //считываем день
                    months = sc.next(); //считываем месяц
                }
                day = Integer.parseInt(days) - 1; // "-1" так как отсчет с 0
                month = Integer.parseInt(months) - 1; // "-1" так как отсчет с 0
                if (Date.isCorrectDate(day, month)) {//проверка корректности даты
                    if ((prevDay == day) && ((month - prevMonth == 1) || (month - prevMonth == 2))) {//соответствие условию
                        isCorrectMove = true;//ход корректный
                    }
                    if ((prevMonth == month) && ((day - prevDay == 1) || (day - prevDay == 2))) {//соответствие условию
                        isCorrectMove = true;//ход корректный
                    }
                }
                if (!isCorrectMove) {
                    System.out.print("Некорректный ход\n");
                }
            }
            if ((day == 30) && (month == 11)) { // при 31.12 поражение
                System.out.println("Вы проиграли");
                return;
            }
            System.out.print("Игрок 2: "); //ход компьютера
            if (solution[month][day] == 1) { //если выигрышная позиция, то
                prevDay = nextDay[month][day]; //КАКОЙ ХОД СОВЕРШИТЬ ЕСЛИ ДЕНЬ=DAY, А МЕСЯЦ = MONTH
                prevMonth = nextMonth[month][day];
                System.out.println((nextDay[month][day] + 1) + " " + (nextMonth[month][day] + 1));
            } else {//ЕСЛИ НЕ ВЫИГРЫШНАЯ ПОЗИЦИЯ, ТО СОВЕРШИТЬ ЛЮБОЙ КОРРЕКТНЫЙ ХОД
                if (Date.isCorrectDate(day + 1, month)) {
                    prevDay = day + 1;
                    prevMonth = month;
                    System.out.println((day + 2) + " " + (month + 1));
                    if ((day + 1 == 30) && (month == 11)) {//условие проигрыша
                        System.out.println("Вы проиграли");
                        return;
                    }
                } else {
                    prevDay = day;
                    prevMonth = month + 1;
                    System.out.println((day + 1) + " " + (month + 2));
                    if ((day == 30) && (month + 1 == 11)) {//условие проигрыша
                        System.out.println("Вы проиграли ");
                        return;
                    }
                }
            }
        }
    }

    private boolean checkString(String string) { // метод проверки на соответствие корректности ввода даты
        int i = 0;
        char c;
        for (; i < string.length(); i++) {
            c = string.charAt(i);
            if (!(c >= '0' && c <= '9')) {
                return false;
            }
        }
        return true;
    }

    void solve() {//заполнение таблицы выигрышных и проигрышных позиций в таблицу. Если solution[][]=1 - победа комп.
        //если solution[][]=0 - даты не существует. //если solution[][]= -1 - выигрышная позиция для игрока.
        solution[COUNT_MONTHS - 1][30] = 1;                                                  //пример таблицы:
        for (int month = COUNT_MONTHS - 1; month >= 0; month--) {                            // { даты    31 30 29 28...}
            for (int day = DAYS_IN_MONTH[month] - 1; day >= 0; day--) {                      // {  ...
                                                                                             // { ноябрь  0  1  -1 1... }
                if (Date.isCorrectDate(day + 1, month) && (solution[month][day + 1] == -1)) {// { декабрь 1 -1   1 1... }
                    solution[month][day] = 1;
                    nextDay[month][day] = day + 1; //запоминаем следующий шаг
                    nextMonth[month][day] = month; // запоминаем следующий шаг
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
