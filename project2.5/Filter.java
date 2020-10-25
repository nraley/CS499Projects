public class Filter extends Module 
    {
    Module input = new Constant(0.5);
    double b0;
    double[] b;
    double[] a;
    double[] x;
    double[] y;
    double x0;
        
    public Filter(double[] a, double[] b, double b0)
        {
        this.a = a;
        this.b = b;
        this.b0 = b0;
        this.x = new double[b.length];
        this.y = new double[a.length];
        }
        
    public Module getInput() { return input; }
    public void setInput(Module input) { this.input = input; }

    public double tick(long tickCount) 
        {
			 double x0 = input.getValue() - 0.5;                     // note change
			// do sum
			double sum = x0 * b0;
			for(int n = 0; n < a.length; n++)
				sum -= (a[n] * y[n]);
			for(int m = 0; m < b.length; m++)
				sum += (b[m] * x[m]);
			// do shifts
			for(int n = a.length - 1; n > 0; n--)
				y[n] = y[n-1];
			y[0] = sum;
			for(int m = b.length - 1; m > 0; m--)
				x[m] = x[m-1];
			x[0] = x0;	
			return sum + 0.5;                                               // note change

        }
    }
