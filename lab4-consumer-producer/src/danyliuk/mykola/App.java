package danyliuk.mykola;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mykola Danyliuk
 */
public class App {

    private void start() throws InterruptedException {
        Queue queue = new Queue();

        List<Producer> producers = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            producers.add(new Producer("Producer-" + (i + 1), queue));
        }

        List<Consumer> consumers = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            consumers.add(new Consumer("Consumer-" + (i + 1), queue));
        }

        producers.forEach(Thread::start);
        consumers.forEach(Thread::start);

        try {
            Thread.sleep(10000);
        } finally {
            System.out.println("Terminate all process.");
            consumers.forEach(Thread::interrupt);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        App app = new App();
        app.start();
    }

}