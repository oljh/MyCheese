package myCheese.connectionDevice;

import jssc.SerialPortEventListener;

public abstract class Weigher extends COMPort {


	protected abstract void setRegexWeightCASADH(String regex);
	
	protected abstract boolean findWeight(String str);

	protected abstract boolean getWeigherCondition();
	
	public abstract void toWeigh();
}
