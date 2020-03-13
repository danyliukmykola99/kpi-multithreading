package danyliuk.mykola;

/**
 * @author Mykola Danyliuk
 */
public class Philosopher extends Thread {

    private String name;
    private Fork firstFork;
    private Fork secondFork;

    Philosopher(String name, Fork firstFork, Fork secondFork) {
        this.name = name;
        this.firstFork = firstFork;
        this.secondFork = secondFork;
    }

    public void run() {

        for (int i = 0; i < 50; ++i) {
            System.out.println(name + " is going to eat");
            try {
                firstFork.take(true);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(name + " took first fork " + firstFork);

            try {
                sleep(15);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            boolean status = false;
            try {
                status = secondFork.take(false);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (status) {
                System.out.println(name + " took second fork " + secondFork);
                System.out.println(name + " is eating");

                secondFork.put();
                System.out.println(name + " put second fork " + secondFork);
            }

            firstFork.put();
            System.out.println(name + " put first fork " + firstFork);

            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
