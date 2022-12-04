import java.util.ArrayList;
import java.util.List;

interface Player {
    int[] chooseCell(GameField gf);
}

public class Main {
    public static void main(String[] args) {
        Reversy.startGame();
    }
}