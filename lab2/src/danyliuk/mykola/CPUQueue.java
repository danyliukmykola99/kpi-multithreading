package danyliuk.mykola;

import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Mykola Danyliuk
 */
public class CPUQueue {

    private int number;
    private final Queue<CPUProcess> data = new LinkedList<>();
    private int pollCount = 0;

    public CPUQueue(int number) {
        this.number = number;
    }

    public void add(CPUProcess process){
        synchronized (data) {
            data.add(process);
            System.out.println(OffsetDateTime.now() + " Added process#" + process.getNumber() + " in queue#" + number + " ,size = " + size());
        }
    }

    public CPUProcess poll(){
        synchronized (data) {
            CPUProcess p = data.poll();
            if(p != null){
                pollCount++;
                System.out.println(OffsetDateTime.now() + " Polled process#" + p.getNumber() + " from queue#" + number + ", size = " + size());
            }
            return p;
        }
    }

    public int size(){
        return data.size();
    }

    public int getNumber() {
        return number;
    }

    public int getPollCount() {
        return pollCount;
    }
}
