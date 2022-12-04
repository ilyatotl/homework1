import java.util.ArrayList;
import java.util.List;

public class GameField {
    private static final int[] dx = {0, 0, 1, 1, 1, -1, -1, -1};
    private static final int[] dy = {1, -1, 1, -1, 0, 1, -1, 0};
    private static final String black = "O";
    private static final String white = "X";
    private static final int N = 8;
    List<int[][]> board = new ArrayList<>();

    GameField() {
        board.add(new int[N][N]);
        board.get(0)[3][4] = board.get(0)[4][3] = 2;
        board.get(0)[3][3] = board.get(0)[4][4] = 1;
    }

    private boolean valid(int i, int j) {
        return i >= 0 && j >= 0 && i < N && j < N;
    }

    public boolean stepsExist() {
        return firstStepsExist() || secondStepsExist();
    }

    public boolean firstStepsExist() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (checkStep(i, j, 1)) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean secondStepsExist() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (checkStep(i, j, 2)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean edgeCage(int i, int j) {
        return i == 0 || j == 0 || i == N - 1 || j == N - 1;
    }

    private boolean cornerCage(int i, int j) {
        return (i == 0 || i == N - 1) && (j == 0 || j == N - 1);
    }

    public float valStep(int i, int j, int val) {
        if (!valid(i, j)) {
            return 0;
        }
        if (board.get(board.size() - 1)[i][j] != 0) {
            return 0;
        }
        float res = 0;

        for (int pos = 0; pos < dx.length; pos++) {
            int x = i + dx[pos], y = j + dy[pos];
            while (valid(x, y) && board.get(board.size() - 1)[x][y] == 3 - val) {
                x += dx[pos];
                y += dy[pos];
            }
            if (valid(x, y) && board.get(board.size() - 1)[x][y] == val) {
                for (int line = i + dx[pos], col = j + dy[pos]; line != x || col != y; line += dx[pos], col += dy[pos]) {
                    if (edgeCage(line, col)) {
                        res += 2;
                    } else {
                        res += 1;
                    }
                }
            }
        }

        if (cornerCage(i, j)) {
            res += 0.8;
        } else if (edgeCage(i, j)) {
            res += 0.4;
        }
        return res;
    }

    public boolean checkStep(int i, int j, int val) {
        return valStep(i, j, val) >= 1;
    }

    public boolean makeStep(int i, int j, int val) {
        if (!checkStep(i, j, val)) {
            return false;
        }
        int[][] newArr = new int[N][N];
        for (int line = 0; line < N; line++) {
            for (int col = 0; col < N; col++) {
                newArr[line][col] = board.get(board.size() - 1)[line][col];
            }
        }
        board.add(newArr);
        for (int pos = 0; pos < dx.length; pos++) {
            int x = i + dx[pos], y = j + dy[pos];
            while (valid(x, y) && board.get(board.size() - 1)[x][y] == 3 - val) {
                x += dx[pos];
                y += dy[pos];
            }
            if (valid(x, y) && board.get(board.size() - 1)[x][y] == val) {
                for (int line = i + dx[pos], col = j + dy[pos]; line != x || col != y; line += dx[pos], col += dy[pos]) {
                    board.get(board.size() - 1)[line][col] = val;
                }
            }
        }
        board.get(board.size() - 1)[i][j] = val;
        return true;
    }

    public void printField(boolean need, int color) {
        System.out.println();
        System.out.print("   ");
        for (int i = 0; i < N; i++) {
            System.out.print("  " + (i + 1) + " ");
        }
        System.out.println();
        for (int i = 0; i < N; i++) {
            System.out.print(" " + (i + 1) + " ");
            System.out.print('|');
            for (int j = 0; j < N; j++) {
                if (board.get(board.size() - 1)[i][j] == 1) {
                    System.out.print(" " + white + " |");
                } else if (board.get(board.size() - 1)[i][j] == 2) {
                    System.out.print(" " + black + " |");
                } else {
                    if (!need || !checkStep(i, j, color)) {
                        System.out.print("   |");
                    } else {
                        System.out.print(" * |");
                    }
                }
            }
            System.out.println();
        }
    }

    public int getCrosses() {
        int cntCross = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board.get(board.size() - 1)[i][j] == 1) {
                    cntCross++;
                }
            }
        }
        return cntCross;
    }

    public int getCircles() {
        int cntCircles = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board.get(board.size() - 1)[i][j] == 2) {
                    cntCircles++;
                }
            }
        }
        return cntCircles;
    }

    public void printResult() {
        int cntCross = getCrosses(), cntCircles = getCircles();
        System.out.println("Score: X - " + cntCross + " O - " + cntCircles);
        if (cntCircles > cntCross) {
            System.out.println("Circles win!!!");
        } else if (cntCross > cntCircles) {
            System.out.println("Crosses win!!!");
        } else {
            System.out.println("Draw!!!");
        }
    }

    public boolean cancelStep() {
        if (board.size() == 1) {
            return false;
        }
        board.remove(board.size() - 1);
        return true;
    }
}
