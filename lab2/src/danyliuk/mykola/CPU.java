package danyliuk.mykola;

import java.time.OffsetDateTime;

/**
 * @author Mykola Danyliuk
 */
public class CPU extends Thread {

    private Kernel kernel;

    public CPU(Kernel kernel) {
        this.kernel = kernel;
    }

    @Override
    public void run() {
        synchronized (this) {
            while (kernel.isActive()) {
                CPUProcess process = kernel.removeProcess();
                if (process == null){
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    int processTime = process.getTime();
                    try {
                        System.out.println(OffsetDateTime.now() + " Start executing process #" + process.getNumber());
                        CPU.sleep(processTime);
                        System.out.println(OffsetDateTime.now() + " Finish executing process #" + process.getNumber());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            kernel.printStatistic();
        }
    }
}