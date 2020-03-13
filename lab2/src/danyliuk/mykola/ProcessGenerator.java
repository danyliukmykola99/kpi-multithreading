package danyliuk.mykola;

import java.time.OffsetDateTime;
import java.util.Random;

/**
 * @author Mykola Danyliuk
 */
public class ProcessGenerator extends Thread {

    private int counter = 0;

    private final int processesCount;
    private final int minProcessTime;
    private final int maxProcessTime;
    private final Kernel kernel;
    private boolean active;

    private static final Random random = new Random();

    public ProcessGenerator(int processesCount,
                            int minProcessTime, int maxProcessTime, Kernel kernel) {
        this.processesCount = processesCount;
        this.minProcessTime = minProcessTime;
        this.maxProcessTime = maxProcessTime;
        this.kernel = kernel;
        this.active = true;
    }

    @Override
    public void run() {
        for(int i = 0; i < processesCount; i++){
            int processTime = random(minProcessTime, maxProcessTime);
            int number = counter++;
            System.out.println(OffsetDateTime.now() + " Generated process #" + number);
            CPUProcess process = new CPUProcess(processTime, number);
            kernel.addProcess(process);
        }
        active = false;
    }

    private int random(int a, int b){
        return a + random.nextInt(b - a);
    }

    public boolean isActive() {
        return active;
    }
}
