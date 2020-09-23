import javax.swing.*;
import java.awt.*;


public class Project1 extends Synth
	{
    public static void main(String[] args) 
    	{
    	Synth.run(new Project1(), args);
    	}

    public void setup() 
    	{
			// Implement me:

			// Build a MidiModule
			// Build a MidiGate
			// Create the window

			// BEFORE THE ORGAN:
			// Create an Add which takes the MidiMod pitch as input

			// Build a Hammond Organ with dials and options.  It now takes the *Add* as its frequency mod

			// THE ENVELOPE:

			// Build an ADSR with dials at least.  It should default to sine waves
			// -- If you were really cool you'd add an options to change the wave type
			// -- If you were REALLY cool you'd add additional options for random LFOs or Sample and Hold Random LFOs.
			// Build an amplifier AAA that takes the Hammond as input, modulated by the ADSR

			// THE LESLIE (DOPPLER EFFECT):

			// Build an LFO.  Its rate should be a dial multiplied by 0.001 (use Mul) to slow it down
			// First we'll make the Pitch effect:
			// Make a Mul whose input is the LFO, multiplied by a "pitch" dial.
			// Make another Mul whose input is the previous Mul multiplied by 0.001 so the pitch doesn't go nuts
			// Set this final Mul as the adder() for the Add so it affects the organ's pitch
			// Next we'll make the volume effect:
			// Make yet another Mul, with the LFO as input, and an "Amplitude" dial as multiplier.
			// Make another Add, with the Mul as the adder(), and that's it.  No input.  (thus it's 1.0 + Mul)
			// Make an amplifier BBB with the first amplifier AAA as input and this new Add as modulation to flutter the overall volume.

			// Build an Output
			// Build a Gate amplifier.  Its signal now gets fed *amplifier BBB*
			// Add a Gain amplifier fed the Gate amplifier
			// Add an Oscillocope
			// Set the output to the gain amplifier (or oscilloscope)
			// Pack and display the window

			MidiModule midimod = new MidiModule(getMidi());
			modules.add(midimod);

			// Add a MidiGate to the chain, using the MidiModule.  This module outputs a 1 or a 0 as its modulation
			// depending on whether a note is being pressed or not.
			MidiGate gate = new MidiGate(midimod);
			modules.add(gate);

			// Create our basic GUI.  This consists of a window (JFrame) containing a Box (an object which lays out
			// widgets horizontally or vertically -- in this case, horizontally) as its primary content pane.
			JFrame frame = new JFrame();
			Box outer = new Box(BoxLayout.X_AXIS);
			frame.setContentPane(outer);

			// Next, we'll make another box and stick it in the first box.  This second box lays stuff out
			// vertically.  We'll give it a nice border.  This way we can add additional vertical boxes
			// later on in other projects.

			Box organBox = new Box(BoxLayout.Y_AXIS);
			organBox.setBorder(BorderFactory.createTitledBorder("Organ"));
			outer.add(organBox);

			Hammond hammond = new Hammond();

			for (int i = 0; i < Hammond.NUM_TONEWHEELS; i++) {
				Dial organDial = new Dial(1.0);
				organBox.add(organDial.getLabelledDial("Bar "+ (i+1)));
				hammond.setAmplitudeMod(i, organDial.getModule());
			}

			organBox.add(hammond.getOptions());		//add presets
			modules.add(hammond);

			Box centerBox = new Box(BoxLayout.Y_AXIS);	//center box to hold Env and LFO
			outer.add(centerBox);

			Box envBox = new Box(BoxLayout.Y_AXIS);		//Env box to hold knobs
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

			//ADSR

			Amplifier AAA = new Amplifier();
			AAA.setAmplitudeMod(new Constant(1));
			AAA.setInput(hammond);
			modules.add(AAA);


			//leslie simulator
			//pitch modulation

			Box lfoBox = new Box(BoxLayout.Y_AXIS);		//LFO box to hold knobs
			lfoBox.setBorder(BorderFactory.createTitledBorder("LFO"));
			centerBox.add(lfoBox);

			Dial rateDial = new Dial(1.0);
			lfoBox.add(rateDial.getLabelledDial("Rate"));

			Mul lfoRateMul = new Mul();
			lfoRateMul.setInput(rateDial.getModule());
			lfoRateMul.setMultiplier(new Constant (0.001));
			modules.add(lfoRateMul);

			LFO lfo = new LFO();
			lfo.setFrequencyMod(lfoRateMul);
			modules.add(lfo);

			Dial pitchDial = new Dial(1.0);
			lfoBox.add(pitchDial.getLabelledDial("Pitch"));

			Mul pitchMul = new Mul();
			pitchMul.setInput(lfo);
			pitchMul.setMultiplier(pitchDial.getModule());
			modules.add(pitchMul);

			Mul pitchScale= new Mul();
			pitchScale.setInput(pitchMul);
			pitchScale.setMultiplier(new Constant (0.001));
			modules.add(pitchScale);

			Add finalAdd = new Add();
			finalAdd.setInput(midimod);
			finalAdd.setAdder(pitchScale);
			modules.add(finalAdd);

			hammond.setFrequencyMod(finalAdd);		//hook the keyboard up to the hammond

			//amplitude modulation

			Dial amplitudeDial = new Dial(1.0);
			lfoBox.add(amplitudeDial.getLabelledDial("Amplitude"));

			Mul volMul = new Mul();
			volMul.setInput(lfo);
			volMul.setMultiplier(amplitudeDial.getModule());
			modules.add(volMul);

			Add volAdder = new Add();
			volAdder.setAdder(volMul);
			modules.add(volAdder);
			//default input of one

			Amplifier BBB = new Amplifier();
			BBB.setInput(AAA);
			BBB.setAmplitudeMod(volAdder);
			modules.add(BBB);

			Box outputBox = new Box(BoxLayout.Y_AXIS);
			outputBox.setBorder(BorderFactory.createTitledBorder("Output"));
			outer.add(outputBox);

			// Let's make a dial.  It'll control the overall volume.  Initially it's 1.0 (maximum volume)
			// Notice that we're not adding the Dial's module to the module chain because it really doesn't
			// need to be ticked (but maybe that's bad style).

			Dial dial = new Dial(1.0);
			outputBox.add(dial.getLabelledDial("Gain"));

			Amplifier gatedAmp = new Amplifier();
			gatedAmp.setInput(BBB);
			gatedAmp.setAmplitudeMod(gate);
			modules.add(gatedAmp);

			Amplifier gainAmp = new Amplifier();
			gainAmp.setInput(gatedAmp);
			gainAmp.setAmplitudeMod(dial.getModule());
			modules.add(gainAmp);

			// Build an Oscilloscope.  We'll feed the signal into its module.  Unlike the Dial, this one
			// has to have its module added to the chain so it's properly updated with data.  Add the
			// oscilloscope to the box so we can see it.
			Oscilloscope oscope = new Oscilloscope();
			Oscilloscope.OModule omodule = oscope.getModule();
			modules.add(omodule);
			omodule.setAmplitudeModule(gainAmp);
			outputBox.add(oscope);

			// Set the oscilloscope's output as our audio output.
			// We could have also set the gain module output as our audio output as well, doesn't matter.
			setOutput(omodule);

			// Pack our window, which causes it to properly size all its widgets according to their desired
			// sizes.  Then display it.
			frame.pack();
			frame.setVisible(true);
        }
        
    }

