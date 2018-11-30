package myCheese.connectionDevice.weighers;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import myCheese.connectionDevice.Weigher;
import myCheese.io.DataFields;

public class Etalon extends Weigher {

	private byte[] dc1 = { 0x00, 0x17, 0x03, 0x02, 0x32, 0x18 }; // query weight
	public String dataHexString;
	private int[] eqWeights = new int[4];
	protected String dataString = "0";
	protected String oldDataString;
	private String strRegex;

	public Etalon() {
		try {
			serialPort.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void setRegexWeightCASADH(String regex) {
		this.setStrRegex(regex);
	}

	@Override
	protected boolean findWeight(String str) {
		return false;

	}

	protected String findWeightStr(String str) {

		dataString = (dataHexString.replaceAll(" ", "").trim().substring(9, 10).equals("1") ? "-" : "")
				+ (dataHexString.replaceAll(" ", "").trim().substring(13, 18));
		return dataString;
	}

	@Override
	protected boolean getWeigherCondition() {

		eqWeights[eqWeights.length - 1] = Integer.parseInt(dataString);

		eqWeights = shift(eqWeights);

		int average;
		int sum = 0;
		for (int i = 0; i < eqWeights.length; i++) {
			sum += eqWeights[i];
		}

		average = sum / eqWeights.length;
		if (average == eqWeights[0]) {
			return true;
		} else {
			return false;
		}

	}

	public static int[] shift(int arr[]) {
		int b[] = new int[arr.length];
		b[0] = arr[arr.length - 1];
		System.arraycopy(arr, 0, b, 1, arr.length - 1);
		return b;
	}

	@Override
	public void toWeigh() {
		try {
			serialPort.writeBytes(dc1);
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}

	public String getStrRegex() {
		return strRegex;
	}

	public void setStrRegex(String strRegex) {
		this.strRegex = strRegex;
	}

	public class PortReader implements SerialPortEventListener {
		public void serialEvent(SerialPortEvent event) {
			if (event.isRXCHAR() & event.getEventValue() == 10) {
				try {
					dataHexString = serialPort.readHexString();
					
					if (DataFields.getStabCondition() != getWeigherCondition()) {
						DataFields.setStabCondition(getWeigherCondition());
					}
						DataFields.setWeight(findWeightStr(dataHexString));
					
				} catch (SerialPortException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
