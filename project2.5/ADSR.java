// Copyright 2018 by George Mason University
// Licensed under the Apache 2.0 License


/** 
	A TIME-based ADSR.  You can make this linear or pseudo-exponential (I'd start with linear).

	how it works: Attack can be comsidered the first state of the signal hence in tick() we 
	check the value of gate in order to determine if we just presed a key or 
	released a key
	if key pressed: enter attack stage (ramp up to the peak volume of signal)
	
	if key released: enter release stage (ramp down to 0)
	
	sustain is simply a value that is set that will be considered the "normal" volume of the signal
	
	decay is the time it takes to go from the peak volume of the signal following the attack stage to 
	reach the volume defined by sustain
*/	
	
public class ADSR extends Module {
    static final int START = 0;
    static final int ATTACK = 1;
    static final int DECAY = 2;
    static final int SUSTAIN = 3;
    static final int RELEASE = 4;
    int state = START;
	int stateTick = 0;
	
    Module attackLevel = new Constant(1.0);
    Module attackTime = new Constant(0.01);	// assuming these values are in
    Module decayTime = new Constant(0.5);	//seconds, it becomes a task
    Module sustainLevel = new Constant(1.0); // to convert these values from 
    Module releaseTime = new Constant(0.01); // seconds to ticks
    Module gate = new Constant(0);
	
    // You should find these handy
    double startTime = 0;
    double endTime = 0;
    double startLevel = 0;
    double endLevel = 0;
	
	double output = 0.0; // calculated output value for tick
	double gateCheck = 0.0; // used to keep track of gate trigger

	int attackTicks = 0; // these three vars are the value of the 
	int decayTicks = 0;	// attack decay and release 
	int releaseTicks = 0; //converted from seconds to ticks
	
	int currState = 0;
	double currVal = 0;
	
    public double getAttackTime() {
        return attackTime.getValue();
    }

    public void setAttackTime(Module attackTime) {
		this.attackTime = attackTime;
    }

    public double getAttackLevel() {
		return attackLevel.getValue();
    }

    public void setAttackLevel(Module attackLevel) {
        this.attackLevel = attackLevel;
    }

    public double getDecayTime() {
        return decayTime.getValue();
    }
    public void setDecayTime(Module decayTime) {
        this.decayTime = decayTime;
    }

    public double getSustainLevel() {
        return sustainLevel.getValue();
    }

    public void setSustainLevel(Module sustainLevel) {
        this.sustainLevel = sustainLevel;
    }

    public double getReleaseTime() {
        return releaseTime.getValue();
    }

    public void setReleaseTime(Module releaseTime) {
        this.releaseTime = releaseTime;
    }
    public double getGate() {
        return gate.getValue();
    }
    public void setGate(Module gate) {
        this.gate = gate;
    }
	
	/*
	These three calcOutput helpers calculate the value to increment the value of output in tick()
	by setting the value of currVal to the appropriate value for this tick. If the time interval we 
	are working in is 0 (e.g. no attack, decay, and/or release), we do not manipulate the signal->output
	is incremented by 0.
	*/
	public void calcOutputAttack(int tick){
		if(attackTicks == 0){
			currVal = 0;
		}
		else{
		currVal = (1.0 - output)/(attackTicks - tick);
	//	System.out.println(attackTicks);
		}
	}
	public void calcOutputDecay(int tick){
		if(decayTicks == 0){
			currVal = 0;
		}
		else{
		currVal = -1 * ((1- sustainLevel.getValue())/(decayTicks - tick));
		}
	}
	public void calcOutputRelease(int tick){
		if(releaseTicks == 0){
			currVal = 0;
		}
		else{
		currVal = -1 * (output/(releaseTicks - tick));
		}
	}
	
    public double tick(long tickCount) {
		//System.out.println(gateCheck);
		attackTime.doUpdate(tickCount);
		decayTime.doUpdate(tickCount);
		releaseTime.doUpdate(tickCount);
		gate.doUpdate(tickCount);
		
		attackTicks = (int)(attackTime.getValue() * Config.SAMPLING_RATE);	//figuring out 
		decayTicks = (int)(decayTime.getValue() * Config.SAMPLING_RATE);	//time as 
		releaseTicks = (int)(releaseTime.getValue() * Config.SAMPLING_RATE); //unit of ticks
		
		double currGate = gate.getValue();	// takes in current value of gate to see if a note was pressed 
		
		if(gateCheck != currGate){	//compare the current value of gate to the previous value to determine if there is a change in state
			currState = 0;	//currState is used to keep track of the current tick; calculate the value to increment output by
			if(gateCheck == 0){	//if the state change was a key press, we enter attack
				calcOutputAttack(currState);
				state = 1;
			}
			else{	//if the state change was a key release, we enter release
				calcOutputRelease(currState);
				state = 4;
			}
		gateCheck = currGate;	// set the previous value of gate to the current value
		}
		/*
		ADSR as a state machine can only take one path: Attack -> Decay -> Sustain [nothing happens;amplitude held]-> Release
		where release is not triggered by time but instead a gate change. This switch will enter Attack on a key press which then goes 
		into decay after a calculated number of ticks AND THEN onto sustain where it will remain until a key is let go. The key
		being let go will trigger the release state. Release then returns to START and the cycle is complete
		*/
		switch(state){
			case 1:
				if(currState < attackTicks){	//if the current tick is in the range of ticks needed for this time interval
					//System.out.println("atk");
					//System.out.println(output);
					output += currVal;	//increment the value of the output
				}
				else{	//set up for decay and change states when we reach the end of this state
					currState = 0;
					calcOutputDecay(currState);
					output = 1.0;
					state = 2;
				}
				break;
			case 2:
				if(currState < decayTicks){
					//System.out.println("dcy");
					//System.out.println(output);
					output += currVal;
				}
				else{
					output = sustainLevel.getValue(); // set the value of output to hold on the sustain level
					state = 3;	//move onto sustain state
				}
				break;
			case 4: //only reached by key release
				if(currState < releaseTicks){
					//System.out.println("rls");
					//System.out.println(output);
					output += currVal;
				}
				else {
					state = 0;	//send state back to start
					output = 0;	// reset output to 0;
				}
				break;
			case 0: //do nothing; start state
			case 3:// do nothing; sustain state
		}
		currState += 1;
        return output;
    }
}