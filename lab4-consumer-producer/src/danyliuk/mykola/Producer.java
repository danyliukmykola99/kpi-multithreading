package danyliuk.mykola;

import java.util.Random;

/**
 * @author Mykola Danyliuk
 */
public class Producer extends Thread {

    private Random random = new Random(System.currentTimeMillis());
    private String name;
    private Queue queue;

    public Producer(String name, Queue queue) {
        this.name = name;
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; ++i) {

            String producedString = name + "-" + "String-" + (i + 1);
            System.out.println(name + " produced: " + producedString);

            try {
                queue.add(producedString);
                sleep(random.nextInt(300) + 300);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
