public class BlitSaw extends Blit 
    {
    // this might be useful
    double prev = 0;

	public double blitsaw(long tickCount)
		{
		double bs = Utils.valueToHz(getFrequencyMod().getValue());
		 bs  = Math.min(Math.max(1.0, bs), Config.NYQUIST_LIMIT);
		 return bs;
		/// IMPLEMENT ME
		}
		
    public double tick(long tickCount) 
        {
        /// IMPLEMENT ME.  Note how the other implementations of tick worked
    	 // clamp so we don't get INF below
        double P  = Config.SAMPLING_RATE / blitsaw(tickCount);
        double alpha = 1 - 1/P;
        double val = alpha*prev + super.blit(tickCount, prev) - 1/P;
        prev = val;
        val += 0.5;
        return val;
        }
    }
