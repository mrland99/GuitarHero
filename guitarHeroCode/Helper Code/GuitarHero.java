/******************************************************************************
 
 * 
 *  Description: Uses keyboard input to simulate the sound of guitar strings.
 * 
 *****************************************************************************/
public class GuitarHero {
   
    public static void main(String[] args) {
        
        // keyboard keys corresponding to notes
        final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' "; 
        // constant length of keyboard (37)
        final int KEYS = keyboard.length();
        // array of guitarStrings
        GuitarString[] notes = new GuitarString[KEYS];
        double CONCERT_A = 440.0;
        
        // initializing each individual guitar string
        for (int i = 0; i < KEYS; i++)
        {
            notes[i] = new GuitarString(CONCERT_A * Math.pow(2, (i-24)/12.));
        }

        // the main input loop
        while (true) {

            // check if the user has typed a key, and, if so, process it
            int index = -1;
            if (StdDraw.hasNextKeyTyped()) {
                
                // the user types this character
                char key = StdDraw.nextKeyTyped();
                index = keyboard.indexOf(key);
                
                // pluck the corresponding string
                if (index > -1)
                    notes[index].pluck();
            }
            
            // compute superposition
            double sample = 0.0;
            for (int i = 0; i < KEYS; i++)
                sample += notes[i].sample();
            
            
            // send the result to standard audio
            StdAudio.play(sample);
            
            // advance the simulation of each guitar string by one step
            for (int i = 0; i < KEYS; i++)
                notes[i].tic();
        }
    }

}
