import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        testBuffer();
    }

    private static void testBuffer() {
        int BUFFER_SIZE = 100;
        int FIRST_BUFFER_THREADS = 5;
        int SECOND_BUFFER_THREADS = 2;

        List<Thread> threads = new ArrayList<>();

        CircularBuffer<String> buffer1 = new CircularBuffer<>(BUFFER_SIZE);
        CircularBuffer<String> buffer2 = new CircularBuffer<>(BUFFER_SIZE);

        for(int i = 0; i < FIRST_BUFFER_THREADS; i++) {
            Thread thread = new Thread(() -> {
                for(int j = 0; j < BUFFER_SIZE/FIRST_BUFFER_THREADS; j++) {
                    Random rand = new Random();
                    buffer1.addElement("Потік " + Thread.currentThread().getName() + "  згенерував повідомлення з числом: " + rand.nextInt(100));
                }
            });
            thread.setDaemon(true);
            threads.add(thread);
        }

        for(int i = 0; i < SECOND_BUFFER_THREADS; i++) {
            Thread thread = new Thread(() -> {
                for(int j = 0; j < BUFFER_SIZE/SECOND_BUFFER_THREADS; j++) {
                    var element = buffer1.getElement();
                    var str = "Потік " + Thread.currentThread().getName() + "  переклав повідомлення " + element;
                    buffer2.addElement(str);
                }
            });
            thread.setDaemon(true);
            threads.add(thread);
        }

        for(Thread thread : threads) {
            thread.start();
        }

        for(int i = 0; i < buffer2.getSize(); i++) {
            System.out.println(buffer2.getElement());
        }
    }

    private static void testBank() throws InterruptedException {
        Random rand = new Random();
        Bank bank = new Bank();

        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            var acc = new  Account();
            acc.deposit(rand.nextInt(100));
            accounts.add(acc);
        }

        int initialSum = accounts.stream().mapToInt(Account::getBalance).sum();
        System.out.println("Initial total balance: " + initialSum);

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            Thread thread = new Thread(() -> {
                Account from = accounts.get(rand.nextInt(accounts.size()));
                Account to = accounts.get(rand.nextInt(accounts.size()));
                int amount = rand.nextInt(50);

                if (from != to) {
                    bank.transfer(from, to, amount);
                }
            });

            threads.add(thread);
        }

        for(Thread thread : threads) {
            thread.start();
        }

        for(Thread thread : threads) {
            thread.join();
        }

        int finalSum = accounts.stream().mapToInt(Account::getBalance).sum();
        System.out.println("Final total balance: " + finalSum);
    }
}
