package danyliuk.mykola;

import java.util.concurrent.Semaphore;

/**
 * @author Mykola Danyliuk
 */
public class Fork {
    private Semaphore lock;
    private String name;
    private volatile boolean free;

    Fork(String name) {
        lock = new Semaphore(1);
        this.name = name;
        free = true;
    }

    boolean take(boolean look) throws InterruptedException {
        boolean status = false;
        if (free || look) {
            lock.acquire();
            free = false;
            status = true;
        }

        return status;
    }

    void put() {
        lock.release();
        free = true;
    }

    public String toString() {
        return name;
    }

}
