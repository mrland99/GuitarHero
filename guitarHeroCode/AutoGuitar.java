/******************************************************************************
 * 
 *  Description: Get RickRolled.
 * 
 *****************************************************************************/
import java.util.Arrays;
public class AutoGuitar {
    private static final double CONCERT_A = 440.0; // frequency for concert A
    
    // returns the index of the note given, to be used in the Harp array
    private static int getIndex(String noteName) {
        String[] noteNames = 
        {"C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B"};
        if (noteName == null || noteName.equals("R")) {
            return -1;
        }
        int octave = noteName.charAt(noteName.length() - 1) - '0';
        String note = noteName.substring(0, noteName.length() - 1);
        int index = Arrays.asList(noteNames).indexOf(note) + 
            (octave - 1) * 12 - 9;

        return index;
    }
    public static void main(String[] args) {
        
        // constant length of keyboard (37)
        final int KEYBOARD_LENGTH = 49;
        // array of harp strings
        Harp[] notes = new Harp[KEYBOARD_LENGTH];
        
        int songLength = 320; // number of beats in song; adjust for testing
        int repeatSection = 124; // number of beats in chorus to repeat
        // the notes in the song, R is rest        
        In inputFile = new In("notes.txt");
        String[] songNotes = new String[songLength]; // first voice (melody)
        for (int i = 0; i < songLength; i++) {
            songNotes[i] = inputFile.readString();
        }
        
        String[] songNotes2 = new String[songLength]; // second voice (soprano)
        for (int i = 0; i < songLength; i++) {
            if (!inputFile.isEmpty())
                songNotes2[i] = inputFile.readString();
        }
        
        String[] songNotes3 = new String[songLength]; // third voice (tenor)
        for (int i = 0; i < songLength; i++) {
            if (!inputFile.isEmpty())
                songNotes3[i] = inputFile.readString();
        }

        inputFile.close();
        
        // initializing each individual harp string
        for (int i = 0; i < KEYBOARD_LENGTH; i++)
        {
            notes[i] = new Harp(CONCERT_A * Math.pow(2, (i-36)/12.) * 2);
        }
        
        int tempo = 130 * 4; // tempo in beats per minute
        int millisBetweenBeats = 60000 / tempo; // milliseconds between beats
        
        int songIndex = 0; // current position in the song array
        int chorusRuns = 0; // number of chorus runs elapsed
        // the main input loop     
        while (chorusRuns < 2) {
            if (songIndex == songLength || 
                (songIndex == repeatSection && chorusRuns > 0)) {
                songIndex = 0; // loop back to the chorus once
                chorusRuns++;
            }
            int index = -1;
            int index2 = -1;
            int index3 = -1;
            
            // find index of note in Harp array
            index = getIndex(songNotes[songIndex]);
            index2 = getIndex(songNotes2[songIndex]);
            index3 = getIndex(songNotes3[songIndex]);
            
            // increment through the song
            songIndex++;
            
            // pluck the corresponding string unless it's a rest
            if (index > -1 && index <= KEYBOARD_LENGTH)
                notes[index].pluck(1);
            if (index2 > -1 && index2 <= KEYBOARD_LENGTH)
                notes[index2].pluck(1);
            if (index3 > -1 && index3 <= KEYBOARD_LENGTH)
                notes[index3].pluck(0.5);

            // send the result to standard audio
            for (int i = 0; i < 44100.0 * millisBetweenBeats / 1000.0; i++) { 
                // compute superposition
                double sample = 0.0;
                for (int j = 0; j < KEYBOARD_LENGTH; j++)
                    sample += notes[j].sample();
            
            
            
                StdAudio.play(sample);
                // advance the simulation of each guitar string by one step
                for (int j = 0; j < KEYBOARD_LENGTH; j++)
                    notes[j].tic();
            }
        }
    }

}