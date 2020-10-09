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

            return offPhaseBlit.tick(tickCount) - blit(tickCount, phaseMod.getValue());
        }

    public double tick(long tickCount) 
        {
        return bpblit(tickCount) / 2 + 0.5;
        }
    }
