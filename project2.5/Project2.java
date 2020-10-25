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

        //Blit oscillators

        Box blitBox = new Box(BoxLayout.Y_AXIS);
        blitBox.setBorder(BorderFactory.createTitledBorder("Blits"));
        outer.add(blitBox);

        Blit blit = new Blit();
        BlitSaw blitSaw = new BlitSaw();
        BlitSquare blitSqr = new BlitSquare();
        BPBlit bpbLit = new BPBlit();
        BlitTriangle blitTri = new BlitTriangle();

    	// Build dials for the amplitude of each of the oscillators, plus
    	//       the pulse width of the square and triangle
        // Put them in a box and put it in the window

        //Blit dials

        Dial blitAmp = new Dial(0.0);
        blitBox.add(blitAmp.getLabelledDial("Blit Amplitude"));

        Dial bpblitAmp = new Dial(0.0);
        blitBox.add(bpblitAmp.getLabelledDial("BPBlit Amplitude"));

        Dial sawAmp = new Dial(1.0);
        blitBox.add(sawAmp.getLabelledDial("Saw Amplitude"));

        Dial sqrAmp = new Dial(0.0);
        blitBox.add(sqrAmp.getLabelledDial("Square Amplitude"));

        Dial sqrPulse = new Dial(0.5);
        blitBox.add(sqrPulse.getLabelledDial("Square Pulse Width"));

        Dial triAmp = new Dial(0.0);
        blitBox.add(triAmp.getLabelledDial("Triangle Amplitude"));

        Dial triPulse = new Dial(0.5);
        blitBox.add(triPulse.getLabelledDial("Triangle Pulse Width"));

        //box for Filter envelope
        Box secondBox = new Box(BoxLayout.Y_AXIS);
        outer.add(secondBox);

        Box filterEnvBox = new Box(BoxLayout.Y_AXIS);
        filterEnvBox.setBorder(BorderFactory.createTitledBorder("Filter Env"));
        secondBox.add(filterEnvBox);

        //filter envelope dials
        Dial filterAttackDial = new Dial(0.1);
        filterEnvBox.add(filterAttackDial.getLabelledDial("Attack Time"));

        Dial filterDecayDial = new Dial(0);
        filterEnvBox.add(filterDecayDial.getLabelledDial("Decay Time"));

        Dial filterSustainDial = new Dial(1.0);
        filterEnvBox.add(filterSustainDial.getLabelledDial("Sustain"));

        Dial filterReleaseDial = new Dial(0.1);
        filterEnvBox.add(filterReleaseDial.getLabelledDial("Release Time"));

        //box for filter

        Box thirdBox = new Box(BoxLayout.Y_AXIS);
        outer.add(thirdBox);

        Box filterBox = new Box(BoxLayout.Y_AXIS);
        filterBox.setBorder(BorderFactory.createTitledBorder("Filter"));
        thirdBox.add(filterBox);

        //filter dials

        Dial filterCutoffDial = new Dial(0.1);
        filterBox.add(filterCutoffDial.getLabelledDial("Cutoff"));

        Dial filterResonanceDial = new Dial(0.1);
        filterBox.add(filterResonanceDial.getLabelledDial("Resonance"));

        //box for amp envelope
        Box fourthBox = new Box(BoxLayout.Y_AXIS);	//fourth box to hold Env
        outer.add(fourthBox);

        Box ampEnvBox = new Box(BoxLayout.Y_AXIS);
        ampEnvBox.setBorder(BorderFactory.createTitledBorder("Amp Env"));
        fourthBox.add(ampEnvBox);

        //amp envelope dials
        Dial ampAttackDial = new Dial(0.1);
        ampEnvBox.add(ampAttackDial.getLabelledDial("Attack Time"));

        Dial ampDecayDial = new Dial(0);
        ampEnvBox.add(ampDecayDial.getLabelledDial("Decay Time"));

        Dial ampSustainDial = new Dial(1.0);
        ampEnvBox.add(ampSustainDial.getLabelledDial("Sustain"));

        Dial ampReleaseDial = new Dial(0.1);
        ampEnvBox.add(ampReleaseDial.getLabelledDial("Release Time"));

        blitSqr.setPhaseMod(sqrPulse.getModule());
        blitTri.setPhaseMod(triPulse.getModule());

        modules.add(blit);
        modules.add(blitSqr);
        modules.add(blitSaw);
        modules.add(blitTri);
        modules.add(bpbLit);

    	// Build a mixer for the various oscillators

        Mixer mixer = new Mixer(7);	//7 oscillators for the mixer
        mixer.setInput(0, blit);
        mixer.setInput(1, blitSaw);
        mixer.setInput(2, blitSqr);
        mixer.setInput(3, blitTri);
        mixer.setInput(4, bpbLit);
        mixer.setAmplitudeMod(0, blitAmp.getModule());
        mixer.setAmplitudeMod(1, sawAmp.getModule());
        mixer.setAmplitudeMod(2, sqrAmp.getModule());
        mixer.setAmplitudeMod(3, triAmp.getModule());
        mixer.setAmplitudeMod(4, bpblitAmp.getModule());
        blit.setFrequencyMod(midimod);
        blitSaw.setFrequencyMod(midimod);
        blitSqr.setFrequencyMod(midimod);
        blitTri.setFrequencyMod(midimod);
        bpbLit.setFrequencyMod(midimod);

        modules.add(mixer);

        // Build an ADSR
        ADSR adsr = new ADSR();
        adsr.setAttackTime(ampAttackDial.getModule());
        adsr.setDecayTime(ampDecayDial.getModule());
        adsr.setSustainLevel(ampSustainDial.getModule());
        adsr.setReleaseTime(ampReleaseDial.getModule());
        adsr.setGate(gate);
        modules.add(adsr);

        /// NEW IN PROJECT 2.5
    	// Build an ADSR for the filter
    	// Build dials for the ADSR and put it in a box
    	// Build dials for the Cutoff and Resonance of the filter
    	// Put them in a box 
    	// Build a Mul to multiply the cutoff by the ADSR output
    	// Feed the Mul as the cutoff for a LOW PASS FILTER (also feed in the resonance)
    	// END NEW IN PROJECT 2.5

        //Build low-pass filters
		ADSR lpADSR = new ADSR();
		lpADSR.setAttackTime(filterAttackDial.getModule());
		lpADSR.setDecayTime(filterDecayDial.getModule());
		lpADSR.setSustainLevel(filterSustainDial.getModule());
		lpADSR.setReleaseTime(filterReleaseDial.getModule());
		lpADSR.setGate(gate);
		modules.add(lpADSR);
		/* Tried to get the ADSR to multiply by the dial value to control the amount of cutoff but could not get it to sound correct
		
		Mul lpMul = new Mul();
		lpMul.setMultiplier(filterCutoffDial.getModule());
		lpMul.setInput(lpADSR);*/
		
        LPF lowPassFilter = new LPF();
        lowPassFilter.setInput(mixer);
        lowPassFilter.setFrequencyMod(filterCutoffDial.getModule());
        lowPassFilter.setResonanceMod(filterResonanceDial.getModule());
        modules.add(lowPassFilter);
		
        // Build a VCA controlled by the ADSR which gets its input from the filter
        Amplifier AAA = new Amplifier();
        AAA.setAmplitudeMod(adsr);
        AAA.setInput(lowPassFilter);
        modules.add(AAA);
        // Make an output box
        Box outputBox = new Box(BoxLayout.Y_AXIS);
        outputBox.setBorder(BorderFactory.createTitledBorder("Output"));
        outer.add(outputBox);

        Dial outputDial = new Dial(1.0);
        outputBox.add(outputDial.getLabelledDial("Gain"));
        // Add a Gain amplifier
        Amplifier gainAmp = new Amplifier();
        gainAmp.setInput(AAA);
        gainAmp.setAmplitudeMod(outputDial.getModule());
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

