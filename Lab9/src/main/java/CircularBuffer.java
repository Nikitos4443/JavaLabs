public class CircularBuffer<T> {

    private int head;
    private int tail;
    private volatile T[] buffer;
    private int size;

    public CircularBuffer(int size) {
        if(size < 1) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }
        this.size = size;
        buffer = (T[]) new Object[size+1];
    }

    public synchronized void addElement(T value) {
        while((tail + 1)%buffer.length == head) {
//           System.out.println("CircularBuffer is full");
           try {
               wait();
           } catch (InterruptedException ignored) {}
        }

        buffer[tail] = value;
        tail = (tail + 1) % buffer.length;
//        System.out.println("Element added");
        notifyAll();
    }

    public synchronized T getElement() {
        while(tail == head) {
//            System.out.println("CircularBuffer is empty");
            try {
                wait();
            } catch (InterruptedException ignored) {}
        }

        T element = buffer[head];
        head = (head + 1) % buffer.length;
        notifyAll();
//        System.out.println("Element got");
        return element;
    }

    public int getSize() {
        return size;
    }
}


