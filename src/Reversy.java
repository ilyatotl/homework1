import java.util.InputMismatchException;
import java.util.Scanner;

public class Reversy {
    static int bestScore = -1;
    static Scanner sc = new Scanner(System.in);
    static public int menu() {
        int option = -1;
        while (option < 1 || option > 5) {
            System.out.println("Welcome to the REVERSY! Choose one of these options and enter its number");
            System.out.print("""
                    1. Play with computer (easy version)
                    2. Play with computer (hard version)
                    3. PLay with a person
                    4. Get the highest score during this session
                    5. End the game
                    """
            );
            try {
                option = sc.nextInt();
            } catch (InputMismatchException ex) {
                sc.next();
                System.out.println("Error! You need to enter a number!");
                continue;
            }
            if (option < 1 || option > 5) {
                System.out.println("Error! The number must be 1, 2, 3, 4 or 5!");
            }
        }
        return option;
    }

    static public void printScore() {
        if (bestScore == -1) {
            System.out.println("You haven't played any games in this session yet");
            return;
        }
        System.out.println("The best score in this session: " + bestScore);
    }

    static public void startGame() {
        int option = menu();
        while (option != 5) {
            switch (option) {
                case 1:
                    againstEasyComputer();
                case 2:
                    againstHardComputer();
                case 3:
                    againstHuman();
                case 4:
                    printScore();
            }
            option = menu();
        }
    }

    static private void againstEasyComputer() {
        GameField gf = new GameField();
        Player player1 = new EasyBot(1);
        Player player2 = new Human(2);
        int turn = 2;
        while (gf.stepsExist()) {
            gf.printField(turn == 2, 2);
            int[] chs;
            if (turn == 1) {
                if (!gf.firstStepsExist()) {
                    System.out.println("Crosses don't have steps");
                }
                chs = player1.chooseCell(gf);
            } else {
                if (!gf.secondStepsExist()) {
                    System.out.println("Circles don't have steps");
                }
                chs = player2.chooseCell(gf);
            }
            if (chs[0] == -1 && chs[1] == -1) {
                gf.cancelStep();
                gf.cancelStep();
                continue;
            }
            gf.makeStep(chs[0], chs[1], turn);
            turn = 3 - turn;
        }
        bestScore = Math.max(bestScore, gf.getCircles());
        gf.printField(false, -1);
        gf.printResult();
    }

    static private void againstHardComputer() {
        GameField gf = new GameField();
        Player player1 = new HardBot(1);
        Player player2 = new Human(2);
        int turn = 2;
        while (gf.stepsExist()) {
            gf.printField(turn == 2, 2);
            int[] chs;
            if (turn == 1) {
                if (!gf.firstStepsExist()) {
                    System.out.println("Crosses don't have steps");
                }
                chs = player1.chooseCell(gf);
            } else {
                if (!gf.secondStepsExist()) {
                    System.out.println("Circles don't have steps");
                }
                chs = player2.chooseCell(gf);
            }
            if (chs[0] == -1 && chs[1] == -1) {
                gf.cancelStep();
                gf.cancelStep();
                continue;
            }
            gf.makeStep(chs[0], chs[1], turn);
            turn = 3 - turn;
        }
        bestScore = Math.max(bestScore, gf.getCircles());
        gf.printField(false, -1);
        gf.printResult();
    }

    static private void againstHuman() {
        GameField gf = new GameField();
        Player player1 = new Human(1);
        Player player2 = new Human(2);
        int turn = 2;
        while (gf.stepsExist()) {
            gf.printField(true, turn);
            int[] chs;
            if (turn == 1) {
                if (!gf.firstStepsExist()) {
                    System.out.println("Crosses don't have steps");
                }
                chs = player1.chooseCell(gf);
            } else {
                if (!gf.secondStepsExist()) {
                    System.out.println("Circles don't have steps");
                }
                chs = player2.chooseCell(gf);
            }
            if (chs[0] == -1 && chs[1] == -1) {
                if (gf.cancelStep()) {
                    turn = 3 - turn;
                }
                continue;
            }
            gf.makeStep(chs[0], chs[1], turn);
            turn = 3 - turn;
        }
        gf.printField(false, -1);
        gf.printResult();
    }
}
