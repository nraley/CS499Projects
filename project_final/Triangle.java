public class Triangle extends Osc {
    @Override
    public double tick(long tickCount) {
    double rampValue = super.tick(tickCount);
    if (rampValue < 0.25) {
        return 2 * rampValue + 0.5;
    } else if (rampValue < 0.75) {
        return -2 * rampValue + 1.5;
    }
    return 2 * rampValue - 1.5;
    }
}