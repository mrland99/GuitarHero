/******************************************************************************

 *  Description:  data type to model a vibrating harp
 * 
 *****************************************************************************/
public class Harp {
    private static final double SCALE = 0.999/2; // constant energy decary 
    // factor
    private static final int SAMPLING_RATE = 44100; // constant sampling rate
    private RingBuffer ringBuffer; // declaring ringBuffer 
    //  creates a harp string sound using a sampling 
    // rate of 44,100
    public         Harp(double frequency) {
        int n = (int) Math.ceil(SAMPLING_RATE/frequency);
        ringBuffer = new RingBuffer(n);
        for (int i = 0; i < n; i++)
        {
            ringBuffer.enqueue(0);
        }   
    }
    //  creates a harp sound whose length and initial values are given
    // by the specified array
    public         Harp(double[] init) 
    {
        ringBuffer = new RingBuffer(init.length);
        for (int i = 0; i < init.length; i++)
        {
            ringBuffer.enqueue(init[i]);
        }
    }
    //  returns the number of samples in the ring buffer
    public     int length() {
        return ringBuffer.size();
    }                        
    //  plucks the string (by replacing the ring buffer 
    // with white noise)
    public    void pluck(double loudness) {
        while (!ringBuffer.isEmpty()) {
            ringBuffer.dequeue();
        }
        for (int i = 0; i < ringBuffer.capacity(); i++) {
            ringBuffer.enqueue((StdRandom.uniform() - 0.5) * loudness);
        }
    }                      
    //  advances the Karplus-Strong simulation one time step
    public    void tic() {
        ringBuffer.enqueue((ringBuffer.dequeue()+ringBuffer.peek())*SCALE*-1);
    }                    
    //  returns the current sample
    public  double sample() {
        return ringBuffer.peek();
    }                  
    //  tests this class by directly calling both constructors 
    // and all instance methods
    public static void main(String[] args) {
        Harp  harp = new  Harp(440.0);
        double[] test = new double[10];
        for (int i = 0; i < test.length; i++)
            test[i]  = -0.5 + 0.1*i;
         Harp  harp2 = new  Harp(test);
         harp.pluck(1);
         harp.tic();
        System.out.println(harp2.length());
        System.out.println(harp.sample());
    }
    
}