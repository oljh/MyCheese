package myCheese.connectionDevice.weighers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;
import myCheese.connectionDevice.Weigher;
import myCheese.io.DataFields;

public class CASADH05 extends Weigher {

	protected String dataString;
	protected String dataHexString;
	private byte enq = 0x05; // access
	private byte dc1 = 0x11; // query weight
	private String ack = "06"; // response +
	private String nck = "15"; // response -

	private String strRegex = "([Uu]|[Ss])(-)?(\\s+)?(\\d+).(\\d+)\\s+?(g|l)";
	private Pattern p = Pattern.compile(strRegex);
	private Matcher m;

	public CASADH05() {
		try {
			serialPort.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void setRegexWeightCASADH(String regex) {
		this.strRegex = regex;
	}

	@Override
	protected boolean findWeight(String inputText) {
		m = p.matcher(inputText);
		if (m.find()) {
			return true;
		}
		return false;
	}

	protected String getWeight() {
		// System.out.println(m.group(0));
		if (m.group(2) == null) {
			return (m.group(4));
		} else {
			return (m.group(2) + m.group(4));
		}
	}

	@Override
	protected boolean getWeigherCondition() {
		if (m.group(1).equals("S")) {
			return true;
		}
		return false;
	}

	@Override
	public void toWeigh() {
		try {
			serialPort.writeByte(enq);
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}

	public class PortReader implements SerialPortEventListener {
		public void serialEvent(SerialPortEvent event) {
			if (event.isRXCHAR() & event.getEventValue() > 0) {
				try {
					dataHexString = serialPort.readHexString();
					if (dataHexString.length() == 2 & dataHexString.equals(ack)) {
						serialPort.writeByte(dc1);
						dataString = serialPort.readString(15, 300);
						// System.out.println(dataString);
						if (findWeight(dataString)) {

							if (DataFields.getStabCondition() != getWeigherCondition()) {
								DataFields.setStabCondition(getWeigherCondition());
							}
							DataFields.setWeight(getWeight());

						}

						Thread.sleep(300);
					}
					;

				} catch (SerialPortException e) {
					e.printStackTrace();
				} catch (SerialPortTimeoutException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
