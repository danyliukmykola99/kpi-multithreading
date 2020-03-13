package mykola;

/**
 * @author Mykola Danyliuk
 */
public class Reader extends Thread {

    private String name;
    private Storage storage;

    public Reader(String name, Storage storage) {
        this.name = name;
        this.storage = storage;
    }

    @Override
    public void run() {
        System.out.println(name + " wants to read");

        String text;
        text = storage.read();

        System.out.println("\n" + name + " read:" + text + "\n");
    }

}
