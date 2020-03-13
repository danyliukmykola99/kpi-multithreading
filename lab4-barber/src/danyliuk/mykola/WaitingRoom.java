package danyliuk.mykola;

import java.util.LinkedList;

/**
 * @author Mykola Danyliuk
 */
public class WaitingRoom {

    private  int placesInWaitingRoom = 3;
    private final Object lock = new Object();
    private LinkedList<Customer> customers = new LinkedList<>();

    Customer getCustomerForHaircut() throws InterruptedException {

        synchronized (lock) {
            Customer customer = customers.pollFirst();
            if (customer == null) {
                lock.wait();
                customer = customers.pollFirst();
            }

            placesInWaitingRoom++;
            return customer;
        }
    }

    boolean getAHaircut(Customer customer) throws InterruptedException {
        synchronized (lock) {
            boolean status = false;

            if(placesInWaitingRoom > 0) {
                customers.push(customer);
                status = true;
                placesInWaitingRoom--;
                lock.notify();

            }

            return status;
        }
    }
}
