import java.util.InputMismatchException;
import java.util.Scanner;

public class Human implements Player {
    Scanner sc = new Scanner(System.in);
    private final int color;

    Human(int val) {
        color = val;
    }
    @Override
    public int[] chooseCell(GameField gf) {
        while (true) {
            System.out.println("Enter the line and column of cells in which you want to put a chip\n" +
                               "Or, if you want to cancel your last step enter 0 0");
            int line, column;
            try {
                line = sc.nextInt();
                column = sc.nextInt();
                if (!gf.checkStep(line - 1, column - 1, color) && !(line == 0 && column == 0)) {
                    System.out.println("Error! It is impossible to put a chip in this cell");
                    continue;
                }
            } catch (InputMismatchException ex) {
                sc.next();
                System.out.println("Error! Incorrect input");
                continue;
            }
            return new int[]{line - 1, column - 1};
        }
    }

}
