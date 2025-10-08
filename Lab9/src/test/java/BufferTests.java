import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class BufferTests {
    @Test
    public void testSimpleInteraction() {
        CircularBuffer<String> buffer = new CircularBuffer<>(10);
        var firstElement = "test";
        var secondElement = "test2";
        var thirdElement = "test3";

        buffer.addElement(firstElement);
        buffer.addElement(secondElement);
        buffer.addElement(thirdElement);

        var firstReceived = buffer.getElement();
        var secondReceived = buffer.getElement();
        var thirdReceived = buffer.getElement();

        assertEquals(firstElement, firstReceived);
        assertEquals(secondElement, secondReceived);
        assertEquals(thirdElement, thirdReceived);
    }

    @Test
    public void testSeveralThreadsInteraction() throws InterruptedException {
        int BUFFER_SIZE = 100;
        int FIRST_BUFFER_THREADS = 5;
        int SECOND_BUFFER_THREADS = 2;

        List<Thread> threads = new ArrayList<>();

        CircularBuffer<String> buffer1 = new CircularBuffer<>(BUFFER_SIZE);
        CircularBuffer<String> buffer2 = new CircularBuffer<>(BUFFER_SIZE);

        List<String> collectedFromBuffer2 = Collections.synchronizedList(new ArrayList<>());

        for (int i = 0; i < FIRST_BUFFER_THREADS; i++) {
            Thread t = new Thread(() -> {
                Random rand = new Random();
                for (int j = 0; j < BUFFER_SIZE / FIRST_BUFFER_THREADS; j++) {
                    buffer1.addElement("Producer " + Thread.currentThread().getName() + " msg=" + rand.nextInt(100));
                }
            });
            threads.add(t);
        }

        for (int i = 0; i < SECOND_BUFFER_THREADS; i++) {
            Thread t = new Thread(() -> {
                for (int j = 0; j < BUFFER_SIZE / SECOND_BUFFER_THREADS; j++) {
                    var element = buffer1.getElement();
                    buffer2.addElement("Translated " + element);
                }
            });
            threads.add(t);
        }

        Thread collector = new Thread(() -> {
            for (int i = 0; i < BUFFER_SIZE; i++) {
                collectedFromBuffer2.add(buffer2.getElement());
            }
        });
        threads.add(collector);

        threads.forEach(Thread::start);

        for (Thread t : threads) {
            t.join(3000);
        }

        assertEquals(BUFFER_SIZE, collectedFromBuffer2.size());
        assertTrue(collectedFromBuffer2.stream().noneMatch(Objects::isNull));
    }

}
