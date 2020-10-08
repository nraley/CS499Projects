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
		MidiModule midimod = new MidiModule(getMidi());
		modules.add(midimod);
    	// Build a MidiGate
		MidiGate gate = new MidiGate(midimod);
		modules.add(gate);
    	// Create the window
		JFrame frame = new JFrame();
		Box outer = new Box(BoxLayout.X_AXIS);
		frame.setContentPane(outer);
    	// Build the oscillators
		Blit blit = new Blit();
		BlitSaw blitSaw = new BlitSaw();
		BlitSquare blitSqr = new BlitSquare();
		BPBlit bpbLit = new BPBlit();
		BlitTriangle blitTri = new BlitTriangle();
    	// Build dials for the amplitude of each of the oscillators, plus
    	//       the pulse width of the square and triangle
    	// Put them in a box and put it in the window
		Box blitBox = new Box(BoxLayout.Y_AXIS);
		blitBox.setBorder(BorderFactory.createTitledBorder("Blits"));
		outer.add(blitBox);
		
		Dial blitAmp = new Dial(1.0);
		blitBox.add(blitAmp.getLabelledDial("Blit Amplitude"));

		Dial bpblitAmp = new Dial(1.0);
		blitBox.add(bpblitAmp.getLabelledDial("BPBlit Amplitude"));

		Dial sawAmp = new Dial(1.0);
		blitBox.add(sawAmp.getLabelledDial("Saw Amplitude"));

		Dial sqrAmp = new Dial(1.0);
		blitBox.add(sqrAmp.getLabelledDial("Square Amplitude"));
		
		Dial sqrPulse = new Dial(1.0);
		blitBox.add(sqrPulse.getLabelledDial("Square Pulse Width"));

		Dial triAmp = new Dial(1.0);
		blitBox.add(triAmp.getLabelledDial("Triangle Amplitude"));

		Dial triPulse = new Dial(1.0);
		blitBox.add(triPulse.getLabelledDial("Triangle Pulse Width"));

		//box for envelope 
		Box centerBox = new Box(BoxLayout.Y_AXIS);	//center box to hold Env
		outer.add(centerBox);
		
		Box envBox = new Box(BoxLayout.Y_AXIS);
		envBox.setBorder(BorderFactory.createTitledBorder("Env"));
		centerBox.add(envBox);
		
		Dial attackDial = new Dial(1.0);
		envBox.add(attackDial.getLabelledDial("Attack Time"));
		
		Dial decayDial = new Dial(1.0);
		envBox.add(decayDial.getLabelledDial("Decay Time"));

		Dial sustainDial = new Dial(1.0);
		envBox.add(sustainDial.getLabelledDial("Sustain"));

		Dial releaseDial = new Dial(1.0);
		envBox.add(releaseDial.getLabelledDial("Release Time"));
		
    	// Build a mixer for the various oscillators
		Mixer mixer = new Mixer(7);	//7 oscillators for the mixer
		mixer.setInput(0, blit);
		mixer.setInput(1, blitSaw);
		mixer.setInput(2, blitSqr);
		mixer.setInput(3, blitTri);
		mixer.setInput(4, bpbLit);
		modules.add(mixer);
    	// Build an ADSR
		ADSR adsr = new ADSR();
		adsr.setAttackTime(attackDial.getModule());
		adsr.setDecayTime(decayDial.getModule());
		adsr.setSustainLevel(sustainDial.getModule());
		adsr.setReleaseTime(releaseDial.getModule());
		adsr.setGate(gate);
		modules.add(adsr);
    	
		// Build a VCA controlled by the ADSR which gets its input from the mixer
		Amplifier AAA = new Amplifier();
		AAA.setAmplitudeMod(adsr);
		AAA.setInput(mixer);
		modules.add(AAA);
    	// Make an output box
		Box outputBox = new Box(BoxLayout.Y_AXIS);
		outputBox.setBorder(BorderFactory.createTitledBorder("Output"));
		outer.add(outputBox);
		
		Dial dial = new Dial(1.0);
		outputBox.add(dial.getLabelledDial("Gain"));
		// Add a Gain amplifier
		Amplifier gainAmp = new Amplifier();
		gainAmp.setInput(AAA);
		gainAmp.setAmplitudeMod(dial.getModule());
		modules.add(gainAmp);
		
    	// Add an oscilloscope
		Oscilloscope oscope = new Oscilloscope();
		Oscilloscope.OModule omodule = oscope.getModule();
		modules.add(omodule);
		omodule.setAmplitudeModule(gainAmp);
		outputBox.add(oscope);
    	// Set the output to the gain amplifier (or oscilloscope)
		setOutput(omodule);
    	// Pack and display the window
		frame.pack();
		frame.setVisible(true);
        }
        
    }

