public class BlitTriangle extends BlitSquare 
    {
    // this might be useful
    double prev = 0;
    double ALPHA = 0.9;
    
    protected double blittriangle(long tickCount)
        {
        // IMPLEMENT ME
            double freq = Utils.valueToHz(getFrequencyMod().getValue());
            double p = Config.SAMPLING_RATE / freq;
            double val = (ALPHA * prev) + (blitsquare(tickCount)/p);

            prev = val;

            return val;
        }
        
    public double tick(long tickCount) 
        {
        if (tickCount <= 0)
            return 0;
        else
            {
            if (Utils.valueToHz(getFrequencyMod().getValue()) == 0.0)
                {
                return 0;
                }
                        
            return blittriangle(tickCount) * 4 + 0.5;
            }
        }
    }
