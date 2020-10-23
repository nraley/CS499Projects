import javax.swing.*;
import java.awt.*;


public class Project2 extends Synth
	{
    public static void main(String[] args) 
    	{
    	Synth.run(new Project2(), args);
    	}

    public void setup() 
    	{
    	// Implement me:
    	
    	// Build a MidiModule
    	// Build a MidiGate
    	// Create the window
    	// Build the oscillators
    	// Build dials for the amplitude of each of the oscillators, plus
    	//       the pulse width of the square and triangle
    	// Put them in a box and put it in the window
    	// Build a mixer for the various oscillators
    	
    	/// NEW IN PROJECT 2.5
    	// Build an ADSR for the filter
    	// Build dials for the ADSR and put it in a box
    	// Build dials for the Cutoff and Resonance of the filter
    	// Put them in a box 
    	// Build a Mul to multiply the cutoff by the ADSR output
    	// Feed the Mul as the cutoff for a LOW PASS FILTER (also feed in the resonance)
    	// END NEW IN PROJECT 2.5
    	
    	// Build an ADSR for the VCA
    	// Build a VCA controlled by the ADSR which gets its input from the FILTER <--- NOTE CHANGE
    	// Make an output box
    	// Add a Gain amplifier
    	// Add an oscilloscope
    	// Set the output to the gain amplifier (or oscilloscope)
    	// Pack and display the window
        }
        
    }

