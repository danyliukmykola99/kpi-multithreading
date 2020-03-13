package mykola;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Mykola Danyliuk
 */
public class App {

    private static Random random = new Random(System.currentTimeMillis());

    private void start() throws InterruptedException {

        Storage storage = new Storage();

        List<Writer> writers = new ArrayList<>();

        for (int i = 0; i < 5; ++i) {
            writers.add(new Writer("Writer-" + (i + 1), storage));
        }

        writers.forEach(Thread::start);

        for (int i = 0; i < 300; ++i) {
            new Reader("Reader-" + (i + 1), storage).start();

            Thread.sleep(10);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        App app = new App();
        app.start();
    }
}