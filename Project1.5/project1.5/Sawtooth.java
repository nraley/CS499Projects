public class Sawtooth extends Osc {
    @Override
    public double tick(long tickCount) {
        double rampValue = super.tick(tickCount);
        if (rampValue < 0.5) {
            return rampValue + 0.5;
        } else {
            return rampValue - 0.5;
        }
    }
}
