package danyliuk.mykola;

import java.util.Random;

/**
 * @author Mykola Danyliuk
 */
public class Consumer extends Thread {

    private static Random random = new Random(System.currentTimeMillis());
    private String name;
    private Queue queue;

    Consumer(String name, Queue queue) {
        this.name = name;
        this.queue = queue;
    }

    public void run() {
        while (!isInterrupted()) {
            String string;
            try {
                string = queue.remove();
                System.out.println(name + " consuming: " + string);
                sleep(random.nextInt(700) + 700);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}

