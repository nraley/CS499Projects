import javax.swing.*;
import java.awt.*;


public class Project3 extends Synth
	{
    public static void main(String[] args) 
    	{
    	Synth.run(new Project3(), args);
    	}

    public void setup() 
    	{
    	// Implement me:
    	
    	// Build a MidiModule
    	// Build a MidiGate
    	// Create the window
    	// FOR EACH OPERATOR (4 operators)
    	//		Make a box
    	//		Make the operator
    	//		Add Relative Frequency
    	//		Add an ADSR
    	//		Add Attack Time, Decay Time, Sustain, Release Time
    	//		*Multiply* (not amplify) the ADSR output against a Gain
		//	 	Add a Mixer to mix in incoming signals from all four operators
		//		Add dials for the four signals
		// 		Add an Out dial
		// Make an Other box
		// Add Algorithm.  I'm nice and provided most of it for you below
		// Make a final mixer whose inputs are the operators, controlled by their Out dials
		// Make a VCA fed by the mixer, controlled by a final Gain dial
		// Make an oscilloscope fed by the VCA.  I'd set	 oscope.setDelay(1);
		// Output the VCA or oscilloscope
		// Pack and display the window
		
		
		
		// Some code you might find useful
		
        Dial[] outs = new Dial[4];				// Out dial
        Dial[] dials[][] = new Dial[4][4];		// Incoming modulation dial [To][From]
        
    	final String[] ALGORITHMS = new String[]
    		{
    		"1>2>3>4",
    		"1>2>4, 3",
    		"1>2>4, 3>4",
    		"1>4, 2>4, 3>4",
    		"1>2, 3>4",
    		"1>2, 3, 4",
    		"1, 2, 3, 4"
    		};

        Options opt = new Options("Algorithms", ALGORITHMS, 6)
            {
            public void update(int val)
                {
                // This is a race condition, but I'll just update the dials, it won't be too bad
                for(int i = 0; i < dials.length; i++)
                    for(int j = 0; j < dials[i].length; j++)
                        dials[i][j].getModule().setValue(0);

                for(int i = 0; i < outs.length; i++)
                    outs[i].getModule().setValue(0);
                                
                switch(val)
                    {
                    case 0:
                        dials[3][2].getModule().setValue(1);
                        dials[2][1].getModule().setValue(1);
                        dials[1][0].getModule().setValue(1);
                        outs[3].getModule().setValue(1);
                        break;
                    case 1:
                        dials[3][1].getModule().setValue(1);
                        dials[1][0].getModule().setValue(1);
                        outs[3].getModule().setValue(1);
                        outs[2].getModule().setValue(1);
                        break;
                    case 2:
                        dials[3][1].getModule().setValue(1);
                        dials[1][0].getModule().setValue(1);
                        dials[3][2].getModule().setValue(1);
                        outs[3].getModule().setValue(1);
                        break;
                    case 3:
                        dials[3][1].getModule().setValue(1);
                        dials[3][0].getModule().setValue(1);
                        dials[3][2].getModule().setValue(1);
                        outs[3].getModule().setValue(1);
                        break;
                    case 4:
                        dials[1][0].getModule().setValue(1);
                        dials[3][2].getModule().setValue(1);
                        outs[3].getModule().setValue(1);
                        outs[1].getModule().setValue(1);
                        break;
                    case 5:
                        dials[1][0].getModule().setValue(1);
                        outs[3].getModule().setValue(1);
                        outs[2].getModule().setValue(1);
                        outs[1].getModule().setValue(1);
                        break;
                    case 6:
                        outs[3].getModule().setValue(1);
                        outs[2].getModule().setValue(1);
                        outs[1].getModule().setValue(1);
                        outs[0].getModule().setValue(1);
                        break;
                    }
                }
            };
        }
        
    }

