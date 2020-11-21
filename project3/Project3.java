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
		MidiModule midimod = new MidiModule(getMidi());
		modules.add(midimod);
    	// Build a MidiGate
		MidiGate gate = new MidiGate(midimod);
		modules.add(gate);
    	// Create the window
		JFrame frame = new JFrame();
		Box outer = new Box(BoxLayout.X_AXIS);
		frame.setContentPane(outer);
    	// FOR EACH OPERATOR (4 operators)
    	//		Make a box
			Box op1Box = new Box(BoxLayout.Y_AXIS);
			op1Box.setBorder(BorderFactory.createTitledBorder("Operator 1"));
			outer.add(op1Box);
			Box op2Box = new Box(BoxLayout.Y_AXIS);
			op2Box.setBorder(BorderFactory.createTitledBorder("Operator 2"));
			outer.add(op2Box);
			Box op3Box = new Box(BoxLayout.Y_AXIS);
			op3Box.setBorder(BorderFactory.createTitledBorder("Operator 3"));
			outer.add(op3Box);
			Box op4Box = new Box(BoxLayout.Y_AXIS);
			op4Box.setBorder(BorderFactory.createTitledBorder("Operator 4"));
			outer.add(op4Box);			
    	//		Make the operator
			PM op1 = new PM();
			PM op2 = new PM();
			PM op3 = new PM();
			PM op4 = new PM();
    	//		Add Relative Frequency
			Dial op1RelFreq = new Dial(0.1);
			opBox1.add(op1RelFreq.getLabelledDial("Relative Frequency")) 
			Dial op2RelFreq = new Dial(0.1);
			opBox2.add(op2RelFreq.getLabelledDial("Relative Frequency")) 
			Dial op3RelFreq = new Dial(0.1);
			opBox3.add(op3RelFreq.getLabelledDial("Relative Frequency")) 
			Dial op4RelFreq = new Dial(0.1);
			opBox4.add(op4RelFreq.getLabelledDial("Relative Frequency")) 
    	//		Add an ADSR
			ADSR adsr1 = new ADSR();
			ADSR adsr2 = new ADSR();
			ADSR adsr3 = new ADSR();
			ADSR adsr4 = new ADSR();
    	//		Add Attack Time, Decay Time, Sustain, Release Time
			Dial op1Atk = new Dial(0.1);
			Dial op2Atk = new Dial(0.1);
			Dial op3Atk = new Dial(0.1);
			Dial op4Atk = new Dial(0.1);
			opBox1.add(op1Atk.getLabelledDial("Attack Time"));
			opBox2.add(op2Atk.getLabelledDial("Attack Time"));
			opBox3.add(op3Atk.getLabelledDial("Attack Time"));
			opBox4.add(op4Atk.getLabelledDial("Attack Time")); 
			Dial op1Decay = new Dial(0);
			Dial op2Decay = new Dial(0);
			Dial op3Decay = new Dial(0);
			Dial op4Decay = new Dial(0);
			opBox1.add(op1Atk.getLabelledDial("Attack Time"));
			opBox2.add(op2Atk.getLabelledDial("Attack Time"));
			opBox3.add(op3Atk.getLabelledDial("Attack Time"));
			opBox4.add(op4Atk.getLabelledDial("Attack Time"));
			Dial op1Sus = new Dial(1.0);
			Dial op2Sus = new Dial(1.0);
			Dial op3Sus = new Dial(1.0);
			Dial op4Sus = new Dial(1.0);
			opBox1.add(op1Atk.getLabelledDial("Attack Time"));
			opBox2.add(op2Atk.getLabelledDial("Attack Time"));
			opBox3.add(op3Atk.getLabelledDial("Attack Time"));
			opBox4.add(op4Atk.getLabelledDial("Attack Time")); 
			Dial op1Release = new Dial(0.1);
			Dial op2Release = new Dial(0.1);
			Dial op3Release = new Dial(0.1);
			Dial op4Release = new Dial(0.1);
			opBox1.add(op1Atk.getLabelledDial("Attack Time"));
			opBox2.add(op2Atk.getLabelledDial("Attack Time"));
			opBox3.add(op3Atk.getLabelledDial("Attack Time"));
			opBox4.add(op4Atk.getLabelledDial("Attack Time")); 			
    	//		*Multiply* (not amplify) the ADSR output against a Gain
			Dial gain1 = new Dial(1.0);
			Dial gain2 = new Dial(1.0);
			Dial gain3 = new Dial(1.0);
			Dial gain4 = new Dial(1.0);
	
			//need to add the muls
	
			opBox1.add(gain1.getLabelledDial("Gain"));
			opBox2.add(gain2.getLabelledDial("Gain"));
			opBox3.add(gain3.getLabelledDial("Gain"));
			opBox4.add(gain4.getLabelledDial("Gain"));
	
		//	 	Add a Mixer to mix in incoming signals from all four operators
			PM opArr[] = new PM[] = {op1, op2, op3, op4};
			Dial mix1op1 = new Dial(0.0);
			Dial mix1op2 = new Dial(0.0);
			Dial mix1op3 = new Dial(0.0);
			Dial mix1op4 = new Dial(0.0);
			Dial ampMod1[] = new Dial[] = {mix1op1, mix1op2, mix1op3, mix1op4};

			Dial mix2op1 = new Dial(0.0);
			Dial mix2op2 = new Dial(0.0);
			Dial mix2op3 = new Dial(0.0);
			Dial mix2op4 = new Dial(0.0);
			Dial ampMod2[] = new Dial[] = {mix2op1, mix2op2, mix2op3, mix2op4};			

			Dial mix3op1 = new Dial(0.0);
			Dial mix3op2 = new Dial(0.0);
			Dial mix3op3 = new Dial(0.0);
			Dial mix3op4 = new Dial(0.0);
			Dial ampMod3[] = new Dial[] = {mix3op1, mix3op2, mix3op3, mix3op4};

			Dial mix4op1 = new Dial(0.0);
			Dial mix4op2 = new Dial(0.0);
			Dial mix4op3 = new Dial(0.0);
			Dial mix4op4 = new Dial(0.0);
			Dial ampMod4[] = new Dial[] = {mix4op1, mix4op2, mix4op3, mix4op4};
			
			Mixer mixer1 = new Mixer(opArr, ampMod1);
			Mixer mixer2 = new Mixer(opArr, ampMod2);
			Mixer mixer3 = new Mixer(opArr, ampMod3);
			Mixer mixer4 = new Mixer(opArr, ampMod4);
			
		//		Add dials for the four signals
		// 		Add an Out dial
		Dial out1 = new Dial(1.0);
		Dial out2 = new Dial(1.0);
		Dial out3 = new Dial(1.0);
		Dial out4 = new Dial(1.0);
			
		
		// Make an Other box
			Box output = new Box(BoxLayout.Y_AXIS);
			output.setBorder(BorderFactory.createTitledBorder("Output"));
			outer.add(output);
			Dial gain = new Dial(1.0);
			output.add(gain.getLabelledDial("Gain"));
		// Add Algorithm.  I'm nice and provided most of it for you below
		// Make a final mixer whose inputs are the operators, controlled by their Out dials
		// Make a VCA fed by the mixer, controlled by a final Gain dial
		// Make an oscilloscope fed by the VCA.  I'd set	 oscope.setDelay(1);
		// Output the VCA or oscilloscope
		// Pack and display the window
		
		
		
		// Some code you might find useful
		
        Dial[] outs = new Dial[4];				// Out dial
        Dial dials[][] = new Dial[4][4];		// Incoming modulation dial [To][From]
        
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
/*
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
        }*/
        
    }
}

