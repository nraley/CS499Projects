import javax.swing.*;
import java.awt.*;


public class Project3 extends Synth
	{
    public static void main(String[] args) 
    	{
    	Synth.run(new Project3(), args);
    	}

	Dial dials[][] = new Dial[4][4];	// Incoming modulation dial [To][From]
	Dial[] outs = new Dial[4];			// Out dial

		public PM buildOperator(int opNum, Box container, MidiModule midiMod, Module midiGate) {
    	// Make a box for the operator
		Box opBox = new Box(BoxLayout.Y_AXIS);
		opBox.setBorder(BorderFactory.createTitledBorder("Operator " + (opNum + 1)));
		container.add(opBox);

		// Make the operator
		PM operator = new PM();
		modules.add(operator);
		operator.setFrequencyMod(midiMod);

		// Add Relative Frequency
		Dial dial = new Dial(0.1);
		opBox.add(dial.getLabelledDial("Relative Frequency"));
		operator.setRelativeFrequency(dial.getModule());
		modules.add(dial.getModule());
		// control with MIDI CC
		dial.attach(midiMod, opNum + 1);


		// Add an ADSR
		ADSR adsr = new ADSR();
		modules.add(adsr);
		adsr.setGate(midiGate);
		operator.setOutputAmplitude(adsr);

		// Add Attack Time, Decay Time, Sustain, Release Time
		dial = new Dial(0.1);
		opBox.add(dial.getLabelledDial("Attack Time"));
		adsr.setAttackLevel(dial.getModule());

		dial = new Dial(0);
		opBox.add(dial.getLabelledDial("Decay Time"));
		adsr.setDecayTime(dial.getModule());

		dial = new Dial(1.0);
		opBox.add(dial.getLabelledDial("Sustain"));
		adsr.setSustainLevel(dial.getModule());

		dial = new Dial(0.1);
		opBox.add(dial.getLabelledDial("Release Time"));
		adsr.setReleaseTime(dial.getModule());

		// *Multiply* (not amplify) the ADSR output against a Gain
		dial = new Dial(1.0);
		opBox.add(dial.getLabelledDial("Gain"));
		Mul mul = new Mul();
		modules.add(mul);
		mul.setInput(adsr);
		mul.setMultiplier(dial.getModule());

		// Add mixer dials
		dials[opNum][0] = new Dial(0.0);
		opBox.add(dials[opNum][0].getLabelledDial("From Operator 1"));
		dials[opNum][1] = new Dial(0.0);
		opBox.add(dials[opNum][1].getLabelledDial("From Operator 2"));
		dials[opNum][2] = new Dial(0.0);
		opBox.add(dials[opNum][2].getLabelledDial("From Operator 3"));
		dials[opNum][3] = new Dial(0.0);
		opBox.add(dials[opNum][3].getLabelledDial("From Operator 4"));

		// Add an Out dial
		outs[opNum] = new Dial(1.0);
		opBox.add(outs[opNum].getLabelledDial("Out"));
		modules.add(outs[opNum].getModule());
		// control with MIDI CC
		outs[opNum].attach(midiMod, opNum + 5);

		return operator;
	}

    public void setup() 
    	{
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
;
    	//		Make the operators
		PM op1 = buildOperator(0, outer, midimod, gate);
		PM op2 = buildOperator(1, outer, midimod, gate);
		PM op3 = buildOperator(2, outer, midimod, gate);
		PM op4 = buildOperator(3, outer, midimod, gate);
	
		//	 	For each operator, add a Mixer to mix in incoming signals from all four operators
		PM opArr[] = new PM[] {op1, op2, op3, op4};

		for (int opNum = 0; opNum < 4; opNum++) {
			Module amplitudeMod[] = new Module[] {dials[opNum][0].getModule(), dials[opNum][1].getModule(), dials[opNum][2].getModule(), dials[opNum][3].getModule()};
			Mixer mixer = new Mixer(opArr, amplitudeMod);
			modules.add(mixer);
			opArr[opNum].setPhaseModulator(mixer);
		}

			// Make an Other box
		Box outputBox = new Box(BoxLayout.Y_AXIS);
		outputBox.setBorder(BorderFactory.createTitledBorder("Output"));
		outer.add(outputBox);
		// Add Algorithm.  I'm nice and provided most of it for you below

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
		outputBox.add(opt);

		// Make a final mixer whose inputs are the operators, controlled by their Out dials
		Module amplitudeMod[] = new Module[] {outs[0].getModule(), outs[1].getModule(), outs[2].getModule(), outs[3].getModule()};
		Mixer finalMixer = new Mixer(opArr, amplitudeMod);
		modules.add(finalMixer);

		// Make a VCA fed by the mixer, controlled by a final Gain dial
		Dial gain = new Dial(1.0);
		outputBox.add(gain.getLabelledDial("Gain"));

		Amplifier gainAmp = new Amplifier();
		gainAmp.setInput(finalMixer);
		gainAmp.setAmplitudeMod(gain.getModule());
		modules.add(gainAmp);

		// Make an oscilloscope fed by the VCA.  I'd set oscope.setDelay(1);
		Oscilloscope oscope = new Oscilloscope();
		Oscilloscope.OModule omodule = oscope.getModule();
		omodule.setAmplitudeModule(gainAmp);
		oscope.setDelay(1);
		modules.add(omodule);
		outputBox.add(oscope);

		// Output the VCA or oscilloscope
		setOutput(omodule);

		// Pack and display the window
		frame.pack();
		frame.setVisible(true);
        }
        
    }

