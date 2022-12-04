import java.util.InputMismatchException;
import java.util.concurrent.TimeUnit;

public class EasyBot implements Player {
    private final int color;

    EasyBot(int val) {
        color = val;
    }

    @Override
    public int[] chooseCell(GameField gf) {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ignored) {
        }

        int[] res = new int[2];
        float val = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                float curVal = gf.valStep(i, j, color);
                if (curVal > val) {
                    val = curVal;
                    res[0] = i;
                    res[1] = j;
                }
            }
        }
        return res;
    }
}
