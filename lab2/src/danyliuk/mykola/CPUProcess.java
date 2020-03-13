package danyliuk.mykola;

/**
 * @author Mykola Danyliuk
 */
public class CPUProcess {

    private int time;
    private int number;

    public CPUProcess(int time, int number) {
        this.time = time;
        this.number = number;
    }

    public int getTime() {
        return time;
    }

    public int getNumber() {
        return number;
    }
}
