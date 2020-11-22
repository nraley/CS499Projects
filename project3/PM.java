/*
 * Utility module that provides some common parameters for all modules inteded to be oscillators
 */
public class PM extends Osc
{
    public static final double MAX_RELATIVE_FREQUENCY = 8;  // The DX7 uses 32

    //private double hz;
    //private double x;
    Module relativeFrequency = new Constant(1.0);
    public void setRelativeFrequency(Module p) { relativeFrequency = p; }
    public Module getRelativeFrequency() { return relativeFrequency; }

    Module phaseModulator = new Constant(0.0);
    public void setPhaseModulator(Module p) { phaseModulator = p; }
    public Module getPhaseModulator() { return phaseModulator; }

    public static final double MAX_PHASE_AMPLIFICATION = 4;

    Module phaseAmplifier = new Constant(1.0);
    public void setPhaseAmplifier(Module p) { phaseAmplifier = p; }
    public Module getPhaseAmplifier() { return phaseAmplifier; }

    Module outputAmplitude = new Constant(1.0);
    public void setOutputAmplitude(Module p) { outputAmplitude = p; }
    public Module getOutputAmplitude() { return outputAmplitude; }

    //double state = 0;

    public double tick(long tickCount)
    {
        double hz = Utils.valueToHz(getFrequencyMod().getValue() * relativeFrequency.getValue() * MAX_RELATIVE_FREQUENCY);

        state += hz * Config.INV_SAMPLING_RATE;
        if (state >= 1)
        {
            state -= 1;
            reset();
        }

        double x = 2 * Math.PI * state;

        double phase = Utils.valueToHz((getPhaseModulator().getValue() / 2) + 0.5);

        x += phase;

        return (outputAmplitude.getValue() * (Utils.fastSin(x) / 2) + 0.5); // output sinwave with appropriate vol and pitch
    }

}
