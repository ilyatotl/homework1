import java.util.concurrent.TimeUnit;

public class HardBot implements Player {
    private final int color;
    HardBot(int val) {
        color = val;
    }

    @Override
    public int[] chooseCell(GameField gf) {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ignored) {
        }

        int[] res = new int[2];
        float val = -100;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!gf.checkStep(i, j, color)) {
                    continue;
                }
                float curVal = gf.valStep(i, j, color);
                gf.makeStep(i, j, color);
                float mx = 0;
                for (int enemyLine = 0; enemyLine < 8; enemyLine++) {
                    for (int enemyCol = 0; enemyCol < 8; enemyCol++) {
                        mx = Math.max(mx, gf.valStep(enemyLine, enemyCol, 3 - color));
                    }
                }
                if (curVal - mx > val) {
                    val = curVal - mx;
                    res[0] = i;
                    res[1] = j;
                }
                gf.cancelStep();
            }
        }
        return res;
    }
}
