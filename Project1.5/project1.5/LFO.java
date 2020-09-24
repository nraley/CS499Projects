// Copyright 2018 by George Mason University
// Licensed under the Apache 2.0 License


import java.lang.reflect.Type;

/**
   A superclass for oscillators.  This basic class performs a simple ramp-style oscillation 
   from 0...1 through the period of the wave.  You could use the tick value to compute more
   useful oscillation functions in subclasses.
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

    public int getType()
    	{
    	    // implement me
            return type;
    	}
    	
    public void setType(int type) 
    	this.type = type;
    	{	
    	switch (type) {
        case 1: type = LFO_TYPE_RAMP; break;
        case 2: type = LFO_TYPE_SAW; break;
        case 3: type = LFO_TYPE_SQUARE; break;
        case 4: type = LFO_TYPE_TRIANGLE; break;
        case 0: type = LFO_TYPE_SINE; break;
    }
    	    // implement me
            
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
    	
    public double tick(long tickCount) 
        {
        // Implement Me
            double yValue = super.tick(tickCount);
            double sineValue = Math.sin(yValue * 2 * Math.PI);
            return (sineValue + 1)/2;
        }
    
    }
