public class BPBlit extends Blit 
    {
    Blit offPhaseBlit = new Blit();

    Module phaseMod = new Constant(0.5);

    public void setPhaseMod(Module phaseMod) {
        this.phaseMod = phaseMod;
        }

    public Module getPhaseMod() { return phaseMod; }

    public void setFrequencyMod(Module frequencyMod) {
        super.setFrequencyMod(frequencyMod);
        offPhaseBlit.setFrequencyMod(frequencyMod);
        }

    protected double bpblit(long tickCount)
        {
    	 double hz = Utils.valueToHz(getFrequencyMod().getValue());
         hz = Math.min(Math.max(1.0, hz), Config.NYQUIST_LIMIT); // clamp so we don't get INF below
         double p = Config.SAMPLING_RATE / hz;
         double val = super.tick(tickCount) - offPhaseBlit.tick(tickCount);
         return val;
        }

    public double tick(long tickCount) 
        {
        return bpblit(tickCount) / 2 + 0.5;
        }
    }
