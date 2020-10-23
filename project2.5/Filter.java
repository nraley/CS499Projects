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
            //what follows it taken from algorithm 14 from textbook
            int N = a.length;
            int M = b.length;
            double sum = x0 * b0;           //A14 line 7
            for (int n = 0; n < N; n++) {   //line 8
                sum -= a[n] * y[n];         //line 9
            }
            for (int m = 0; m < M; m++) {   //line 10
                sum += b[m] * x[m];         //line 11
            }
            for (int n = N - 1; n > 0; n--) {   //line 12
                y[n] = y[n-1];                  //line 13
            }
            y[0] = sum;                         //line 14
            for (int m = M -1; m > 0; m--) {    //line 15
                x[m] = x[m-1];                  //line 16
            }
            x[0] = x0;                          //line 17
            return sum;
            
        //return this.input.getValue();     //placeholder return statement to get the ball rolling
        }
    }
