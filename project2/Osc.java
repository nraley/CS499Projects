/**
   A superclass for oscillators.  This basic class performs a simple ramp-style oscillation 
   from 0...1 through the period of the wave.  You could use the tick value to compute more
   useful oscillation functions in subclasses.
*/
 
public abstract class Osc extends Module 
    {
    double state = 0;

    private Module frequencyMod = new Constant(Utils.hzToValue(220));

    /** Sets the module used to define the frequency of the oscillator. */
    public void setFrequencyMod(Module frequencyMod) { this.frequencyMod = frequencyMod; }
    /** Returns the module used to define the frequency of the oscillator. */
    public Module getFrequencyMod() { return this.frequencyMod; }

    /** Returns the current state of the oscillator.  This is the position of the oscillator
        [0...1) in its period.
    */
    public double getState() { return state; }

    /** Sets the current state of the oscillator.  This is the position of the oscillator
        [0...1) in its period.  If you set a value outside [0...1), then the value will
        be wrapped around to be inside this interval.
    */
    public void setState(double state) 
        { 
        // force to be between 0 and 1 toroidally
        if (state >= 1)
            {
            state = state - (int) state;            // state = state % 1
            }
        else if (state < 0)
            {
            state = state - (int) state;            // state = (state % 1) + 1
            state += 1;
            }
        this.state = state;
        reset();
        }

	/** Called when the state exceeds 1 and is rolled over.  A hook you can override. */
	protected void reset() { }
		
    public double tick(long tickCount) 
        {
        double hz = Utils.valueToHz(getFrequencyMod().getValue());
        state += hz * Config.INV_SAMPLING_RATE;
        if (state >= 1)
        	{
            state -= 1;
            reset();
            }
        return state;
        }
    
    }
