package myCheese.connectionDevice;

import java.awt.Toolkit;

import javax.swing.JOptionPane;

import jssc.SerialPort;
import jssc.SerialPortException;
import myCheese.io.InicialData;


public abstract class COMPort {

	protected SerialPort serialPort;

	COMPort() {
		
		try {
			serialPort = new SerialPort(new InicialData().getSerialPortName()); //inicialization COM1 ... COM3 .. 
			serialPort.openPort();
			serialPort.setParams(SerialPort.BAUDRATE_9600, 
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
			// serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
		} catch (SerialPortException ex) {
			new Thread(new Runnable() {
				public void run() {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog( null, "Отсутствует либо занят другим процессом параллельный порт " + new InicialData().getSerialPortName() , "Ошибка!", JOptionPane.ERROR_MESSAGE ); 
				}
			}).start();
		}
	}

}
