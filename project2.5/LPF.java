public class LPF extends Filter 
    {
    Module frequencyMod = new Constant(1.0);
        
    public void setFrequencyMod(Module frequencyMod) {
        this.frequencyMod = frequencyMod;
        }

    public Module getFrequencyMod() {
        return this.frequencyMod;
        }

    Module resonanceMod = new Constant(1.0);
        
    public void setResonanceMod(Module resonanceMod) {
        this.resonanceMod = resonanceMod;
        }

    public Module getResonanceMod() {
        return this.resonanceMod;
        }

    void updateFilter(double CUTOFF, double Q)
        {
            //from lines 1-4 of algorithm 14 in book
            b0 = 1.0;       //initial value
            b[0] = 0.5;
            b[1] = 0.5;
            a[0] = 0.1;     //feedback
            a[1] = 0.25;    //feedback
            x0 = input.getValue();         //current input
            //only assigned b0, a[], b[]
            // more work needed, J, W, T, etc
        }
        
    public LPF()
        {
        super(new double[2], new double[2], 0);
        }
        
    public static final double MIN_CUTOFF = 10.0;		// don't set the cutoff below this (in Hz)
    public double tick(long tickCount) 
        {
        double q = (resonanceMod.getValue() * 10 + 1) * Math.sqrt(0.5);
        double cutoff = Math.max(MIN_CUTOFF, Utils.valueToHz(frequencyMod.getValue()));
        updateFilter(cutoff, q);
        return super.tick(tickCount);
        }
    private void Coefficients() {

        double T        = Config.INV_SAMPLING_RATE;
        double herz       = Utils.valueToHz(frequencyMod.getValue());
        double w       = herz>0 ? 2*Math.PI*herz : 0.0001;
        double Quo        = resonanceMod.getValue()*10 + Math.sqrt(0.5);
        double M        = w*w*Quo*T*T;
        double J        = 4*Quo + 2*w*T + M;
        double bottom = 1/J;

        this.b0   = bottom*M;                  // b0
        this.b[0] = bottom*2*M;                // b1
        this.b[1] = bottom*M;                  // b2
        this.a[0] = bottom*(-8*Quo + 2*M);       // a1
        this.a[1] = bottom*(4*Quo - 2*w*T + M); // a2

    }
    }
