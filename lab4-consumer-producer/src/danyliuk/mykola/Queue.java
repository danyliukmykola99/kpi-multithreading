package danyliuk.mykola;


/**
 * @author Mykola Danyliuk
 */
public class Queue {

    private final Object lock = new Object();
    private final String[] items = new String[100];
    private int count, addPointer, removePointer;

    public void add(String s) throws InterruptedException {
        synchronized (lock) {
            if (count == items.length){
                lock.wait();
                add(s);
            }
            items[addPointer] = s;
            if (++addPointer == items.length) addPointer = 0;
            ++count;
        }
    }

    public String remove() throws InterruptedException {
        synchronized (lock) {
            if (count == 0){
                lock.wait();
                remove();
            }
            String s = items[removePointer];
            if (++removePointer == items.length)
                removePointer = 0;

            --count;
            return s;
        }
    }

}
