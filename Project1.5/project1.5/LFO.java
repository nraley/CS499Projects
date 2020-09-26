// Copyright 2018 by George Mason University
// Licensed under the Apache 2.0 License


import java.lang.reflect.Type;

/**
*/
 
public class LFO extends Osc 
    {
    public static final String[] TYPES = new String[] { "Sine", "Ramp", "Sawtooth", "Square", "Triangle"};
    public static final int LFO_TYPE_SINE = 0;
    public static final int LFO_TYPE_RAMP = 1;
    public static final int LFO_TYPE_SAW = 2;
    public static final int LFO_TYPE_SQUARE = 3;
    public static final int LFO_TYPE_TRIANGLE = 4;    
    int type = LFO_TYPE_SINE;
    public Osc waveform = new Sine();

    public int getType()
    	{
    	    // implement me
            return type;
    	}
    	
    public void setType(int type)
    	{
            this.type = type;
            switch (type) {     //switch statement to hook up drop down menu items to waveforms
                case LFO_TYPE_SINE:
                    this.waveform = new Sine();
                    break;
                case LFO_TYPE_RAMP:
                    this.waveform = new Ramp();
                    break;
                case LFO_TYPE_SQUARE:
                    this.waveform = new Square();
                    break;
                case LFO_TYPE_SAW:
                    this.waveform = new Sawtooth();
                    break;
                case LFO_TYPE_TRIANGLE:
                    this.waveform = new Triangle();
                    break;
            }
            waveform.setFrequencyMod(this.getFrequencyMod());
    	}
    
    public Options getOptions()
    	{
    	return new Options("Types", TYPES, 0)
    		{
            public void update(int val)
                {
                // this is a race condition but whatever
                setType(val);
                }
    		};
    	}

        @Override
        public void setFrequencyMod(Module frequencyMod) {
            super.setFrequencyMod(frequencyMod);
            waveform.setFrequencyMod(frequencyMod);
        }

        public double tick(long tickCount)
        {
            super.tick(tickCount);
            waveform.doUpdate(tickCount);
            return waveform.getValue();
        }
    }
