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
        
        //// IMPLEMENT ME
        
        return 0;
        }
    }
