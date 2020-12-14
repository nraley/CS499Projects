import java.awt.*;
import java.awt.geom.*;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.concurrent.locks.*;
/*
	Another GUI module that should present itself as a very primitive immitation of the drum sequencer from
	FLStudio. 8 steps for one sequence by default, each step being represented by a pair of 1) a dial for velocity 
	and 2) a dropdown menu for representing the note to be programmed in that sequence. 
*/

public class Sequencer 
	{
	public static final String[] NOTES = new String[]{"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#","G","G#"};
		public Sequencer(){
		// create the 8 dropdowns for pitch
		ArrayList<Options> pitchSteps = new ArrayList<Options>();
			for(int i = 0; i < 8; i++){
				pitchSteps.add(new Options("", NOTES, 0));
			}
		// create the 8 dials for velocity
		ArrayList<Dial> velSteps = new ArrayList<Dial>();
			for(int i = 0; i < 8; i++){
			velSteps.add(new Dial(1.0));
			velSteps.get(i).getLabelledDial("Velocity");
			}
		}
	}