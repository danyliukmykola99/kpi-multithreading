package danyliuk.mykola;

import java.util.concurrent.Semaphore;

/**
 * @author Mykola Danyliuk
 */
public class Storage {

    private Semaphore access;
    private volatile int readCount;
    private String storage;

    Storage() {
        access = new Semaphore(1);
        storage = null;
    }

    String read() throws InterruptedException {
        readCount++;
        if (readCount == 1) {
            access.acquire();
        }
        String result = this.storage;
        readCount--;
        if (readCount == 0) {
            access.release();
        }
        return result;
    }

    void write(String string) throws InterruptedException {
        access.acquire();
        storage = string;
        access.release();
    }

}
