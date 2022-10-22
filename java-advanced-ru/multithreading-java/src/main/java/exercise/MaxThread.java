package exercise;

import java.util.Arrays;
import java.util.logging.Logger;

// BEGIN
public class MaxThread extends Thread {
    private static final Logger LOGGER = Logger.getLogger("MaxThread");
    private int[] numbers;
    private int result;

    public MaxThread(int[] numbers) {
        this.numbers = numbers;
    }

    @Override
    public void run() {
        LOGGER.info(Thread.currentThread().getName() + ": started");
        result = Arrays.stream(numbers).max().getAsInt();
        LOGGER.info(Thread.currentThread().getName() + ": finished");
    }

    public int getResult() {
        return result;
    }
}
// END
