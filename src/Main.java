import java.util.Random;
import java.util.Scanner;

public class Main {
    public enum Field {
        X('X'),
        O('O'),
        EMPTY(' ');

        private char value;

        Field(char value) {
            this.value = value;
        }

        public char getValue() {
            return value;
        }
    }

    public enum CurrentPlayer {
        USER,
        COMP
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();
        Field[][] field = new Field[3][3];
        CurrentPlayer player = CurrentPlayer.USER;
        CurrentPlayer winner = null;
        boolean isGame = true;
        int unfilledCells = 9;

        // Инициализация поля
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = Field.EMPTY;
            }
        }

        while (isGame) {
            // Вывод поля
            System.out.println("-------------");
            for (int i = 0; i < field.length; i++) {
                System.out.print("| ");
                for (int j = 0; j < field[i].length; j++) {
                    System.out.print(field[i][j].getValue() + " | ");
                }
                System.out.println("\n-------------");
            }

            // Ход игрока
            if (player == CurrentPlayer.USER) {
                int cellI = -1, cellJ = -1;
                boolean validInput = false;

                while (!validInput) {
                    try {
                        System.out.println("Введите номер строки (1-3): ");
                        cellI = Integer.parseInt(sc.nextLine()) - 1;
                        System.out.println("Введите номер столбца (1-3): ");
                        cellJ = Integer.parseInt(sc.nextLine()) - 1;

                        if (cellI < 0 || cellI > 2 || cellJ < 0 || cellJ > 2) {
                            System.out.println("Ошибка! Введите числа от 1 до 3.");
                        } else if (field[cellI][cellJ] != Field.EMPTY) {
                            System.out.println("Эта клетка уже занята. Выберите другую.");
                        } else {
                            validInput = true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка! Введите числа, а не символы.");
                    }
                }

                field[cellI][cellJ] = Field.X;
            }
            // Ход компьютера
            else {
                int cellI, cellJ;
                do {
                    cellI = rand.nextInt(3);
                    cellJ = rand.nextInt(3);
                } while (field[cellI][cellJ] != Field.EMPTY);

                field[cellI][cellJ] = Field.O;
                System.out.println("Компьютер сделал ход: " + (cellI + 1) + ", " + (cellJ + 1));
            }

            unfilledCells--;

            // Проверка на победителя
            Field currentSymbol = (player == CurrentPlayer.USER) ? Field.X : Field.O;
            boolean hasWinner = false;

            // Проверка по горизонтали и вертикали
            for (int i = 0; i < 3; i++) {
                if ((field[i][0] == currentSymbol && field[i][1] == currentSymbol && field[i][2] == currentSymbol) ||
                        (field[0][i] == currentSymbol && field[1][i] == currentSymbol && field[2][i] == currentSymbol)) {
                    hasWinner = true;
                    break;
                }
            }

            // Проверка по диагоналям
            if (!hasWinner) {
                if ((field[0][0] == currentSymbol && field[1][1] == currentSymbol && field[2][2] == currentSymbol) ||
                        (field[0][2] == currentSymbol && field[1][1] == currentSymbol && field[2][0] == currentSymbol)) {
                    hasWinner = true;
                }
            }

            if (hasWinner) {
                winner = player;
                isGame = false;
            } else if (unfilledCells == 0) {
                isGame = false;
            }

            player = (player == CurrentPlayer.USER) ? CurrentPlayer.COMP : CurrentPlayer.USER;
        }

        // Вывод финального состояния поля
        System.out.println("-------------");
        for (int i = 0; i < field.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < field[i].length; j++) {
                System.out.print(field[i][j].getValue() + " | ");
            }
            System.out.println("\n-------------");
        }

        // Объявление результата
        if (winner != null) {
            System.out.println(winner == CurrentPlayer.USER ? "Вы выиграли!" : "Компьютер выиграл!");
        } else {
            System.out.println("Ничья!");
        }
    }
}