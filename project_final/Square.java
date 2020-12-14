public class Square extends Osc {
    @Override
    public double tick(long tickCount) {
        double rampValue = super.tick(tickCount);
        if(rampValue < 0.5) {
            return 1;
        }
        return 0;
    }
}
