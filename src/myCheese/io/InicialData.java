package myCheese.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class InicialData {

	private FileInputStream fis;
	private Properties property = new Properties();
	private String dstDocx;
	private String tempDocxName;
	private String quantityPrintShippingLabel;
	private String weigherType;
	private String serialPortName;
	private String conditionAPP;

	public InicialData() {
		try {
			fis = new FileInputStream("res\\config.properties");
			property.load(fis);
			this.dstDocx = property.getProperty("dstDocx");
			this.tempDocxName = property.getProperty("tempDocxName");
			this.quantityPrintShippingLabel = property.getProperty("quantityPrintShippingLabel");
			this.serialPortName = property.getProperty("serialPortName");
			this.weigherType = property.getProperty("weigherType");
			this.setConditionAPP(property.getProperty("conditionAPP"));

		} catch (IOException e) {
			System.err.println(" Error! config file not find!\n ОШИБКА: Файл свойств отсуствует!");
		}
	}

	public String getWeigherType() {

		return weigherType;
	}
	public String getSerialPortName() {

		return serialPortName;
	}

	public void setQuantityPrintShippingLabel(int q) {
		property.setProperty(quantityPrintShippingLabel, Integer.toString(q));
	}

	public int getQuantityPrintShippingLabel() {

		return Integer.parseInt(quantityPrintShippingLabel);
	}

	public void updateQuantityPrintShippingLabel() {
		this.quantityPrintShippingLabel = property.getProperty("quantityPrintShippingLabel");
	}

	public String getDstDocx() {
		return dstDocx;
	}

	public String getTempDocxName() {
		return tempDocxName;
	}

	public String getConditionAPP() {
		return conditionAPP;
	}

	public void setConditionAPP(String conditionAPP) {
		this.conditionAPP = conditionAPP;
	}

}