package myCheese.io;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class DataFields {

	private static int quantityItemsInBox;

	private static int weight;
	private static int grossMass;
	private static int numberPallet;
	private static int numberItem;
	private static int lotNumber;
	private static int quantityBoxes;
	private static int boxNumber;
	private static int weightBox;
	private static int weightFilm;
	private static int shelfLife;
	private static int radioButtonChosen;

	private static Date packagingDate; // дата упаковывания, фасовки
	private static Date productionDate; // дата изготовления
	private static Date outputDate; // дата выработки
	private static Date expirationDate; // дата срока годности

	private static String responsible;
	private static String customer;
	private static String employee;
	private static String shippingLabels;
	private static String consumerLabels;
	private static String nameItem;

	private static int netMass;
	private static int weightBoxAndFilms;
	private static int countUnits;

	private static boolean stab;
	
	private final static SimpleDateFormat dMy = new SimpleDateFormat("dd.MM.yyyy");
	private final static SimpleDateFormat iso8601DateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private final static SimpleDateFormat ymd = new SimpleDateFormat("yyMMdd");

	private static PropertyChangeSupport pcs = new PropertyChangeSupport(Object.class);

	 private final static List<PropertyChangeListener> listeners = new ArrayList<>();
	
	public static void addListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	public static void removeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}
	
	
	public static void addPropertyChangeListener(PropertyChangeListener listener) {
        listeners.add(listener);
    }

    private static void firePropertyChange(String property, Object oldValue, Object newValue) {
        for (PropertyChangeListener l : listeners) {
            l.propertyChange(new PropertyChangeEvent(property, property, oldValue, newValue));
        }
    }
	
	// private final SimpleDateFormat iso8601Date = new
	// SimpleDateFormat("dd-M-yyyy");

	public static String getBoxNumberString() {
		return String.valueOf(boxNumber);
	}

	public static int getBoxNumberInt() {
		return boxNumber;
	}

	public static String getConsumerLabels() {
		return consumerLabels;
	}

	public static String getCustomer() {
		return customer;
	}

	public static String getEmployee() {
		return employee;
	}

	public static String getGrossMassString() {
		return String.valueOf(grossMass);
	}

	public static String getGrossMassStringKg() {
		return String.format("%.3f",Float.valueOf(grossMass) / 1000);
	}

	public static int getGrossMassInt() {
		return grossMass;
	}

	public static String getNetMassString() {
		return String.valueOf(netMass);
	}

	public static String getNetMassStringKg() {

		return String.format("%.3f",Float.valueOf(netMass) / 1000);
	}

	public static int getNetMassInt() {
		return netMass;
	}

	public static String getLotNumberString() {
		return String.valueOf(lotNumber);
	}

	public static int getLotNumberInt() {
		return lotNumber;
	}

	public static String getNameItem() {
		return nameItem;
	}

	public static String getNumberItemString() {
		return String.valueOf(numberItem);
	}

	public static int getNumberItemInt() {
		return numberItem;
	}

	public static String getNumberPalletString() {
		return String.valueOf(numberPallet);
	}

	public static int getNumberPalletInt() {
		return numberPallet;
	}

	public static Date getExpirationDate() {
		return expirationDate;
	}

	public static String getExpirationDateDMY() {
		return dMy.format(expirationDate);
	}

	public static String getExpirationDateYMD() {
		return ymd.format(expirationDate);
	}

	public static String getExpirationDateISO8601() {
		return iso8601DateTime.format(expirationDate);
	}

	public static Date getOutputDate() {
		return outputDate;
	}

	public static String getOutputDateDMY() {
		return dMy.format(outputDate);
	}
	
	public static String getOutputDateYMD() {
		return ymd.format(expirationDate);
	}

	public static String getOutputDateISO8601() {
		return iso8601DateTime.format(outputDate);
	}

	public static Date getPackagingDate() {
		return packagingDate;
	}

	public static String getPackagingDateDMY() {
		return dMy.format(packagingDate);
	}

	public static String getPackagingDateISO8601() {
		return iso8601DateTime.format(packagingDate);
	}

	public static Date getProductionDate() {
		return productionDate;
	}

	public static String getProductionDateDMY() {
		return dMy.format(productionDate);
	}

	public static String getProductionDateYMD() {
		return ymd.format(productionDate);
	}

	public static String getProductionDateISO8601() {
		return iso8601DateTime.format(productionDate);
	}

	public static String getQuantityBoxesString() {
		return String.valueOf(quantityBoxes);
	}

	public static int getQuantityBoxesInt() {
		return quantityBoxes;
	}

	public static String getQuantityItemsInBoxString() {
		return String.valueOf(quantityItemsInBox);
	}

	public static int getQuantityItemsInBoxInt() {
		return quantityItemsInBox;
	}

	public static String getShelfLifeString() {
		return String.valueOf(shelfLife);
	}

	public static int getShelfLifeInt() {
		return shelfLife;
	}

	public static String getResponsible() {
		return responsible;
	}

	public static String getShippingLabels() {
		return shippingLabels;
	}

	public static String getWeightString() {
		return String.valueOf(weight);
	}

	public static String getWeightKg() {
		return String.format("%.3f",Float.valueOf(weight) / 1000);
	}

	public static int getWeightInt() {
		return weight;
	}

	public static String getWeightBoxString() {
		return String.valueOf(weightBox);
	}

	public static String getWeightBoxKg() {
		return String.format("%.3f",Float.valueOf(weightBox) / 1000);
	}

	public static int getWeightBoxInt() {
		return weightBox;
	}

	public static String getWeightFilmString() {
		return String.valueOf(weightFilm);
	}

	public static int getWeightFilmInt() {
		return weightFilm;
	}
	
	public static String getWeightBoxAndFilmsKg() {
		return String.format("%.3f",Float.valueOf(weightBoxAndFilms) / 1000);
	}
	
	public static String getWeightBoxAndFilmsString() {
		return String.valueOf(weightBoxAndFilms);
	}

	public static int getWeightBoxAndFilmsInt() {
		return weightBoxAndFilms;
	}

	public static int getRadioButtonChoser() {
		return radioButtonChosen;
	}

	public static boolean getStabCondition() {
		return stab;
	}
	
	public static String getCountUnitsString() {
		return String.valueOf(countUnits);
	}
	
	public static int getCountUnits() {
		return countUnits;
	}

	// _______________________________________________________________________________
	// Setters

	public static void setBoxNumber(String boxNumber) {
		DataFields.boxNumber = Integer.parseInt(boxNumber);
	}

	public static void setBoxNumber(int boxNumber) {
		DataFields.boxNumber = boxNumber;
	}

	public static void setConsumerLabels(String consumerLabels) {
		DataFields.consumerLabels = consumerLabels;
	}

	public static void setCustomer(String customer) {
		DataFields.customer = customer;
	}

	public static void setEmployee(String employee) {
		DataFields.employee = employee;
	}

	public static void setExpirationDate(Date date) {
		DataFields.expirationDate = date;
	}

	public static void setExpirationDate(String date) {
		try {
			DataFields.expirationDate = iso8601DateTime.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static void setGrossMass(String grossMass) {
		DataFields.grossMass = Integer.parseInt(grossMass);
	}

	public static void setGrossMass(int grossMass) {
		DataFields.grossMass = grossMass;
	}

	public static void setNetMass(String netMass) {
		DataFields.netMass = Integer.parseInt(netMass);
	}

	public static void setNetMass(int netMass) {
		DataFields.netMass = netMass;
	}

	public static void setLotNumber(String lotNumber) {
		DataFields.lotNumber = Integer.parseInt(lotNumber);
	}

	public static void setLotNumber(int lotNumber) {
		DataFields.lotNumber = lotNumber;
	}

	public static void setNameItem(String nameItem) {
		DataFields.nameItem = nameItem;
	}

	public static void setNumberItem(String numberItem) {
		DataFields.numberItem = Integer.parseInt(numberItem);
	}

	public static void setNumberItem(int numberItem) {
		DataFields.numberItem = numberItem;
	}

	public static void setNumberPallet(String numberPallet) {
		DataFields.numberPallet = Integer.parseInt(numberPallet);
	}

	public static void setNumberPallet(int numberPallet) {
		DataFields.numberPallet = numberPallet;
	}

	public static void setOutputDate(Date date) {
		DataFields.outputDate = date;
	}

	public static void setOutputDate(String date) {
		try {
			DataFields.outputDate = iso8601DateTime.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static void setPackagingDate(Date date) {
		DataFields.packagingDate = date;
	}

	public static void setPackagingDate(String date) {
		try {
			DataFields.packagingDate = iso8601DateTime.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static void setProductionDate(Date date) {
		DataFields.productionDate = date;
	}

	public static void setProductionDate(String date) {
		try {
			DataFields.productionDate = iso8601DateTime.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static void setQuantityBoxes(String quantityBoxes) {
		DataFields.quantityBoxes = Integer.parseInt(quantityBoxes);
	}

	public static void setQuantityBoxes(int quantityBoxes) {
		DataFields.quantityBoxes = quantityBoxes;
	}

	public static void setQuantityItemsInBox(String quantityItemsInBox) {
		DataFields.quantityItemsInBox = Integer.parseInt(quantityItemsInBox);
	}

	public static void setQuantityItemsInBox(int quantityItemsInBox) {
		DataFields.quantityItemsInBox = quantityItemsInBox;
	}

	public static void setResponsible(String responsible) {
		DataFields.responsible = responsible;
	}

	public static void setShelfLife(String shelfLife) {
		DataFields.shelfLife = Integer.parseInt(shelfLife);
	}

	public static void setShelfLife(int shelfLife) {
		DataFields.shelfLife = shelfLife;
	}

	public static void setShippingLabels(String shippingLabels) {
		DataFields.shippingLabels = shippingLabels;
	}

	public static void setWeight(String weight) {
		int newValue = Integer.parseInt(weight.trim(), 10);
		int oldValue = DataFields.weight;
		if (oldValue != newValue - DataFields.weightFilm) {
			DataFields.weight = newValue - DataFields.weightFilm;
			firePropertyChange("weight", oldValue, newValue);
		}
			
		
	}

	public static void setWeight(int weight) {
	
		DataFields.weight = weight;
	}

	public static void setWeightBox(String weightBox) {
		DataFields.weightBox = Integer.parseInt(weightBox);
	}

	public static void setWeightBox(int weightBox) {
		DataFields.weightBox = weightBox;
	}

	public static void setWeightFilm(String weightFilm) {
		DataFields.weightFilm = Integer.parseInt(weightFilm);
	}

	public static void setWeightFilm(int weightFilm) {
		DataFields.weightFilm = weightFilm;
	}
	
	public static void setWeightBoxAndFilms(String weightBoxAndFilms) {
		DataFields.weightBoxAndFilms = Integer.parseInt(weightBoxAndFilms);
	}

	public static void setRadioButtonChoser(int radioButtonChosen) {
		DataFields.radioButtonChosen = radioButtonChosen;
	}

	public static void setRadioButtonChoser(String radioButtonChosen) {
		DataFields.radioButtonChosen = Integer.parseInt(radioButtonChosen);
	}

	public static void setStabCondition(boolean b) {
		boolean oldValue = DataFields.stab;
		DataFields.stab = b;
		firePropertyChange("stab", oldValue, b);
	}

	public static void setCountUnit(String countUnits) {
		DataFields.countUnits = Integer.parseInt(countUnits);
	}
	
	public static void setCountUnit(int countUnits) {
		DataFields.countUnits = countUnits;
	}

}
