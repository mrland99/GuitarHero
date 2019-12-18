/******************************************************************************
 * 
 *  Description:  A data type that models a ring buffer
 * 
 *****************************************************************************/
public class RingBuffer {
    private double[] samples; // the array containing the ring buffer data
    private int n; // capacity
    private int first = 0; // index of least recently inserted item
    private int last = 0; // index one beyond the most recently inserted item
    private int size = 0; // number of items currently in ring buffer
    
    //  creates an empty ring buffer with the specified capacity
    public         RingBuffer(int capacity)  {
        samples = new double[capacity];
        n = capacity;
    }
    
    //  returns the capacity of this ring buffer
    public     int capacity() {               
        return n;
    }
    
    //  returns the number of items currently in this ring buffer
    public     int size() {
        return size;
    }
    
    //  is this ring buffer empty (size equals zero)?
    public boolean isEmpty() {                
        return size == 0;
    }
    
    //  is this ring buffer full (size equals capacity)?
    public boolean isFull() {                 
        return size == n;
    }
    
    //  adds item x to the end of this ring buffer
    public    void enqueue(double x) {
        if (size == n) {
            throw new RuntimeException("Cannot enqueue. Ring buffer is full");
        }
        size++;
        samples[last] = x;
        last = (last + 1) % n; // increment last
    }
    
    //  deletes and returns the item at the front of this ring buffer
    public  double dequeue() {
        if (size == 0) {
            throw new RuntimeException("Cannot dequeue. Ring buffer is empty");
        }
        size--;
        double result = samples[first];
        samples[first] = 0.0;
        first = (first + 1) % n;
        return result;
    }
    
    //  returns the item at the front of this ring buffer
    public  double peek() {
        if (size == 0) {
            throw new RuntimeException(
                                  "cannot call peek. Ring buffer is empty");
        }
        return samples[first];
    }
    
    //  tests this class by directly calling all instance method
    public static void main(String[] args) {
        RingBuffer ringBuffer = new RingBuffer(10);
        ringBuffer.enqueue(0.1);
        ringBuffer.enqueue(0.2);
        ringBuffer.enqueue(0.4);
        ringBuffer.dequeue();
        System.out.println(ringBuffer.capacity());
        System.out.println(ringBuffer.size());
        System.out.println(ringBuffer.isEmpty());
        System.out.println(ringBuffer.isFull());
        System.out.println(ringBuffer.peek());
    }
}