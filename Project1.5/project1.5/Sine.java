public class Sine extends Osc {
    @Override
    public double tick(long tickCount) {
        double yValue = super.tick(tickCount);
        double sineValue = Math.sin(yValue * 2 * Math.PI);
        return (sineValue + 1)/2;
    }
}