package exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

class App {
    private static final Logger LOGGER = Logger.getLogger("AppLogger");

    // BEGIN

    public static void main(String[] args) {
        int[] numbers = {10, -4, 67, 100, -100, 8};
        System.out.println(App.getMinMax(numbers)); // => {min=-100, max=100}
        LOGGER.info(App.getMinMax(numbers).toString());
    }

    private static Map<String, Integer> getMinMax(int[] numbers) {
        MaxThread maxThread = new MaxThread(numbers);
        MinThread minThread = new MinThread(numbers);
        maxThread.start();
        minThread.start();
        try {
            maxThread.join();
            minThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Map<String, Integer> result = new HashMap<>(2);
        result.put("min", minThread.getResult());
        result.put("max", maxThread.getResult());
        return result;
    }

    // END
}
