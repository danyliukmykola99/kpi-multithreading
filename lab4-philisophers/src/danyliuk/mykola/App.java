package danyliuk.mykola;

import java.util.ArrayList;

/**
 * @author Mykola Danyliuk
 */
public class App {

    public void start() throws InterruptedException {

        ArrayList<Fork> forks = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            forks.add(new Fork("Fork-" + (i + 1)));
        }

        ArrayList<Philosopher> philosophers = new ArrayList<>();
        for (int i = 0; i < 4; ++i) {
            philosophers.add(new Philosopher("Philosopher-" + (i + 1) ,forks.get(i), forks.get(i + 1)));
        }

        philosophers.add(new Philosopher("Philosopher-5", forks.get(0),forks.get(4)));

        philosophers.forEach(Thread::start);

        for (Philosopher philosopher : philosophers) {
            philosopher.join();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        App app = new App();
        app.start();
    }

}
