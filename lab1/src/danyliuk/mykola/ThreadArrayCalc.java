package danyliuk.mykola;

public class ThreadArrayCalc implements Runnable {

    private double[] vector;
    private double norm;

    private int indexFrom;
    private int indexTo;

    public ThreadArrayCalc(double[] vector, int indexFrom, int indexTo) {
        this.vector = vector;
        this.indexFrom = indexFrom;
        this.indexTo = indexTo;
    }

    public double getResult(){
        return norm;
    }

    @Override
    public void run() {
        this.norm = 0.0;

        for (int i = 0; i < indexTo - indexFrom; i++) {
            int vecIndex = i + indexFrom;
            this.norm = this.norm + this.vector[vecIndex];
        }
    }
}
