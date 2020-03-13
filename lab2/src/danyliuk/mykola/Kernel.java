package danyliuk.mykola;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author Mykola Danyliuk
 */
public class Kernel {

    private final int PROCESSES_COUNT = 100;

    private final CPU cpu;
    private List<CPUQueue> queues;
    private ProcessGenerator processGenerator;

    public Kernel() {
        cpu = new CPU(this);
        processGenerator = new ProcessGenerator(PROCESSES_COUNT, 100, 200, this);
        queues = Arrays.asList(new CPUQueue(0), new CPUQueue(1));
    }

    public void run(){
        processGenerator.start();
        cpu.start();
    }

    public void printStatistic() {
        queues.forEach(q -> System.out.println("Polls percentage in queue#" + q.getNumber() + " - " + (double) q.getPollCount() * 100 / PROCESSES_COUNT + "%"));
    }

    public void addProcess(CPUProcess process){
        CPUQueue queueWithMinLength = queues.stream().min(Comparator.comparing(CPUQueue::size)).get();
        queueWithMinLength.add(process);
        synchronized (cpu) {
            cpu.notify();
        }
    }

    public CPUProcess removeProcess(){
        CPUQueue queueWithMaxLength = queues.stream().max(Comparator.comparing(CPUQueue::size)).get();
        return queueWithMaxLength.poll();
    }

    public boolean isActive() {
        return processGenerator.isActive() ||
                queues.stream().max(Comparator.comparing(CPUQueue::size)).get().size() != 0;
    }

}