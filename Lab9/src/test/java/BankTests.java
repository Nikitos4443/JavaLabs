import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class BankTests {
    @Test
    public void testAccountBalanceAndDeposit() {
        Account account = new Account();
        account.deposit(100);
        assertEquals(100, account.getBalance());
    }

    @Test
    public void testAccountWithDraw() {
        Account account = new Account();
        account.deposit(100);
        account.withdraw(100);
        assertEquals(0, account.getBalance());
    }

    @Test
    public void testAccountWithDrawException() {
        Account account = new Account();
        account.deposit(100);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(200));
    }

    @Test
    public void testBankTransferInOneThread() {
        Account account1 = new Account();
        account1.deposit(100);

        Account account2 = new Account();

        Bank bank = new Bank();
        bank.transfer(account1, account2, 50);

        assertEquals(50, account1.getBalance());
        assertEquals(50, account2.getBalance());
    }

    @Test
    public void testBankTransferInManyThreads() {
        Random rand = new Random();
        Bank bank = new Bank();

        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            var acc = new  Account();
            acc.deposit(rand.nextInt(100));
            accounts.add(acc);
        }

        int initialSum = accounts.stream().mapToInt(Account::getBalance).sum();

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

        try {
            for(Thread thread : threads) {
                thread.join();
            }
        }catch (InterruptedException e){
            fail("Thread was interrupted");
        }

        int finalSum = accounts.stream().mapToInt(Account::getBalance).sum();

        assertEquals(initialSum, finalSum);
    }
}
