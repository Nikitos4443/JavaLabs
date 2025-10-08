import java.util.concurrent.locks.Lock;

public class Bank {
    public void transfer(Account from, Account to, int amount) {
        if(from.equals(to)) {
            throw new IllegalArgumentException("Sender can not be equal to receiver");
        }

        Lock fromLock = from.getLock();
        Lock toLock = to.getLock();

        Lock first = from.hashCode() > to.hashCode() ?  fromLock : toLock;
        Lock second = from.hashCode() > to.hashCode() ?  toLock : fromLock;

        first.lock();
        second.lock();
        try {
            from.withdraw(amount);
            to.deposit(amount);
        }catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }finally {
            first.unlock();
            second.unlock();
        }
    }
}
