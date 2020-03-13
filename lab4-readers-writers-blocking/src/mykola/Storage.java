package mykola;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Mykola Danyliuk
 */
public class Storage {

    private ReentrantLock access = new ReentrantLock();
    private ReentrantLock readSem = new ReentrantLock();
    private volatile int readCount;
    private String storage;

    Storage() {
        storage = null;
    }

    String read() {
        readSem.lock();
        readCount++;
        if (readCount == 1) {
            access.lock();
        }
        readSem.unlock();
        String result = this.storage;
        readSem.lock();
        readCount--;
        if (readCount == 0) {
            access.unlock();
        }
        readSem.unlock();
        return result;
    }

    void write(String string) {
        access.lock();
        storage = string;
        access.unlock();
    }

}
