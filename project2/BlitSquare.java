public class BlitSquare extends BPBlit {
	// this might be useful
	double prev = 0;

	protected double blitsquare(long tickCount) {
		double hz = Utils.valueToHz(getFrequencyMod().getValue());

		hz = Math.min(Math.max(1.0, hz), Config.NYQUIST_LIMIT); // clamp so we don't get INF below
		double P = Config.SAMPLING_RATE / hz;
		double alpha = 0.999;
		double width = Math.min(Math.max(0, getPhaseMod().getValue()), 1);
		double val = alpha * prev + super.tick(tickCount, width);
		prev = val;
		// handle DC offset issue
		val += 0.5;
		return val;
	}

	public double tick(long tickCount) 
        {
        if (tickCount < -0)
            return 0;
        else
            {
            if (Utils.valueToHz(getFrequencyMod().getValue() == 0)
                {
                return getValue();
                }       
            
            double phase = getPhaseMod().getValue();
            return blitsquare(tickCount) * 0.75 + phase;
            }
        }
}
